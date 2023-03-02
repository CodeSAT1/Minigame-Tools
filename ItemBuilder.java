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

/**
 * Create a new ItemBuilder instance<br>
 * And string all methods you need for the item creation together<br>
 * To get the created item use the method getItem()
 * 
 * @author CodeSAT
 *
 */
public class ItemBuilder {

	private ItemStack itemStack;
	private ItemMeta itemMeta;

	/**
	 * Create a new ItemBuilder from an already existing ItemStack
	 * 
	 * @param itemStack The input ItemStack
	 */
	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
		itemMeta = itemStack.getItemMeta();
	}

	/**
	 * Create a new ItemBuilder from a Material and set an amount
	 * 
	 * @param material The Material of the new item
	 * @param amount   The amount of the new item <br>
	 *                 Its value is restricted from 0 to 127
	 */
	public ItemBuilder(Material material, int amount) {
		itemStack = new ItemStack(material);
		setAmount(amount);
		itemMeta = itemStack.getItemMeta();
	}

	/**
	 * Create a new ItemBuilder from a Material with standard amount of 1
	 * 
	 * @param material The Material of the new item
	 */
	public ItemBuilder(Material material) {
		this(material, 1);
	}

	/**
	 * Change the type of the item
	 * 
	 * @param material The new type
	 */
	public ItemBuilder setType(Material material) {
		itemStack.setType(material);
		return this;
	}

	/**
	 * Set the amount of the item to the new amount
	 * 
	 * @param amount The new amount of the item <br>
	 *               The value of the amount is restricted from 0 to 127
	 */
	public ItemBuilder setAmount(int amount) {
		amount = amount < 0 ? 0 : amount > 127 ? 127 : amount;
		itemStack.setAmount(amount);
		return this;
	}

	/**
	 * Give the item a custom name
	 * 
	 * @param name The new custom name of the item
	 */
	public ItemBuilder setName(String name) {
		itemMeta.setDisplayName(name);
		return this;
	}

	/**
	 * Set the damage of an item <br>
	 * It only works for items which are able to get damage like tool or armor items
	 * 
	 * @param damage The damage of the item <br>
	 *               The damage is subtracted from the items full durability
	 */
	public ItemBuilder setDamage(int damage) {
		if (itemMeta instanceof Damageable) {
			Damageable damageable = (Damageable) itemMeta;
			damageable.setDamage(damage);
			itemMeta = damageable;
		}
		return this;
	}

	/**
	 * Set the item unbreakable/breakable <br>
	 * Only useful with breakable items like tools or armor
	 * 
	 * @param unbreakable If true the item set to unbreakable <br>
	 *                    If false the item set to breakable
	 */
	public ItemBuilder setUnbreakable(boolean unbreakable) {
		itemMeta.setUnbreakable(unbreakable);
		return this;
	}

	/**
	 * Set the complete lore of the item to the new lore
	 * 
	 * @param lore StringList with the new loreItems
	 */
	public ItemBuilder setLore(List<String> lore) {
		itemMeta.setLore(lore);
		return this;
	}

	/**
	 * Set the complete lore of the item to the new lore
	 * 
	 * @param lore Variable StringArray input with the new loreItems
	 */
	public ItemBuilder setLore(String... lore) {
		return setLore(Arrays.asList(lore));
	}

	/**
	 * Add a list of loreItems to the already existing lore
	 * 
	 * @param loreItems The StringList with the added loreItems
	 */
	public ItemBuilder addLore(List<String> loreItems) {
		loreItems.addAll(itemMeta.getLore());
		return setLore(loreItems);
	}

	/**
	 * Add a array of loreItems to the already existing lore
	 * 
	 * @param loreItems The StringArry with the added loreItems
	 */
	public ItemBuilder addLore(String... loreItems) {
		return addLore(Arrays.asList(loreItems));
	}

	/**
	 * Remove a list of loreItems from the actual lore <br>
	 * Only removes a loreItem if the lore contains such a loreItem
	 * 
	 * @param loreItems The StringList with the loreItems which should be removed
	 */
	public ItemBuilder removeLore(List<String> loreItems) {
		List<String> lore = itemMeta.getLore();
		loreItems.forEach(loreItem -> lore.remove(loreItem));
		return setLore(lore);
	}

	/**
	 * Remove a array of loreItems from the actual lore <br>
	 * Only removes a loreItem if the lore contains such a loreItem
	 * 
	 * @param loreItems The StringArray with the loreItems which should be removed
	 */
	public ItemBuilder removeLore(String... loreItems) {
		return removeLore(Arrays.asList(loreItems));
	}

	/**
	 * Remove the complete lore from the item
	 */
	public ItemBuilder clearLore() {
		itemMeta.setLore(null);
		return this;
	}

	/**
	 * Add a variable array of ItemFlags to the item
	 * 
	 * @param itemFlags The added ItemFlags
	 */
	public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
		itemMeta.addItemFlags(itemFlags);
		return this;
	}

	/**
	 * Remove a variable array if ItemFalgs from the item
	 * 
	 * @param itemFlags The removed ItemFlags
	 */
	public ItemBuilder removeItemFlags(ItemFlag... itemFlags) {
		itemMeta.removeItemFlags(itemFlags);
		return this;
	}

	/**
	 * Remove all ItemFlags from the item
	 */
	public ItemBuilder clearItemFlags() {
		return removeItemFlags((ItemFlag[]) itemMeta.getItemFlags().toArray());
	}

	/**
	 * Add an enchant to the item<br>
	 * The enchant has no max level
	 * 
	 * @param enchant The enchant to add
	 * @param level   The level of the enchant<br>
	 *                It´s not restricted
	 */
	public ItemBuilder addEnchant(Enchantment enchant, int level) {
		itemMeta.addEnchant(enchant, level, true);
		return this;
	}

	/**
	 * Add a array of enchants with a level for each enchant
	 * 
	 * @param enchants The array of enchants to add
	 * @param levels   The array of levels for each enchant<br>
	 *                 If it´s null every enchant is level 1<br>
	 *                 If it´s shorter than the array of enchants the overflow
	 *                 enchants get the last level of the level array
	 */
	public ItemBuilder addEnchants(Enchantment[] enchants, int... levels) {
		for (int i = 0; i < enchants.length; i++) {
			int level = levels == null || levels.length == 0 ? 1
					: i < levels.length ? levels[i] : levels[levels.length - 1];
			addEnchant(enchants[i], level);
		}
		return this;
	}

	/**
	 * Add an array of enchants with each level 1 to the item
	 * 
	 * @param enchants The array of enchants to add
	 */
	public ItemBuilder addEnchants(Enchantment... enchants) {
		return addEnchants(enchants, 1);
	}

	/**
	 * Removing an array of enchants from the item
	 * 
	 * @param enchants The array of enchants which should be removed
	 */
	public ItemBuilder removeEnchants(Enchantment... enchants) {
		for (Enchantment enchant : enchants) {
			itemMeta.removeEnchant(enchant);
		}
		return this;
	}

	/**
	 * Removing all enchants from the item
	 */
	public ItemBuilder clearEnchants() {
		return removeEnchants((Enchantment[]) itemMeta.getEnchants().keySet().toArray());
	}

	/**
	 * Set the item glowing or removes the glow
	 * 
	 * @param glowing If true add an enchant to the item with no effect and the
	 *                ItemFlag HIDE_ENCHANTS<br>
	 *                If false removing all enchants from the item
	 */
	public ItemBuilder setGlowing(boolean glowing) {
		if (glowing) {
			return addEnchant(Enchantment.DURABILITY, 0).addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		return clearEnchants().removeItemFlags(ItemFlag.HIDE_ENCHANTS);
	}

	/**
	 * If the item is a type of leather armor set the color of the leather armor to
	 * the new color
	 * 
	 * @param color The new color of the leather armor
	 */
	public ItemBuilder setLeatherColor(Color color) {
		if (itemMeta instanceof LeatherArmorMeta) {
			LeatherArmorMeta leatherMeta = (LeatherArmorMeta) itemMeta;
			leatherMeta.setColor(color);
			itemMeta = leatherMeta;
		}
		return this;
	}

	/**
	 * If the item is a type of leather armor set the color of the leather armor to
	 * a new color represented by the rgb code
	 * 
	 * @param red   value of red
	 * @param green value of green
	 * @param blue  value of blue
	 */
	public ItemBuilder setLeatherColor(int red, int green, int blue) {
		return setLeatherColor(Color.fromRGB(red, green, blue));
	}

	/**
	 * Gives you the created item back as an ItemStack
	 * 
	 * @return The created ItemStack
	 */
	public ItemStack getItem() {
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
}
