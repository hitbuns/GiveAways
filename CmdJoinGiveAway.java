package com.Kotori;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdJoinGiveAway implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return true;
		Player p = (Player) sender;
		if (args.length != 1) {
			p.sendMessage(utils.TS(Main.error+"&cUsage: /joingiveaway <id>"));
			utils.listGiveAways(p);
			return true;
		}
		if (!Main.fm.b.containsKey(args[0])) {
			p.sendMessage(utils.TS(Main.error+"&cThere is no active giveaway with ID "+args[0]));
			return true;
		}
		List<String> uuid = (Main.fm.b.get(args[0]).getStringList("Players") == null) ? new ArrayList<String>() : Main.fm.b.get(args[0]).getStringList("Players");
		if (uuid.contains(p.getUniqueId().toString())) {
			p.sendMessage(utils.TS(Main.error+"&cYou have already joined this giveaway!"));
			return true;
		}
		uuid.add(p.getUniqueId().toString());
		Main.fm.b.get(args[0]).set("Players", uuid);
		Main.fm.saveResource(args[0]);
		p.sendMessage(utils.TS(Main.prefix+"&eYou have successfully joined the giveaway &a"+args[0]));
		return true;
	}

}
