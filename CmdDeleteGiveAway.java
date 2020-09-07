package com.Kotori;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CmdDeleteGiveAway implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (!sender.hasPermission("admin.access")) {
				sender.sendMessage(utils.TS(Main.error+"&cYou do not have permission to run this command!"));
				return true;
			}
		}
		if (args.length != 1) {
			sender.sendMessage(utils.TS(Main.error+"&cUsage: /gdelete <id>"));
			sender.sendMessage(utils.TS("&eList of Active Giveaway ID's"));
			Set<Entry<String, FileConfiguration>> t = Main.fm.b.entrySet();
			int counter = 0;
			for (Map.Entry<String, FileConfiguration> f : t) {
				counter++;
				sender.sendMessage(utils.TS("&8[&6"+counter+"&8] &e ID: "+f.getKey()+" Reward: &a$"+f.getValue().getDouble("Reward")+"&e Time until giveaway: &a"+utils.convertlongtodate(f.getValue().getLong("TimespanParsed")-(System.currentTimeMillis()-f.getValue().getLong("Timestamp")))));				
			}
			if (t.size() <= 0) sender.sendMessage(utils.TS(Main.error+"&eThere are currently no active giveaways. There is nothing to delete"));
			return true;
		}
		if (!Main.fm.b.containsKey(args[0])) {
			sender.sendMessage(utils.TS(Main.error+"&cThere is no active giveaway with that ID to delete"));
			return true;
		}
		utils.deleteGiveAway(args[0]);
		sender.sendMessage(utils.TS(Main.prefix+"&cYou have deleted the giveaway &a"+args[0]));
		return true;
	}

}
