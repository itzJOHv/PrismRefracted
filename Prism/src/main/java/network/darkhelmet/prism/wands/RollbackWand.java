package network.darkhelmet.prism.wands;

import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.actionlibs.QueryParameters;
import network.darkhelmet.prism.actionlibs.QueryResult;
import network.darkhelmet.prism.api.actions.PrismProcessType;
import network.darkhelmet.prism.appliers.PrismApplierCallback;
import network.darkhelmet.prism.appliers.Rollback;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RollbackWand extends QueryWandBase {
    /**
     * Constructor.
     * 
     * @param plugin Prism
     */
    public RollbackWand(Prism plugin) {
        super(plugin);
    }

    @Override
    public void playerLeftClick(Player player, Location loc) {
        if (loc != null) {
            rollback(player, loc);
        }
    }

    @Override
    public void playerRightClick(Player player, Location loc) {
        if (loc != null) {
            rollback(player, loc);
        }
    }

    @Override
    public void playerRightClick(Player player, Entity entity) {
    }

    protected void rollback(Player player, Location loc) {

        final Block block = loc.getBlock();
        QueryParameters params = checkQueryParams(block, parameters, player);
        params.setProcessType(PrismProcessType.ROLLBACK);
        final QueryResult results = getResult(params, player);
        if (!results.getActionResults().isEmpty()) {
            final Rollback rb = new Rollback(plugin, player, results.getActionResults(), params,
                    new PrismApplierCallback());
            rb.apply();
        } else {
            final String space_name = (block.getType().equals(Material.AIR) ? "space"
                    : block.getType().toString().replaceAll("_", " ").toLowerCase()
                            + (block.getType().toString().endsWith("BLOCK") ? "" : " block"));
            Prism.messenger.sendMessage(player,
                    Prism.messenger.playerError("Nothing to rollback for this " + space_name + " found."));
        }
    }

    @Override
    public void setItemWasGiven(boolean given) {
        this.itemGiven = given;
    }

    @Override
    public boolean itemWasGiven() {
        return itemGiven;
    }
}