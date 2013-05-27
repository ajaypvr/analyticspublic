package com.vacationanalytics.classification.train;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;

/**
 * Tweet training object. Used by NaiveBayeTraining for the training process.
 * 
 * @author ajaymangalam
 *
 */
public class TweetTrainingInstance {
	
	private long tweetId;
	private String tweetText;
	private TweetCategoryType tweetCategoryType;
	
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public TweetCategoryType getTweetCategoryType() {
		return tweetCategoryType;
	}
	public void setTweetCategoryType(TweetCategoryType tweetCategoryType) {
		this.tweetCategoryType = tweetCategoryType;
	}
	public long getTweetId() {
		return tweetId;
	}
	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}

}
