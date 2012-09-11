package com.mapinfopage.shared;

import java.io.Serializable;

public class DestinationTweetCount implements Serializable{
	
	private String destination;
	private int tweetCount;
	private double longitude;
	private double latitude;
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getTweetCount() {
		return tweetCount;
	}
	public void setTweetCount(int tweetCount) {
		this.tweetCount = tweetCount;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		return "DestinationTweetCount [destination=" + destination
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", tweetCount=" + tweetCount + "]";
	}
	
	
	
	
	
	
	

}
