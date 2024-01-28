package Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class NBTHandler {

    public NBTHandler() {
    }

    public void addCommand(ItemStack item, String cmd) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + getHighestId(item)), PersistentDataType.STRING, cmd);
            item.setItemMeta(itemMeta);
        }
    }

    public void removeCommand(ItemStack item, int id) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            ArrayList<String> cmds = getCmdArray(item);
            if (id < cmds.size()) {
                cmds.remove(id - 1);
            }
            for (int i = 1; i < cmds.size() + 2; i++) {
                itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbcmd" + i));
            }

            for (int i = 0; i < cmds.size(); i++) {
                itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + (i + 1)), PersistentDataType.STRING, cmds.get(i));
            }
            item.setItemMeta(itemMeta);
        }
    }

    public int getHighestId(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            int id = 1;
            while (true) {
                if (!itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcmd" + id), PersistentDataType.STRING)) {
                    return id;
                } else {
                    id++;
                }
            }
        }
        return 0;
    }

    public String getCommand(ItemStack item, int id) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbcmd" + id), PersistentDataType.STRING);
        }
        return null;
    }

    public ArrayList<String> getCmdArray(ItemStack item) {
        ArrayList<String> cmds = new ArrayList<>();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            int id = 1;
            while (true) {
                if (!itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcmd" + id), PersistentDataType.STRING)) {
                    return cmds;
                } else {
                    cmds.add(itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbcmd" + id), PersistentDataType.STRING));
                    id++;
                }
            }
        }
        return null;
    }
}
