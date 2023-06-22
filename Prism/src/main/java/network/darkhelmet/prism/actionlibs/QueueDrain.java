package network.darkhelmet.prism.actionlibs;

import network.darkhelmet.prism.Prism;

import java.util.Timer;
import java.util.TimerTask;

public class QueueDrain {
    private static boolean draining = false;

    private final Prism plugin;

    public static boolean isDraining() {
        return draining;
    }

    /**
     * Creat a drain.
     *
     * @param plugin Prism.
     */
    public QueueDrain(Prism plugin) {
        this.plugin = plugin;
    }

    /**
     * Drain the queue.
     */
    public void forceDrainQueue() {
        Prism.log("Forcing recorder queue to run a new batch before shutdown...");

        draining = true;
        RecordingManager.failedDbConnectionCount = 0;

        final RecordingTask recorderTask = new RecordingTask(plugin);

        RecordingTask.setActionsPerInsert(512);
        Prism.getInstance().getConfig().set("prism.query.max-failures-before-wait", 10);
        Prism.getInstance().getConfig().set("prism.query.queue-empty-tick-delay", 0);

        if (!RecordingQueue.getQueue().isEmpty()) {
            Prism.log("Starting drain batch...");

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    int size = RecordingQueue.getQueueSize();

                    Prism.log("Current queue size: " + size);

                    if (size == 0) {
                        timer.cancel();
                    }
                }
            };

            timer.schedule(task, 0, 5000);
        }

        // Force queue to empty
        while (!RecordingQueue.getQueue().isEmpty()) {
            if (Prism.getPrismDataSource().isPaused()) {
                Prism.getPrismDataSource().setPaused(false);
                Prism.log("Force unpaused the recorder for drain.");
            }

            // run insert
            try {
                recorderTask.insertActionsIntoDatabase();
            } catch (final Exception e) {
                e.printStackTrace();
                Prism.log("Stopping queue drain due to caught exception. Queue items lost: "
                        + RecordingQueue.getQueueSize());
                break;
            }

            if (RecordingManager.failedDbConnectionCount > 0) {
                Prism.log("Stopping queue drain due to detected database error. Queue items lost: "
                        + RecordingQueue.getQueueSize());
            }
        }
    }
}