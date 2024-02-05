package de.hgpractice.commandbinder;

import Commands.CommandBinderCmd;
import Listeners.ItemInteractListener;
import Utils.SQL.ConfigHandler;
import Utils.NBTHandler;
import Utils.SQL.PermissionSystemHandler;
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
    public static ConfigHandler getPermsConfig() {
        return permsConfig;
    }

    private static PermissionSystemHandler pSysHandler;
    public static PermissionSystemHandler getPermissionSystemHandler() {
        return pSysHandler;
    }

    @Override
    public void onEnable() {
        instance = this;
        nbtHandler = new NBTHandler();

        // ------------------ Configs ------------------ //
        HashMap<String, Object> settings = new HashMap<>();
        settings.put("Host", "localhost");
        settings.put("Port", "3306");
        settings.put("Database", "yourDatabase");
        settings.put("Username", "yourUsername");
        settings.put("Password", "yourPassword");
        settings.put("Table_User_Permissions", "yourTable");
        settings.put("Column_UUID", "yourColumn");
        settings.put("Column_Permissions", "yourColumn");
        permsConfig = new ConfigHandler("CommandBinder", "PermissionSystem", settings);
        // ------------------ Configs ------------------ //

        // ------------- Permission System ------------- //
        pSysHandler = new PermissionSystemHandler(
                permsConfig.getConfigSetting("Host"),
                permsConfig.getConfigSetting("Port"),
                permsConfig.getConfigSetting("Database"),
                permsConfig.getConfigSetting("Username"),
                permsConfig.getConfigSetting("Password")
        );
        // ------------- Permission System ------------- //

        // ------------------ Commands ------------------ //
        getCommand("commandbinder").setExecutor(new CommandBinderCmd());
        getCommand("commandbinder").setTabCompleter(new CommandBinderCmd());
        // ------------------ Commands ------------------ //

        // ------------------ Listeners ------------------ //
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        // ------------------ Listeners ------------------ //
    }

    @Override
    public void onDisable() {
        pSysHandler.close();
    }
}
