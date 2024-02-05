package Utils;

import Utils.SQL.ConfigHandler;
import Utils.SQL.PermissionSystemHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PermissionHandler {

    private final PermissionSystemHandler pSysHandler;
    private final ConfigHandler permsConfig;

    public PermissionHandler(PermissionSystemHandler pSysHandler, ConfigHandler permsConfig) {
        this.pSysHandler = pSysHandler;
        this.permsConfig = permsConfig;
    }

    public void addPermission(UUID uuid, String permission) {
        String table = permsConfig.getConfigSetting("Table_User_Permissions");
        String[] columns = {
            permsConfig.getConfigSetting("Column_UUID"),
            permsConfig.getConfigSetting("Column_Permissions")
        };
        String[] values = { uuid.toString(), permission };
        pSysHandler.insert(table, columns, values);
    }

    public void removePermission(UUID uuid, String permission) {
        String table = permsConfig.getConfigSetting("Table_User_Permissions");
        String[] columns = {
            permsConfig.getConfigSetting("Column_UUID"),
            permsConfig.getConfigSetting("Column_Permissions")
        };
        String[] values = { uuid.toString(), permission };
        pSysHandler.delete(table, columns, values);
    }
}
