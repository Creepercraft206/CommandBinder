package Commands;

import Utils.Messages;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommandBinderCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0 || args[0].equals("help")) {
                if (p.hasPermission("commandbinder.help")) {
                    p.sendMessage(Messages.helpText);
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("placeholders")) {
                if (p.hasPermission("commandbinder.placeholders")) {
                    p.sendMessage(Messages.placeholderText);
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("info")) {
                if (p.hasPermission("commandbinder.info")) {
                    p.sendMessage(Messages.infoText);
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("customcommands") || args[0].equals("customcmds") || args[0].equals("ccmds")) {
                if (p.hasPermission("commandbinder.customcmds")) {
                    p.sendMessage(Messages.customCmdsText);
                } else {
                    p.sendMessage(Messages.noPerms);
                }
        } else if (args[0].equals("add")) {
                if (p.hasPermission("commandbinder.add")) {
                    if (args.length > 1) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                            if (CommandBinder.getInstance().nbtHandlers.containsKey(p)) {
                                CommandBinder.getInstance().nbtHandlers.get(p).addCommand(item, commandWithArgs);
                                p.sendMessage(Messages.cmdAdded);
                            } else {
                                p.sendMessage(Messages.invalidSession);
                            }
                        }
                    } else {
                        p.sendMessage(Messages.usageAdd);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("remove")) {
                if (p.hasPermission("commandbinder.remove")) {
                    if (args.length == 2) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            if (CommandBinder.getInstance().nbtHandlers.containsKey(p)) {
                                CommandBinder.getInstance().nbtHandlers.get(p).removeCommand(item, Integer.parseInt(args[1]));
                                p.sendMessage(Messages.cmdRemoved);
                            } else {
                                p.sendMessage(Messages.invalidSession);
                            }
                        }
                    } else {
                        p.sendMessage(Messages.usageRemove);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("list")) {
                if (p.hasPermission("commandbinder.list")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        p.sendMessage(Messages.listHeader);
                        if (CommandBinder.getInstance().nbtHandlers.containsKey(p)) {
                            for (int i = 1; i < CommandBinder.getInstance().nbtHandlers.get(p).getHighestId(item); i++) {
                                if (i % 2 == 0) {
                                    p.sendMessage(Messages.listItemEven + i + Messages.listItemPlaceholder + CommandBinder.getInstance().nbtHandlers.get(p).getCommand(item, i));
                                } else {
                                    p.sendMessage(Messages.listItemOdd + i + Messages.listItemPlaceholder + CommandBinder.getInstance().nbtHandlers.get(p).getCommand(item, i));
                                }
                            }
                        } else {
                            p.sendMessage(Messages.invalidSession);
                        }
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            }
        }
        return false;
    }
}
