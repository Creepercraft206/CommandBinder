package Commands;

import Utils.Messages;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandBinderCmd implements CommandExecutor, TabCompleter {

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
                            CommandBinder.getNbtHandler().addCommand(item, commandWithArgs);
                            p.sendMessage(Messages.cmdAdded);
                        } else {
                            p.sendMessage(Messages.noItem);
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
                            if (Integer.parseInt(args[1]) <= CommandBinder.getNbtHandler().getHighestId(item) && Integer.parseInt(args[1]) > 0) {
                                CommandBinder.getNbtHandler().removeCommand(item, Integer.parseInt(args[1]));
                                p.sendMessage(Messages.cmdRemoved);
                            } else {
                                p.sendMessage(Messages.invalidId);
                            }
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageRemove);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("insert")) {
                if (p.hasPermission("commandbinder.insert")) {
                    if (args.length > 2) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                            if (Integer.parseInt(args[1]) < CommandBinder.getNbtHandler().getHighestId(item) && Integer.parseInt(args[1]) >= 0) {
                                CommandBinder.getNbtHandler().insertCommand(item, Integer.parseInt(args[1]), commandWithArgs);
                                p.sendMessage(Messages.cmdInserted);
                            } else {
                                p.sendMessage(Messages.invalidId);
                            }
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageInsert);
                    }
                }
            } else if (args[0].equals("list")) {
                if (p.hasPermission("commandbinder.list")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        p.sendMessage(Messages.listHeader);
                        if (CommandBinder.getNbtHandler().getHighestId(item) != 0) {
                            for (int i = 1; i < CommandBinder.getNbtHandler().getHighestId(item); i++) {
                                if (i % 2 == 0) {
                                    p.sendMessage(Messages.listItemEven + i + Messages.listItemPlaceholder + CommandBinder.getNbtHandler().getCommand(item, i));
                                } else {
                                    p.sendMessage(Messages.listItemOdd + i + Messages.listItemPlaceholder + CommandBinder.getNbtHandler().getCommand(item, i));
                                }
                            }
                        } else {
                            p.sendMessage(Messages.noCmds);
                        }
                    } else {
                        p.sendMessage(Messages.noItem);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("addperm")) {
                if (p.hasPermission("commandbinder.addperm")) {
                    if (args.length > 1) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String permission = args[1];
                            CommandBinder.getNbtHandler().addPermission(item, permission);
                            p.sendMessage(Messages.permAdded);
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageAddPerm);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("removeperm")) {
                if (p.hasPermission("commandbinder.removeperm")) {
                    if (args.length > 1) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String permission = args[1];
                            CommandBinder.getNbtHandler().removePermission(item, permission);
                            p.sendMessage(Messages.permRemoved);
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageRemovePerm);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("listperms")) {
                if (p.hasPermission("commandbinder.listperms")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        p.sendMessage(Messages.listPermHeader);
                        if (CommandBinder.getNbtHandler().getHighestPermId(item) != 0) {
                            for (int i = 1; i < CommandBinder.getNbtHandler().getHighestPermId(item); i++) {
                                p.sendMessage(Messages.listItemEven + "§3" + CommandBinder.getNbtHandler().getPermission(item, i));
                            }
                        } else {
                            p.sendMessage(Messages.noPerms);
                        }
                    } else {
                        p.sendMessage(Messages.noItem);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String string, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("help");
            completions.add("add");
            completions.add("remove");
            completions.add("insert");
            completions.add("list");
            completions.add("addperm");
            completions.add("removeperm");
            completions.add("listperms");
            completions.add("placeholders");
            completions.add("info");
            completions.add("customcommands");
            completions.add("customcmds");
            completions.add("ccmds");
        }
        return completions;
    }
}
