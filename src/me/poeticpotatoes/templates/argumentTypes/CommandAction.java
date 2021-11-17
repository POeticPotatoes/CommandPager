package me.poeticpotatoes.templates.argumentTypes;

import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface CommandAction {
	
public Boolean onCommand(CommandSender s, List<Object> parameters, CommandExecutor command);
}
