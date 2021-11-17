package me.poeticpotatoes.templates.argumentTypes;

import org.bukkit.command.CommandSender;

public interface Argumentable {
	//method should throw an error if unable to parse object
	public Object parseArgument(CommandSender sender, String s) throws Exception;
	//TODO: public List<String> getSuggestions(CommandSender sender);
}
