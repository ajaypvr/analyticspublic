package com.vacationanalytics.tweets;

import java.net.URL;
import java.sql.Timestamp;

import twitter4j.Status;

public class TweetTranslator {
	
	
	public static TweetStatus translateToTweetStatus(Status status){
		
		TweetStatus tweetStatus = new TweetStatus();
		
		tweetStatus.setTimeCreated(new Timestamp(status.getCreatedAt().getTime()));
		tweetStatus.setTweetId(status.getId());
		tweetStatus.setUserId(status.getUser().getId());
		tweetStatus.setTweetText(status.getText());
		tweetStatus.setUserLocation(status.getUser().getLocation());
		tweetStatus.setUserName(status.getUser().getName());
		URL profileURL = status.getUser().getProfileImageURL();
		tweetStatus.setProfileImageURL(profileURL.getProtocol() + "://" +profileURL.getHost() + profileURL.getPath());
		
		return tweetStatus;
		
	}

}
