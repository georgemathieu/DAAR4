package com.sorbonne.daar.utils.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Used to order the closeness map in decreasing value. The interesting books
 * have a higher closeness, so their rank must be higher.
 */
public class MapUtil {
	public static Map<Integer, Float> sortByValue(Map<Integer, Float> map) {
		List<Entry<Integer, Float>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());

		Map<Integer, Float> result = new LinkedHashMap<>();
		for (Entry<Integer, Float> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
}