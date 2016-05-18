package me.markeh.ffw;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class Utils {

	public static List<String> toList(Material... items) {
		List<String> finalList = new ArrayList<String>();
		
		for(Material item : items) {
			finalList.add(item.name());
		}
		
		return finalList;
	}
}
