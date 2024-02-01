package de.hgpractice.commandbinder;

import Commands.CommandBinderCmd;
import Listeners.ItemInteractListener;
import Listeners.JoinListener;
import Utils.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class CommandBinder extends JavaPlugin {

    private static CommandBinder instance;

    public static CommandBinder getInstance() {
        return instance;
    }

    public HashMap<Player, NBTHandler> nbtHandlers = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        // ------------------ Commands ------------------ //
        getCommand("commandbinder").setExecutor(new CommandBinderCmd());
        // ------------------ Commands ------------------ //

        // ------------------ Listeners ------------------ //
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        // ------------------ Listeners ------------------ //

        // --------- Refresh sessions after reload -------- //
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("commandbinder.add") || all.hasPermission("commandbinder.remove") || all.hasPermission("commandbinder.list") || all.hasPermission("commandbinder.insert")) {
                CommandBinder.getInstance().nbtHandlers.put(all, new NBTHandler());
            }
        }
        // --------- Refresh sessions after reload -------- //
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
