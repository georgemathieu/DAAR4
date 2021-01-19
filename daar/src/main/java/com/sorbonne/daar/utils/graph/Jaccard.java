package com.sorbonne.daar.utils.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Jaccard {
	

	/** Important : la fonction renvoie (1 - similarité) pour
	 * montrer la distance entre les textes
	 * -> Deux textes indentiques renvoient 0
	 */
	public static Float distanceJaccard(String str1, String str2) {
		if(str1.trim().isEmpty() || str1 == null || str2.trim().isEmpty() || str2 == null) {
			return 1f;
		}
		Set<String> s1 = new HashSet<String>(Arrays.asList(str1.split(" +")));
		Set<String> s2 = new HashSet<String>(Arrays.asList(str2.split(" +")));

		int intersection = 0;
		// Better performances if we put the smaller set first
		if (s1.size() < s2.size()) {
			intersection = Sets.intersection(s1, s2).size();
		} else {
			intersection = Sets.intersection(s2, s1).size();
		}

		// Intersection / Union
		float similarity = intersection / (float) (s1.size() + s2.size() - intersection);

		// Distance is 1 - similarity
		return 1 - similarity;
	}
	
	public static void buildJaccardMatrice() throws FileNotFoundException, IOException, ClassNotFoundException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		final String gutendexUrl = "https://gutendex.com/";
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("jaccard.ser"));
		JaccardMatrice jm = (JaccardMatrice) ois.readObject();
		ois.close();
		
		Float[][] jArray = jm.getJaccardMatrice();
		
		System.out.println("test jArray origin size : " + jArray[121][1]);
		
		for (int i = 1050; i < 1664; i++) {
			// We get the first text
			String content1 = readFile("data", i, StandardCharsets.US_ASCII);

			for (int j = 0; j < 1664; j++) {
				if (j < i) {
					jArray[i][j] = jArray[j][i];
				} else if (i == j) {
					jArray[i][j] = 0f;
				} else {
					// We get the 2nd text and compute the jaccard distance
					String content2 = readFile("data", j, StandardCharsets.US_ASCII);
					jArray[i][j] = distanceJaccard(content1, content2);
					if(j % 200 == 0) System.out.println("column : " + j);
				}
			}
			System.out.println("current book : " + i);
			if (i % 50 == 0) {
				System.out.println("saving file");
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("jaccard.ser"));
				oos.writeObject(jm);
				oos.flush();
				oos.close();
			}
		}
		System.out.println("end, saving the file");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("jaccard.ser"));
		oos.writeObject(jm);
		oos.flush();
		oos.close();
	} 
	
	/**
	 * Read the content of a file
	 */
	private static String readFile(String path, int i, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path + File.separator + i + ".txt"));
		return new String(encoded, encoding);
	}
	
	/**
	 * Download books from gutenberg, handles zip files
	 */
	public static void downloadFromGutemberg() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		final String gutendexUrl = "https://gutendex.com/";
		RestTemplate rt = new RestTemplate();
		
		for (int i = 1664; i < 1665; i++) {
			String url = "";
			try {
				// First we get the json content for the book with the id i
				String book = rt.getForObject(gutendexUrl + "books/" + i, String.class);
				JsonObject jo = gson.fromJson(book, JsonObject.class);
				
				// We get the donload url for the file
				url = GsonManager.getBookContentURL(jo);
				byte[] downloadedBytes;
				
				// We check if the file is a zip or not
				if(url.split("\\.")[3].equals("zip")) {
					// We unzip the file and save it in the data/ directory
					downloadedBytes = rt.getForObject(url, byte[].class);
					int id = jo.get("id").getAsInt();
					Zip.unzip(downloadedBytes, "data/"+ (id - 1) +".txt");
				} else {
					// Not a zip, we just save the .txt file in the data/ directory
					String content = rt.getForObject(url, String.class);
					PrintWriter pw = new PrintWriter(new FileOutputStream("data/" + (i - 1) + ".txt"));
					pw.println(content);
					pw.flush();
					pw.close();
				}
			} catch (HttpClientErrorException e) {
				System.err.println("not found");
				PrintWriter pw = new PrintWriter(new FileOutputStream("data/" + (i - 1) + ".txt"));
				pw.println("");
				pw.flush();
				pw.close();
			}
		}
	}
	
		
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		// Download all the books
		//downloadFromGutemberg();
		
		// Computes the entire jaccard distance matrix
		//buildJaccardMatrice();
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("jaccard.ser"));
		JaccardMatrice jmIN = (JaccardMatrice) ois.readObject();
		ois.close();
		
		System.out.println(jmIN.toString());
	}
}
