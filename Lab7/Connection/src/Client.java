import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Client {

	public static void main(String[] args) {
		try {
			System.out.println("in the client");

			// Client will connect to this location
			URL site = new URL("http://localhost:8000/sendresults");
			HttpURLConnection conn = (HttpURLConnection) site.openConnection();

			// now create a POST request
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());

			// build a string that contains JSON from console
			String content = "";
			content += "data=" + getJSON();
			System.out.println("\n" + content);

			// write out string to output buffer for message
			out.writeBytes(content);
			out.flush();
			out.close();

			System.out.println("Done sent to server");

			InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

			// string to hold the result of reading in the response
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up
			// the sharedResponse
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				sb = sb.append((char) nextChar);
				nextChar = inputStr.read();
			}
			System.out.println("Return String: " + sb);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getJSON() {
		Scanner s = new Scanner(System.in);
		String fname, lname, dept, phone;
		Directory d = new Directory();
		int i = 1;

		while (i == 1) {
			System.out.print("Enter first name: ");
			fname = s.next();
			System.out.print("Enter last name: ");
			lname = s.next();
			System.out.print("Enter Dept: ");
			dept = s.next();
			System.out.print("Enter Phone Number: ");
			phone = s.next();

			d.addEmployee(fname, lname, dept, phone);

			System.out.print("Enter more employees? YES[1], NO[0] ");
			i = s.nextInt();
		}

		s.close();
		
		
			
			Gson g = new Gson();
			String out = g.toJson(d.getList());
			System.out.println(out);

		
		return g.toJson(d);
	}

}
