package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import chronotimer.RacerRun;

public class WebServer {

	// a shared area where we store POST data.
	static String racerNamesFilePath = "data/racers.txt";
	static ArrayList<RacerRun> data = new ArrayList<RacerRun>();
	static Map<Integer, String> names = new HashMap<Integer, String>();
	static Comparator<RacerRun> cmp = new Comparator<RacerRun>() {
		@Override
		public int compare(RacerRun r1, RacerRun r2) {
			double result = r1.getElapsedTime() - r2.getElapsedTime();
			if (result < 0)
				return -1;
			if (result > 0)
				return 1;
			return 0;
		}
	};

	public static void main(String[] args) throws Exception {
		// set up a simple HTTP server on our local host
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to display the results
		server.createContext("/results", new DisplayHandler());

		server.createContext("/style.css", new CSSHandler());

		// create a context to get the request for the POST
		server.createContext("/sendresults", new PostHandler());
		server.setExecutor(null); // creates a default executor

		addNames(); // add names for racers;

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}

	private static void addNames() {
		Scanner fileIn = null;
		try {
			// racers.txt must be in the following format:
			// <RACERID><tab><Racer Name>
			// where RACERID is an integer
			fileIn = new Scanner(new File(racerNamesFilePath));
		} catch (FileNotFoundException e) {
			// file wasn't found
			return;
		}
		while (fileIn.hasNextLine()) {
			String line = fileIn.nextLine();
			try {
				String[] parts = line.split("\t");
				if (parts.length < 2)
					continue;
				int rid = Integer.parseInt(parts[0]);
				String rname = parts[1];
				names.put(rid, rname);
			} catch (Exception e) {
				// ignore bad lines
			}
		}
		fileIn.close();
	}

	static class DisplayHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			t.getResponseHeaders().set("Content-Type", "text/html");
			String response = "<!doctype html><html><head><link rel=\"stylesheet\" href=\"style.css\" />"
					+ "<title>Race Results</title></head><body>";
			Gson g = new Gson();
			if (!data.isEmpty()) {

				response += "<table><tr><th>Place</th><th>Number</th><th>Name</th><th>Time</th></tr>";
				int place = 1;
				for (RacerRun rr : data) {
					int rid = rr.getRacer();
					response += "<tr>";
					response += "<td>" + place + "</td>";
					response += "<td>" + rid + "</td>";
					response += "<td>" + names.getOrDefault(rid, "") + "</td>";
					response += "<td>" + rr.getElapsedTime() + "</td>";
					response += "</tr>";
					place++;
				}
				response += "</table>";
			} else {
				response += "<p>No Data</p>";
			}
			response += "</body></html>";
			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class CSSHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			t.getResponseHeaders().set("Content-Type", "text/css");
			String response = "";

			InputStream inStream = new FileInputStream(new File("src/server/style.css"));
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStream));
			String line;
			while ((line = fileReader.readLine()) != null) {
				response += line + "\n";
			}
			fileReader.close();

			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class PostHandler implements HttpHandler {
		public void handle(HttpExchange transmission) throws IOException {
			// shared data that is used with other handlers
			String sharedResponse = "";

			// set up a stream to read the body of the request
			InputStream inputStr = transmission.getRequestBody();

			// set up a stream to write out the body of the response
			OutputStream outputStream = transmission.getResponseBody();

			// string to hold the result of reading in the request
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up
			// the sharedResponse
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				sb = sb.append((char) nextChar);
				nextChar = inputStr.read();
			}

			// create our response String to use in other handler
			sharedResponse = sharedResponse + sb.toString();

			System.out.println(sharedResponse);

			Gson gson = new Gson();
			ArrayList<RacerRun> tmpData = gson.fromJson(sharedResponse, new TypeToken<Collection<RacerRun>>() {
			}.getType());
			if (!tmpData.isEmpty()) {
				data = tmpData;
				data.sort(cmp);
			}

			// respond to the POST with ROGER
			String postResponse = "ROGER JSON RECEIVED";

			// assume that stuff works all the time
			transmission.sendResponseHeaders(300, postResponse.length());

			// write it and return it
			outputStream.write(postResponse.getBytes());

			outputStream.close();
		}
	}
}
