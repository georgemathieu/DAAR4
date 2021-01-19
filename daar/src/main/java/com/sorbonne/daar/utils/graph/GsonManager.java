package com.sorbonne.daar.utils.graph;

import java.io.IOException;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonManager {

	/** Get the plain text url of jo
	 * @throws IOException */
	public static String getBookContentURL(JsonObject jo) throws IOException {
		JsonElement plainText = new JsonObject();
		JsonElement el = jo.getAsJsonObject("formats").get("text/plain");
		if (el != null) {
			plainText = el;
		} else if ((el = jo.getAsJsonObject("formats").get("text/plain; charset=us-ascii")) != null) {
			plainText = el;
		} else if ((el = jo.getAsJsonObject("formats").get("text/plain; charset=utf-8")) != null) {
			plainText = el;
		}
		
		return plainText.getAsString();
	}
	
	/** returns the content of a book based on his url */
	public static String getBookContent(String url) {
		RestTemplate rt = new RestTemplate();
		return rt.getForObject(url, String.class);
	}

	/** Returns a list with all the plain text urls from a list of book */
	public static JsonArray getURLList(JsonObject jo) {
		JsonArray ja = jo.getAsJsonArray("results");
		JsonArray urlList = new JsonArray();
		ja.forEach(a -> {
			JsonElement el = a.getAsJsonObject().getAsJsonObject("formats").get("text/plain");
			if (el != null) {
				urlList.add(el);
			} else if ((el = a.getAsJsonObject().getAsJsonObject("formats").get("text/plain; charset=utf-8")) != null) {
				urlList.add(el);
			} else if ((el = a.getAsJsonObject().getAsJsonObject("formats")
					.get("text/plain; charset=us-ascii")) != null) {
				urlList.add(el);
			}
		});

		return urlList;
	}

}
