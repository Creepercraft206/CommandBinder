package Listeners;

import Utils.CommandBuilder;
import Utils.ItemCreatorClass;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
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
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
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
    }

    // Interaction with entity
    @EventHandler
    private void onEntityInteract(EntityInteractEvent e) {
        Player p = (Player) e.getEntity();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.getItemMeta() != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("cbcmd1"))) {
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

    // Click-Event in Confirm-Inventory
    @EventHandler
    private void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("§3Item nutzen?")) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                        CommandBuilder builder = new CommandBuilder(p, CommandBinder.getNbtHandler().getCmdArray(p.getInventory().getItemInMainHand()), CommandBinder.getNbtHandler().getPermArray(p.getInventory().getItemInMainHand()));
                        if (CommandBinder.getNbtHandler().getOneTimeUseState(p.getInventory().getItemInMainHand())) {
                            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                        }
                        builder.startCmds();
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                        p.closeInventory();
                    }
                }
            }
        }
    }
}
