package cz.fel.cvut.via.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;

import cz.fel.cvut.via.entities.Note;
import cz.fel.cvut.via.entities.Share;

public class SendAndReceive {
	private static Gson gson = new Gson();
	
	//send POST request to urlPart
	//receives response, converts to string and returns
	public static String doInputOutput(String urlPart, Object data, String method) throws Exception {
		try {
			URL url = new URL(Utils.URL +  urlPart);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			setConnectionParameters(null, con, method, true, true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(gson.toJson(data));
			System.out.println("JSON: " + gson.toJson(data));
			wr.flush();
			wr.close();			
			
			printResponse(con);
			
			return getResponse(con);

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
			
			setConnectionParameters(token, con, "GET", true, false);

			printResponse(con);

			
			return getResponse(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	    //send GET request and return String as a response
		public static String getAllSharedNotes(String username, String token) throws Exception {
			try {
				URL url = new URL(Utils.URL +  "/" + username + "/shared");

				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				
				setConnectionParameters(token, con, "GET", true, false);

				printResponse(con);

				
				return getResponse(con);

			} catch (Exception e) {
				e.printStackTrace();
			}
		
			return null;
		}
	
	public static String doInputOutputAuthenticated(String urlPart, Object data, String method, String token) throws Exception {
		try {
			URL url = new URL(Utils.URL +  urlPart);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			

			setConnectionParameters(token, con, method, true, true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(gson.toJson(data));
			wr.flush();
			wr.close();			
			
			printResponse(con);
			
			
			//response
			return getResponse(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}

	private static String getResponse(HttpURLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		String resp = response.toString();
		
		return resp;
	}
	
	
	//delete note or remove from shared with me
	public static String deleteNote(Note note, String token, String username, boolean shared) throws Exception {
		try {
			URL url = null;
			
			if(!shared)
				url  = new URL(Utils.URL +  "/" + username + "/notes/" + note.getId());
			else
				url = new URL(Utils.URL +  "/" + username + "/shared/" + note.getId());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("DELETE");
			con.setRequestProperty("X-Token", token);

			setConnectionParameters(token, con, "DELETE", false, false);
			
			int responseCode = con.getResponseCode();
			System.out.println("Response to Delete: " + responseCode);



		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}

	//share note to another user
	public static String shareNote(Note note, String shareToUsername, boolean readOnly, String token, String username) throws Exception {
		try {
			URL url = new URL(Utils.URL +  "/" + username + "/sharing/" + note.getId());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
	
			setConnectionParameters(token, con, "POST", false, true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes("[{\"username\":\""+ shareToUsername +"\", \"readonly\":"+ (readOnly?1:0) +"}]");

			wr.flush();
			wr.close();	
			
			printResponse(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	
	//update note
	public static String editNote(Note note, String token, String username, boolean shared) throws Exception {
		try {
			URL url = null;
			
			if (!shared)
				url = new URL(Utils.URL +  "/" + username + "/notes/" + note.getId());
			else
				url = new URL(Utils.URL +  "/" + username + "/shared/" + note.getId());
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			setConnectionParameters(token, con, "PUT", true, false);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(gson.toJson(note));

			wr.flush();
			wr.close();	
			
			printResponse(con);


		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}

	private static void printResponse(HttpURLConnection con) throws IOException {
		int responseCode = con.getResponseCode();
		Log.d(SendAndReceive.class.getCanonicalName(), "Response: " + responseCode);
	}

	private static void setConnectionParameters(String token, HttpURLConnection con, String method, boolean input, boolean output) throws ProtocolException {
		Log.d(SendAndReceive.class.getCanonicalName(), "URL: " + con.getURL().toString());
		
		con.setRequestMethod(method);
		
		if (token != null)
			con.setRequestProperty("X-Token", token);
		
		
		con.setRequestProperty("Content-Type", "application/json");
		con.addRequestProperty("Accept", "application/json");
		
		// Send post request		
		con.setDoOutput(output);
		con.setDoInput(input);
		
	}
	
	//send GET request and return String as a response
	public static String getAllMySharedNotes(String username, String token) throws Exception {
		try {
			URL url = new URL(Utils.URL +  "/" + username + "/sharing");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			setConnectionParameters(token, con, "GET", true, false);

			printResponse(con);

			
			return getResponse(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
    //send GET request and return String as a response
	public static String getSharesToNote(Note n, String username, String token) throws Exception {
		try {
			URL url = new URL(Utils.URL +  "/" + username + "/sharing/" + n.getId());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			setConnectionParameters(token, con, "GET", true, false);

			printResponse(con);

			
			return getResponse(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	//update shares
	public static String updateMyShares(Note note, List<Share> shares, String token, String username) throws Exception {
		try {
			URL url = null;
			
			url = new URL(Utils.URL +  "/" + username + "/sharing/" + note.getId());
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			setConnectionParameters(token, con, "PUT", true, false);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(gson.toJson(shares));

			System.out.println("JSON UPDATE: " + gson.toJson(shares));
			
			wr.flush();
			wr.close();	
			
			printResponse(con);


		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	//delete note or remove from shared with me
		public static String deleteMySharedNote(Note note, String token, String username) throws Exception {
			try {
				URL url = null;
				
				
				url = new URL(Utils.URL +  "/" + username + "/sharing/" + note.getId());
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				
				con.setRequestMethod("DELETE");
				con.setRequestProperty("X-Token", token);

				setConnectionParameters(token, con, "DELETE", false, false);
				
				int responseCode = con.getResponseCode();
				System.out.println("Response to Delete: " + responseCode);



			} catch (Exception e) {
				e.printStackTrace();
			}
		
			return null;
		}

	
}
