package network.darkhelmet.prism.wands;

import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.actionlibs.QueryParameters;
import network.darkhelmet.prism.actionlibs.QueryResult;
import network.darkhelmet.prism.api.actions.PrismProcessType;
import network.darkhelmet.prism.appliers.Previewable;
import network.darkhelmet.prism.appliers.PrismApplierCallback;
import network.darkhelmet.prism.appliers.Restore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RestoreWand extends QueryWandBase {
    /**
     * Constructor.
     * 
     * @param plugin Prism
     */
    public RestoreWand(Prism plugin) {
        super(plugin);
    }

    @Override
    public void playerLeftClick(Player player, Location loc) {
        if (loc != null) {
            restore(player, loc);
        }
    }

    @Override
    public void playerRightClick(Player player, Location loc) {
        if (loc != null) {
            restore(player, loc);
        }
    }

    @Override
    public void playerRightClick(Player player, Entity entity) {
    }

    protected void restore(Player player, Location loc) {

        final Block block = loc.getBlock();
        QueryParameters params = checkQueryParams(block, parameters, player);
        params.setProcessType(PrismProcessType.RESTORE);
        final QueryResult results = getResult(params, player);
        if (!results.getActionResults().isEmpty()) {
            final Previewable rb = new Restore(plugin, player, results.getActionResults(), params,
                    new PrismApplierCallback());
            rb.apply();
        } else {
            final String space_name = (block.getType().equals(Material.AIR) ? "space"
                    : block.getType().toString().replaceAll("_", " ").toLowerCase()
                            + (block.getType().toString().endsWith("BLOCK") ? "" : " block"));
            Prism.messenger.sendMessage(player,
                    Prism.messenger.playerError("Nothing to restore for this " + space_name + " found."));
        }
    }

}
