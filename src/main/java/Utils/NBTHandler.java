package Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Objects;

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
            itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbcmd" + id));
            item.setItemMeta(itemMeta);

            // Arrange new cmd-order
            int highestId = getHighestId(item);
            for (int i = id; i < highestId; i++) {
                if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcmd" + (i + 1)), PersistentDataType.STRING)) {
                    itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + i), PersistentDataType.STRING, Objects.requireNonNull(getCommand(item, id)));
                    itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbcmd" + (i + 1)));
                    item.setItemMeta(itemMeta);
                }
            }
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
