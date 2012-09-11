package com.vacationanalytics.search.dao;

import java.util.List;

import com.vacationanalytics.tweets.TweetStatus;

public interface TweetSearchDAO {
	
	public void insertTweetDestinationIdentifiedNotClassified(TweetStatus tweetStatus);
	
	public List<TweetStatus> getTweetDestinationIdentifiedNotClassified();
	
	public List<String> getDestinationLocation();
	
	public void insertTweetDestinationValid(TweetStatus tweetStatus);
	
	public void insertTweetDestinationInValid(TweetStatus tweetStatus);

}
