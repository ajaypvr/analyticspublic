package com.mapinfopage.shared;

import java.io.Serializable;
import java.sql.Timestamp;

public class TweetStatusDO implements Serializable{
	
	private long userId;
	private long tweetId;
	private String tweetText;
	private String userLocation;
	private String userName;
	private Timestamp timeCreated;
	private String destination;
	private String profileImageURL;
	private double heightOfRowRequired;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getTweetId() {
		return tweetId;
	}
	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Timestamp timeCreated) {
		this.timeCreated = timeCreated;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getProfileImageURL() {
		return profileImageURL;
	}
	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}
	
	
	public double getHeightOfRowRequired() {
		return heightOfRowRequired;
	}
	public void setHeightOfRowRequired(double heightOfRowRequired) {
		this.heightOfRowRequired = heightOfRowRequired;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (tweetId ^ (tweetId >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TweetStatusDO other = (TweetStatusDO) obj;
		if (tweetId != other.tweetId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	

}
