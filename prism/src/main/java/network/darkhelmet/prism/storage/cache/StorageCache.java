package network.darkhelmet.prism.storage.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import network.darkhelmet.prism.api.storage.cache.IStorageCache;
import network.darkhelmet.prism.api.storage.models.ActionModel;
import network.darkhelmet.prism.api.storage.models.WorldModel;

import org.bukkit.World;

public class StorageCache implements IStorageCache {
    /**
     * Cache the action models.
     */
    private final Map<String, ActionModel> actionModels = new HashMap<>();

    /**
     * Cache of world models by the world uuid.
     */
    private final Map<UUID, WorldModel> worldModels = new HashMap<>();

    @Override
    public void cacheActionModel(ActionModel actionModel) {
        actionModels.put(actionModel.key(), actionModel);
    }

    @Override
    public void cacheWorldModel(WorldModel worldModel) {
        worldModels.put(worldModel.worldUuid(), worldModel);
    }

    @Override
    public Optional<WorldModel> getWorldModel(World world) {
        return Optional.ofNullable(worldModels.get(world.getUID()));
    }
}
