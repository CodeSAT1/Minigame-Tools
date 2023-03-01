import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemBuilder {

	private ItemStack itemStack;
	private ItemMeta itemMeta;

	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
		itemMeta = itemStack.getItemMeta();
	}

	public ItemBuilder(Material material, int amount) {
		itemStack = new ItemStack(material);
		setAmount(amount);
		itemMeta = itemStack.getItemMeta();
	}

	public ItemBuilder(Material material) {
		this(material, 1);
	}

	public ItemBuilder setType(Material material) {
		itemStack.setType(material);
		return this;
	}

	public ItemBuilder setAmount(int amount) {
		amount = amount < 0 ? 0 : amount > 127 ? 127 : amount;
		itemStack.setAmount(amount);
		return this;
	}

	public ItemBuilder setName(String name) {
		itemMeta.setDisplayName(name);
		return this;
	}

	public ItemBuilder setDamage(int damage) {
		if (itemMeta instanceof Damageable) {
			Damageable damageable = (Damageable) itemMeta;
			damageable.setDamage(damage);
			itemMeta = damageable;
		}
		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {
		itemMeta.setUnbreakable(unbreakable);
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		itemMeta.setLore(lore);
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		return setLore(Arrays.asList(lore));
	}

	public ItemBuilder addLore(List<String> loreItems) {
		loreItems.addAll(itemMeta.getLore());
		return setLore(loreItems);
	}

	public ItemBuilder addLore(String... loreItems) {
		return addLore(Arrays.asList(loreItems));
	}

	public ItemBuilder removeLore(List<String> loreItems) {
		List<String> lore = itemMeta.getLore();
		loreItems.forEach(loreItem -> lore.remove(loreItem));
		return setLore(lore);
	}

	public ItemBuilder removeLore(String... loreItems) {
		return removeLore(Arrays.asList(loreItems));
	}

	public ItemBuilder clearLore() {
		itemMeta.setLore(null);
		return this;
	}

	public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
		itemMeta.addItemFlags(itemFlags);
		return this;
	}

	public ItemBuilder removeItemFlags(ItemFlag... itemFlags) {
		itemMeta.removeItemFlags(itemFlags);
		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchant, int level) {
		itemMeta.addEnchant(enchant, level, true);
		return this;
	}

	public ItemBuilder addEnchants(Enchantment[] enchants, int... levels) {
		for (int i = 0; i < enchants.length; i++) {
			int level = levels == null || levels.length == 0 ? 1
					: i < levels.length ? levels[i] : levels[levels.length - 1];
			addEnchant(enchants[i], level);
		}
		return this;
	}

	public ItemBuilder addEnchants(Enchantment... enchants) {
		return addEnchants(enchants, 1);
	}

	public ItemBuilder removeEnchants(Enchantment... enchants) {
		for (Enchantment enchant : enchants) {
			itemMeta.removeEnchant(enchant);
		}
		return this;
	}

	public ItemBuilder clearEnchants() {
		return removeEnchants((Enchantment[]) itemMeta.getEnchants().keySet().toArray());
	}

	public ItemBuilder setGlowing(boolean glowing) {
		if (glowing) {
			return addEnchant(Enchantment.DURABILITY, 0).addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		return clearEnchants().removeItemFlags(ItemFlag.HIDE_ENCHANTS);
	}

	public ItemBuilder setLeatherColor(Color color) {
		if (itemMeta instanceof LeatherArmorMeta) {
			LeatherArmorMeta leatherMeta = (LeatherArmorMeta) itemMeta;
			leatherMeta.setColor(color);
			itemMeta = leatherMeta;
		}
		return this;
	}

	public ItemBuilder setLeatherColor(int red, int green, int blue) {
		return setLeatherColor(Color.fromRGB(red, green, blue));
	}

	public ItemStack getItem() {
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

}
