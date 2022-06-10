package network.darkhelmet.prism.actions;

import network.darkhelmet.prism.api.ChangeResult;
import network.darkhelmet.prism.api.ChangeResultType;
import network.darkhelmet.prism.api.PrismParameters;
import network.darkhelmet.prism.appliers.ChangeResultImpl;
import org.bukkit.entity.Boat;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;

public class VehicleAction extends GenericAction {
    private String vehicleName;

    /**
     * Set the vehicle.
     * @param vehicle Entity
     */
    public void setVehicle(Entity vehicle) {

        if (vehicle instanceof PoweredMinecart) {
            vehicleName = "powered minecart";
        } else if (vehicle instanceof HopperMinecart) {
            vehicleName = "minecart hopper";
        } else if (vehicle instanceof SpawnerMinecart) {
            vehicleName = "spawner minecart";
        } else if (vehicle instanceof ExplosiveMinecart) {
            vehicleName = "tnt minecart";
        } else if (vehicle instanceof StorageMinecart) {
            vehicleName = "storage minecart";
        } else if (vehicle instanceof ChestBoat) {
            vehicleName = "chest boat";
        } else {
            vehicleName = vehicle.getType().name().toLowerCase();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNiceName() {
        return vehicleName;
    }

    @Override
    public boolean hasExtraData() {
        return vehicleName != null;
    }

    @Override
    public String serialize() {
        return vehicleName;
    }

    @Override
    public void deserialize(String data) {
        vehicleName = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeResult applyRollback(Player player, PrismParameters parameters, boolean isPreview) {
        Entity vehicle = null;
        switch (vehicleName) {
            case "command block minecart":
                vehicle = getWorld().spawn(getLoc(), CommandMinecart.class);
                break;
            case "powered minecart":
                vehicle = getWorld().spawn(getLoc(), PoweredMinecart.class);
                break;
            case "storage minecart":
                vehicle = getWorld().spawn(getLoc(), StorageMinecart.class);
                break;
            case "tnt minecart":
                vehicle = getWorld().spawn(getLoc(), ExplosiveMinecart.class);
                break;
            case "spawner minecart":
                vehicle = getWorld().spawn(getLoc(), SpawnerMinecart.class);
                break;
            case "minecart hopper":
                vehicle = getWorld().spawn(getLoc(), HopperMinecart.class);
                break;
            case "minecart":
                vehicle = getWorld().spawn(getLoc(), Minecart.class);
                break;
            case "boat":
                vehicle = getWorld().spawn(getLoc(), Boat.class);
                break;
            case "chest boat":
                vehicle = getWorld().spawn(getLoc(), ChestBoat.class);
                break;
            default:
                //null
        }
        if (vehicle != null) {
            return new ChangeResultImpl(ChangeResultType.APPLIED, null);
        }
        return new ChangeResultImpl(ChangeResultType.SKIPPED, null);
    }
}
