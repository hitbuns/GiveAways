package com.Kotori;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{
	
	public static Main Instance;
	public static final String prefix = "&8[&6KotoriCore&8] &e";
	public static final String error = "&4\\ERROR\\ &c";
	public static FileManager fm;
	public static Economy economy;
	
	@Override
	public void onEnable() {
		if (!setupEconomy()) Bukkit.shutdown();
		Instance = this;
		fm = new FileManager(this);
		this.getCommand("gadmin").setExecutor(new CmdGiveAway());
		this.getCommand("joingiveaway").setExecutor(new CmdJoinGiveAway());
		this.getCommand("gdelete").setExecutor(new CmdDeleteGiveAway());
		this.getCommand("giveaways").setExecutor(new CmdListGiveAway());
		File[] lf = fm.giveawayfolder.listFiles();
		for (File f : lf) {
			String id = f.getName().replace(".yml", "");
			utils.compileTasks(id);
		}
	}
	
	@Override
	public void onDisable() {
		for (String c : fm.b.keySet()) {
			fm.saveResource(c);
			utils.clearTask(c);
		}
	}

	private boolean setupEconomy() {
	    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
	    if (economyProvider != null) economy = (Economy)economyProvider.getProvider(); 
	    return (economy != null);
	}
	
}
