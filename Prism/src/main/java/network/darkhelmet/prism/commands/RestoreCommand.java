package network.darkhelmet.prism.commands;

import network.darkhelmet.prism.Il8nHelper;
import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.actionlibs.ActionsQuery;
import network.darkhelmet.prism.actionlibs.QueryParameters;
import network.darkhelmet.prism.actionlibs.QueryResult;
import network.darkhelmet.prism.api.actions.PrismProcessType;
import network.darkhelmet.prism.appliers.Previewable;
import network.darkhelmet.prism.appliers.PrismApplierCallback;
import network.darkhelmet.prism.appliers.Restore;
import network.darkhelmet.prism.commandlibs.CallInfo;
import network.darkhelmet.prism.commandlibs.PreprocessArgs;
import network.darkhelmet.prism.text.ReplaceableTextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class RestoreCommand extends AbstractCommand {
    private final Prism plugin;

    public RestoreCommand(Prism plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(final CallInfo call) {
        final QueryParameters parameters = PreprocessArgs.process(plugin, call.getSender(), call.getArgs(),
                PrismProcessType.RESTORE, 1, !plugin.getConfig().getBoolean("prism.queries.never-use-defaults"));
        if (parameters == null) {
            return;
        }

        parameters.setProcessType(PrismProcessType.RESTORE);
        parameters.setStringFromRawArgs(call.getArgs(), 1);

        StringBuilder defaultsReminder = checkIfDefaultUsed(parameters);

        Prism.messenger.sendMessage(call.getSender(),
                Prism.messenger.playerSubduedHeaderMsg(ReplaceableTextComponent.builder("restore-prepare")
                        .replace("<defaults>", defaultsReminder).build()));

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            final ActionsQuery aq = new ActionsQuery(plugin);
            final QueryResult results = aq.lookup(parameters, call.getSender());

            if (!results.getActionResults().isEmpty()) {
                Prism.messenger.sendMessage(call.getSender(),
                        Prism.messenger.playerHeaderMsg(Il8nHelper.getMessage("restore-start")));

                // Inform nearby players
                if (call.getSender() instanceof Player) {
                    final Player player = (Player) call.getSender();

                    plugin.notifyNearby(player, parameters.getRadius(), ReplaceableTextComponent
                            .builder("block-changes-near")
                            .replace("<player>", player.getDisplayName())
                            .build());
                }

                // Perform restore on the main thread
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    final Previewable rs = new Restore(plugin, call.getSender(), results.getActionResults(),
                            parameters, new PrismApplierCallback());

                    rs.apply();
                });
            } else {
                Prism.messenger.sendMessage(call.getSender(),
                        Prism.messenger.playerError(Il8nHelper.getMessage("restore-error")));
            }
        });
    }

    @Override
    public List<String> handleComplete(CallInfo call) {
        return PreprocessArgs.complete(call.getSender(), call.getArgs());
    }

    @Override
    public String[] getHelp() {
        return new String[] { Il8nHelper.getRawMessage("help-restore") };
    }

    @Override
    public String getRef() {
        return "/restore.html";
    }
}