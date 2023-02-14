package network.darkhelmet.prism.actionlibs;

import network.darkhelmet.prism.actions.BlockAction;
import network.darkhelmet.prism.actions.BlockChangeAction;
import network.darkhelmet.prism.actions.BlockFallAction;
import network.darkhelmet.prism.actions.BlockShiftAction;
import network.darkhelmet.prism.actions.EntityAction;
import network.darkhelmet.prism.actions.EntityTravelAction;
import network.darkhelmet.prism.actions.GrowAction;
import network.darkhelmet.prism.actions.HangingItemAction;
import network.darkhelmet.prism.actions.ItemStackAction;
import network.darkhelmet.prism.actions.PlayerAction;
import network.darkhelmet.prism.actions.PlayerDeathAction;
import network.darkhelmet.prism.actions.PortalCreateAction;
import network.darkhelmet.prism.actions.PrismProcessAction;
import network.darkhelmet.prism.actions.PrismRollbackAction;
import network.darkhelmet.prism.actions.SignAction;
import network.darkhelmet.prism.actions.UseAction;
import network.darkhelmet.prism.actions.VehicleAction;
import network.darkhelmet.prism.api.actions.Handler;
import network.darkhelmet.prism.api.actions.PrismProcessType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.Map;

public class ActionFactory {
    /**
     * BlockAction.
     *
     * @param actionType the action
     * @param block      the block
     * @param player     Offline Player
     */
    public static Handler createBlock(String actionType, Block block, OfflinePlayer player) {
        final BlockAction a = new BlockAction();
        a.setActionType(actionType);
        a.setBlock(block);
        a.setPlayer(player);
        return a;
    }

    /**
     * Create Handler.
     *
     * @param actionType type
     * @param block      block
     * @param nonPlayer  not a player
     * @return Handler
     */
    public static Handler createBlock(String actionType, Block block, String nonPlayer) {
        final Handler a = createBlock(actionType, block, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     * BlockAction.
     *
     * @param actionType the action
     * @param state      the block state
     * @param player     Offline Player
     */
    @SuppressWarnings("WeakerAccess")
    public static Handler createBlock(String actionType, BlockState state, OfflinePlayer player) {
        final BlockAction a = new BlockAction();
        a.setActionType(actionType);
        a.setBlock(state);
        a.setPlayer(player);
        return a;
    }

    /**
     * Create Handler.
     *
     * @param actionType type
     * @param block      block
     * @param nonPlayer  not a player
     * @return Handler
     */
    public static Handler createBlock(String actionType, BlockState block, String nonPlayer) {
        final Handler a = createBlock(actionType, block, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     * PlayerBucketEmptyEvent | WorldEditAction.
     *
     * @param actionType the action
     * @param player     Offline Player
     */
    public static Handler createBlockChange(String actionType, Location loc, Material oldMat, BlockData oldData,
            Material newMat, BlockData newData, OfflinePlayer player) {
        final BlockChangeAction a = new BlockChangeAction();
        a.setActionType(actionType);
        a.setMaterial(newMat);
        a.setBlockData(newData);
        a.setOldMaterial(oldMat);
        a.setOldBlockData(oldData);
        a.setPlayer(player);
        a.setLoc(loc);
        return a;
    }

    /**
     * BlockPlaceEvent | EntityBlockFormEvent.
     *
     * @param actionType type
     * @param oldMat     old
     * @param oldData    old data
     * @param newState   new state
     * @param player     Offline Player
     * @return Handler.
     */
    public static Handler createBlockChange(String actionType, Material oldMat, BlockData oldData,
            BlockState newState, OfflinePlayer player) {
        final BlockChangeAction a = new BlockChangeAction();
        a.setActionType(actionType);
        a.setBlock(newState);
        a.setOldMaterial(oldMat);
        a.setOldBlockData(oldData);
        a.setPlayer(player);
        return a;
    }

    /**
     * Handles Spread, Fade and Form events.
     *
     * @param actionType type
     * @param oldMat     old
     * @param oldData    old data
     * @param newState   new state
     * @param nonPlayer  nonplayer.
     * @return Handler.
     */
    public static Handler createBlockChange(String actionType, Material oldMat, BlockData oldData,
            BlockState newState, String nonPlayer) {
        final Handler a = createBlockChange(actionType, oldMat, oldData, newState, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     *
     * @param actionType type
     * @param loc        Location
     * @param oldMat     old
     * @param oldData    old data
     * @param newMat     new
     * @param newData    new data
     * @param nonPlayer  nonplayer.
     * @return Handler.
     */
    public static Handler createBlockChange(String actionType, Location loc, Material oldMat, BlockData oldData,
            Material newMat, BlockData newData, String nonPlayer) {
        final Handler a = createBlockChange(actionType, loc, oldMat, oldData, newMat, newData, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     * BlockShiftAction.
     *
     * @param actionType the action
     */
    public static Handler createBlockShift(String actionType, Block from, Location to, String nonPlayer) {
        final BlockShiftAction a = new BlockShiftAction();
        a.setActionType(actionType);
        a.setBlock(from);
        a.setSourceName(nonPlayer);
        a.setLoc(to);
        return a;
    }

    /**
     * BlockFallAction.
     *
     * @param actionType the action.
     * @param from       null if block starts to fall.
     */
    public static Handler createBlockFall(String actionType, Material material, Material oldMaterial, Location from,
            Location to, String nonPlayer) {
        final BlockFallAction a = new BlockFallAction();
        a.setActionType(actionType);
        a.setSourceName(nonPlayer);
        a.setMaterial(material);
        a.setOldMaterial(oldMaterial);
        a.setFrom(from);
        a.setLoc(to);
        return a;
    }

    /**
     * EntityAction.
     *
     * @param actionType the action
     * @param player     the acting player
     */
    public static Handler createEntity(String actionType, Entity entity, OfflinePlayer player) {
        return ActionFactory.createEntity(actionType, entity, player, null);
    }

    /**
     * Entity Handler.
     *
     * @param actionType type
     * @param entity     entity
     * @param nonPlayer  not a player
     * @return Handler
     */
    public static Handler createEntity(String actionType, Entity entity, String nonPlayer) {
        return ActionFactory.createEntity(actionType, entity, nonPlayer, null);
    }

    /**
     * Entity Handler.
     *
     * @param actionType type
     * @param entity     entity
     * @param player     player
     * @param dyeUsed    string
     * @return Handler
     */
    public static Handler createEntity(String actionType, Entity entity, OfflinePlayer player, String dyeUsed) {
        final EntityAction a = new EntityAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setEntity(entity, dyeUsed);
        return a;
    }

    /**
     * Entity Handler.
     *
     * @param actionType type
     * @param entity     entity
     * @param nonPlayer  nonPlayer
     * @param dyeUsed    string
     * @return Handler
     */
    @SuppressWarnings("WeakerAccess")
    public static Handler createEntity(String actionType, Entity entity, String nonPlayer, String dyeUsed) {
        final Handler a = createEntity(actionType, entity, (OfflinePlayer) null, dyeUsed);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     * EntityTravel.
     *
     * @param actionType type
     * @param entity     entity
     * @param from       location
     * @param to         location
     * @param cause      Teleport cause
     * @return Handler
     */
    public static Handler createEntityTravel(String actionType, Entity entity, Location from, Location to,
            TeleportCause cause) {
        final EntityTravelAction a = new EntityTravelAction();
        a.setEntity(entity);
        a.setActionType(actionType);
        a.setLoc(from);
        a.setToLocation(to);
        a.setCause(cause);
        return a;
    }

    /**
     * GrowAction.
     *
     * @param actionType the action
     * @param player     the player
     */
    public static Handler createGrow(String actionType, BlockState blockstate, OfflinePlayer player) {
        final GrowAction a = new GrowAction();
        a.setActionType(actionType);
        a.setBlock(blockstate);
        a.setPlayer(player);
        return a;
    }

    /**
     * GrowHandler.
     *
     * @param actionType type
     * @param blockstate state
     * @param nonPlayer  nonplayer
     * @return Handler
     */
    public static Handler createGrow(String actionType, BlockState blockstate, String nonPlayer) {
        final Handler a = createGrow(actionType, blockstate, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     * PortalCreateAction.
     *
     * @param actionType the action
     * @param player     the player
     */
    public static Handler createPortal(String actionType, BlockState newBlockState, BlockState oldBlockState,
            OfflinePlayer player) {
        final PortalCreateAction a = new PortalCreateAction();
        a.setActionType(actionType);
        a.setLocation(newBlockState.getLocation());
        a.setNewBlock(newBlockState);
        a.setOldBlock(oldBlockState);
        a.setPlayer(player);
        return a;
    }

    /**
     * PortalCreateHandler.
     *
     * @param actionType    type
     * @param newBlockState new state
     * @param oldBlockState old state
     * @param nonPlayer     nonplayer
     * @return Handler
     */
    public static Handler createPortal(String actionType, BlockState newBlockState, BlockState oldBlockState,
            String nonPlayer) {
        final Handler a = createPortal(actionType, newBlockState, oldBlockState, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     * HangingItemAction.
     *
     * @param actionType type
     * @param hanging    hanging
     * @param player     player
     * @return Handler
     */
    public static Handler createHangingItem(String actionType, Hanging hanging, OfflinePlayer player) {
        final HangingItemAction a = new HangingItemAction();
        a.setActionType(actionType);
        a.setHanging(hanging);
        a.setPlayer(player);
        return a;
    }

    /**
     * HangingItemAction.
     *
     * @param actionType type
     * @param hanging    hanging
     * @param nonPlayer  nonPlayer
     * @return Handler
     */
    public static Handler createHangingItem(String actionType, Hanging hanging, String nonPlayer) {
        final Handler a = createHangingItem(actionType, hanging, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }

    /**
     * ItemStack Handler for item enchanting.
     *
     * @param actionType   String
     * @param item         Item
     * @param enchantments map
     * @param loc          Location
     * @param player       OfflinePlayer
     * @return handler
     */
    public static Handler createItemStack(String actionType, ItemStack item, Map<Enchantment, Integer> enchantments,
            Location loc, OfflinePlayer player) {
        return ActionFactory.createItemStack(actionType, item, 1, -1, enchantments, loc, player);
    }

    /**
     * ItemStack Handler.
     *
     * @param actionType   String
     * @param item         Item
     * @param quantity     int
     * @param slot         int
     * @param enchantments map
     * @param loc          Location
     * @param player       Player
     * @return handler
     */
    public static Handler createItemStack(String actionType, ItemStack item, int quantity, int slot,
            Map<Enchantment, Integer> enchantments, Location loc, OfflinePlayer player) {
        final ItemStackAction a = createItemStack(actionType, item, quantity, enchantments, loc, player);
        a.setSlot(String.valueOf(slot));

        return a;
    }

    /**
     * Handles a block being dropped or dispensed.
     *
     * @param actionType   type
     * @param item         item
     * @param quantity     qty
     * @param slot         slot it was in
     * @param enchantments Map of enchants
     * @param loc          Location
     * @param sourceName   Source of the item.
     * @return Handler
     */
    public static Handler createItemStack(String actionType, ItemStack item, int quantity, int slot,
            Map<Enchantment, Integer> enchantments,
            Location loc, String sourceName) {
        final ItemStackAction a = new ItemStackAction();
        a.setActionType(actionType);
        a.setLoc(loc);
        a.setSourceName(sourceName);
        a.setItem(item, quantity, enchantments);
        a.setSlot(String.valueOf(slot));
        return a;
    }

    /**
     * Handles Item being removed or inserted.
     *
     * @param actionType   type
     * @param item         item
     * @param quantity     qty
     * @param slot         slot of inventory
     * @param enchantments map
     * @param loc          Location
     * @param player       Player
     * @return handler
     */
    public static Handler createItemStack(String actionType, ItemStack item, int quantity, EquipmentSlot slot,
            Map<Enchantment, Integer> enchantments, Location loc, OfflinePlayer player) {
        final ItemStackAction a = createItemStack(actionType, item, quantity, enchantments, loc, player);
        a.setSlot(slot.name().toLowerCase(Locale.ENGLISH));

        return a;
    }

    private static ItemStackAction createItemStack(String actionType, ItemStack item, int quantity,
            Map<Enchantment, Integer> enchantments,
            Location loc, OfflinePlayer player) {
        final ItemStackAction a = new ItemStackAction();
        a.setActionType(actionType);
        a.setLoc(loc);
        a.setPlayer(player);
        a.setItem(item, quantity, enchantments);
        return a;
    }

    /**
     * ItemFrame Handler.
     *
     * @param actionType   String
     * @param item         Item
     * @param quantity     int
     * @param attachedFace BlockFace
     * @param enchantments map
     * @param loc          Location
     * @param player       OfflinePlayer
     * @return handler
     */
    public static Handler createItemFrame(String actionType, ItemStack item, int quantity, BlockFace attachedFace,
            Map<Enchantment, Integer> enchantments, Location loc, OfflinePlayer player) {
        final ItemStackAction a = createItemStack(actionType, item, quantity, enchantments, loc, player);
        a.setSlot(attachedFace.name().toLowerCase(Locale.ENGLISH));
        return a;
    }

    /**
     * PlayerAction.
     *
     * @param actionType     the action
     * @param player         the acting player
     * @param additionalInfo more info
     **/
    public static Handler createPlayer(String actionType, Player player, String additionalInfo) {
        final PlayerAction a = new PlayerAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setLoc(player.getLocation());
        a.deserialize(additionalInfo);
        return a;
    }

    /**
     * PlayerDeathAction.
     *
     * @param actionType the action
     * @param player     the acting player
     * @param cause      Cause of death
     * @param attacker   attacker name
     */
    public static Handler createPlayerDeath(String actionType, Player player, String cause, String attacker) {
        final PlayerDeathAction a = new PlayerDeathAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setLoc(player.getLocation());
        a.setCause(cause);
        a.setAttacker(attacker);
        return a;
    }

    /**
     * PrismProcessActionData.
     *
     * @param actionType  type
     * @param processType process
     * @param player      player
     * @param parameters  parameters
     * @return Handler
     */
    public static Handler createPrismProcess(String actionType, PrismProcessType processType, Player player,
            String parameters) {
        final PrismProcessAction a = new PrismProcessAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setLoc(player.getLocation());
        a.setProcessData(processType, parameters);
        return a;
    }

    /**
     * PrismRollbackAction.
     *
     * @param actionType type
     * @param oldBlock   old
     * @param newBlock   new
     * @param player     player
     * @param parentId   id
     * @return Handler
     */
    public static Handler createPrismRollback(String actionType, BlockState oldBlock, BlockState newBlock,
            OfflinePlayer player, long parentId) {
        final PrismRollbackAction a = new PrismRollbackAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setLoc(oldBlock.getLocation());
        a.setBlockChange(oldBlock, newBlock, parentId);
        return a;
    }

    /**
     * SignAction.
     *
     * @param actionType the action
     * @param block      the block acted on
     * @param player     the acting player
     */
    public static Handler createSign(String actionType, Block block, String[] lines, OfflinePlayer player) {
        final SignAction a = new SignAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setBlock(block, lines);
        return a;
    }

    /**
     * UseAction.
     *
     * @param actionType the action
     * @param block      the block acted on
     * @param player     the acting player
     */
    public static Handler createUse(String actionType, Material item, Block block, OfflinePlayer player) {
        final UseAction a = new UseAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setLoc(block.getLocation());
        a.setMaterial(item);
        return a;
    }

    /**
     * VehicleAction.
     *
     * @param actionType the action
     * @param player     the acting player
     */
    public static Handler createVehicle(String actionType, Vehicle vehicle, OfflinePlayer player) {
        final VehicleAction a = new VehicleAction();
        a.setActionType(actionType);
        a.setPlayer(player);
        a.setLoc(vehicle.getLocation());
        a.setVehicle(vehicle);
        return a;
    }

    /**
     * Create a vehicle.
     *
     * @param actionType type
     * @param vehicle    vehicle
     * @param nonPlayer  nonplayer
     * @return Handler
     */
    public static Handler createVehicle(String actionType, Vehicle vehicle, String nonPlayer) {
        final Handler a = createVehicle(actionType, vehicle, (OfflinePlayer) null);
        a.setSourceName(nonPlayer);
        return a;
    }
}