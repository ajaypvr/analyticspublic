package com.vacationanalytics.search.dao;

import java.util.List;

import com.vacationanalytics.tweets.TweetStatus;

/**
 * DAO for tweets
 * 
 * @author ajaymangalam
 *
 */
public interface TweetSearchDAO {
	
	/**
	 * Insert the tweets for which the location has been identified but not yet classified
	 * @param tweetStatus
	 */
	public void insertTweetDestinationIdentifiedNotClassified(TweetStatus tweetStatus);
	
	/**
	 * Get the tweets for which the location has been identified but not yet classified
	 * @return
	 */
	public List<TweetStatus> getTweetDestinationIdentifiedNotClassified();
	
	/**
	 * Get the list of locations
	 * @return
	 */
	public List<String> getDestinationLocation();
	
	/**
	 * Insert all the valid destination tweets
	 * @param tweetStatus
	 */
	public void insertTweetDestinationValid(TweetStatus tweetStatus);
	
	/**
	 * Insert all the Invalid destination tweets
	 * @param tweetStatus
	 */
	public void insertTweetDestinationInValid(TweetStatus tweetStatus);

}
