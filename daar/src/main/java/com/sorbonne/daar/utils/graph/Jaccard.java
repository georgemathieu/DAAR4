package com.sorbonne.daar.utils.graph;

import org.apache.commons.text.similarity.JaccardDistance;

public class Jaccard {
	
	private static JaccardDistance INSTANCE = new JaccardDistance();

	/** Important : la fonction renvoie (1 - similarité) pour
	 * montrer la distance entre les textes
	 * -> Deux textes indentiques renvoient 0
	 * -> Compte les espaces
	 */
	public static Double distanceJaccard(String text1, String text2) {
		return INSTANCE.apply(text1, text2);
	}
}
