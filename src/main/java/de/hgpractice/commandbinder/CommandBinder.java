package de.hgpractice.commandbinder;

import Commands.CommandBinderCmd;
import Listeners.InventoryListener;
import Listeners.ItemInteractListener;
import Utils.ConfigHandler;
import Utils.Messages;
import Utils.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mariuszgromada.math.mxparser.License;

import java.util.LinkedHashMap;
import java.util.logging.Level;

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
        System.out.println("\n   \u001B[36mCommandBinder \u001B[34mv." + getDescription().getVersion() + "\u001B[0m");
        System.out.println("   \u001B[37mAuthor: \u001B[34m" + getDescription().getAuthors().get(0) + "\u001B[0m\n");
        instance = this;
        nbtHandler = new NBTHandler();

        License.iConfirmNonCommercialUse("Tim Bahlinger");

        // ------------------ Configs ------------------ //
        LinkedHashMap<String, Object> settings = new LinkedHashMap<String, Object>();
        settings.put("Cmd-add-permission", "lp user %player% permission set %permission%");
        settings.put("Cmd-remove-permission", "lp user %player% permission unset %permission%");
        permsConfig = new ConfigHandler("CommandBinder", "PermissionSystem", settings);

        LinkedHashMap<String, Object> messages = new LinkedHashMap<String, Object>();
        messages.put("Prefix", "§7» §3CommandBinder §8×§7");
        messages.put("Cmd-Added", "%prefix% Der §3Befehl §7wurde erfolgreich §3hinzugefügt§7!");
        messages.put("Cmd-Removed", "%prefix% Der §3Befehl §7wurde erfolgreich §3entfernt§7!");
        messages.put("Cmd-Inserted", "%prefix% Der §3Befehl §7wurde erfolgreich §3eingefügt§7!");
        messages.put("Cmd-Set", "%prefix% Der §3Befehl §7wurde erfolgreich §3gesetzt§7!");
        messages.put("Perm-Added", "%prefix% Die §3Permission §7wurde erfolgreich §3hinzugefügt§7!");
        messages.put("Perm-Removed", "%prefix% Die §3Permission §7wurde erfolgreich §3entfernt§7!");
        messages.put("OneTimeUse-True", "%prefix% §3OneTimeUse §7wurde erfolgreich auf §3true §7gesetzt!");
        messages.put("OneTimeUse-False", "%prefix% §3OneTimeUse §7wurde erfolgreich auf §3false §7gesetzt!");
        messages.put("Confirm-True", "%prefix% §3Confirm §7wurde erfolgreich auf §3true §7gesetzt!");
        messages.put("Confirm-False", "%prefix% §3Confirm §7wurde erfolgreich auf §3false §7gesetzt!");
        messages.put("Cooldown-Set", "%prefix% §3Cooldown §7wurde erfolgreich auf §3%cooldown% §7Sekunden gesetzt!");
        messages.put("Cooldown-Removed", "%prefix% §3Cooldown §7wurde erfolgreich entfernt!");
        messages.put("On-Cooldown", "%prefix% §cDu musst noch §3%remaining% §cSekunden warten, bevor du dieses Item erneut verwenden kannst.");
        messages.put("List-Header", "%prefix% §3Commands:");
        messages.put("List-Perm-Header", "%prefix% §3Permissions:");
        messages.put("List-Item-Odd", "§7× §9");
        messages.put("List-Item-Even", "§7× §b");
        messages.put("List-Item-Placeholder", " §8» §7");
        messages.put("No-Permissions", "%prefix% §cDazu hast du keine Rechte!");
        messages.put("Invalid-ID", "%prefix% §cDiese §3ID §cexistiert nicht!");
        messages.put("Invalid-Perm", "%prefix% §cDiese §3Permission §cexistiert nicht auf dem Item!");
        messages.put("Invalid-Cooldown", "%prefix% §cDer §3Cooldown §cmuss eine Zahl sein!");
        messages.put("No-Cmds-On-Item", "%prefix% Es sind §ckeine §7Befehle auf diesem Item gespeichert!");
        messages.put("No-Item-In-Hand", "%prefix% §cDu musst ein Item in der Hand halten!");
        messages.put("Message-Set", "%prefix% Die Message §3%type% §7wurde erfolgreich auf '%message%§r§7' für dieses Item gesetzt!");
        messages.put("Message-Unset", "%prefix% §7Die Message §3%type% §7wurde erfolgreich für dieses Item entfernt!");
        messages.put("Message-Reset", "%prefix% §7Die Message §3%type% §7wurde erfolgreich auf den §3Systemwert §7zurückgesetzt!");
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
        Messages.cmdSet = messagesConfig.getConfigSetting("Cmd-Set").replace("%prefix%", Messages.prefix);
        Messages.permAdded = messagesConfig.getConfigSetting("Perm-Added").replace("%prefix%", Messages.prefix);
        Messages.permRemoved = messagesConfig.getConfigSetting("Perm-Removed").replace("%prefix%", Messages.prefix);
        Messages.oneTimeUseTrue = messagesConfig.getConfigSetting("OneTimeUse-True").replace("%prefix%", Messages.prefix);
        Messages.oneTimeUseFalse = messagesConfig.getConfigSetting("OneTimeUse-False").replace("%prefix%", Messages.prefix);
        Messages.confirmTrue = messagesConfig.getConfigSetting("Confirm-True").replace("%prefix%", Messages.prefix);
        Messages.confirmFalse = messagesConfig.getConfigSetting("Confirm-False").replace("%prefix%", Messages.prefix);
        Messages.cooldownSet = messagesConfig.getConfigSetting("Cooldown-Set").replace("%prefix%", Messages.prefix);
        Messages.cooldownRemoved = messagesConfig.getConfigSetting("Cooldown-Removed").replace("%prefix%", Messages.prefix);
        Messages.onCooldown = messagesConfig.getConfigSetting("On-Cooldown").replace("%prefix%", Messages.prefix);
        Messages.listHeader = messagesConfig.getConfigSetting("List-Header").replace("%prefix%", Messages.prefix);
        Messages.listPermHeader = messagesConfig.getConfigSetting("List-Perm-Header").replace("%prefix%", Messages.prefix);
        Messages.listItemOdd = messagesConfig.getConfigSetting("List-Item-Odd");
        Messages.listItemEven = messagesConfig.getConfigSetting("List-Item-Even");
        Messages.listItemPlaceholder = messagesConfig.getConfigSetting("List-Item-Placeholder");
        Messages.noPerms = messagesConfig.getConfigSetting("No-Permissions").replace("%prefix%", Messages.prefix);
        Messages.invalidId = messagesConfig.getConfigSetting("Invalid-ID").replace("%prefix%", Messages.prefix);
        Messages.invalidPerm = messagesConfig.getConfigSetting("Invalid-Perm").replace("%prefix%", Messages.prefix);
        Messages.invalidCooldown = messagesConfig.getConfigSetting("Invalid-Cooldown").replace("%prefix%", Messages.prefix);
        Messages.noCmds = messagesConfig.getConfigSetting("No-Cmds-On-Item").replace("%prefix%", Messages.prefix);
        Messages.noItem = messagesConfig.getConfigSetting("No-Item-In-Hand").replace("%prefix%", Messages.prefix);
        Messages.messageSet = messagesConfig.getConfigSetting("Message-Set").replace("%prefix%", Messages.prefix);
        Messages.messageUnset = messagesConfig.getConfigSetting("Message-Unset").replace("%prefix%", Messages.prefix);
        Messages.messageReset = messagesConfig.getConfigSetting("Message-Reset").replace("%prefix%", Messages.prefix);
        // ------------------ Messages ------------------ //
    }

    @Override
    public void onDisable() {

    }
}
