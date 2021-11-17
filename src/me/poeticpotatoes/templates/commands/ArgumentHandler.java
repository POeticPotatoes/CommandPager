package me.poeticpotatoes.templates.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;

import me.poeticpotatoes.templates.argumentTypes.Accumulator;
import me.poeticpotatoes.templates.argumentTypes.Argumentable;
import me.poeticpotatoes.templates.argumentTypes.CommandAction;

public class ArgumentHandler {
	private List<Object> ans = new ArrayList<Object>();
	private String route;
	private Accumulator acc = null, loadLater = null;
	private Map<Class<?>, Object> section;
	private CommandSender sender;
	private Object o;
	
	public ArgumentHandler(Map<Class<?>, Object> argStruct, CommandSender sender) {
		section = argStruct;
		this.sender = sender;
	}
	
	public String getRoute() {
		return route;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Object> parseArgs(String[] args) throws Exception{
		for (String i: args) {
			route = null;
			if (acc != null) {
				fillAccumulator(i);
				addObject();
				continue;
			}
			
			if (section == null) throw new Exception("Too many arguments");
			
			for (Class<?> c: section.keySet()) {
				if (c.equals(CommandAction.class)) {
					route = (String) section.get(c);
					continue;
				} 
				Argumentable a = (Argumentable) c.newInstance();
				if (c.isAssignableFrom(Accumulator.class)) {
					acc = (Accumulator) a;
					if (acc.loadLast()) {
						loadLater = acc;
						continue;
					}
					try {
						fillAccumulator(i);
						break;
					} catch (Exception e) {
						if (!acc.isEmpty()) {
							throw e;
						}
						acc = null;
					}
				}
				try {
					o = a.parseArgument(sender, i);
					if (o == null) continue;
					section = (Map<Class<?>, Object>) section.get(c);
					break;
				} catch (Exception e) {
				}
				
			}
			
			if (o == null) {
				if (acc != null) continue;
				if (loadLater != null) {
					acc = loadLater;
					loadLater = null;
					fillAccumulator(i);
				} else {
					throw new Exception("No valid argument");
				}
			}
			addObject();
		}
		if (acc != null) {
			o = acc.parseArgument(sender, null);
			ans.add(o);
		}
		return ans;
	}
	
	@SuppressWarnings("unchecked")
	private void fillAccumulator(String i) throws Exception {
		acc.addValue(sender, i);
		if (acc.isFilled()) {
			o = acc.parseArgument(sender, i);
			section = (Map<Class<?>, Object>) section.get(acc.getClass());
			acc = null;
			loadLater = null;
		}
	}
	
	private void addObject() {
		if (acc != null) return;
		ans.add(o);
		o = null;
		loadLater = null;
	}
}
