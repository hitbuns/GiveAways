package com.Kotori;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	public Main plugin;
	public File giveawayfolder;
	public Map<String,File> a = new HashMap<String,File>();
	public Map<String,FileConfiguration> b = new HashMap<String,FileConfiguration>();

	public FileManager(Main plugin) {
		this.plugin = plugin;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();
		setupFolders();
		File[] lf = giveawayfolder.listFiles();
		for (File f : lf) {
			FileConfiguration cfg  = YamlConfiguration.loadConfiguration(f);
			String id = f.getName().replace(".yml", "");
			a.put(id, f);
			b.put(id, cfg);
		}
	}
	
	public void setupFolders() {
		giveawayfolder = new File(this.plugin.getDataFolder()+"\\GiveAways");
		if (!giveawayfolder.exists()) {
			try {
				giveawayfolder.mkdirs();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setupnewGiveawayFile(String id) {
		File c = new File(giveawayfolder,id+".yml");
		if (!c.exists()) {
			try {
				c.createNewFile();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		FileConfiguration d = YamlConfiguration.loadConfiguration(c);
		a.put(id, c);
		b.put(id, d);
	}
	
	public void updateResource(String id,String path,Object value) {
		if (!(a.containsKey(id) && b.containsKey(id))) setupnewGiveawayFile(id);
		loadResource(id);
		b.get(id).set(path, value);
		saveResource(id);
	}
	
	public void loadResource(String id) {
		if (!(a.containsKey(id) && b.containsKey(id))) return;
		FileConfiguration c = YamlConfiguration.loadConfiguration(a.get(id));
		b.put(id, c);
	}
	
	
	public void saveResource(String id) {
		if (!(a.containsKey(id) && b.containsKey(id))) return;
		try {
			b.get(id).save(a.get(id));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
