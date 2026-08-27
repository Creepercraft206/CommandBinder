package Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class NBTHandler {

    private final ItemStack item;

    public NBTHandler(ItemStack item) {
        this.item = item;
    }

    // ------------------- Commands ------------------- //
    public void addCommand(String cmd) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + getHighestId()), PersistentDataType.STRING, cmd);
            this.item.setItemMeta(itemMeta);
        }
    }

    public void removeCommand(int id) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            ArrayList<String> cmds = getCmdArray();
            if (!cmds.isEmpty() && id > 0 && id <= cmds.size()) {
                cmds.remove(id-1);
                for (int i = 1; i < cmds.size() + 2; i++) {
                    itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbcmd" + i));
                }

                for (int i = 0; i < cmds.size(); i++) {
                    itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + (i + 1)), PersistentDataType.STRING, cmds.get(i));
                }
                this.item.setItemMeta(itemMeta);
            }
        }
    }

    public void insertCommand(int id, String cmd) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            ArrayList<String> cmds = getCmdArray();
            for (int i = cmds.size() - 1; i >= id; i--) {
                itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + (i + 2)), PersistentDataType.STRING, cmds.get(i));
            }
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + (id + 1)), PersistentDataType.STRING, cmd);
            this.item.setItemMeta(itemMeta);
        }
    }

    public void setCommand(int id, String cmd) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcmd" + id), PersistentDataType.STRING, cmd);
            this.item.setItemMeta(itemMeta);
        }
    }

    public int getHighestId() {
        ItemMeta itemMeta = this.item.getItemMeta();
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

    public String getCommand(int id) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbcmd" + id), PersistentDataType.STRING);
        }
        return null;
    }

    public ArrayList<String> getCmdArray() {
        ArrayList<String> cmds = new ArrayList<>();
        ItemMeta itemMeta = this.item.getItemMeta();
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
    public void setOneTimeUseState(boolean state) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbotu"), PersistentDataType.INTEGER, state ? 1 : 0);
            item.setItemMeta(itemMeta);
        }
    }

    public void setConfirmState(boolean state) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbconfirm"), PersistentDataType.INTEGER, state ? 1 : 0);
            item.setItemMeta(itemMeta);
        }
    }

    public boolean getOneTimeUseState() {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbotu"), PersistentDataType.INTEGER)) {
                return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbotu"), PersistentDataType.INTEGER) == 1;
            }
            return false;
        }
        return false;
    }

    public boolean getConfirmState() {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbconfirm"), PersistentDataType.INTEGER)) {
                return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbconfirm"), PersistentDataType.INTEGER) == 1;
            }
            return false;
        }
        return false;
    }

    public void setCooldown(double cooldown) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcooldown"), PersistentDataType.DOUBLE, cooldown);
            item.setItemMeta(itemMeta);
        }
    }

    public void removeCooldown() {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcooldown"), PersistentDataType.DOUBLE)) {
                itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbcooldown"));
                itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbcooldown_start"));
                item.setItemMeta(itemMeta);
            }
        }
    }

    public void startCooldown() {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcooldown"), PersistentDataType.DOUBLE)) {
                itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbcooldown_start"), PersistentDataType.LONG, System.currentTimeMillis());
                item.setItemMeta(itemMeta);
            }
        }
    }

    public boolean isOnCooldown() {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcooldown"), PersistentDataType.DOUBLE) &&
                itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcooldown_start"), PersistentDataType.LONG)) {
                long startTime = itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbcooldown_start"), PersistentDataType.LONG);
                double cooldown = itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbcooldown"), PersistentDataType.DOUBLE);
                return System.currentTimeMillis() - startTime < cooldown * 1000;
            }
        }
        return false;
    }

    public double getRemainingCooldown() {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcooldown"), PersistentDataType.DOUBLE) &&
                itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbcooldown_start"), PersistentDataType.LONG)) {
                return (itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbcooldown_start"), PersistentDataType.LONG) +
                        (itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbcooldown"), PersistentDataType.DOUBLE) * 1000)) - System.currentTimeMillis();
            }
        }
        return 0;
    }
    // ------------------- Options ------------------- //

    // ------------------ Permissions ------------------ //
    public void addPermission(String permission) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbperm" + getHighestPermId()), PersistentDataType.STRING, permission);
            this.item.setItemMeta(itemMeta);
        }
    }

    public boolean removePermission(String permission) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            ArrayList<String> perms = getPermArray();
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

    public String getPermission(int id) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            return itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbperm" + id), PersistentDataType.STRING);
        }
        return null;
    }

    public int getHighestPermId() {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
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

    public ArrayList<String> getPermArray() {
        ArrayList<String> perms = new ArrayList<>();
        ItemMeta itemMeta = this.item.getItemMeta();
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

    // ------------------- Messages ------------------- //
    public void setMessage(MessageType messageType, String message) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.minecraft("cbmsg-" + messageType.getMesssageIdentifier()), PersistentDataType.STRING, message);
            this.item.setItemMeta(itemMeta);
        }
    }

    public void resetMessage(MessageType messageType) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.minecraft("cbmsg-" + messageType.getMesssageIdentifier()), PersistentDataType.STRING)) {
                itemMeta.getPersistentDataContainer().remove(NamespacedKey.minecraft("cbmsg-" + messageType.getMesssageIdentifier()));
                this.item.setItemMeta(itemMeta);
            }
        }
    }

    public @Nullable String getMessage(MessageType messageType) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta != null) {
            String message = itemMeta.getPersistentDataContainer().get(NamespacedKey.minecraft("cbmsg-" + messageType.getMesssageIdentifier()), PersistentDataType.STRING);
            if (message != null) {
                return message.isEmpty() ? null : message;
            }

            return switch (messageType) {
                case CMD_ADDED -> Messages.cmdAdded;
                case CMD_REMOVED -> Messages.cmdRemoved;
                case CMD_INSERTED -> Messages.cmdInserted;
                case CMD_SET -> Messages.cmdSet;
                case ONE_TIME_USE_TRUE -> Messages.oneTimeUseTrue;
                case ONE_TIME_USE_FALSE -> Messages.oneTimeUseFalse;
                case CONFIRM_TRUE -> Messages.confirmTrue;
                case CONFIRM_FALSE -> Messages.confirmFalse;
                case ON_COOLDOWN -> Messages.onCooldown.replace("%remaining%", String.valueOf(getRemainingCooldown() / 1000));
                case COOLDOWN_SET -> Messages.cooldownSet;
                case COOLDOWN_REMOVED -> Messages.cooldownRemoved;
                case NO_PERMS -> Messages.noPerms;
            };
        }
        return null;
    }
    // ------------------- Messages ------------------- //
}
