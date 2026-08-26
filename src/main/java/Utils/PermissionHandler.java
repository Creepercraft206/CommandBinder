package Utils;

import org.bukkit.Bukkit;

import java.util.UUID;

public class PermissionHandler {

    private final String cmdAddPerm;
    private final String cmdRemovePerm;

    public PermissionHandler(ConfigHandler permsConfig) {
        cmdAddPerm = permsConfig.getConfigSetting("Cmd-add-permission");
        cmdRemovePerm = permsConfig.getConfigSetting("Cmd-remove-permission");
    }

    public void addPermission(UUID uuid, String permission) {
        String cmd = cmdAddPerm;
        cmd = cmd.replace("%player%", Bukkit.getPlayer(uuid).getName());
        cmd = cmd.replace("%permission%", permission);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }

    public void removePermission(UUID uuid, String permission) {
        String cmd = cmdRemovePerm;
        cmd = cmd.replace("%player%", Bukkit.getPlayer(uuid).getName());
        cmd = cmd.replace("%permission%", permission);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
}
