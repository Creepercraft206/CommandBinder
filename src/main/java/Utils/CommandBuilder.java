package Utils;

import de.hgpractice.commandbinder.CommandBinder;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.mariuszgromada.math.mxparser.Expression;

import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandBuilder {

    private final Player p;
    private final Calendar cal = Calendar.getInstance();
    private final Queue<String> cmds;
    private final ArrayList<String> perms;
    private final PermissionHandler permissionHandler;

    public CommandBuilder(Player p, ArrayList<String> cmds, ArrayList<String> perms) {
        this.p = p;
        this.cmds = new ArrayDeque<>(cmds);
        this.perms = perms;
        permissionHandler = new PermissionHandler(CommandBinder.getPermsConfig());
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.systemDefault());
        cal.setTimeZone(timeZone);
    }

    @SuppressWarnings("deprecation")
    private String replacePlaceholders(String cmd) {
        // Handle normal placeholders
        Entity target = getTargetEntity(p);
        cmd = cmd
            .replace("%player%", p.getName())
            .replace("%UUID%", p.getUniqueId().toString())
            .replace("%world%", p.getWorld().getName())
            .replace("%x%", String.valueOf(p.getLocation().getX()))
            .replace("%y%", String.valueOf(p.getLocation().getY()))
            .replace("%z%", String.valueOf(p.getLocation().getZ()))
            .replace("%yaw%", String.valueOf(p.getLocation().getYaw()))
            .replace("%pitch%", String.valueOf(p.getLocation().getPitch()))
            .replace("%item%", p.getInventory().getItemInMainHand().getType().toString())
            .replace("%amount%", String.valueOf(p.getInventory().getItemInMainHand().getAmount()))
            .replace("%slot%", String.valueOf(p.getInventory().getHeldItemSlot() + 1))
            .replace("%durability%", String.valueOf(p.getInventory().getItemInMainHand().getType().getMaxDurability() - p.getInventory().getItemInMainHand().getDurability()))
            .replace("%playersOnline%", String.valueOf(Bukkit.getOnlinePlayers().size()))
            .replace("%block%", p.getTargetBlock(null, 128).getType().toString())
            .replace("%year%", String.valueOf(cal.get(Calendar.YEAR)))
            .replace("%month%", String.valueOf(cal.get(Calendar.MONTH) + 1))
            .replace("%day%", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
            .replace("%hour%", String.valueOf(cal.get(Calendar.HOUR_OF_DAY)))
            .replace("%minute%", String.valueOf(cal.get(Calendar.MINUTE)))
            .replace("%second%", String.valueOf(cal.get(Calendar.SECOND)))
            .replace("%entityuuid%", target != null ? target.getUniqueId().toString() : "none")
            .replace("%entitytype%", target != null ? target.getType().toString() : "none")
            .replace("%entityname%", target != null ? target.getName() : "none")
            .replace("%entityx%", target != null ? String.valueOf(target.getLocation().getX()) : "none")
            .replace("%entityy%", target != null ? String.valueOf(target.getLocation().getY()) : "none")
            .replace("%entityz%", target != null ? String.valueOf(target.getLocation().getZ()) : "none")
            .replace("%entityyaw%", target != null ? String.valueOf(target.getLocation().getYaw()) : "none")
            .replace("%entitypitch%", target != null ? String.valueOf(target.getLocation().getPitch()) : "none");

        if (cmd.contains("%lookingAt%")) {
            // Code too long to be inlined :(
            if (p.getEyeLocation().getBlock().getType() != Material.AIR) {
                Location loc = p.getTargetBlock(null, 128).getLocation();
                cmd = cmd.replace("%lookingAt%", loc.getX() + " " + loc.getY() + " " + loc.getZ());
            }
        }

        // Handle PlaceholderAPI placeholders
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            cmd = PlaceholderAPI.setPlaceholders(p, cmd);
        }

        // Handle placeholders with inputs after all others got replaced
        if (cmd.contains("%randomNum:")) {
            Pattern pattern = Pattern.compile("%randomNum:([^%]+)%");
            Matcher matcher = pattern.matcher(cmd);
            while (matcher.find()) {
                String[] range = matcher.group(1).split("-");
                if (range.length == 2) {
                    try {
                        int min = Integer.parseInt(range[0].trim());
                        int max = Integer.parseInt(range[1].trim());
                        if (min > max) {
                            cmd = cmd.replace(matcher.group(0), "Error: Min is greater than Max");
                        } else {
                            int randomNum = new Random().nextInt(max - min + 1) + min;
                            cmd = cmd.replace(matcher.group(0), String.valueOf(randomNum));
                        }
                    } catch (NumberFormatException e) {
                        cmd = cmd.replace(matcher.group(0), "Error: Invalid number format");
                    }
                } else {
                    cmd = cmd.replace(matcher.group(0), "Error: Invalid range format");
                }
            }
        }
        if (cmd.contains("%score:")) {
            Pattern scorePattern = Pattern.compile("%score:([^%]+)%");
            Matcher matcher = scorePattern.matcher(cmd);
            while (matcher.find()) {
                String scoreName = matcher.group(1);
                if (p.getScoreboard().getObjective(scoreName) != null) {
                    int score = p.getScoreboard().getObjective(scoreName).getScore(p.getName()).getScore();
                    cmd = cmd.replace("%score:" + scoreName + "%", String.valueOf(score));
                }
            }
        }
        if (cmd.contains("%math:")) {
            Pattern mathPattern = Pattern.compile("%math:([^%]+)%");
            Matcher matcher = mathPattern.matcher(cmd);
            while (matcher.find()) {
                String expr = matcher.group(1);
                String result;
                try {
                    if (expr.startsWith("if(") && expr.endsWith(")")) {
                        // syntax: %math:if(condition, trueValue, falseValue)%
                        String inner = expr.substring(3, expr.length() - 1);
                        List<String> parts = new ArrayList<>();
                        int last = 0, count = 0;
                        boolean inQuotes = false;
                        for (int i = 0; i < inner.length(); i++) {
                            char c = inner.charAt(i);
                            if (c == '"') inQuotes = !inQuotes;
                            if (c == ',' && !inQuotes) {
                                parts.add(inner.substring(last, i).trim());
                                last = i + 1;
                                count++;
                                if (count == 2) break;
                            }
                        }
                        parts.add(inner.substring(last).trim());
                        if (parts.size() == 3) {
                            Expression conditionExpr = new Expression(parts.get(0));
                            boolean condition = conditionExpr.calculate() == 1.0;
                            if (Double.isNaN(conditionExpr.calculate())) {
                                if (parts.get(0).contains("==")) {
                                    String[] conditionParts = parts.get(0).split("==");
                                    if (conditionParts.length == 2) {
                                        String left = conditionParts[0].trim();
                                        String right = conditionParts[1].trim();
                                        condition = left.equals(right);
                                    } else {
                                        return "§cError in condition: " + parts.get(0);
                                    }
                                } else {
                                    return "§cError in condition: " + parts.get(0);
                                }
                            }
                            String trueVal = parts.get(1).replaceAll("^\"|\"$", "");
                            String falseVal = parts.get(2).replaceAll("^\"|\"$", "");
                            result = condition ? trueVal : falseVal;
                        } else {
                            result = "§cError in expression: " + expr;
                        }
                    } else {
                        Expression expression = new Expression(expr);
                        result = String.valueOf(expression.calculate());
                    }
                } catch (Exception ex) {
                    result = "§cError calculating expression: " + expr;
                }
                cmd = cmd.replace("%math:" + expr + "%", result);
            }
        }

        return cmd;
    }

    public void startCmds() {
        executeNextCmd();
    }

    private void executeNextCmd() {
        if (!cmds.isEmpty()) {
            String cmd = cmds.poll();
            cmd = replacePlaceholders(cmd);
            if (cmd.startsWith("!wait")) {
                String[] split = cmd.split(" ");
                double delay = Double.parseDouble(split[1]);
                delayCode(delay);
            } else if (cmd.startsWith("!repeat")) {
                String[] split = cmd.split(" ");
                int times = Integer.parseInt(split[1]);
                repeatCode(times);
            } else if (cmd.startsWith("!if")) {
                Expression expression = new Expression(cmd.replace("!if ", ""));
                boolean condition = expression.calculate() == 1.0; // 1.0 -> true
                // check for NaN
                if (Double.isNaN(expression.calculate())) {
                    if (cmd.contains("==")) {
                        String[] parts = cmd.replace("!if ", "").split("==");
                        if (parts.length == 2) {
                            String left = parts[0].trim();
                            String right = parts[1].trim();
                            condition = left.equals(right);
                        } else {
                            p.sendMessage("§cError in condition: " + cmd.replace("!if ", ""));
                            executeNextCmd();
                            return;
                        }
                    } else {
                        p.sendMessage("§cError in condition: " + cmd.replace("!if ", ""));
                        executeNextCmd();
                        return;
                    }
                }
                checkCondition(condition);
            } else if (cmd.startsWith("!broadcast") || cmd.startsWith("!bc")) {
                String message = cmd.replace("!broadcast ", "").replace("!bc ", "");
                message = message.replace("&", "§");
                Bukkit.broadcastMessage(message);
                executeNextCmd();
            } else if (cmd.startsWith("!text") || cmd.startsWith("!t")) {
                String message = cmd.replace("!text ", "").replace("!t ", "");
                message = message.replace("&", "§");
                p.sendMessage(message);
                executeNextCmd();
            } else if (cmd.startsWith("!actionbar") || cmd.startsWith("!acb") || cmd.startsWith("!ac")) {
                String message = cmd.replace("!actionbar ", "").replace("!acb ", "").replace("!ac ", "");
                message = message.replace("&", "§");
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(message));
                executeNextCmd();
            } else if (cmd.startsWith("!sound")) {
                String sound = cmd.replace("!sound ", "");
                String[] soundParts = sound.split(":");
                Sound s = Sound.valueOf(soundParts[0].toUpperCase());
                if (soundParts.length == 2) {
                    try {
                        p.playSound(p.getLocation(), s, Float.parseFloat(soundParts[1]), 1.0f);
                    } catch (NumberFormatException e) {
                        p.sendMessage("§cInvalid sound volume specified.");
                    }
                } else if (soundParts.length == 3) {
                    try {
                        p.playSound(p.getLocation(), s, Float.parseFloat(soundParts[1]), Float.parseFloat(soundParts[2]));
                    } catch (NumberFormatException e) {
                        p.sendMessage("§cInvalid sound volume specified.");
                    }
                } else {
                    p.playSound(p.getLocation(), s, 1.0f, 1.0f);
                }
                executeNextCmd();
            } else {
                for (String perm : perms) {
                    permissionHandler.addPermission(p.getUniqueId(), perm);
                }
                boolean hasAllPermissionsReceived = perms.isEmpty();
                while (!hasAllPermissionsReceived) {
                    for (String perm : perms) {
                        if (p.hasPermission(perm)) {
                            hasAllPermissionsReceived = true;
                        } else {
                            hasAllPermissionsReceived = false;
                            break;
                        }
                    }
                }
                p.performCommand(cmd);
                for (String perm : perms) {
                    permissionHandler.removePermission(p.getUniqueId(), perm);
                }
                executeNextCmd();
            }
        }
    }

    private void delayCode(double delay) {
        delay = Math.round((delay * 10.0) / 10.0) * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                executeNextCmd();
            }
        }.runTaskLater(CommandBinder.getInstance(), (long) delay);
    }

    private void repeatCode(int times) {
        String cmd = cmds.poll();
        ArrayList<String> repeatCmds = new ArrayList<>();
        ArrayList<String> followingCmds = new ArrayList<>();
        boolean endRepeatEncountered = false;
        while (cmd != null && !cmd.equals("!endrepeat")) {
            repeatCmds.add(cmd);
            cmd = cmds.poll();
            if (cmd != null && cmd.equals("!endrepeat")) {
                endRepeatEncountered = true;
                cmd = cmds.poll();
            }
        }
        while (cmd != null) {
            if (endRepeatEncountered) {
                followingCmds.add(cmd);
            }
            cmd = cmds.poll();
        }
        for (int i = 0; i < times; i++) {
            cmds.addAll(repeatCmds);
        }
        cmds.addAll(followingCmds);
        executeNextCmd();
    }

    private void checkCondition(boolean condition) {
        ArrayList<String> ifCmds = new ArrayList<>();
        ArrayList<String> followingCmds = new ArrayList<>();
        int ifDepth = 1;
        while (!cmds.isEmpty()) {
            String cmd = cmds.poll();
            if (cmd == null) break;
            if (cmd.startsWith("!if")) {
                ifDepth++;
                ifCmds.add(cmd);
            } else if (cmd.equals("!endif")) {
                ifDepth--;
                if (ifDepth == 0) break;
                ifCmds.add(cmd);
            } else {
                ifCmds.add(cmd);
            }
        }
        // Restliche Befehle nach !endif
        while (!cmds.isEmpty()) {
            followingCmds.add(cmds.poll());
        }
        if (condition) {
            cmds.addAll(ifCmds);
        }
        cmds.addAll(followingCmds);
        executeNextCmd();
    }

    private Entity getTargetEntity(final Entity entity) {
        return getTarget(entity, entity.getWorld().getEntities());
    }

    private Entity getTarget(Entity entity, List<Entity> entities) {
        if (entity == null) return null;
        Entity target = null;
        final double threshold = 1;
        for (Entity e : entities) {
            if (e.equals(entity)) continue;
            Vector n = e.getLocation().toVector().subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold) {
                if (target == null || target.getLocation().distanceSquared(entity.getLocation()) > e.getLocation().distanceSquared(entity.getLocation())) {
                    target = e;
                }
            }
        }
        return target;
    }
}
