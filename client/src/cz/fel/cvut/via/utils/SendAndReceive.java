package cz.fel.cvut.via.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import cz.fel.cvut.via.entities.User;

public class SendAndReceive {
	private static Gson gson = new Gson();
	
	//send POST request to urlPart
	//receives response, converts to string and returns
	public static String doInputOutput(String urlPart, Object data, String method) throws Exception {
		try {
			URL url = new URL(Utils.URL +  urlPart);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod(method);
			con.setRequestProperty("Content-Type", "application/json");

			// Send post request
			con.setDoOutput(true);
			con.setDoInput(true);
			con.addRequestProperty("Accept", "application/json");
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(gson.toJson(data));
			System.out.println("JSON: " + gson.toJson(data));
			wr.flush();
			wr.close();			
			
			int responseCode = con.getResponseCode();

			if (responseCode != 200) {
				System.out.println("RESPONSE: " + responseCode);
				throw new Exception("Chyba, odpoved neni 200!");
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

			String resp = response.toString();
			
			return resp;

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}

	//send GET request and return String as a response
	public static String getAllNotes(String username, String token) throws Exception {
		try {
			URL url = new URL(Utils.URL +  "/" + username + "/notes");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Token", token);
//			con.setRequestProperty("Content-Type", "application/json");

			// Send post request
//			con.setDoOutput(true);
			con.setDoInput(true);
			con.addRequestProperty("Accept", "application/json");
			
//			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//			wr.writeBytes(gson.toJson(data));
//			System.out.println("JSON: " + gson.toJson(data));
//			wr.flush();
//			wr.close();			
			
			int responseCode = con.getResponseCode();

			if (responseCode != 200) {
				System.out.println("RESPONSE: " + responseCode);
				throw new Exception("Chyba pri loginu, odpoved neni 200!");
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

			String resp = response.toString();
			
			return resp;

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
}
