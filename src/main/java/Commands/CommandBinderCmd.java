package Commands;

import Utils.CommandBuilder;
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
            } else if (args[0].equals("set")) {
                if (p.hasPermission("commandbinder.set")) {
                    if (args.length > 2) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                            if (Integer.parseInt(args[1]) <= CommandBinder.getNbtHandler().getHighestId(item) && Integer.parseInt(args[1]) > 0) {
                                CommandBinder.getNbtHandler().setCommand(item, Integer.parseInt(args[1]), commandWithArgs);
                                p.sendMessage(Messages.cmdSet);
                            } else {
                                p.sendMessage(Messages.invalidId);
                            }
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageSet);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
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
                            if (CommandBinder.getNbtHandler().removePermission(item, args[1])) {
                                p.sendMessage(Messages.permRemoved);
                            } else {
                                p.sendMessage(Messages.invalidPerm);
                            }
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
                        }
                    } else {
                        p.sendMessage(Messages.noItem);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("use")) {
                if (p.hasPermission("commandbinder.use")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        CommandBuilder builder = new CommandBuilder(p, CommandBinder.getNbtHandler().getCmdArray(item), CommandBinder.getNbtHandler().getPermArray(item));
                        builder.startCmds();
                    } else {
                        p.sendMessage(Messages.noItem);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("onetimeuse")) {
                if (p.hasPermission("commandbinder.onetimeuse")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (args.length == 2) {
                            if (args[1].equals("true")) {
                                CommandBinder.getNbtHandler().setOneTimeUseState(item, true);
                                p.sendMessage(Messages.oneTimeUseTrue);
                            } else if (args[1].equals("false")) {
                                CommandBinder.getNbtHandler().setOneTimeUseState(item, false);
                                p.sendMessage(Messages.oneTimeUseFalse);
                            } else {
                                p.sendMessage(Messages.usageOneTimeUse);
                            }
                        } else {
                            p.sendMessage(Messages.usageOneTimeUse);
                        }
                    } else {
                        p.sendMessage(Messages.noItem);
                    }
                } else {
                    p.sendMessage(Messages.noPerms);
                }
            } else if (args[0].equals("confirm")) {
                if (p.hasPermission("commandbinder.confirm")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (args.length == 2) {
                            if (args[1].equals("true")) {
                                CommandBinder.getNbtHandler().setConfirmState(item, true);
                                p.sendMessage(Messages.confirmTrue);
                            } else if (args[1].equals("false")) {
                                CommandBinder.getNbtHandler().setConfirmState(item, false);
                                p.sendMessage(Messages.confirmFalse);
                            } else {
                                p.sendMessage(Messages.usageConfirm);
                            }
                        } else {
                            p.sendMessage(Messages.usageConfirm);
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
            completions.add("set");
            completions.add("list");
            completions.add("use");
            completions.add("onetimeuse");
            completions.add("confirm");
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
