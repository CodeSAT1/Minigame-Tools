import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import main.Main;

/**
 * Create a BossBar with the 3 create methods depends on how much information
 * your bar needs <br>
 * Add players to bars and remove players from bars, so a player can see a bar
 * or not <br>
 * Change attributes of a bar with the setter methods (use the right id)
 * 
 * @author CodeSAT
 * @version 1.02
 *
 */
public class Bossbar {

	/**
	 * A HashMap for storing all BossBars with names, each name for one
	 * NamespacedKeys, each key leads to a BossBar
	 */
	private static HashMap<String, NamespacedKey> ids = new HashMap<>();

	// -----------
	// Create Bars
	// -----------

	/**
	 * Create a BossBar with attributes:
	 * 
	 * @param id      The BossBars id, you cannot change it afterwards
	 * @param title   The title which stand above the BossBar
	 * @param color   The color of the BossBar
	 * @param style   The style of the BossBar
	 * @param percent The fill-progress of the BossBar in percent, 0-100 (0 and 100
	 *                included) <br>
	 *                If it´s less than 0 the progress set to 0<br>
	 *                If it´s more than 100 the progress set to 100
	 * @param flags   The array of flags added to the BossBar
	 */
	public static void createBar(String id, String title, BarColor color, BarStyle style, double percent,
			BarFlag... flags) {
		NamespacedKey key = createKey(id);

		ids.put(id, key);
		Bukkit.createBossBar(key, title, color, style, flags);
		setProgress(id, percent);
	}

	/**
	 * Create a BossBar with attributes:
	 * 
	 * @param title   The title which stand above the BossBar<br>
	 *                The title is used as the BossBars id<br>
	 *                WARNING: To get the BossBar from the id you have to use the
	 *                complete title
	 * @param color   The color of the BossBar
	 * @param style   The style of the BossBar
	 * @param percent The fill-progress of the BossBar in percent, 0-100 (0 and 100
	 *                included) <br>
	 *                If it´s less than 0 the progress set to 0<br>
	 *                If it´s more than 100 the progress set to 100
	 * @param flags   The array of flags added to the BossBar
	 */
	public static void createBar(String title, BarColor color, BarStyle style, double percent, BarFlag... flags) {
		createBar(title, title, color, style, percent, flags);
	}

	/**
	 * Create a BossBar with attributes:
	 * 
	 * @param title   The title which stand above the BossBar<br>
	 *                The title is used as the BossBars id<br>
	 *                WARNING: To get the BossBar from the id you have to use the
	 *                complete title
	 * @param color   The color of the BossBar
	 * @param style   The style of the BossBar
	 * @param percent The fill-progress of the BossBar in percent, 0-100 (0 and 100
	 *                included) <br>
	 *                If it´s less than 0 the progress set to 0<br>
	 *                If it´s more than 100 the progress set to 100
	 */
	public static void createBar(String title, BarColor color, BarStyle style, double percent) {
		createBar(title, color, style, percent, BarFlag.PLAY_BOSS_MUSIC);
	}

	// -----------
	// Add Players
	// -----------

	/**
	 * Add and array of BossBars to one player
	 * 
	 * @param player The player who see the BossBars after this method
	 * @param ids    The id's of the BossBars added to the player
	 */
	public static void addPlayer(Player player, String... ids) {
		addPlayers(ids, player);
	}

	/**
	 * Add one BossBar to an array of players
	 * 
	 * @param id      The BossBar added to the players
	 * @param players The players who will see the BossBar after this method
	 */
	public static void addPlayers(String id, Player... players) {
		BossBar bar = getBar(id);
		if (bar == null) {
			return;
		}
		for (Player player : players) {
			bar.addPlayer(player);
		}
	}

	/**
	 * Add an array of BossBars to an array of players
	 * 
	 * @param ids     The added array of BossBar id´s
	 * @param players The players who receive the BossBars
	 */
	public static void addPlayers(String[] ids, Player... players) {
		for (String id : ids) {
			addPlayers(id, players);
		}
	}

	/**
	 * Add an array of BossBars to a list of players
	 * 
	 * @param players The player list
	 * @param ids     The added array of BossBar id´s
	 */
	public static void addPlayers(List<Player> players, String... ids) {
		for (Player player : players) {
			addPlayer(player, ids);
		}
	}

	/**
	 * Add an array of BossBars to all online Players
	 * 
	 * @param ids The array of BossBar id´s
	 */
	@SuppressWarnings("unchecked")
	public static void addAllPlayers(String... ids) {
		addPlayers((List<Player>) Bukkit.getOnlinePlayers(), ids);
	}

	// --------------
	// Remove Players
	// --------------

	/**
	 * Remove and array of BossBars from one player
	 * 
	 * @param player The player who can´t see the BossBars after this method
	 * @param ids    The id's of the BossBars removed from the player
	 */
	public static void removePlayer(Player player, String... ids) {
		removePlayers(ids, player);
	}

	/**
	 * Remove one BossBar from an array of players
	 * 
	 * @param id      The BossBar removed from the players
	 * @param players The players who won´t see the BossBar after this method
	 */
	public static void removePlayers(String id, Player... players) {
		BossBar bar = getBar(id);
		if (bar == null) {
			return;
		}
		for (Player player : players) {
			bar.removePlayer(player);
		}
	}

	/**
	 * Remove an array of BossBars from an array of players
	 * 
	 * @param ids     The removed array of BossBar id´s
	 * @param players The players who can´t see the BossBars afterwards
	 */
	public static void removePlayers(String[] ids, Player... players) {
		for (String id : ids) {
			removePlayers(id, players);
		}
	}

	/**
	 * Remove an array of BossBars from a list of players
	 * 
	 * @param players The player list
	 * @param ids     The removed array of BossBar id´s
	 */
	public static void removePlayers(List<Player> players, String... ids) {
		for (Player player : players) {
			removePlayer(player, ids);
		}
	}

	/**
	 * Remove an array of BossBars from all online Players
	 * 
	 * @param ids The array of BossBar id´s
	 */
	@SuppressWarnings("unchecked")
	public static void removeAllPlayers(String... ids) {
		removePlayers((List<Player>) Bukkit.getOnlinePlayers(), ids);
	}

	// -------
	// HashMap
	// -------

	/**
	 * Get a BossBar attached to an id
	 * 
	 * @param id The id of the BossBar that you want
	 * @return The BossBar, if a BossBar is attached to the id <br>
	 *         null, if no BossBar is attached to the id
	 */
	public static BossBar getBar(String id) {
		if (ids != null && ids.containsKey(id)) {
			return Bukkit.getBossBar(ids.get(id));
		}
		Bukkit.broadcastMessage("§4No BossBar with id=§6" + id + "§4 found");
		return null;
	}

	/**
	 * Delete an array of BossBars
	 * 
	 * @param ids The array of BossBar id´s
	 */
	public static void deleteBars(String... ids) {
		removeAllPlayers(ids);
		for (String id : ids) {
			if (Bossbar.ids.containsKey(id)) {
				Bukkit.removeBossBar(Bossbar.ids.get(id));
				Bossbar.ids.remove(id);
			}
		}
	}

	/**
	 * Delete all BossBars<br>
	 * If you use BossBars it´s good to put this method in the onDisable() method
	 */
	public static void clearBars() {
		Set<String> ids = new HashSet<>(Bossbar.ids.keySet());
		ids.forEach(s -> deleteBars(s));
	}

	// -------
	// Setters
	// -------

	/**
	 * Set the title of the BossBar connected to the id to the new title
	 * 
	 * @param id    The id of the BossBar
	 * @param title The new title
	 */
	public static void setTitle(String id, String title) {
		BossBar bar = getBar(id);
		if (bar != null) {
			bar.setTitle(title);
		}
	}

	/**
	 * Set the color of the BossBar connected to the id to the new color
	 * 
	 * @param id    The id of the BossBar
	 * @param color The new color
	 */
	public static void setColor(String id, BarColor color) {
		BossBar bar = getBar(id);
		if (bar != null) {
			bar.setColor(color);
		}
	}

	/**
	 * Set the style of the BossBar connected to the id to the new style
	 * 
	 * @param id    The id of the BossBar
	 * @param style The new style
	 */
	public static void setStyle(String id, BarStyle style) {
		BossBar bar = getBar(id);
		if (bar != null) {
			bar.setStyle(style);
		}
	}

	/**
	 * Set the progress of the BossBar connected to the id to the new progress in
	 * percent (0-100)
	 * 
	 * @param id       The id of the BossBar
	 * @param progress The fill-progress of the BossBar in percent, 0-100 (0 and 100
	 *                 included) <br>
	 *                 If it´s less than 0 the progress set to 0<br>
	 *                 If it´s more than 100 the progress set to 100
	 */
	public static void setProgress(String id, double percent) {
		BossBar bar = getBar(id);
		if (bar != null) {
			bar.setProgress(createProgress(percent));
		}
	}

	/**
	 * Add an array of flags to an BossBar
	 * 
	 * @param id    The id of the BossBar
	 * @param flags The flags added to the BossBar
	 */
	public static void addFlags(String id, BarFlag... flags) {
		BossBar bar = getBar(id);
		if (bar == null) {
			return;
		}
		for (BarFlag flag : flags) {
			bar.addFlag(flag);
		}
	}

	/**
	 * Remove an array of flags from an BossBar
	 * 
	 * @param id    The id of the BossBar
	 * @param flags The flags removed from the BossBar
	 */
	public static void removeFlags(String id, BarFlag... flags) {
		BossBar bar = getBar(id);
		if (bar == null) {
			return;
		}
		for (BarFlag flag : flags) {
			bar.removeFlag(flag);
		}
	}

	// ------------
	// Help methods
	// ------------

	/**
	 * Create a fill-progress for a BossBar between 0 to 1 (0 and 1 included)
	 * 
	 * @param percent The input percent
	 * @return The progress converted from the input percent
	 */
	private static double createProgress(double percent) {
		percent /= 100;
		return percent < 0 ? 0 : percent > 1 ? 1 : percent;
	}

	/**
	 * Create a new NamespacedKey from an id
	 * 
	 * @param id The id of the NamespacedKey
	 * @return A valid NamespacedKey
	 */
	private static NamespacedKey createKey(String id) {
		return new NamespacedKey(Main.instance, getValidId(id));
	}

	/**
	 * Create a valid id for a NamespacedKey from an input id
	 * 
	 * @param id The id
	 * @return A valid id for a NamespacedKey
	 */
	private static String getValidId(String id) {
		String validId = "";
		for (char c : id.toCharArray()) {
			if ((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123)) {
				validId += c;
			}
		}
		return validId;
	}

}
