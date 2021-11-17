package me.poeticpotatoes.templates.argumentTypes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public abstract class GenericArgument extends Accumulator{
	Location loc;
	protected List<Class<?>> accumulators = new ArrayList<Class<?>>();
	protected List<Class<?>> basicArgs = new ArrayList<Class<?>>();
	private final List<Accumulator> activeAccs = new ArrayList<Accumulator>();
	private Object val = null;
	private Boolean first = true;
	
	public Object parseAll(CommandSender sender, String s) throws Exception {
		if (val != null) return val;
		for (Accumulator a: activeAccs) {
			try {
				val = a.parseArgument(sender, s);
			} catch (Exception e) {
			}
			if (val!= null) return val;
		}
		throw new Exception("Invalid argument");
	}

	/*@Override
	public List<String> getSuggestions(CommandSender sender) {
		List<String> ans = new ArrayList<String>();
		if (first) basicArgs.stream().forEach(a -> ans.addAll(a.getSuggestions(sender)));
		activeAccs.stream().forEach(a -> ans.addAll(a.getSuggestions(sender)));
		return ans;
	}*/

	@SuppressWarnings("deprecation")
	@Override
	public void addValue(CommandSender sender, String s) throws Exception {
		if (first) {
			first = false;
			accumulators.stream().forEach(a -> {
				try {
					activeAccs.add((Accumulator) a.newInstance());
				} catch (InstantiationException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			});
			if (basicArgs.size() > 0) for (Class<?> c: basicArgs) {
				try {
					val = ((Argumentable) c.newInstance()).parseArgument(sender, s);
				} catch (Exception e) {
				}
				if (val != null) {
					return;
				}
			}
		}
		List<Accumulator> invalid = new ArrayList<Accumulator>();
		if (activeAccs.size() == 0) throw new Exception();
		for (Accumulator a: activeAccs) {
			try {
				a.addValue(sender, s);
				if (a.isFilled()) {
					val = a.parseArgument(sender, s);
					return;
				}
			} catch (Exception e) {
				invalid.add(a);
			}
		}
		activeAccs.removeAll(invalid);
	}

	@Override
	public Boolean isFilled() {
		return val != null;
	}

	@Override
	public Boolean isEmpty() {
		if (activeAccs.size() == 0) return true;
		for (Accumulator a: activeAccs) if (!a.isEmpty()) return false;
		return true;
	}
}
