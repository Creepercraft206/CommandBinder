package Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemCreatorClass {

    public static ItemStack createItem(Material mat, int amount, String name, boolean unbreakable, String[] lore) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if(unbreakable)
            meta.setUnbreakable(true);
        if (lore != null)
            meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }
}