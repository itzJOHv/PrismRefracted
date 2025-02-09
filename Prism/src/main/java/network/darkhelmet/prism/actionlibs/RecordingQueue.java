package network.darkhelmet.prism.actionlibs;

import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.api.actions.Handler;

import java.util.concurrent.LinkedBlockingQueue;

public class RecordingQueue {
    private static final LinkedBlockingQueue<Handler> queue = new LinkedBlockingQueue<>();

    public static int getQueueSize() {
        return queue.size();
    }

    /**
     * Add a handler to the queue.
     *
     * @param a handler to add
     */
    public static void addToQueue(final Handler a) {
        if (a == null) {
            return;
        }

        if (a.getSourceName() == null || a.getSourceName().trim().isEmpty()) {
            Prism.debug("Handler dropped Source empty - " + a.toString());
            return;
        }

        queue.add(a);
    }

    /**
     * Get the Queue.
     *
     * @return the queue
     */
    public static LinkedBlockingQueue<Handler> getQueue() {
        return queue;
    }
}