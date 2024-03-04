package de.hgpractice.commandbinder;

import Commands.CommandBinderCmd;
import Listeners.InventoryListener;
import Listeners.ItemInteractListener;
import Utils.ConfigHandler;
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
        // ------------------ Configs ------------------ //

        // ------------------ Commands ------------------ //
        getCommand("commandbinder").setExecutor(new CommandBinderCmd());
        getCommand("commandbinder").setTabCompleter(new CommandBinderCmd());
        // ------------------ Commands ------------------ //

        // ------------------ Listeners ------------------ //
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        // ------------------ Listeners ------------------ //
    }

    @Override
    public void onDisable() {

    }
}
