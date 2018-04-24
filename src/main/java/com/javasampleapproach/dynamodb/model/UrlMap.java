package com.javasampleapproach.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@DynamoDBTable( tableName= "UrlMap")
public class UrlMap {
	
	private String id;
	private String oUrl;
	private String sUrl;
	private String userName;
	
	public UrlMap() {
	}
	
	public UrlMap(String originalUrl, String shortUrl, String userName) {
	
		this.oUrl = originalUrl;
		this.sUrl = shortUrl;
		this.userName = userName;
	}
	
	@DynamoDBHashKey(attributeName = "Id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBHashKey(attributeName = "originalUrl")
	public String getoUrl() {
		return oUrl;
	}

	public void setoUrl(String oUrl) {
		this.oUrl = oUrl;
	}
	
	@DynamoDBHashKey(attributeName = "shortUrl")
	public String getsUrl() {
		return sUrl;
	}

	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}
	
	@DynamoDBHashKey(attributeName = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return String.format("UrlMap[id='%s', oURL='%s', sURL='%s', userName='%s']", id, oUrl, sUrl, userName);
	}
}
