package me.poeticpotatoes.templates.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.poeticpotatoes.templates.Pager;
import me.poeticpotatoes.templates.argumentTypes.CommandAction;

public abstract class DefaultCommand implements CommandExecutor, TabCompleter{
	private Map<Class<?>, Object> structure;
	private Boolean checkPlayer;
	private String permission;
	private Map<String, CommandAction> routes;
	
	public DefaultCommand(Pager main, String cmd, Boolean checkPlayer, String permission) {
		this.permission = permission;
		this.checkPlayer = checkPlayer;
		main.getPlugin().getCommand(cmd).setExecutor(this);
		main.getCommandHandler().registerCommand(this, cmd);
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg, String[] args) {
		if (checkPlayer && !(s instanceof Player)) return false;
		if (permission != null && !s.hasPermission(permission)) return false;
		
		if (args.length == 0) return run(s);
		
		List<Object> parameters = null;
		ArgumentHandler argHandler = new ArgumentHandler(structure, s);
		try {
			parameters = argHandler.parseArgs(args);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (!routes.containsKey(argHandler.getRoute())) return runOnInvalidArguments(s, args);
		return routes.get(argHandler.getRoute()).onCommand(s, parameters, this);
	}
	
	public void setArgumentStructure(Map<Class<?>, Object> a) {
		this.structure = a;
	}
	
	public Map<Class<?>, Object> getArgumentStructure() {
		return this.structure;
	}
	
	/*@Override
	public List<String> onTabComplete(CommandSender s, Command cmd, String arg, String[] args) {
		return null;
	}*/
	
	public Boolean runRoute(String str, CommandSender s, List<Object> parameters) {
		return routes.get(str).onCommand(s, parameters, this);
	}
	
	protected void registerRoute(String s, CommandAction a) {
		routes.put(s, a);
	}
	
	protected Boolean runOnInvalidArguments(CommandSender s, String[] args) {
		s.sendMessage(ChatColor.RED + "Invalid arguments.");
		return false;
	}
	
	protected abstract Boolean run(CommandSender s);
}