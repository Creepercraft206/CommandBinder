package Commands;

import Utils.CommandBuilder;
import Utils.MessageType;
import Utils.Messages;
import de.hgpractice.commandbinder.CommandBinder;
import org.bukkit.Sound;
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

                            sendMessage(p, item, MessageType.CMD_ADDED);
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageAdd);
                    }
                } else {
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("remove")) {
                if (p.hasPermission("commandbinder.remove")) {
                    if (args.length == 2) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            if (Integer.parseInt(args[1]) <= CommandBinder.getNbtHandler().getHighestId(item) && Integer.parseInt(args[1]) > 0) {
                                CommandBinder.getNbtHandler().removeCommand(item, Integer.parseInt(args[1]));

                                sendMessage(p, item, MessageType.CMD_REMOVED);
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
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("insert")) {
                if (p.hasPermission("commandbinder.insert")) {
                    if (args.length > 2) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                            if (Integer.parseInt(args[1]) < CommandBinder.getNbtHandler().getHighestId(item) && Integer.parseInt(args[1]) >= 0) {
                                CommandBinder.getNbtHandler().insertCommand(item, Integer.parseInt(args[1]), commandWithArgs);

                                sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.CMD_INSERTED);
                            } else {
                                p.sendMessage(Messages.invalidId);
                            }
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageInsert);
                    }
                } else {
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("set")) {
                if (p.hasPermission("commandbinder.set")) {
                    if (args.length > 2) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                            if (Integer.parseInt(args[1]) <= CommandBinder.getNbtHandler().getHighestId(item) && Integer.parseInt(args[1]) > 0) {
                                CommandBinder.getNbtHandler().setCommand(item, Integer.parseInt(args[1]), commandWithArgs);

                                sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.CMD_SET);
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
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
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
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("cooldown")) {
                if (p.hasPermission("commandbinder.cooldown")) {
                    if (args.length == 2) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            try {
                                int cooldown = Integer.parseInt(args[1]);
                                if (cooldown == 0) {
                                    CommandBinder.getNbtHandler().removeCooldown(item);
                                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.COOLDOWN_REMOVED);
                                } else {
                                    CommandBinder.getNbtHandler().setCooldown(item, cooldown);
                                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.COOLDOWN_SET);
                                }
                            } catch (NumberFormatException e) {
                                p.sendMessage(Messages.invalidCooldown);
                            }
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageCooldown);
                    }
                } else {
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("addperm")) {
                if (p.hasPermission("commandbinder.addperm")) {
                    if (args.length > 1) {
                        if (p.getInventory().getItemInMainHand().getType().isItem()) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            String permission = args[1];
                            CommandBinder.getNbtHandler().addPermission(item, permission);
                        } else {
                            p.sendMessage(Messages.noItem);
                        }
                    } else {
                        p.sendMessage(Messages.usageAddPerm);
                    }
                } else {
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
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
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
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
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
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
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("onetimeuse")) {
                if (p.hasPermission("commandbinder.onetimeuse")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (args.length == 2) {
                            if (args[1].equals("true")) {
                                CommandBinder.getNbtHandler().setOneTimeUseState(item, true);
                                sendMessage(p, item, MessageType.ONE_TIME_USE_TRUE);
                            } else if (args[1].equals("false")) {
                                CommandBinder.getNbtHandler().setOneTimeUseState(item, false);
                                sendMessage(p, item, MessageType.ONE_TIME_USE_FALSE);
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
                                sendMessage(p, item, MessageType.CONFIRM_TRUE);
                            } else if (args[1].equals("false")) {
                                CommandBinder.getNbtHandler().setConfirmState(item, false);
                                sendMessage(p, item, MessageType.CONFIRM_FALSE);
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
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("setmessage") || args[0].equals("setmsg")) {
                if (p.hasPermission("commandbinder.setmessage")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (args.length >= 2) {
                            if (Arrays.stream(MessageType.getMessageTypes()).anyMatch(args[1]::equalsIgnoreCase)) {
                                if (args.length >= 3) {
                                    CommandBinder.getNbtHandler().setMessage(item, args[1], String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
                                    p.sendMessage(Messages.messageSet.replace("%type%", args[1]).replace("%message%", String.join(" ", Arrays.copyOfRange(args, 2, args.length))));
                                } else {
                                    CommandBinder.getNbtHandler().setMessage(item, args[1], "");
                                    p.sendMessage(Messages.messageUnset.replace("%type%", args[1]));
                                }
                            } else {
                                p.sendMessage(Messages.usageSetMessage);
                            }
                        } else {
                            p.sendMessage(Messages.usageSetMessage);
                        }
                    } else {
                        p.sendMessage(Messages.noItem);
                    }
                } else {
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
                }
            } else if (args[0].equals("resetmessage") || args[0].equals("resetmsg")) {
                if (p.hasPermission("commandbinder.resetmessage")) {
                    if (p.getInventory().getItemInMainHand().getType().isItem()) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (args.length >= 2) {
                            if (Arrays.stream(MessageType.getMessageTypes()).anyMatch(args[1]::equalsIgnoreCase)) {
                                CommandBinder.getNbtHandler().resetMessage(item, args[1]);
                                p.sendMessage(Messages.messageReset.replace("%type%", args[1]));
                            } else {
                                p.sendMessage(Messages.usageResetMessage);
                            }
                        } else {
                            p.sendMessage(Messages.usageResetMessage);
                        }
                    } else {
                        p.sendMessage(Messages.noItem);
                    }
                } else {
                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.NO_PERMS);
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
            completions.add("cooldown");
            completions.add("setmessage");
            completions.add("setmsg");
            completions.add("resetmessage");
            completions.add("resetmsg");
        } else if (args.length == 2) {
            if (args[0].equals("add")) {
                completions.add("!wait");
                completions.add("!repeat");
                completions.add("!endrepeat");
                completions.add("!if");
                completions.add("!endif");
                completions.add("!broadcast");
                completions.add("!text");
                completions.add("!actionbar");
                completions.add("!sound");
            } else if (args[0].equals("remove") || args[0].equals("insert") || args[0].equals("set")) {
                if (sender instanceof Player) {
                    ItemStack item = ((Player) sender).getInventory().getItemInMainHand();
                    if (item.getType().isItem()) {
                        for (int i = 1; i <= CommandBinder.getNbtHandler().getHighestId(item); i++) {
                            completions.add(String.valueOf(i));
                        }
                    }
                }
            } else if (args[0].equals("onetimeuse") || args[0].equals("confirm")) {
                completions.add("true");
                completions.add("false");
            } else if (args[0].equals("setmessage") || args[0].equals("resetmessage") || args[0].equals("setmsg") || args[0].equals("resetmsg")) {
                completions.addAll(Arrays.asList(MessageType.getMessageTypes()));
            }
        } else if (args.length == 3) {
            if (args[0].equals("insert") || args[0].equals("set")) {
                completions.add("!wait");
                completions.add("!repeat");
                completions.add("!endrepeat");
                completions.add("!if");
                completions.add("!endif");
                completions.add("!broadcast");
                completions.add("!text");
                completions.add("!actionbar");
                completions.add("!sound");
            } else if (args[0].equals("add") && args[1].equals("!sound")) {
                for (Sound sound : Sound.values()) {
                    completions.add(sound.toString());
                }
            }
        } else if (args.length == 4 && (args[0].equals("insert") || args[0].equals("set")) && args[2].equals("!sound")) {
            for (Sound sound : Sound.values()) {
                completions.add(sound.toString());
            }
        }
        return completions;
    }

    private void sendMessage(Player p, ItemStack item, MessageType type) {
        String msg = CommandBinder.getNbtHandler().getMessage(item, type);
        if (msg != null) {
            p.sendMessage(msg);
        }
    }
}
