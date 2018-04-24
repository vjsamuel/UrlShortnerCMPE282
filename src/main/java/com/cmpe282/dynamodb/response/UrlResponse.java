package com.javasampleapproach.dynamodb.response;

public class UrlResponse {

	private final String shortenedURL;
	private final String originalURL;
	private final String Status;
	
	public UrlResponse(String shortenedURL, String originalURL,  String Status) {
		this.shortenedURL = shortenedURL;
		this.originalURL = originalURL;
		this.Status = Status;
	}
	
	public String getShorternedURL() {
		return this.shortenedURL;
	}
	
	public String getOriginalURL() {
		return this.originalURL;
	}
	
	public String getStatus() {
		return this.Status;
	}
}
