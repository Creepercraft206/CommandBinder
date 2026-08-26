package Listeners;

import Utils.CommandBuilder;
import Utils.ItemCreatorClass;
import Utils.MessageType;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
        if (item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("cbcmd1"))) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                e.setCancelled(true);
                handleInteract(item, p);
            }
        }
    }

    // Interaction with entity
    @EventHandler
    private void onEntityInteract(EntityInteractEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("cbcmd1"))) {
                e.setCancelled(true);
                handleInteract(item, p);
            }
        }
    }

    private void handleInteract(ItemStack item, Player p) {
        if (CommandBinder.getNbtHandler().isOnCooldown(item)) {
            double remainingCooldown = CommandBinder.getNbtHandler().getRemainingCooldown(item);
            if (remainingCooldown > 0) {
                String msg = CommandBinder.getNbtHandler().getMessage(p.getInventory().getItemInMainHand(), MessageType.ON_COOLDOWN);
                if (msg != null) {
                    p.sendMessage(msg);
                }
                return;
            }
        } else {
            CommandBinder.getNbtHandler().startCooldown(item);
        }
        if (CommandBinder.getNbtHandler().getConfirmState(item)) {
            Inventory inv = Bukkit.createInventory(p, 9, "§3Item nutzen?");
            for (int i = 0; i < 9; i++) inv.setItem(i, ItemCreatorClass.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, "§7", false, null));
            inv.setItem(2, ItemCreatorClass.createItem(Material.LIME_DYE, 1, "§aJa", false, null));
            inv.setItem(6, ItemCreatorClass.createItem(Material.RED_DYE, 1, "§cNein", false, null));
            p.openInventory(inv);
        } else {
            CommandBuilder builder = new CommandBuilder(p, CommandBinder.getNbtHandler().getCmdArray(item), CommandBinder.getNbtHandler().getPermArray(item));
            if (CommandBinder.getNbtHandler().getOneTimeUseState(item)) {
                item.setAmount(item.getAmount() - 1);
            }
            builder.startCmds();
        }
    }
}
