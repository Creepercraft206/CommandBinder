package Commands;

import Utils.CommandBuilder;
import Utils.MessageType;
import Utils.Messages;
import Utils.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandBinderCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (!(sender instanceof Player p)) {
            Bukkit.getConsoleSender().sendMessage(Color.RED + "Only players can use this command!");
            return false;
        }

        // Display Help
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            if (!p.hasPermission("commandbinder.help")) {
                p.sendMessage(Messages.noPerms);
                return false;
            }
            p.sendMessage(Messages.helpText);
            return true;
        }

        // Ensure case insensitivity
        switch (args[0].toLowerCase()) {
            //region Placeholders, Info, Custom Commands
            case "placeholders" -> {
                if (!p.hasPermission("commandbinder.placeholders")) {
                    p.sendMessage(Messages.noPerms);
                    return false;
                }
                p.sendMessage(Messages.placeholderText);
                return true;
            }

            case "info" -> {
                if (!p.hasPermission("commandbinder.info")) {
                    p.sendMessage(Messages.noPerms);
                    return false;
                }
                p.sendMessage(Messages.infoText);
                return true;
            }

            case "customcommands", "customcmds", "ccmds" -> {
                if (!p.hasPermission("commandbinder.customcmds")) {
                    p.sendMessage(Messages.noPerms);
                    return false;
                }
                p.sendMessage(Messages.customCmdsText);
                return true;
            }
            //endregion

            //region Add Command
            case "add" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.add")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length == 1) {
                    p.sendMessage(Messages.usageAdd);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                // Get everything after "add" for the command
                String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

                NBTHandler nbtHandler = new NBTHandler(item);
                nbtHandler.addCommand(commandWithArgs);

                sendMessage(p, item, MessageType.CMD_ADDED);
                return true;
            }
            //endregion

            //region Remove Command
            case "remove" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.remove")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length != 2) {
                    p.sendMessage(Messages.usageRemove);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                try {
                    NBTHandler nbtHandler = new NBTHandler(item);

                    // Check if the id is valid and even an integer
                    if (Integer.parseInt(args[1]) > nbtHandler.getHighestId() || Integer.parseInt(args[1]) <= 0) {
                        p.sendMessage(Messages.invalidId);
                        return false;
                    }
                    nbtHandler.removeCommand(Integer.parseInt(args[1]));

                    sendMessage(p, item, MessageType.CMD_REMOVED);
                    return true;
                } catch (NumberFormatException e) {
                    p.sendMessage(Messages.invalidId);
                    return false;
                }
            }
            //endregion

            //region Insert Command
            case "insert" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.insert")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length <= 2) {
                    p.sendMessage(Messages.usageInsert);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                try {
                    NBTHandler nbtHandler = new NBTHandler(item);

                    // Check if the id is valid and even an integer
                    if (Integer.parseInt(args[1]) >= nbtHandler.getHighestId() || Integer.parseInt(args[1]) < 0) {
                        p.sendMessage(Messages.invalidId);
                        return false;
                    }

                    // Get everything after "insert <ID>" for the command
                    String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    nbtHandler.insertCommand(Integer.parseInt(args[1]), commandWithArgs);

                    sendMessage(p, p.getInventory().getItemInMainHand(), MessageType.CMD_INSERTED);
                    return true;
                } catch (NumberFormatException e) {
                    p.sendMessage(Messages.invalidId);
                    return false;
                }
            }
            //endregion

            //region Set Command
            case "set" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.set")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length <= 2) {
                    p.sendMessage(Messages.usageSet);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                try {
                    NBTHandler nbtHandler = new NBTHandler(item);

                    // Check if the id is valid and even an integer
                    if (Integer.parseInt(args[1]) > nbtHandler.getHighestId() || Integer.parseInt(args[1]) <= 0) {
                        p.sendMessage(Messages.invalidId);
                        return false;
                    }

                    // Get everything after "set <ID>" for the command
                    String commandWithArgs = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    nbtHandler.setCommand(Integer.parseInt(args[1]), commandWithArgs);

                    sendMessage(p, item, MessageType.CMD_SET);
                    return true;
                } catch (NumberFormatException e) {
                    p.sendMessage(Messages.invalidId);
                    return false;
                }
            }
            //endregion

            //region List Commands
            case "list" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.list")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                p.sendMessage(Messages.listHeader);

                if (nbtHandler.getHighestId() == 0) {
                    p.sendMessage(Messages.noCmds);
                    return true;
                }

                // Display commands with altering prefix based on id mod 2
                for (int i = 1; i < nbtHandler.getHighestId(); i++) {
                    if (i % 2 == 0) {
                        p.sendMessage(Messages.listItemEven + i + Messages.listItemPlaceholder + nbtHandler.getCommand(i));
                    } else {
                        p.sendMessage(Messages.listItemOdd + i + Messages.listItemPlaceholder + nbtHandler.getCommand(i));
                    }
                }
                return true;
            }
            //endregion

            //region Set Cooldown
            case "cooldown" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.cooldown")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length != 2) {
                    p.sendMessage(Messages.usageCooldown);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                try {
                    NBTHandler nbtHandler = new NBTHandler(item);
                    int cooldown = Integer.parseInt(args[1]);

                    // Remove cooldown if cooldown is 0
                    if (cooldown == 0) {
                        nbtHandler.removeCooldown();
                        sendMessage(p, item, MessageType.COOLDOWN_REMOVED);
                    } else {
                        nbtHandler.setCooldown(cooldown);
                        sendMessage(p, item, MessageType.COOLDOWN_SET);
                    }
                    return true;
                } catch (NumberFormatException e) {
                    p.sendMessage(Messages.invalidCooldown);
                    return false;
                }
            }
            //endregion

            //region Add Permission
            case "addperm" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.addperm")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length == 1) {
                    p.sendMessage(Messages.usageAddPerm);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                nbtHandler.addPermission(args[1]);
                return true;
            }
            //endregion

            //region Remove Permission
            case "removeperm" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.removeperm")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length == 1) {
                    p.sendMessage(Messages.usageRemovePerm);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                if (nbtHandler.removePermission(args[1])) {
                    p.sendMessage(Messages.permRemoved);
                    return true;
                } else {
                    p.sendMessage(Messages.invalidPerm);
                    return false;
                }
            }
            //endregion

            //region List Permissions
            case "listperms" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.listperms")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                p.sendMessage(Messages.listPermHeader);
                for (int i = 1; i < nbtHandler.getHighestPermId(); i++) {
                    p.sendMessage(Messages.listItemEven + "§3" + nbtHandler.getPermission(i));
                }
                return true;
            }
            //endregion

            //region Use Item
            case "use" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.use")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                CommandBuilder builder = new CommandBuilder(p, nbtHandler.getCmdArray(), nbtHandler.getPermArray());
                builder.startCmds();
                return true;
            }
            //endregion

            //region Set One-Time-Use State
            case "onetimeuse" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.onetimeuse")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length != 2) {
                    p.sendMessage(Messages.usageOneTimeUse);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                if (args[1].equalsIgnoreCase("true")) {
                    nbtHandler.setOneTimeUseState(true);
                    sendMessage(p, item, MessageType.ONE_TIME_USE_TRUE);
                } else if (args[1].equalsIgnoreCase("false")) {
                    nbtHandler.setOneTimeUseState(false);
                    sendMessage(p, item, MessageType.ONE_TIME_USE_FALSE);
                } else {
                    p.sendMessage(Messages.usageOneTimeUse);
                    return false;
                }
                return true;
            }
            //endregion

            //region Set Confirm State
            case "confirm" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.confirm")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length != 2) {
                    p.sendMessage(Messages.usageConfirm);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                if (args[1].equals("true")) {
                    nbtHandler.setConfirmState(true);
                    sendMessage(p, item, MessageType.CONFIRM_TRUE);
                    return true;
                } else if (args[1].equals("false")) {
                    nbtHandler.setConfirmState(false);
                    sendMessage(p, item, MessageType.CONFIRM_FALSE);
                    return true;
                } else {
                    p.sendMessage(Messages.usageConfirm);
                    return false;
                }
            }
            //endregion

            //region Set Item Message
            case "setmessage", "setmsg" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.setmessage")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length < 2) {
                    p.sendMessage(Messages.usageSetMessage);
                    return false;
                }
                // Check if MessageType is valid
                MessageType messageType = MessageType.fromString(args[1]);
                if (messageType == null) {
                    p.sendMessage(Messages.usageSetMessage);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                if (args.length >= 3) {
                    nbtHandler.setMessage(messageType, String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
                    p.sendMessage(Messages.messageSet.replace("%type%", messageType.getMesssageIdentifier()).replace("%message%", String.join(" ", Arrays.copyOfRange(args, 2, args.length))));
                } else {
                    // If no message is provided, disable the output
                    nbtHandler.setMessage(messageType, "");
                    p.sendMessage(Messages.messageUnset.replace("%type%", messageType.getMesssageIdentifier()));
                }
                return true;
            }
            //endregion

            //region Reset Item Message
            case "resetmessage", "resetmsg" -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (!p.hasPermission("commandbinder.resetmessage")) {
                    sendMessage(p, item, MessageType.NO_PERMS);
                    return false;
                }
                if (args.length < 2) {
                    p.sendMessage(Messages.usageResetMessage);
                    return false;
                }
                // Check if MessageType is valid
                MessageType messageType = MessageType.fromString(args[1]);
                if (messageType == null) {
                    p.sendMessage(Messages.usageResetMessage);
                    return false;
                }
                if (!item.getType().isItem()) {
                    p.sendMessage(Messages.noItem);
                    return false;
                }

                NBTHandler nbtHandler = new NBTHandler(item);
                nbtHandler.resetMessage(messageType);
                p.sendMessage(Messages.messageReset.replace("%type%", args[1]));
                return true;
            }
            //endregion
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        // Main commands
        if (args.length == 1) {
            completions.addAll(Arrays.asList(
                    "help",
                    "add",
                    "remove",
                    "insert",
                    "set",
                    "list",
                    "use",
                    "onetimeuse",
                    "confirm",
                    "addperm",
                    "removeperm",
                    "listperms",
                    "placeholders",
                    "info",
                    "customcommands",
                    "customcmds",
                    "ccmds",
                    "cooldown",
                    "setmessage",
                    "setmsg",
                    "resetmessage",
                    "resetmsg"
            ));
            return completions;
        }

        if (args.length == 2) {
            switch (args[0]) {
                // Return custom commands
                case "add" -> {
                    return getCustomCommandList();
                }
                // Return IDs of item
                case "remove", "insert", "set" -> {
                    if (sender instanceof Player p) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        NBTHandler nbtHandler = new NBTHandler(item);
                        if (item.getType().isItem()) {
                            for (int i = 1; i <= nbtHandler.getHighestId(); i++) {
                                completions.add(String.valueOf(i));
                            }
                        }
                    }
                    return completions;
                }
                // Boolean options
                case "onetimeuse", "confirm" -> {
                    completions.addAll(Arrays.asList("true", "false"));
                    return completions;
                }
                // Add MessageType Enum options
                case "setmessage", "resetmessage", "setmsg", "resetmsg" -> {
                    completions.addAll(Arrays.asList(MessageType.getMessageTypes()));
                    return completions;
                }
                default -> {
                    return completions;
                }
            }
        }

        if ((args.length == 3 && args[0].equals("add") && args[1].equals("!sound")) ||
            (args.length == 4 && (args[0].equals("insert") || args[0].equals("set")) && args[2].equals("!sound"))
        ) {
            for (Sound sound : Sound.values()) {
                completions.add(sound.toString());
            }
            return completions;
        }

        if (args.length == 3) {
            if (args[0].equals("insert") || args[0].equals("set")) {
                return getCustomCommandList();
            }
        }
        return completions;
    }

    /**
     * @return The list of available custom commands (e.g. !wait, !repeat)
     */
    private ArrayList<String> getCustomCommandList() {
        ArrayList<String> customCommands = new ArrayList<>();
        customCommands.add("!wait");
        customCommands.add("!repeat");
        customCommands.add("!endrepeat");
        customCommands.add("!if");
        customCommands.add("!endif");
        customCommands.add("!broadcast");
        customCommands.add("!text");
        customCommands.add("!actionbar");
        customCommands.add("!sound");
        return customCommands;
    }

    /**
     * Sends the item-specific message of the item or the global one if not set.
     * @param p The Player to send the message to.
     * @param item The ItemStack to check the item-specific message for.
     * @param type The MessageType of the message to send/check.
     */
    private void sendMessage(Player p, ItemStack item, MessageType type) {
        NBTHandler nbtHandler = new NBTHandler(item);
        String msg = nbtHandler.getMessage(type);
        if (msg != null) {
            p.sendMessage(msg);
        }
    }
}
