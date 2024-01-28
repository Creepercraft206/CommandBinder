package Utils;

import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Date;

public class CommandBuilder {

    private final Player p;
    private final Date date = new Date();
    private final ArrayList<String> cmds = new ArrayList<>();

    public CommandBuilder(Player p, ArrayList<String> cmds) {
        this.p = p;
        this.cmds.addAll(cmds);
    }

    @SuppressWarnings("deprecation")
    private void replacePlaceholders() {
        ArrayList<String> replacedList = new ArrayList<>();
        for (String cmd : cmds) {
            if (cmd.contains("%player%")) {
                cmd = cmd.replace("%player%", p.getName());
            }
            if (cmd.contains("%UUID%")) {
                cmd = cmd.replace("%UUID%", p.getUniqueId().toString());
            }
            if (cmd.contains("%world%")) {
                cmd = cmd.replace("%world%", p.getWorld().getName());
            }
            if (cmd.contains("%x%")) {
                cmd = cmd.replace("%x%", String.valueOf(p.getLocation().getX()));
            }
            if (cmd.contains("%y%")) {
                cmd = cmd.replace("%y%", String.valueOf(p.getLocation().getY()));
            }
            if (cmd.contains("%z%")) {
                cmd = cmd.replace("%z%", String.valueOf(p.getLocation().getZ()));
            }
            if (cmd.contains("%yaw%")) {
                cmd = cmd.replace("%yaw%", String.valueOf(p.getLocation().getYaw()));
            }
            if (cmd.contains("%pitch%")) {
                cmd = cmd.replace("%pitch%", String.valueOf(p.getLocation().getPitch()));
            }
            if (cmd.contains("%item%")) {
                cmd = cmd.replace("%item%", p.getInventory().getItemInMainHand().getType().toString());
            }
            if (cmd.contains("%amount%")) {
                cmd = cmd.replace("%amount%", String.valueOf(p.getInventory().getItemInMainHand().getAmount()));
            }
            if (cmd.contains("%slot%")) {
                cmd = cmd.replace("%slot%", String.valueOf(p.getInventory().getHeldItemSlot()));
            }
            if (cmd.contains("%durability%")) {
                cmd = cmd.replace("%durability%", String.valueOf(p.getInventory().getItemInMainHand().getDurability()));
            }
            if (cmd.contains("%playersOnline%")) {
                cmd = cmd.replace("%playersOnline%", String.valueOf(Bukkit.getOnlinePlayers().size()));
            }
            if (cmd.contains("%block%")) {
                cmd = cmd.replace("%block%", p.getEyeLocation().getBlock().getType().toString());
            }
            if (cmd.contains("%lookingAt%")) {
                if (p.getEyeLocation().getBlock().getType() != Material.AIR) {
                    Location loc = p.getEyeLocation().getBlock().getLocation();
                    cmd = cmd.replace("%lookingAt%", loc.getX() + " " + loc.getY() + " " + loc.getZ());
                }
            }
            if (cmd.contains("%year%")) {
                cmd = cmd.replace("%year%", String.valueOf(date.getYear()));
            }
            if (cmd.contains("%month%")) {
                cmd = cmd.replace("%month%", String.valueOf(date.getMonth()));
            }
            if (cmd.contains("%day%")) {
                cmd = cmd.replace("%day%", String.valueOf(date.getDay()));
            }
            if (cmd.contains("%hour%")) {
                cmd = cmd.replace("%hour%", String.valueOf(date.getHours()));
            }
            if (cmd.contains("%minute%")) {
                cmd = cmd.replace("%minute%", String.valueOf(date.getMinutes()));
            }
            if (cmd.contains("%second%")) {
                cmd = cmd.replace("%second%", String.valueOf(date.getSeconds()));
            }
            replacedList.add(cmd);
        }
        cmds.clear();
        cmds.addAll(replacedList);
    }

    private void delayCode(String code, int delay) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), code);
            }
        }.runTaskLater(CommandBinder.getInstance(), delay);
    }

    private void repeatCode(String code, int times) {
        for (int i = 0; i < times; i++) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), code);
        }
    }

    public void runCommands() {
        replacePlaceholders();
        for (int i = 0; i < cmds.size(); i++) {
            if (cmds.get(i).startsWith("!wait")) {
                String[] split = cmds.get(i).split(" ");
                int delay = Integer.parseInt(split[1]);
                String code = cmds.get(i + 1);
                i++;
                delayCode(code, delay);
            } else if (cmds.get(i).startsWith("!repeat")) {
                String[] split = cmds.get(i).split(" ");
                int times = Integer.parseInt(split[1]);
                String code = cmds.get(i + 1);
                i++;
                repeatCode(code, times);
            } else {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmds.get(i));
            }
        }
    }
}
