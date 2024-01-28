package de.hgpractice.commandbinder;

import Commands.CommandBinderCmd;
import Listeners.ItemInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandBinder extends JavaPlugin {

    private static CommandBinder instance;

    public static CommandBinder getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // ------------------ Commands ------------------ //
        getCommand("commandbinder").setExecutor(new CommandBinderCmd());
        // ------------------ Commands ------------------ //

        // ------------------ Listeners ------------------ //
        getServer().getPluginManager().registerEvents(new ItemInteractListener(), this);
        // ------------------ Listeners ------------------ //

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
