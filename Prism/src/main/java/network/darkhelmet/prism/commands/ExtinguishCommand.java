package network.darkhelmet.prism.commands;

import network.darkhelmet.prism.Il8nHelper;
import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.api.BlockStateChange;
import network.darkhelmet.prism.commandlibs.CallInfo;
import network.darkhelmet.prism.commandlibs.SubHandler;
import network.darkhelmet.prism.events.EventHelper;
import network.darkhelmet.prism.events.PrismExtinguishEvent;
import network.darkhelmet.prism.utils.TypeUtils;
import network.darkhelmet.prism.utils.block.Utilities;
import net.kyori.adventure.identity.Identity;

import java.util.ArrayList;
import java.util.List;

public class ExtinguishCommand implements SubHandler {
    private final Prism plugin;

    /**
     * Constructor.
     * 
     * @param plugin Prism
     */
    public ExtinguishCommand(Prism plugin) {
        this.plugin = plugin;
    }

    /**
     * Handle the command.
     */
    @Override
    public void handle(CallInfo call) {
        int radius = plugin.getConfig().getInt("prism.ex.default-radius");

        if (call.getArgs().length == 2) {
            if (TypeUtils.isNumeric(call.getArg(1))) {
                final int _tmp_radius = Integer.parseInt(call.getArg(1));

                if (_tmp_radius > 0) {
                    if (_tmp_radius > plugin.getConfig().getInt("prism.ex.max-radius")) {
                        Prism.messenger.sendMessage(call.getPlayer(),
                                Prism.messenger.playerError(Il8nHelper.getMessage("radius-max")));
                        return;
                    } else {
                        radius = _tmp_radius;
                    }
                } else {
                    Prism.messenger.sendMessage(call.getPlayer(),
                            Prism.messenger.playerError(Il8nHelper.getMessage("radius-small")));
                    return;
                }
            } else {
                Prism.messenger.sendMessage(call.getPlayer(), Prism.messenger.playerError(
                        Il8nHelper.getMessage("radius-not-numeric")));
                return;
            }
        }

        final ArrayList<BlockStateChange> blockStateChanges = Utilities.extinguish(call.getPlayer().getLocation(),
                radius);

        if (!blockStateChanges.isEmpty()) {
            Prism.messenger.sendMessage(call.getPlayer(),
                    Prism.messenger.playerHeaderMsg(Il8nHelper.getMessage("fire-extinguished-sucess")));

            // Trigger the event
            final PrismExtinguishEvent event = EventHelper.createExtinguishEvent(blockStateChanges, call.getPlayer(),
                    radius);

            plugin.getServer().getPluginManager().callEvent(event);
        } else {
            Prism.getAudiences().player(call.getPlayer())
                    .sendMessage(Identity.nil(),
                            Prism.messenger.playerError(Il8nHelper.getMessage("no-fires-found")));
        }
    }

    @Override
    public List<String> handleComplete(CallInfo call) {
        return null;
    }

    @Override
    public String[] getHelp() {
        return new String[] { Il8nHelper.getRawMessage("help-extinguish-radius") };
    }

    @Override
    public String getRef() {
        return "/extinguish.html";
    }
}