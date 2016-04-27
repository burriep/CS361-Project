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
import chronotimer.Timer;

public class WebServer {

	// a shared area where we store POST data.
	static String racerNamesFilePath = "data/racers.txt";
	static ArrayList<ArrayList<RacerRun>> data = new ArrayList<>();
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
				String[] parts = line.split("\\s", 2);
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

	private static Map<String, String> getQueryMap(String query) {
		// info from: http://stackoverflow.com/a/17472462
		Map<String, String> map = new HashMap<String, String>();
		if (query != null) {
			String[] queries = query.split("&");
			for (String arg : queries) {
				String[] parts = arg.split("=");
				if (parts.length == 2)
					map.put(parts[0], parts[1]);
				else
					map.put(parts[0], "");
			}
		}
		return map;
	}

	static class DisplayHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			Map<String, String> queries = getQueryMap(t.getRequestURI().getQuery());
			t.getResponseHeaders().set("Content-Type", "text/html");
			StringBuilder r = new StringBuilder();
			r.append("<!doctype html><html>");
			r.append("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
			r.append("<link rel=\"stylesheet\" href=\"style.css\" />");
			r.append("<title>Race Results</title></head>");
			r.append("<body>");
			r.append("<form class=\"changeRunForm group\"><label for=\"run\">Choose a run:</label>");
			r.append("<select name=\"run\" id=\"run\" onchange=\"if(this.value==''){window.location.href=window.location.pathname;}else{window.location.search='?run='+this.value;}\">");
			r.append("<option value=\"\">Most Recent Run</option>");

			int runIndex = data.size() - 1;
			try {
				String tmp = queries.get("run");
				int val = Integer.parseInt(tmp) - 1;
				if (val >= 0 && val < data.size())
					runIndex = val;
			} catch (NumberFormatException e) {
			}

			for (int i = data.size() - 1; i >= 0; --i) {
				r.append("<option value=\"").append((i + 1)).append("\"");
				if (runIndex == i)
					r.append(" selected");
				r.append(">Run ").append(i + 1).append("</option>");
			}

			r.append("</select>");
			// r.append("<button type=\"submit\">View</button>");
			r.append("</form>");
			r.append("<table class=\"runTable\"><tr class=\"headerRow\">");
			r.append("<th class=\"runHeader run__place\">Place</th>");
			r.append("<th class=\"runHeader run__rid\">Number</th>");
			r.append("<th class=\"runHeader run__name\">Name</th>");
			r.append("<th class=\"runHeader run__time\">Time</th></tr>");

			if (!data.isEmpty()) {
				int place = 1;
				ArrayList<RacerRun> cr = data.get(runIndex);
				for (RacerRun rr : cr) {
					int rid = rr.getRacer();
					r.append("<tr class=\"run__item\">");
					r.append("<td class=\"run__place\">").append(place).append("</td>");
					r.append("<td class=\"run__rid\">").append(rid).append("</td>");
					r.append("<td class=\"run__name\">").append(names.getOrDefault(rid, "")).append("</td>");
					r.append("<td class=\"run__time\">").append(Timer.durationToTimeString(rr.getElapsedTime())).append("</td>");
					r.append("</tr>");
					place++;
				}
			}
			r.append("</table>");
			r.append("</body></html>");
			// write out the response
			t.sendResponseHeaders(200, r.length());
			OutputStream os = t.getResponseBody();
			os.write(r.toString().getBytes());
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
			String response = "";

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
			response = sb.toString();

			System.out.println(response);

			if (!response.isEmpty()) {
				Gson gson = new Gson();
				ArrayList<RacerRun> tmpData = gson.fromJson(response, new TypeToken<Collection<RacerRun>>() {
				}.getType());
				tmpData.sort(cmp);
				data.add(tmpData);
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
