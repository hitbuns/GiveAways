package com.Kotori;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGiveAway implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && !sender.hasPermission("admin.access")) {
			((Player)sender).sendMessage(utils.TS(Main.prefix+"&cYou do not have permission to run this command"));
			return true;
		}
		if (args.length != 4) {
			sender.sendMessage(utils.TS(Main.error+"&cUsage: /gadmin <id> <amount> <time> <amount_of_winners>"));
			return true;
		}
		if (Main.fm.b.containsKey(args[0])) {
			sender.sendMessage(utils.TS(Main.error+"&cThe id for this giveaway already exists! Please choose another one"));
			return true;
		}
		double amount = 0;
		try {
			amount = Double.parseDouble(args[1]);
		} catch (Exception e) {
			sender.sendMessage(utils.TS(Main.error+"&cPlease provide a numerical value for argument 1"));
			sender.sendMessage(utils.TS(Main.error+"&cUsage: /gadmin <id> <amount> <time> <amount_of_winners>"));
			return true;
		}
		long differencetocheck = utils.convertDateFormattoLong(args[2]);
		if (differencetocheck == -1L) {
			sender.sendMessage(utils.TS(Main.error+"&cPlease provide a date format in dhms format for argument 2 e.g. 1d3h5m5s or 24h or 50m or 1h3m"));
			return true;
		}
		long timestamp = System.currentTimeMillis();
		int winners = 1;
		try {
			winners = Integer.parseInt(args[3]);
		} catch (Exception e) {
			sender.sendMessage(utils.TS(Main.error+"&cPlease provide a numerical value for argument 3"));
			sender.sendMessage(utils.TS(Main.error+"&cUsage: /gadmin <id> <amount> <time> <amount_of_winners>"));
			return true;
		}
		String id = args[0];
		Main.fm.setupnewGiveawayFile(id);
		Main.fm.b.get(id).set("Timestamp", timestamp);
		Main.fm.b.get(id).set("TimespanParsed", differencetocheck);
		Main.fm.b.get(id).set("TimespanDHMSFormat", args[2]);
		Main.fm.b.get(id).set("Winners", winners);
		Main.fm.b.get(id).set("Reward", amount);
		Main.fm.b.get(id).set("TaskID", utils.compileTasks(id));
		Main.fm.saveResource(id);
		Main.Instance.getServer().broadcastMessage(utils.TS(Main.prefix+"&eA new giveaway has been generated under ID &a"+id
				+" &ethat will draw in &a"+args[2] +" &ewith a reward of &a$"+amount+" &efor &a"+winners+" &ewinners"));
		return true;
	}
	
	
	
}
