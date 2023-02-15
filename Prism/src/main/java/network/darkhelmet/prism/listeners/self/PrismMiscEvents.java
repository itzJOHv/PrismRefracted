package network.darkhelmet.prism.listeners.self;

import network.darkhelmet.prism.actionlibs.ActionFactory;
import network.darkhelmet.prism.actionlibs.RecordingQueue;
import network.darkhelmet.prism.api.BlockStateChange;
import network.darkhelmet.prism.events.PrismDrainEvent;
import network.darkhelmet.prism.events.PrismExtinguishEvent;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class PrismMiscEvents implements Listener {

    /**
     * PrismDrainEvent.
     *
     * @param event PrismDrainEvent
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPrismBlocksDrainEvent(final PrismDrainEvent event) {
        // Get all block changes for this event
        final ArrayList<BlockStateChange> blockStateChanges = event.getBlockStateChanges();
        if (!blockStateChanges.isEmpty()) {
            for (final BlockStateChange stateChange : blockStateChanges) {
                final BlockState orig = stateChange.getOriginalBlock();
                final BlockState newBlock = stateChange.getNewBlock();

                // Build the action
                RecordingQueue.addToQueue(
                        ActionFactory.createPrismRollback("prism-drain", orig, newBlock, event.onBehalfOf()));
            }
            // ActionQueue.save();
        }
    }

    /**
     * PrismExtinguishEvent.
     * 
     * @param event PrismExtinguishEvent.
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPrismBlocksExtinguishEvent(final PrismExtinguishEvent event) {
        // Get all block changes for this event
        final ArrayList<BlockStateChange> blockStateChanges = event.getBlockStateChanges();
        if (!blockStateChanges.isEmpty()) {
            for (final BlockStateChange stateChange : blockStateChanges) {
                final BlockState orig = stateChange.getOriginalBlock();
                final BlockState newBlock = stateChange.getNewBlock();

                // Build the action
                RecordingQueue.addToQueue(
                        ActionFactory.createPrismRollback("prism-extinguish", orig, newBlock, event.onBehalfOf()));
            }
            // ActionQueue.save();
        }
    }
}