package me.poeticpotatoes.templates.utils.defaults;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.poeticpotatoes.templates.argumentTypes.Argumentable;

public class At implements Argumentable{

	@Override
	public Object parseArgument(CommandSender sender, String s) throws Exception {
		if (s.charAt(0) != '@' || s.length() != 2) return null;
		switch (s) {
		case "@a":
			return Bukkit.getOnlinePlayers();
		case "@e":
			if (sender instanceof Player) return ((Player) sender).getWorld().getEntities();
			List<Entity> ans = new ArrayList<Entity>();
			Bukkit.getWorlds().stream().forEach(w -> ans.addAll(w.getEntities()));
			return ans;
		case "@p":
			if (sender instanceof Player) return (Player) sender;
			throw new Exception("Sender is not a player");
		default:
			throw new Exception("Invalid argument");
		}
	}

	/*@Override
	public List<String> getSuggestions(CommandSender sender) {
		return null;
	}*/
}
