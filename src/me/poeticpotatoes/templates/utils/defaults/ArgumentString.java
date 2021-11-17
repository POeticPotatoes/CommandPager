package me.poeticpotatoes.templates.utils.defaults;

import org.bukkit.command.CommandSender;

import me.poeticpotatoes.templates.argumentTypes.Accumulator;

public class ArgumentString extends Accumulator{
	String string = "";
	
	public ArgumentString() {
		setLast(true);
	}
	
	@Override
	public Object parseArgument(CommandSender sender, String s) throws Exception {
		return string;
	}

	/*@Override
	public List<String> getSuggestions(CommandSender sender) {
		return null;
	}*/

	@Override
	public void addValue(CommandSender sender, String s) throws Exception {
		string += s + " ";
	}

	@Override
	public Boolean isFilled() {
		return false;
	}

	@Override
	public Boolean isEmpty() {
		return string.length() == 0;
	}

}
