package me.poeticpotatoes.templates.utils.defaults;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.poeticpotatoes.templates.argumentTypes.Argumentable;

public class PlayerName implements Argumentable{
	
	@Override
	public Object parseArgument(CommandSender sender, String s) throws Exception {
		for(Player p: Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(s)) return p;
		}
		return null;
	}

	/*@Override
	public List<String> getSuggestions(CommandSender s) {
		return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
	}*/
}
