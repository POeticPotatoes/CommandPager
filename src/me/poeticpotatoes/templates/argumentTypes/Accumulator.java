package me.poeticpotatoes.templates.argumentTypes;

import org.bukkit.command.CommandSender;

public abstract class Accumulator implements Argumentable{
	private Boolean loadLast = false;
	
	public abstract void addValue(CommandSender sender, String s) throws Exception;
	
	public abstract Boolean isFilled();
	
	public abstract Boolean isEmpty();
	
	public Boolean loadLast() {
		return loadLast;
	}
	
	protected void setLast(Boolean b) {
		this.loadLast = b;
	}
}