package com.Kotori;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdListGiveAway implements CommandExecutor {
	
	public Map<UUID,Long> spamchecker = new HashMap<UUID,Long>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (spamchecker.containsKey(((Player)sender).getUniqueId()) && 
					TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-spamchecker.get(((Player)sender).getUniqueId())) < 2) {
				sender.sendMessage(utils.TS(Main.error+"&cPlease wait for another 2 seconds to use this command!"));
				return true;
			}
			spamchecker.put(((Player)sender).getUniqueId(), System.currentTimeMillis());
			utils.listGiveAways(((Player)sender));
			return true;
		}
		utils.listGiveAways(sender);
		return true;
	}

}
