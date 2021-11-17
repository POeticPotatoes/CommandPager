package me.poeticpotatoes.templates.files;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationFile extends FileHandler{
	private final String section;
	
	public LocationFile(JavaPlugin main, String path, String section) {
		super(main, path);
		this.section = section;
		loadFile();
	}
	
	protected Set<Location> parseLocations() {
		Set<Location> ans = new HashSet<Location>();
		ConfigurationSection data = getFileConfiguration();
		if (section != null) data = data.getConfigurationSection(section);
		for (String w: data.getKeys(false)) {
			for (String x: data.getConfigurationSection(w).getKeys(false)) {
				for (String y: data.getConfigurationSection(w + "." + x).getKeys(false)) {
					for (String z: data.getConfigurationSection(w + "." + x + "." + y).getKeys(false)) {
						ans.add(new Location(main.getServer().getWorld(w), 
								Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)));
					}
				}
					
			}
		}
		return ans;
	}
	
	protected String parseLocation(Location l) {
		return l.getWorld().getName() + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ();
	}
}