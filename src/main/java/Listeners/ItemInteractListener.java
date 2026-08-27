package Listeners;

import Utils.CommandBuilder;
import Utils.ItemCreatorClass;
import Utils.MessageType;
import Utils.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemInteractListener implements Listener {

    // Item Interaction
    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        NBTHandler nbtHandler = new NBTHandler(item);

        if (item.getItemMeta() != null && nbtHandler.getCommand(1) != null) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR ||
                e.getAction() == Action.RIGHT_CLICK_BLOCK ||
                e.getAction() == Action.LEFT_CLICK_AIR ||
                e.getAction() == Action.LEFT_CLICK_BLOCK
            ) {
                e.setCancelled(true);
                handleInteract(item, p);
            }
        }
    }

    // Interaction with entity
    @EventHandler
    private void onEntityInteract(EntityInteractEvent e) {
        if (e.getEntity() instanceof Player p) {
            ItemStack item = p.getInventory().getItemInMainHand();
            NBTHandler nbtHandler = new NBTHandler(item);
            if (item.getItemMeta() != null && nbtHandler.getCommand(1) != null) {
                e.setCancelled(true);
                handleInteract(item, p);
            }
        }
    }

    /**
     * Handles the click interaction with an item for a player.
     * @param item The item to handle the interaction for.
     * @param p The player who made the interaction.
     */
    private void handleInteract(ItemStack item, Player p) {
        NBTHandler nbtHandler = new NBTHandler(item);
        if (nbtHandler.isOnCooldown()) {
            // If cooldown is still running, print it to the player
            double remainingCooldown = nbtHandler.getRemainingCooldown();
            if (remainingCooldown > 0) {
                String msg = nbtHandler.getMessage(MessageType.ON_COOLDOWN);
                if (msg != null) {
                    p.sendMessage(msg);
                }
                return;
            }
        } else {
            // If cooldown is not running, start it (only starts if a cooldown is registered on the item, otherwise pass)
            nbtHandler.startCooldown();
        }

        // If the item has confirm = true, show the inventory for it
        if (nbtHandler.getConfirmState()) {
            Inventory inv = Bukkit.createInventory(p, 9, "§3Item nutzen?");
            for (int i = 0; i < 9; i++) inv.setItem(i, ItemCreatorClass.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, "§7", false, null));
            inv.setItem(2, ItemCreatorClass.createItem(Material.LIME_DYE, 1, "§aJa", false, null));
            inv.setItem(6, ItemCreatorClass.createItem(Material.RED_DYE, 1, "§cNein", false, null));
            p.openInventory(inv);
        } else {
            // If not, execute the builder
            CommandBuilder builder = new CommandBuilder(p, nbtHandler.getCmdArray(), nbtHandler.getPermArray());
            if (nbtHandler.getOneTimeUseState()) {
                item.setAmount(item.getAmount() - 1);
            }
            builder.startCmds();
        }
    }
}
