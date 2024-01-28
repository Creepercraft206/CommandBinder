package Listeners;

import Utils.NBTHandler;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("commandbinder.add") || p.hasPermission("commandbinder.remove") || p.isOp()) {
            CommandBinder.getInstance().nbtHandlers.put(p, new NBTHandler());
            p.sendMessage("§aCommandBinder has been enabled for you!");
        } else {
            p.sendMessage("§cYou do not have permission to use CommandBinder!");
        }
    }
}
