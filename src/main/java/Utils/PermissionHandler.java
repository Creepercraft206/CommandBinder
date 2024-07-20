package Utils;

import org.bukkit.Bukkit;

import java.util.UUID;

public class PermissionHandler {

    private final ConfigHandler permsConfig;

    public PermissionHandler(ConfigHandler permsConfig) {
        this.permsConfig = permsConfig;
    }

    public void addPermission(UUID uuid, String permission) {
        String cmd = permsConfig.getConfigSetting("Cmd-add-permission");
        cmd = cmd.replace("%player%", Bukkit.getPlayer(uuid).getName());
        cmd = cmd.replace("%permission%", permission);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }

    public void removePermission(UUID uuid, String permission) {
        String cmd = permsConfig.getConfigSetting("Cmd-remove-permission");
        cmd = cmd.replace("%player%", Bukkit.getPlayer(uuid).getName());
        cmd = cmd.replace("%permission%", permission);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
}
