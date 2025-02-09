package network.darkhelmet.prism.bridge;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import network.darkhelmet.prism.actionlibs.QueryParameters;
import network.darkhelmet.prism.api.actions.PrismProcessType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class WorldEditBridge {
    /**
     * Worldedit bridge.
     *
     * @param plugin     Prism
     * @param player     Player
     * @param parameters {@link QueryParameters}
     * @return boolean.
     */
    public static boolean getSelectedArea(Plugin plugin, Player player, QueryParameters parameters) {
        // Get selected area
        Region region = null;

        try {
            final BukkitPlayer lp = BukkitAdapter.adapt(player);
            final World lw = lp.getWorld();

            LocalSession session = WorldEdit.getInstance().getSessionManager().getIfPresent(lp);

            if (session != null) {
                region = session.getSelection(lw);
            }

            if (region == null) {
                return false;
            }

            final Vector minLoc = new Vector(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(),
                    region.getMinimumPoint().getZ());
            final Vector maxLoc = new Vector(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(),
                    region.getMaximumPoint().getZ());
            final Region sel = session.getRegionSelector(lw).getRegion();
            final double lRadius = ((float) sel.getLength() + 1) / 2;
            final double wRadius = ((float) sel.getWidth() + 1) / 2;
            final double hRadius = ((float) sel.getHeight() + 1) / 2;

            String procType = "applier";

            if (parameters.getProcessType().equals(PrismProcessType.LOOKUP)) {
                procType = "lookup";
            }

            final int maxRadius = plugin.getConfig().getInt("prism.queries.max-" + procType + "-radius");

            if (maxRadius != 0 && (lRadius > maxRadius || wRadius > maxRadius || hRadius > maxRadius)
                    && !player.hasPermission("prism.override-max-" + procType + "-radius")) {
                return false;
            } else {
                parameters.setWorld(region.getWorld().getName());
                parameters.setMinLocation(minLoc);
                parameters.setMaxLocation(maxLoc);
            }
        } catch (final IncompleteRegionException e) {
            return false;
        }

        return true;
    }
}
