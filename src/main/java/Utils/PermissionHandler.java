package Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PermissionHandler {

    private final String cmdAddPerm;
    private final String cmdRemovePerm;

    /**
     * The PermissionHandler adds and removes permissions based on the set commands of the config keys
     * `Cmd-add-permission` and `Cmd-remove-permission`.
     * @param permsConfig The config to use for the permission commands.
     */
    public PermissionHandler(ConfigHandler permsConfig) {
        cmdAddPerm = permsConfig.getConfigSetting("Cmd-add-permission");
        cmdRemovePerm = permsConfig.getConfigSetting("Cmd-remove-permission");
    }

    /**
     * Adds a permission to a player.
     * @param p The player to add the permission to.
     * @param permission The permission to add.
     */
    public void addPermission(Player p, String permission) {
        String cmd = cmdAddPerm;
        cmd = cmd.replace("%player%", p.getName());
        cmd = cmd.replace("%permission%", permission);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }

    /**
     * Removes a permission from a player.
     * @param p The player to remove the permission from.
     * @param permission The permission to remove.
     */
    public void removePermission(Player p, String permission) {
        String cmd = cmdRemovePerm;
        cmd = cmd.replace("%player%", p.getName());
        cmd = cmd.replace("%permission%", permission);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
}
