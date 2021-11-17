package me.poeticpotatoes.templates;

import org.bukkit.plugin.java.JavaPlugin;

import me.poeticpotatoes.templates.commands.CommandHandler;
import me.poeticpotatoes.templates.commands.Parameters;

public class Pager{
	private Parameters param;
	private JavaPlugin main;
	private CommandHandler comm;
	
	public Pager(JavaPlugin main, Parameters param, String path) {
		this.main = main;
		this.param = param;
		this.comm = new CommandHandler(this, path);
	}
	
	public void setParameterHandler(Parameters param) {
		this.param = param;
	}
	
	public Parameters getParameterHandler() {
		return param;
	}
	
	public CommandHandler getCommandHandler() {
		return comm;
	}
	
	public JavaPlugin getPlugin() {
		return main;
	}
}