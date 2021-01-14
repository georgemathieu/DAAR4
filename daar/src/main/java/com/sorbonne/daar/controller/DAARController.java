package com.sorbonne.daar.controller;

import org.apache.tomcat.util.json.ParseException;
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
import com.sorbonne.daar.utils.graph.GsonManager;
import com.sorbonne.daar.utils.graph.Jaccard;

@CrossOrigin
@RestController
public class DAARController {
	
	private final String url = "http://localhost:8000/";
	private final String gutendexUrl = "https://gutendex.com/";
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	
	/**
	 * Get a book from a keyword
	 */
	@GetMapping("/search/{content}")
	@ResponseBody
	public ResponseEntity<String> searchAllBooks(@PathVariable String content) throws JsonMappingException, JsonProcessingException, ParseException {
		
		// not ready, testing stuff 
		
		RestTemplate rt = new RestTemplate();
		String res = rt.getForObject(gutendexUrl + "books/", String.class);
		
		JsonObject jo = gson.fromJson(res, JsonObject.class);
		
		GsonManager.getBookContent(jo);
		
		// to get content (directly calls the gutenberg file) :
		return ResponseEntity.ok(GsonManager.getBookContent(jo));
		
	}
	
	/**
	 * Jaccard Test
	 */
	@GetMapping("/jaccardTest")
	@ResponseBody
	public ResponseEntity<String> testJaccard() throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String text1 = rt.getForObject(gutendexUrl + "books/1", String.class);
		String text2 = rt.getForObject(gutendexUrl + "books/2", String.class);
		
		JsonObject jo1 = gson.fromJson(text1, JsonObject.class);
		JsonObject jo2 = gson.fromJson(text2, JsonObject.class);
		
		return ResponseEntity.ok(Jaccard.distanceJaccard(GsonManager.getBookContent(jo1), GsonManager.getBookContent(jo2)).toString());
	}
	
	/**
	 * Jaccard Test
	 */
	@GetMapping("/jaccardTest1")
	@ResponseBody
	public ResponseEntity<String> testJaccard1() throws JsonMappingException, JsonProcessingException, ParseException {
		
		RestTemplate rt = new RestTemplate();
		String text1 = rt.getForObject(gutendexUrl + "books/1", String.class);
		
		JsonObject jo1 = gson.fromJson(text1, JsonObject.class);
		
		return ResponseEntity.ok(Jaccard.distanceJaccard(GsonManager.getBookContent(jo1), GsonManager.getBookContent(jo1)).toString());
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
