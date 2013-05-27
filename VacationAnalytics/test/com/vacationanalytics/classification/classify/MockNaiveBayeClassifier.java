package com.vacationanalytics.classification.classify;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.tweets.TweetStatus;

public class MockNaiveBayeClassifier implements NaiveBayeClassifier{
	
	public static final String SPAM_TWEET = "This is Spam from london";
	public static final String NOTSPAM_TWEET = "This is not a Spam from london";

	@Override
	public void setUpNaiveBayeClassifier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TweetCategoryType classify(TweetStatus tweetStatus) {
		String txt = tweetStatus.getTweetText();
		
		if(SPAM_TWEET.equals(txt)) return TweetCategoryType.SPAM;
		if(NOTSPAM_TWEET.equals(txt)) return TweetCategoryType.NOTSPAM;
		
		return null;
	}

	

}
