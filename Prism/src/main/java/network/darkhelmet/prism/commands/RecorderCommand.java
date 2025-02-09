package network.darkhelmet.prism.commands;

import network.darkhelmet.prism.Il8nHelper;
import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.commandlibs.CallInfo;

import java.sql.Connection;
import java.util.List;

public class RecorderCommand extends AbstractCommand {
    private final Prism plugin;

    /**
     * Constructor.
     * 
     * @param plugin Prism
     */
    public RecorderCommand(Prism plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(final CallInfo call) {
        if (call.getArgs().length <= 1) {
            Prism.messenger.sendMessage(call.getSender(),
                    Prism.messenger.playerError(Il8nHelper.getMessage("invalid-command")));
            return;
        }

        boolean recorderActive = checkRecorderActive(plugin);

        // Allow for canceling recorders
        if (call.getArg(1).equals("cancel")) {
            if (recorderActive) {
                plugin.recordingTask.cancel();
                plugin.recordingTask = null;
                Prism.messenger.sendMessage(call.getSender(), Prism.messenger
                        .playerMsg(Il8nHelper.getMessage("recorder-stopped")));
                Prism.messenger.sendMessage(call.getSender(), Prism.messenger
                        .playerError(Il8nHelper.getMessage("recorder-stopped-warn")));
            } else {
                Prism.messenger.sendMessage(call.getSender(),
                        Prism.messenger.playerError(Il8nHelper.getMessage("report-recorder-stopped")));
            }

            return;
        }

        // Allow for force-restarting recorders
        if (call.getArg(1).equals("start")) {
            if (recorderActive) {
                Prism.messenger.sendMessage(call.getSender(),
                        Prism.messenger.playerError(Il8nHelper.getMessage("report-already-running")));
            } else {
                // Run db tests...
                Prism.messenger.sendMessage(call.getSender(),
                        Prism.messenger.playerMsg(Il8nHelper.getMessage("database-validating")));

                try (Connection conn = Prism.getPrismDataSource().getConnection()) {
                    if (conn == null || conn.isClosed()) {
                        Prism.messenger.sendMessage(call.getSender(),
                                Prism.messenger.playerError(Il8nHelper.getMessage("no-valid-database")));
                        return;
                    }

                    Prism.messenger.sendMessage(call.getSender(),
                            Prism.messenger.playerSuccess(Il8nHelper.getMessage("pool-valid-connection")));
                    Prism.messenger.sendMessage(call.getSender(),
                            Prism.messenger.playerMsg(Il8nHelper.getMessage("recorder-restarting")));

                    plugin.actionRecorderTask();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<String> handleComplete(CallInfo call) {
        return null;
    }

    @Override
    public String[] getHelp() {
        return new String[] {
                Il8nHelper.getRawMessage("help-recorder-start"),
                Il8nHelper.getRawMessage("help-recorder-stop")
        };
    }

    @Override
    public String getRef() {
        return "/settings.html";
    }
}