import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import main.Main;

public class Bossbar {

	private static HashMap<String, NamespacedKey> ids = new HashMap<>();

	// -----------
	// Create Bars
	// -----------

	public static void createBar(String id, String title, BarColor color, BarStyle style, double percent,
			BarFlag... flags) {
		NamespacedKey key = createKey(id);
		ids.put(id, key);
		Bukkit.createBossBar(key, title, color, style, flags);
		setProgress(id, percent);
	}

	public static void createBar(String title, BarColor color, BarStyle style, double percent, BarFlag... flags) {
		createBar(title, title, color, style, percent, flags);
	}

	public static void createBar(String title, BarColor color, BarStyle style, double percent) {
		createBar(title, color, style, percent, BarFlag.PLAY_BOSS_MUSIC);
	}

	// -----------
	// Add Players
	// -----------

	public static void addPlayer(Player player, String... ids) {
		addPlayers(ids, player);
	}

	public static void addPlayers(String id, Player... players) {
		BossBar bar = getBar(id);
		for (Player player : players) {
			bar.addPlayer(player);
		}
	}

	public static void addPlayers(List<Player> players, String... ids) {
		for (Player player : players) {
			addPlayer(player, ids);
		}
	}

	public static void addPlayers(String[] ids, Player... players) {
		for (String id : ids) {
			addPlayers(id, players);
		}
	}

	@SuppressWarnings("unchecked")
	public static void addAllPlayers(String... ids) {
		addPlayers((List<Player>) Bukkit.getOnlinePlayers(), ids);
	}

	// --------------
	// Remove Players
	// --------------

	public static void removePlayer(Player player, String... ids) {
		removePlayers(ids, player);
	}

	public static void removePlayers(String id, Player... players) {
		BossBar bar = getBar(id);
		for (Player player : players) {
			bar.removePlayer(player);
		}
	}

	public static void removePlayers(String[] ids, Player... players) {
		for (String id : ids) {
			removePlayers(id, players);
		}
	}

	public static void removePlayers(List<Player> players, String... ids) {
		for (Player player : players) {
			removePlayer(player, ids);
		}
	}

	@SuppressWarnings("unchecked")
	public static void removeAllPlayers(String... ids) {
		removePlayers((List<Player>) Bukkit.getOnlinePlayers(), ids);
	}

	// -------
	// HashMap
	// -------

	public static BossBar getBar(String id) {
		if (ids != null && ids.containsKey(id)) {
			return Bukkit.getBossBar(ids.get(id));
		}
		Bukkit.broadcastMessage("§4No BossBar with id=§6" + id + "§4 found");
		return null;
	}

	public static void deleteBars(String... ids) {
		removeAllPlayers(ids);
		for (String id : ids) {
			Bukkit.removeBossBar(Bossbar.ids.get(id));
			Bossbar.ids.remove(id);
		}
	}

	public static void clearBars() {
		for (String id : ids.keySet()) {
			deleteBars(id);
		}
	}

	// -------
	// Setters
	// -------

	public static void setTitle(String id, String title) {
		getBar(id).setTitle(title);
	}

	public static void setColor(String id, BarColor color) {
		getBar(id).setColor(color);
	}

	public static void setStyle(String id, BarStyle style) {
		getBar(id).setStyle(style);
	}

	public static void setProgress(String id, double percent) {
		getBar(id).setProgress(createProgress(percent));
	}

	public static void addFlags(String id, BarFlag... flags) {
		BossBar bar = getBar(id);
		for (BarFlag flag : flags) {
			bar.addFlag(flag);
		}
	}

	public static void removeFlags(String id, BarFlag... flags) {
		BossBar bar = getBar(id);
		for (BarFlag flag : flags) {
			bar.removeFlag(flag);
		}
	}

	// ------------
	// Help methods
	// ------------

	private static double createProgress(double percent) {
		percent /= 100;
		return percent < 0 || percent > 1 ? 0 : percent;
	}

	private static NamespacedKey createKey(String id) {
		String rightId = "";
		for (char c : id.toCharArray()) {
			if ((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123)) {
				rightId += c;
			}
		}
		return new NamespacedKey(Main.instance, rightId);
	}

}
