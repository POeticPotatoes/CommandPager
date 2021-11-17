package me.poeticpotatoes.templates.commands;

import java.util.HashMap;
import java.util.Map;

import me.poeticpotatoes.templates.utils.defaults.ArgumentString;
import me.poeticpotatoes.templates.utils.defaults.At;
import me.poeticpotatoes.templates.utils.defaults.Coordinate;
import me.poeticpotatoes.templates.utils.defaults.GenericLocation;
import me.poeticpotatoes.templates.utils.defaults.PlayerName;

public class Parameters {
	private Map<String, Class<?>> paramList = new HashMap<String, Class<?>>();
	
	public Parameters() {
		paramList.put("playername", PlayerName.class);
		paramList.put("at", At.class);
		paramList.put("string", ArgumentString.class);
		paramList.put("coordinate", Coordinate.class);
		paramList.put("location", GenericLocation.class);
	}
	
	protected void registerParameter(String name, Class<?> c) throws Exception {
		if (paramList.containsKey(name)) throw new Exception("Repeat use of argument name " + name);
		paramList.put(name, c);
	}
	
	public Class<?> getParameter(String s) {
		return paramList.get(s);
	}
}
