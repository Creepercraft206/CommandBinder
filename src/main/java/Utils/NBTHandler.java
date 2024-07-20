package Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class NBTHandler {

    public NBTHandler() {
    }

    // ------------------- Commands ------------------- //
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
            if (!cmds.isEmpty() && id > 0 && id <= cmds.size()) {
                cmds.remove(id-1);
                for (int i = 1; i < cmds.size() + 2; i++) {
                    itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbcmd" + i));
                }

                for (int i = 0; i < cmds.size(); i++) {
                    itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + (i + 1)), PersistentDataType.STRING, cmds.get(i));
                }
                item.setItemMeta(itemMeta);
            }
        }
    }

    public void insertCommand(ItemStack item, int id, String cmd) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            ArrayList<String> cmds = getCmdArray(item);
            for (int i = cmds.size() - 1; i >= id; i--) {
                itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + (i + 2)), PersistentDataType.STRING, cmds.get(i));
            }
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + (id + 1)), PersistentDataType.STRING, cmd);
            item.setItemMeta(itemMeta);
        }
    }

    public void setCommand(ItemStack item, int id, String cmd) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + id), PersistentDataType.STRING, cmd);
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
    // ------------------- Commands ------------------- //


    // ------------------- Options ------------------- //
    public void setOneTimeUseState(ItemStack item, boolean state) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbotu"), PersistentDataType.INTEGER, state ? 1 : 0);
            item.setItemMeta(itemMeta);
        }
    }

    public void setConfirmState(ItemStack item, boolean state) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbconfirm"), PersistentDataType.INTEGER, state ? 1 : 0);
            item.setItemMeta(itemMeta);
        }
    }

    public boolean getOneTimeUseState(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbotu"), PersistentDataType.INTEGER)) {
                return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbotu"), PersistentDataType.INTEGER) == 1;
            }
            return false;
        }
        return false;
    }

    public boolean getConfirmState(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbconfirm"), PersistentDataType.INTEGER)) {
                return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbconfirm"), PersistentDataType.INTEGER) == 1;
            }
            return false;
        }
        return false;
    }
    // ------------------- Options ------------------- //

    // ------------------ Permissions ------------------ //
    public void addPermission(ItemStack item, String permission) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbperm" + getHighestPermId(item)), PersistentDataType.STRING, permission);
            item.setItemMeta(itemMeta);
        }
    }

    public boolean removePermission(ItemStack item, String permission) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            ArrayList<String> perms = getPermArray(item);
            if (perms.contains(permission)) {
                perms.remove(permission);
                for (int i = 1; i < perms.size() + 2; i++) {
                    itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbperm" + i));
                }
                for (int i = 0; i < perms.size(); i++) {
                    itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbperm" + (i + 1)), PersistentDataType.STRING, perms.get(i));
                }
                item.setItemMeta(itemMeta);
                return true;
            }
        }
        return false;
    }

    public String getPermission(ItemStack item, int id) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbperm" + id), PersistentDataType.STRING);
        }
        return null;
    }

    public int getHighestPermId(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) { //  && itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbperm1"), PersistentDataType.STRING)
            int id = 1;
            while (true) {
                if (!itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbperm" + id), PersistentDataType.STRING)) {
                    return id;
                } else {
                    id++;
                }
            }
        }
        return 0;
    }

    public ArrayList<String> getPermArray(ItemStack item) {
        ArrayList<String> perms = new ArrayList<>();
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            int id = 1;
            while (true) {
                if (!itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbperm" + id), PersistentDataType.STRING)) {
                    return perms;
                } else {
                    perms.add(itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbperm" + id), PersistentDataType.STRING));
                    id++;
                }
            }
        }
        return null;
    }
    // ------------------ Permissions ------------------ //
}
