package com.sorbonne.daar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sorbonne.daar.services.BookService;
import com.sorbonne.daar.utils.graph.GsonManager;
import com.sorbonne.daar.utils.graph.Jaccard;

@CrossOrigin
@RestController
public class DAARController {
	
	private final String url = "http://localhost:8000/";
	private final String gutendexUrl = "https://gutendex.com/";
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Autowired
    private BookService bookService = new BookService();
	
	
	/**
	 * Get a book from a keyword
	 * @throws IOException 
	 */
	@GetMapping("/basicsearch/{content}")
	@ResponseBody
	public ResponseEntity<Set<Integer>> basicSearch(@PathVariable String content) throws ParseException, IOException {
		
		String[] keywords = content.split("\\s+");
		Set<Integer> ids = new HashSet<>();
		Arrays.asList(keywords).forEach(k -> {
			ids.addAll(bookService.getRelatedBooksByTitle(k.toLowerCase()));
			ids.addAll(bookService.getRelatedBooksByAuthor(k.toLowerCase()));
			ids.addAll(bookService.getRelatedBooksKeywords(k.toLowerCase()));
		});
		
		return ResponseEntity.ok(ids);
	}
	
	/**
	 * Jaccard Test
	 * @throws IOException 
	 */
	@GetMapping("/jaccardTest")
	@ResponseBody
	public ResponseEntity<String> testJaccard() throws ParseException, IOException {
		
		RestTemplate rt = new RestTemplate();
		String text1 = rt.getForObject(gutendexUrl + "books/1", String.class);
		String text2 = rt.getForObject(gutendexUrl + "books/2", String.class);
		
		JsonObject jo1 = gson.fromJson(text1, JsonObject.class);
		JsonObject jo2 = gson.fromJson(text2, JsonObject.class);
		
		return ResponseEntity.ok(Jaccard.distanceJaccard(
				GsonManager.getBookContent(GsonManager.getBookContentURL(jo1)), 
				GsonManager.getBookContent(GsonManager.getBookContentURL(jo2))).toString());
	}
	
	
	/**
	 * Get all the books
	 */
	@GetMapping("/books")
	@ResponseBody
	public ResponseEntity<String> getAllBooks() throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String res = rt.getForObject(gutendexUrl + "books/", String.class);
		
		JsonObject jo = gson.fromJson(res, JsonObject.class);
		//JsonElement je = jo.get("results");
		
		return ResponseEntity.ok(jo.toString());
		
	}
	
	
	/**
	 * Get a book from its id
	 */
	@GetMapping("/book/{id}")
	@ResponseBody
	public ResponseEntity<String> getBook(@PathVariable Long id) throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String res = rt.getForObject(gutendexUrl + "books/" + id, String.class);
		
		JsonObject jo = gson.fromJson(res, JsonObject.class);
		
		return ResponseEntity.ok(jo.toString());
		
	}
	
	
	/**
	 * Get all the books written in french
	 */
	@GetMapping("/frenchbooks")
	@ResponseBody
	public ResponseEntity<String> getAllFrenchBooks() throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String res = rt.getForObject(gutendexUrl + "books?languages=fr", String.class);
		
		JsonObject jo = gson.fromJson(res, JsonObject.class);
		//JsonElement je = jo.get("results");
		
		return ResponseEntity.ok(jo.toString());
		
	}
	
	
	/**
	 * Get a book in french from its id
	 */
	@GetMapping("/frenchbook/{id}")
	@ResponseBody
	public ResponseEntity<String> getFrenchBook(@PathVariable Long id) throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String res = rt.getForObject(gutendexUrl + "books?languages=fr&ids=" + id, String.class);
		
		JsonObject jo = gson.fromJson(res, JsonObject.class);
		
		return ResponseEntity.ok(jo.toString());
		
	}
	
	/**
	 * Get all english books
	 */
	@GetMapping("/englishbooks")
	@ResponseBody
	public ResponseEntity<String> getAllEnglishBooks() throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String res = rt.getForObject(gutendexUrl + "books?languages=en", String.class);
		
		JsonObject jo = gson.fromJson(res, JsonObject.class);
		//JsonElement je = jo.get("results");
		
		return ResponseEntity.ok(jo.toString());
		
	}
	
	/**
	 * Get a book in english from its id
	 */
	@GetMapping("/englishbook/{id}")
	@ResponseBody
	public ResponseEntity<String> getEnglishBook(@PathVariable Long id) throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String res = rt.getForObject(gutendexUrl + "books?languages=en&ids=" + id, String.class);
		
		JsonObject jo = gson.fromJson(res, JsonObject.class);
		
		return ResponseEntity.ok(jo.toString());
		
	}
	
}
