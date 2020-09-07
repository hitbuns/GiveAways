package com.Kotori;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class utils {
	
	public static String TS(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static void listGiveAways(Player p) {
		p.sendMessage(utils.TS("&eList of Active Giveaway ID's"));
		Set<Entry<String, FileConfiguration>> t = Main.fm.b.entrySet();
		int counter = 0;
		for (Map.Entry<String, FileConfiguration> f : t) {
			counter++;
			if (f.getValue().getStringList("Players") != null && f.getValue().getStringList("Players").contains(p.getUniqueId().toString())) {
				p.sendMessage(utils.TS("&8[&6"+counter+"&8] &8[&a&lJOINED&8] &e ID: "+f.getKey()+" Reward: &a$"+f.getValue().getDouble("Reward")+"&e Time until giveaway: &a"+utils.convertlongtodate(f.getValue().getLong("TimespanParsed")-(System.currentTimeMillis()-f.getValue().getLong("Timestamp")))));
			} else {
				p.sendMessage(utils.TS("&8[&6"+counter+"&8] &8[&7&lJOINABLE&8] &e ID: "+f.getKey()+" Reward: &a$"+f.getValue().getDouble("Reward")+"&e Time until giveaway: &a"+utils.convertlongtodate(f.getValue().getLong("TimespanParsed")-(System.currentTimeMillis()-f.getValue().getLong("Timestamp")))));
			}
		}
		if (t.size() <= 0) p.sendMessage(utils.TS(Main.error+"&eThere are currently no active giveaways. Please try again later"));
	}
	
	public static void listGiveAways(CommandSender p) {
		p.sendMessage(utils.TS("&eList of Active Giveaway ID's"));
		Set<Entry<String, FileConfiguration>> t = Main.fm.b.entrySet();
		int counter = 0;
		for (Map.Entry<String, FileConfiguration> f : t) {
			counter++;
			p.sendMessage(utils.TS("&8[&6"+counter+"&8] &e ID: "+f.getKey()+" Reward: &a$"+f.getValue().getDouble("Reward")+"&e Time until giveaway: &a"+utils.convertlongtodate(f.getValue().getLong("TimespanParsed")-(System.currentTimeMillis()-f.getValue().getLong("Timestamp")))));
		}
		if (t.size() <= 0) p.sendMessage(utils.TS(Main.error+"&eThere are currently no active giveaways. Please try again later"));
	}

	
	public static long convertDateFormattoLong(String target) {
		int DAYS = 0;
		int HOURS = 0;
		int MINUTES = 0;
		int SECONDS = 0;
		String slit =target;
		if (slit.contains("d")) {
			String[] split = slit.split("d");
			try {
				DAYS = (Integer.parseInt(split[0]) >= 0) ? Integer.parseInt(split[0]) : 0;
			}catch(Exception e) {
				return -1L;
			}
			slit = slit.replace(split[0]+"d", "");
		}
		if (slit.contains("h")) {
			String[] split = slit.split("h");
			try {
				HOURS = (Integer.parseInt(split[0]) >= 0) ? Integer.parseInt(split[0]) : 0;
			}catch(Exception e) {
				return -1L;
			}
			slit = slit.replace(split[0]+"h", "");
		}
		if (slit.contains("m")) {
			String[] split = slit.split("m");
			try {
				MINUTES = (Integer.parseInt(split[0]) >= 0) ? Integer.parseInt(split[0]) : 0;
			}catch(Exception e) {
				return -1L;
			}
			slit = slit.replace(split[0]+"m", "");
		}
		if (slit.contains("s")) {
			String[] split = slit.split("s");
			try {
				SECONDS = (Integer.parseInt(split[0]) >= 0) ? Integer.parseInt(split[0]) : 0;
			}catch(Exception e) {
				return -1L;
			}
			slit = slit.replace(split[0]+"s", "");
		}
		if (DAYS == 0 && HOURS == 0 && MINUTES == 0 && SECONDS == 0) return -1L;
		long days = TimeUnit.DAYS.toMillis(DAYS);
		long hours = TimeUnit.HOURS.toMillis(HOURS);
		long minutes = TimeUnit.MINUTES.toMillis(MINUTES);
		long seconds = TimeUnit.SECONDS.toMillis(SECONDS);
		return days + hours + minutes + seconds;
	}
	
	public static String convertlongtodate(long l1) {
		int day = (int) TimeUnit.MILLISECONDS.toDays(l1);
        long hours = TimeUnit.MILLISECONDS.toHours(l1) - (day * 24);
        long minute = TimeUnit.MILLISECONDS.toMinutes(l1) - (TimeUnit.MILLISECONDS.toHours(l1) * 60);
        long second = TimeUnit.MILLISECONDS.toSeconds(l1) - (TimeUnit.MILLISECONDS.toMinutes(l1) * 60);
        String DAYS = "";
        String HOURS = "";
        String MINUTES = "";
        String SECONDS = "00s";
        if (day >=1) {
        	DAYS = day + "d";
        }
        if (hours >=1) {
        	HOURS = hours + "h";
        }
        if (minute >=1) {
        	MINUTES = minute + "m";
        }
        if (second >=1) {
        	if (second<10) {
        		SECONDS = "0"+second+"s";
        	} else {
        		SECONDS = second + "s";
        	}
        }
        return DAYS + HOURS + MINUTES + SECONDS;
	}
	
	public static double getRNG_Double(Double min,Double max) {
		double range = max-min;
		double random = (Math.random() * range) + min;
		return random;
	}
	
	public static int getRNG_Int(Integer min,Integer max) {
		Integer range = max-min;
		Integer random = (int) Math.round((Math.random() * range) + min);
		return random;
	}
	
	
	public static void clearTask(String id) {
		List<Integer> ints = (Main.fm.b.get(id).getIntegerList("TaskID") == null) ? new ArrayList<Integer>() : Main.fm.b.get(id).getIntegerList("TaskID");
		if (!ints.isEmpty()) {
			for (int element : ints) {
				Main.Instance.getServer().getScheduler().cancelTask(element);
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static List<Integer> compileTasks(String id) {
		clearTask(id);
		long timestamp = Main.fm.b.get(id).getLong("Timestamp");
		long system = System.currentTimeMillis();
		long diff = Main.fm.b.get(id).getLong("TimespanParsed");
		long calc = diff-(system-timestamp);
		long t = TimeUnit.MILLISECONDS.toSeconds(calc)*20;
		List<Integer> taskid = new ArrayList<Integer> () {{
			add(Main.Instance.getServer().getScheduler().runTaskLater(Main.Instance, new Runnable() {

				@Override
				public void run() {
					List<String> uuid = Main.fm.b.get(id).getStringList("Players");
					int w = Main.fm.b.get(id).getInt("Winners",1);
					double reward = Main.fm.b.get(id).getInt("Reward",0);
					List<OfflinePlayer> winwinchickendinner = new ArrayList<OfflinePlayer>() {{
						for (int i = 0;i < w;i++)  {
							if (uuid.size() <=0) break;
							int random = utils.getRNG_Int(0, uuid.size()-1);
							add(Main.Instance.getServer().getOfflinePlayer(UUID.fromString(uuid.get(random))));
							uuid.remove(random);
						}
					}};
					if (winwinchickendinner.size() > 0) {
						Main.Instance.getServer().broadcastMessage(utils.TS("&eThe giveaway for &a$"+reward+" &ewas drawed and the winners are:"));
						for (OfflinePlayer p : winwinchickendinner) {
							Main.Instance.getServer().broadcastMessage(utils.TS("&f* &6"+p.getName()));
							Main.economy.depositPlayer(p, reward);
						}
					} else {
						Main.Instance.getServer().broadcastMessage(utils.TS("&cNoone joined the previous giveaway for &a$"+reward));
					}
					deleteGiveAway(id);
				}
				
			}, t).getTaskId());
			for (int i = 5; i > 0; i--) {
				if (t<i*20L) break;
				final int b = i;
				long delay = t-i*20L;
				add(Main.Instance.getServer().getScheduler().runTaskLater(Main.Instance, new Runnable(){

					@Override
					public void run() {
						Main.Instance.getServer().broadcastMessage(utils.TS("&eGiveaway &a"+id+" &ewill be drawed in &a"+b+"&e seconds"));
					}
					
				}, delay).getTaskId());
			}
			if (t>=200L) {
				long delay = t-200L;
				add(Main.Instance.getServer().getScheduler().runTaskLater(Main.Instance, new Runnable(){
	
					@Override
					public void run() {
						Main.Instance.getServer().broadcastMessage(utils.TS("&eGiveaway &a"+id+" &ewill be drawed in &a10&e seconds"));
					}
					
				}, delay).getTaskId());
			}
		}};
		return taskid;
	}
	
	
	public static void deleteGiveAway(String id) {
		if (Main.fm.a.containsKey(id)) {
			Main.fm.a.get(id).delete();
			Main.fm.a.remove(id);
		}
		if (Main.fm.b.containsKey(id)) {
			Main.fm.b.remove(id);
		}
	}
}
