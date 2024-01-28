package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemInteractListener implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
    }
}
