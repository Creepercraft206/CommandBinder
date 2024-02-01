package Utils;

import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.ZoneId;
import java.util.*;

public class CommandBuilder {

    private final Player p;
    private TimeZone timeZone;
    private final Calendar cal = Calendar.getInstance();
    private Queue<String> cmds;

    public CommandBuilder(Player p, ArrayList<String> cmds) {
        this.p = p;
        this.cmds = new ArrayDeque<>(cmds);
        timeZone = TimeZone.getTimeZone(ZoneId.systemDefault());
        cal.setTimeZone(timeZone);
    }

    @SuppressWarnings("deprecation")
    private String replacePlaceholders(String cmd) {
        ArrayList<String> replacedList = new ArrayList<>();
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
            cmd = cmd.replace("%slot%", String.valueOf(p.getInventory().getHeldItemSlot() + 1));
        }
        if (cmd.contains("%durability%")) {
            cmd = cmd.replace("%durability%", String.valueOf(p.getInventory().getItemInMainHand().getType().getMaxDurability() - p.getInventory().getItemInMainHand().getDurability()));
        }
        if (cmd.contains("%playersOnline%")) {
            cmd = cmd.replace("%playersOnline%", String.valueOf(Bukkit.getOnlinePlayers().size()));
        }
        if (cmd.contains("%block%")) {
            cmd = cmd.replace("%block%", p.getTargetBlock(null, 128).getType().toString());
        }
        if (cmd.contains("%lookingAt%")) {
            if (p.getEyeLocation().getBlock().getType() != Material.AIR) {
                Location loc = p.getTargetBlock(null, 128).getLocation();
                cmd = cmd.replace("%lookingAt%", loc.getX() + " " + loc.getY() + " " + loc.getZ());
            }
        }
        if (cmd.contains("%year%")) {
            cmd = cmd.replace("%year%", String.valueOf(cal.get(Calendar.YEAR)));
        }
        if (cmd.contains("%month%")) {
            cmd = cmd.replace("%month%", String.valueOf(cal.get(Calendar.MONTH)));
        }
        if (cmd.contains("%day%")) {
            cmd = cmd.replace("%day%", String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + 1));
        }
        if (cmd.contains("%hour%")) {
            cmd = cmd.replace("%hour%", String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        }
        if (cmd.contains("%minute%")) {
            cmd = cmd.replace("%minute%", String.valueOf(cal.get(Calendar.MINUTE)));
        }
        if (cmd.contains("%second%")) {
            cmd = cmd.replace("%second%", String.valueOf(cal.get(Calendar.SECOND)));
        }
        return cmd;
    }


    /*
    private void repeatCode(ArrayList<String> code, int times) {
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < code.size(); j++) {
                dispatchCmds(current + j);
            }
        }
        continueCmds();
    }

    private void dispatchCmds(int index) {
        if (cmds.get(index).startsWith("!wait")) {
            String[] split = cmds.get(index).split(" ");
            int delay = Integer.parseInt(split[1]);
            delayCode(delay);
        } else if (cmds.get(index).startsWith("!repeat")) {
            int end = 0;
            for (int i = current + 1; i < cmds.size(); i++) {
                if (cmds.get(i).startsWith("!endrepeat")) {
                    end = i;
                    break;
                }
            }
            String[] split = cmds.get(index).split(" ");
            int times = Integer.parseInt(split[1]);
            ArrayList<String> repeatCmds = new ArrayList<>();
            for (int i = index + 2; i < end; i++) {
                repeatCmds.add(cmds.get(i));
            }
            repeatCode(repeatCmds, times);
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmds.get(index));
            current++;
        }
    }
    --------------------------------------------

    int current = 0;
    public void startCmds() {
        dispatchCmd();
    }

    private void dispatchCmd() {
        if (current >= cmds.size()) {
            return;
        }

        String cmd = cmds.get(current);
        if (cmd.startsWith("!wait")) {
            String[] split = cmd.split(" ");
            int delay = Integer.parseInt(split[1]);
            delayCode(delay);
        } else if (cmd.startsWith("!repeat")) {
            String[] split = cmd.split(" ");
            int times = Integer.parseInt(split[1]);
            int end = cmds.indexOf("!endrepeat") - current;
            if (end != -1) {
                ArrayList<String> repeatCmds = new ArrayList<>(cmds.subList(current + 1, end));
                repeatCode(repeatCmds, times);
                current = cmds.indexOf("!endrepeat");
            }
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
        current++;
        dispatchCmd();
    }

    private void delayCode(int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                dispatchCmd();
            }
        }.runTaskLater(CommandBinder.getInstance(), delay);
    }

    private void repeatCode(ArrayList<String> code, int times) {
        for (int i = 0; i < times; i++) {
            for (String cmd : code) {
                if (cmd.startsWith("!wait")) {
                    String[] split = cmd.split(" ");
                    int delay = Integer.parseInt(split[1]);
                    delayCode(delay);
                } else {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
                }
            }
        }
    }
     */

    public void startCmds() {
        executeNextCmd();
    }

    private void executeNextCmd() {
        if (!cmds.isEmpty()) {
            String cmd = cmds.poll();
            cmd = replacePlaceholders(cmd);
            if (cmd.startsWith("!wait")) {
                String[] split = cmd.split(" ");
                int delay = Integer.parseInt(split[1]);
                delayCode(delay);
            } else if (cmd.startsWith("!repeat")) {
                String[] split = cmd.split(" ");
                int times = Integer.parseInt(split[1]);
                repeatCode(times);
            } else {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
                executeNextCmd();
            }
        }
    }

    private void delayCode(int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                executeNextCmd();
            }
        }.runTaskLater(CommandBinder.getInstance(), delay);
    }

    private void repeatCode(int times) {
        String cmd = cmds.poll();
        ArrayList<String> repeatCmds = new ArrayList<>();
        while (cmd != null && !cmd.equals("!endrepeat")) {
            repeatCmds.add(cmd);
            cmd = cmds.poll();
        }
        for (int i = 0; i < times; i++) {
            cmds.addAll(repeatCmds);
        }
        executeNextCmd();
    }
}
