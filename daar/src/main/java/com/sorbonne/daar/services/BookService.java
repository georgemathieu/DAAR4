package com.sorbonne.daar.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sorbonne.daar.DaarApplication;

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
		// A reflechir
		/*for (String kwFromDB : DaarApplication.keywords.keySet()) {
			// the serialized keywords are stem, we need to compare in both directions
			if(keyword.contains(kwFromDB.toLowerCase())) {
				ids.addAll(DaarApplication.keywords.get(kwFromDB));
			}
			if(kwFromDB.toLowerCase().contains(keyword)) {
				ids.addAll(DaarApplication.keywords.get(kwFromDB));
			}
		}*/
		return ids;
	}

}
