package Listeners;

import Utils.CommandBuilder;
import Utils.NBTHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    private void onClick(InventoryClickEvent e) {
        //
        // TODO: Add Title and Options to config
        //
        if (e.getView().getTitle().equals("§3Item nutzen?")) {
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null) {
                return;
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                p.closeInventory();
                ItemStack item = p.getInventory().getItemInMainHand();
                NBTHandler nbtHandler = new NBTHandler(item);
                CommandBuilder builder = new CommandBuilder(p, nbtHandler.getCmdArray(), nbtHandler.getPermArray());
                if (nbtHandler.getOneTimeUseState()) {
                    item.setAmount(item.getAmount() - 1);
                }
                builder.startCmds();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                p.closeInventory();
            }
        }
    }
}
