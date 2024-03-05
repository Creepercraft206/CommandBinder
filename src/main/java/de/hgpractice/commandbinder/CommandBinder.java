package de.hgpractice.commandbinder;

import Commands.CommandBinderCmd;
import Listeners.InventoryListener;
import Listeners.ItemInteractListener;
import Utils.ConfigHandler;
import Utils.Messages;
import Utils.NBTHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class CommandBinder extends JavaPlugin {

    private static CommandBinder instance;

    public static CommandBinder getInstance() {
        return instance;
    }

    private static NBTHandler nbtHandler;
    public static NBTHandler getNbtHandler() {
        return nbtHandler;
    }

    private static ConfigHandler permsConfig;
    private static ConfigHandler messagesConfig;
    public static ConfigHandler getPermsConfig() {
        return permsConfig;
    }

    @Override
    public void onEnable() {
        instance = this;
        nbtHandler = new NBTHandler();

        // ------------------ Configs ------------------ //
        HashMap<String, Object> settings = new HashMap<>();
        settings.put("Cmd-add-permission", "lp user %player% permission set %permission%");
        settings.put("Cmd-remove-permission", "lp user %player% permission unset %permission%");
        permsConfig = new ConfigHandler("CommandBinder", "PermissionSystem", settings);

        HashMap<String, Object> messages = new HashMap<>();
        messages.put("Prefix", "§7» §3CommandBinder §8×§7");
        messages.put("Cmd-Added", "%prefix% Der §3Befehl §7wurde erfolgreich §3hinzugefügt§7!");
        messages.put("Cmd-Removed", "%prefix% Der §3Befehl §7wurde erfolgreich §3entfernt§7!");
        messages.put("Cmd-Inserted", "%prefix% Der §3Befehl §7wurde erfolgreich §3eingefügt§7!");
        messages.put("Perm-Added", "%prefix% Die §3Permission §7wurde erfolgreich §3hinzugefügt§7!");
        messages.put("Perm-Removed", "%prefix% Die §3Permission §7wurde erfolgreich §3entfernt§7!");
        messages.put("OneTimeUse-True", "%prefix% §3OneTimeUse §7wurde erfolgreich auf §3true §7gesetzt!");
        messages.put("OneTimeUse-False", "%prefix% §3OneTimeUse §7wurde erfolgreich auf §3false §7gesetzt!");
        messages.put("Confirm-True", "%prefix% §3Confirm §7wurde erfolgreich auf §3true §7gesetzt!");
        messages.put("Confirm-False", "%prefix% §3Confirm §7wurde erfolgreich auf §3false §7gesetzt!");
        messages.put("List-Header", "%prefix% §3Commands:");
        messages.put("List-Perm-Header", "%prefix% §3Permissions:");
        messages.put("List-Item-Odd", "§7× §9");
        messages.put("List-Item-Even", "§7× §b");
        messages.put("List-Item-Placeholder", " §8» §7");
        messages.put("No-Permissions", "%prefix% §cDazu hast du keine Rechte!");
        messages.put("Invalid-ID", "%prefix% §cDiese §3ID §7existiert nicht!");
        messages.put("No-Cmds-On-Item", "%prefix% Es sind §ckeine §7Befehle auf diesem Item gespeichert!");
        messages.put("No-Item-In-Hand", "%prefix% Du musst ein Item in der Hand halten!");
        messagesConfig = new ConfigHandler("CommandBinder", "Messages", messages);
        // ------------------ Configs ------------------ //

        // ------------------ Commands ------------------ //
        getCommand("commandbinder").setExecutor(new CommandBinderCmd());
        getCommand("commandbinder").setTabCompleter(new CommandBinderCmd());
        // ------------------ Commands ------------------ //

        // ------------------ Listeners ------------------ //
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        // ------------------ Listeners ------------------ //

        // ------------------ Messages ------------------ //
        Messages.prefix = messagesConfig.getConfigSetting("Prefix");
        Messages.cmdAdded = messagesConfig.getConfigSetting("Cmd-Added").replace("%prefix%", Messages.prefix);
        Messages.cmdRemoved = messagesConfig.getConfigSetting("Cmd-Removed").replace("%prefix%", Messages.prefix);
        Messages.cmdInserted = messagesConfig.getConfigSetting("Cmd-Inserted").replace("%prefix%", Messages.prefix);
        Messages.permAdded = messagesConfig.getConfigSetting("Perm-Added").replace("%prefix%", Messages.prefix);
        Messages.permRemoved = messagesConfig.getConfigSetting("Perm-Removed").replace("%prefix%", Messages.prefix);
        Messages.oneTimeUseTrue = messagesConfig.getConfigSetting("OneTimeUse-True").replace("%prefix%", Messages.prefix);
        Messages.oneTimeUseFalse = messagesConfig.getConfigSetting("OneTimeUse-False").replace("%prefix%", Messages.prefix);
        Messages.confirmTrue = messagesConfig.getConfigSetting("Confirm-True").replace("%prefix%", Messages.prefix);
        Messages.confirmFalse = messagesConfig.getConfigSetting("Confirm-False").replace("%prefix%", Messages.prefix);
        Messages.listHeader = messagesConfig.getConfigSetting("List-Header").replace("%prefix%", Messages.prefix);
        Messages.listPermHeader = messagesConfig.getConfigSetting("List-Perm-Header").replace("%prefix%", Messages.prefix);
        Messages.listItemOdd = messagesConfig.getConfigSetting("List-Item-Odd").replace("%prefix%", Messages.prefix);
        Messages.listItemEven = messagesConfig.getConfigSetting("List-Item-Even").replace("%prefix%", Messages.prefix);
        Messages.listItemPlaceholder = messagesConfig.getConfigSetting("List-Item-Placeholder").replace("%prefix%", Messages.prefix);
        Messages.noPerms = messagesConfig.getConfigSetting("No-Permissions").replace("%prefix%", Messages.prefix);
        Messages.invalidId = messagesConfig.getConfigSetting("Invalid-ID").replace("%prefix%", Messages.prefix);
        Messages.noCmds = messagesConfig.getConfigSetting("No-Cmds-On-Item").replace("%prefix%", Messages.prefix);
        Messages.noItem = messagesConfig.getConfigSetting("No-Item-In-Hand").replace("%prefix%", Messages.prefix);
        // ------------------ Messages ------------------ //

    }

    @Override
    public void onDisable() {

    }
}
