package me.poeticpotatoes.templates.utils.defaults;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.poeticpotatoes.templates.argumentTypes.GenericArgument;

public class GenericLocation extends GenericArgument{
	public GenericLocation() {
		accumulators = List.of(Coordinate.class);
		basicArgs = List.of(At.class, PlayerName.class);
	}

	@Override
	public Object parseArgument(CommandSender sender, String s) throws Exception {
		Object o = parseAll(sender, s);
		
		if (o instanceof Player) return ((Player)o).getLocation();
		if (o instanceof Location) return o;
		throw new Exception("Invalid argument");
	}
}