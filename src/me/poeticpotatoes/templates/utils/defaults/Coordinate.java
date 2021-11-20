package me.poeticpotatoes.templates.utils.defaults;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.poeticpotatoes.templates.argumentTypes.Accumulator;

public class Coordinate extends Accumulator{
	private int counter = 0;
	private Location loc;
	
	@Override
	public Object parseArgument(CommandSender sender, String s) throws Exception {
		return loc;
	}

	@Override
	public Boolean isFilled() {
		return this.counter == 3;
	}


	@Override
	public void addValue(CommandSender sender, String s) throws Exception {
		if (!(sender instanceof Player)) throw new Exception("Sender is not a player");
		if (counter > 2) throw new Exception("Invalid argument");
		Location l = ((Player)sender).getLocation();
		Boolean here = s.charAt(0) == '~';
		int i=0, offset= 0;
		if (!here) i = Integer.parseInt(s); 
		else if (s.length()>1) offset = Integer.parseInt(s.substring(1));
		switch (counter) {
		case 0:
			this.loc = new Location(((Player)sender).getWorld(),0,0,0);
			this.loc.setX(here? l.getBlockX()+offset:i);
		case 1:
			this.loc.setY(here? l.getBlockY()+offset:i);
		case 2:
			this.loc.setZ(here? l.getBlockZ()+offset:i);
		}
		counter++;
	}

	@Override
	public Boolean isEmpty() {
		return counter == 0;
	}
}
