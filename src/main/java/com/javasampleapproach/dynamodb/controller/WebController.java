package com.javasampleapproach.dynamodb.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.dynamodb.model.UrlMap;
import com.javasampleapproach.dynamodb.repo.UrlRepository;
import com.javasampleapproach.dynamodb.response.UrlResponse;

@CrossOrigin(origins="*")
@RestController
public class WebController {
	
	
	@Autowired
	UrlRepository repository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/url/{shorturl}")
	public ResponseEntity<String> expandUrl(@PathVariable String shorturl){
		if(repository.findBysUrl(shorturl).isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add("Location", repository.findBysUrl(shorturl).get(0).getoUrl());
		return new ResponseEntity<>(header, HttpStatus.PERMANENT_REDIRECT);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/urls")
	public ResponseEntity<List<UrlResponse>> getShortUrls(String user){
		if(user.equals("") || user == null) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		List<UrlMap> userList = repository.findByuserName(user);
		if(userList.isEmpty() == true) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		List<UrlResponse> responseList = new ArrayList<>();
		for(UrlMap listElement : userList ) {
			UrlResponse response = new UrlResponse(listElement.getoUrl(), listElement.getsUrl(), "");
			responseList.add(response);
		}
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/url/shorten")
	public ResponseEntity<UrlResponse> shortenUrl(String user, @RequestBody UrlMap input){
		try {
		if (user.equals(null) || user.isEmpty() == true) {
    		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    	}
		String oUrl = input.getoUrl();
		
		if(oUrl.equals("") || oUrl == null) {
			return new ResponseEntity<> (null,HttpStatus.EXPECTATION_FAILED);
		}
		
		if(!repository.findByoUrl(oUrl).isEmpty()) {
			return new ResponseEntity<UrlResponse> (new UrlResponse(repository.findByoUrl(oUrl).get(0).getsUrl(), oUrl, ""), HttpStatus.ACCEPTED);
		}
		
		String sUrl = getHash(oUrl);
		//(oUrl);

		if (!repository.findBysUrl(sUrl).isEmpty()) {
			long salt = System.currentTimeMillis();
			sUrl = getHash(oUrl + salt);
		}
	
		UrlMap urlMap = new UrlMap(oUrl, sUrl, user);
		repository.save(urlMap);
		return new ResponseEntity<>(new UrlResponse(sUrl, oUrl, ""), HttpStatus.ACCEPTED);
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
	}
  }
	
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
