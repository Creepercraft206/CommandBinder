package Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemCreatorClass {

    /**
     * Creates an ItemStack with the given parameters.
     * @param mat The material of the item.
     * @param amount The amount of the item.
     * @param name The display name of the item.
     * @param unbreakable Whether the item is unbreakable.
     * @param lore The lore of the item.
     * @return The created ItemStack.
     */
    public static ItemStack createItem(Material mat, int amount, String name, boolean unbreakable, String[] lore) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }

        meta.setDisplayName(name);
        if (unbreakable) {
            meta.setUnbreakable(true);
        }
        if (lore != null) {
            meta.setLore(Arrays.asList(lore));
        }

        item.setItemMeta(meta);
        return item;
    }
}