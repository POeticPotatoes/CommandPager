package me.poeticpotatoes.templates.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import me.poeticpotatoes.templates.Pager;
import me.poeticpotatoes.templates.argumentTypes.CommandAction;
import me.poeticpotatoes.templates.files.FileHandler;

public class CommandHandler {
	private Pager main;
	private FileConfiguration data;
	
	public CommandHandler(Pager main, String path) {
		this.main = main;
		FileHandler handler = new FileHandler(main.getPlugin(), path);
		this.data = handler.loadResource();
		
		//initCommands();
	}
	
	/*private void initCommands() {
		for (String s: data.getKeys(false)) {
			DefaultCommand cmd = ((DefaultCommand) main.getPlugin().getCommand(s).getExecutor());
			try {
				cmd.setArgumentStructure(parseData(data.getList(s), main.getParameterHandler()));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}*/
	
	public void registerCommand(DefaultCommand cmd, String name) {
		if (!data.contains(name)) {
			Bukkit.getLogger().warning("The command structure for " + name + " was not found!");
			return;
		}
		try {
			cmd.setArgumentStructure(parseData(data.getList(name), main.getParameterHandler()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getData() {
		return data;
	}
	
	@SuppressWarnings("unchecked")
	private Map<Class<?>, Object> parseData(List<?> data, Parameters p) throws Exception {
		if (data == null) return null;
		Map<Class<?>, Object> ans = new HashMap<Class<?>, Object>();
		String key = null;
		List<?> next = null;
		for (Object o: data) {
			if (o instanceof String) {
				key = (String) o;
				if (key.charAt(0) == '/') {
					ans.put(CommandAction.class, key.substring(1));
				}
			} else {
				HashMap<String, ?> h = (HashMap<String, ?>) o;
				key = h.keySet().iterator().next();
				next = (ArrayList<?>) h.get(key);
			}
			Class<?> a = p.getParameter(key);
			if (a == null) throw new Exception("Invalid Argument type: " + key);
			ans.put(a, parseData(next, p));
		}
		return ans;
	}
}