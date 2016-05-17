package me.markeh.ffw;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class Utils {

	public static List<Material> toList(Material... items) {
		List<Material> finalList = new ArrayList<Material>();
		
		for(Material item : items) {
			finalList.add(item);
		}
		
		return finalList;
	}
}
