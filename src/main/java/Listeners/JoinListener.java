package Listeners;

import Utils.NBTHandler;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("commandbinder.add") || p.hasPermission("commandbinder.remove") || p.hasPermission("commandbinder.list")) {
            CommandBinder.getInstance().nbtHandlers.put(p, new NBTHandler());
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        CommandBinder.getInstance().nbtHandlers.remove(p);
    }
}
