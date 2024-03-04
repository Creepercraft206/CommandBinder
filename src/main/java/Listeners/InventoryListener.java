package Listeners;

import Utils.CommandBuilder;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    private void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("§3Item nutzen?")) {
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aJa")) {
                    p.closeInventory();
                    ItemStack item = p.getInventory().getItemInMainHand();
                    CommandBuilder builder = new CommandBuilder(p, CommandBinder.getNbtHandler().getCmdArray(item), CommandBinder.getNbtHandler().getPermArray(item));
                    if (CommandBinder.getNbtHandler().getOneTimeUseState(item)) {
                        item.setAmount(item.getAmount() - 1);
                    }
                    builder.startCmds();
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNein")) {
                    p.closeInventory();
                }
            }
        }
    }
}
