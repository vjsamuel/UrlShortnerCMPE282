package com.javasampleapproach.dynamodb.urlgenerator;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateHash {

	 public static String getHash(String longurl) {
	        // Moving to SHA-512 as it is the secure Hash algorithm:
		        try {
		            MessageDigest md = MessageDigest.getInstance("SHA-512");
		            byte[] sourceurl = md.digest(longurl.getBytes());
		            BigInteger number = new BigInteger(1, sourceurl);
		            String hashtext = number.toString(16);
		            // Now we need to zero pad it if you actually want the full 32 chars.
		            while (hashtext.length() < 32) {
		                hashtext = "0" + hashtext;
		            }
		            return hashtext.substring(0, 7);
		        }
		        catch (NoSuchAlgorithmException e) {
		            throw new RuntimeException(e);
		        }
		    }
}
