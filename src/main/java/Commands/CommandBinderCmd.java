package Commands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CommandBinderCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args[0].equals("help")) {
                if (p.hasPermission("commandbinder.help")) {
                    p.sendMessage("§7§m------------------------------------");
                    p.sendMessage("§6§lCommandBinder §7- §fHelp");
                    p.sendMessage("§7§m------------------------------------");
                    p.sendMessage("§6§l/cb help §7- §fShows this help page");
                    p.sendMessage("§6§l/cb add <command> §7- §fBinds a command to the item in your hand");
                    p.sendMessage("§6§l/cb remove <id> §7- §fRemove the command with the specified id from your item");
                    p.sendMessage("§6§l/cb list §7- §fLists all bound commands");
                    p.sendMessage("§7§m------------------------------------");
                } else {
                    p.sendMessage("§cYou do not have permission to use this command!");
                }
            } else if (args[0].equals("placeholders")) {
                if (p.hasPermission("commandbinder.placeholders")) {
                    p.sendMessage("§7§m------------------------------------");
                    p.sendMessage("§6§lCommandBinder §7- §fPlaceholders");
                    p.sendMessage("§7§m------------------------------------");
                    p.sendMessage("§6§l%player% §7- §fThe name of the player");
                    p.sendMessage("§6§l%UUID% §7- §fThe UUID of the player");
                    p.sendMessage("§6§l%world% §7- §fThe name of the world the player is in");
                    p.sendMessage("§6§l%x% §7- §fThe x-coordinate of the player");
                    p.sendMessage("§6§l%y% §7- §fThe y-coordinate of the player");
                    p.sendMessage("§6§l%z% §7- §fThe z-coordinate of the player");
                    p.sendMessage("§6§l%yaw% §7- §fThe yaw of the player");
                    p.sendMessage("§6§l%pitch% §7- §fThe pitch of the player");
                    p.sendMessage("§6§l%item% §7- §fThe name of the item in the player's hand");
                    p.sendMessage("§6§l%amount% §7- §fThe amount of the item in the player's hand");
                    p.sendMessage("§6§l%slot% §7- §fThe slot of the item in the player's hand");
                    p.sendMessage("§6§l%durability% §7- §fThe durability of the item in the player's hand");
                    p.sendMessage("§6§l%playersOnline% §7- §fThe amount of players online");
                    p.sendMessage("§6§l%block% §7- §fThe name of the block the player is looking at");
                    p.sendMessage("§6§l%lookingAt% §7- §fThe coordinates of the block the player is looking at");
                    p.sendMessage("§6§l%year% §7- §fThe current year");
                    p.sendMessage("§6§l%month% §7- §fThe current month");
                    p.sendMessage("§6§l%day% §7- §fThe current day");
                    p.sendMessage("§6§l%hour% §7- §fThe current hour");
                    p.sendMessage("§6§l%minute% §7- §fThe current minute");
                    p.sendMessage("§6§l%second% §7- §fThe current second");
                    p.sendMessage("§7§m------------------------------------");
                } else {
                    p.sendMessage("§cYou do not have permission to use this command!");
                }
            } else if (args[0].equals("add")) {
                if (p.hasPermission("commandbinder.add")) {
                    if (args.length > 1) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            ItemMeta itemMeta = item.getItemMeta();
                            if (itemMeta != null) {
                                String type = (String) itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("type"), PersistentDataType.STRING);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
