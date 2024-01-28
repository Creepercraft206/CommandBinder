package Listeners;

import Utils.CommandBuilder;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemInteractListener implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                CommandBuilder builder = new CommandBuilder(p, CommandBinder.getInstance().nbtHandlers.get(p).getCmdArray(item));
                builder.runCommands();
            }
        }
    }
}
