package Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigHandler {

    private final String dirName;
    private final String fileName;
    private final HashMap<String, Object> configSettings;


    public ConfigHandler(String dirName, String fileName, HashMap<String, Object> configSettings) {
        this.dirName = dirName;
        this.fileName = fileName;
        this.configSettings = configSettings;
        createConfig();
    }

    private void createConfig() {
        File dir = new File("plugins//" + dirName + "//");
        File file = new File("plugins//" + dirName + "//" + fileName + ".yml");
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        for (String setting : configSettings.keySet()) {
            if (cfg.get(setting) == null) {
                cfg.set(setting, configSettings.get(setting));
            }
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConfigSetting(String setting) {
        File file = new File("plugins//" + dirName + "//" + fileName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getString(setting);
    }

    public void setConfigSetting(String setting, Object value) {
        File file = new File("plugins//" + dirName + "//" + fileName + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(setting, value);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
