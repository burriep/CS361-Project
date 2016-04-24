package chronotimer;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebClient {
	URL site;
	HttpURLConnection conn;
	DataOutputStream out;

	public WebClient() {
		try {
			site = new URL("http://localhost:8000/sendresults");
		} catch (MalformedURLException e) {
		}
	}

	public boolean sendData(String message) {
		try {
			conn = (HttpURLConnection) site.openConnection();

			// now create a POST request
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new DataOutputStream(conn.getOutputStream());

			if (message == null) {
				message = "";
			}

			// write out string to output buffer for message
			out.writeBytes(message);
			out.flush();
			out.close();

			// Done sent to server
			InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

			// string to hold the result of reading in the response
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up
			// the Response
			int nextChar;
			while ((nextChar = inputStr.read()) > -1) {
				sb = sb.append((char) nextChar);
			}
			inputStr.close();
			conn.disconnect();
			// TODO actually check if the response was successful
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
