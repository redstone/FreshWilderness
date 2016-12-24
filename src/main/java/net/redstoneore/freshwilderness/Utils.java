package net.redstoneore.freshwilderness;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class Utils {

	// -------------------------------------------------- //
	// STATIC METHODS  
	// -------------------------------------------------- //
	
	public static List<String> toList(Material... items) {
		List<String> finalList = new ArrayList<String>();
		
		for(Material item : items) {
			finalList.add(item.name());
		}
		
		return finalList;
	}

	public static List<String> toList(String... items) {
		List<String> finalList = new ArrayList<String>();
		
		for(String item : items) {
			finalList.add(item);
		}
		
		return finalList;
	}
}
