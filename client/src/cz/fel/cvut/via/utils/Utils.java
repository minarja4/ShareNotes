package cz.fel.cvut.via.utils;

import java.io.UnsupportedEncodingException;
import 	java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	public static final String URL = "http://46.255.228.241:8080/sharenotes";
	
	//generuje SHA256 hash ze stringu
	public static String getSHA256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    digest.reset();

	    byte[] byteData = digest.digest(input.getBytes("UTF-8"));
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < byteData.length; i++){
	      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	}
	
}
