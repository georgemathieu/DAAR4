package com.sorbonne.daar.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sorbonne.daar.DaarApplication;
import com.sorbonne.daar.controller.DAARController;

@Service
public class BookService {
	
	/**
	 * Get all the books with a title containing a specific keyword
	 */
	public List<Integer> getRelatedBooksByTitle(String keyword){
		List<Integer> ids = new ArrayList<>();
		
		for (String title : DaarApplication.titles.keySet()) {
			if(title.toLowerCase().contains(keyword)) {
				ids.add(DaarApplication.titles.get(title));
			}
		}
		return ids;
	}
	
	/**
	 * Get all the books for a specific author
	 */
	public List<Integer> getRelatedBooksByAuthor(String keyword){
		List<Integer> ids = new ArrayList<>();
		
		for (String author : DaarApplication.authors.keySet()) {
			if(author.toLowerCase().contains(keyword)) {
				ids.addAll(DaarApplication.authors.get(author));
			}
		}
		return ids;
	}
	
	/**
	 * Get all the books containing a related keyword
	 */
	public Set<Integer> getRelatedBooksKeywords(String keyword){
		// We use a hashset to remove duplicates
		Set<Integer> ids = new HashSet<>();
		
		for (String kwFromDB : DaarApplication.keywords.keySet()) {
			// the serialized keywords are stem, we need to compare in both directions
			if(keyword.contains(kwFromDB.toLowerCase())) {
				ids.addAll(DaarApplication.keywords.get(kwFromDB));
			}
			if(kwFromDB.toLowerCase().contains(keyword)) {
				ids.addAll(DaarApplication.keywords.get(kwFromDB));
			}
		}
		return ids;
	}
	
	/**
	 * Order the ids of a result Set based on the closeness graph
	 */
	public void orderResults(List<Integer> ids) {
		List<Integer> orderedIndexes = new ArrayList<>(DaarApplication.closeness.keySet());
		Collections.sort(ids, Comparator.comparing(id -> orderedIndexes.indexOf(id)));
	}
}
