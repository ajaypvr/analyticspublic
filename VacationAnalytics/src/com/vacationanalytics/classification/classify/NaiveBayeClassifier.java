package com.vacationanalytics.classification.classify;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.tweets.TweetStatus;


/**
 * This class useses NaiveBaye classification algorithm to assign category to the 
 * tweets.
 * 
 * @author ajaymangalam
 *
 */
public interface NaiveBayeClassifier {
	/**
	 * Setup the basic parameters that are required for the Naive Baye classification
	 */
	public void setUpNaiveBayeClassifier();
	
	/**
	 * Classify the tweet. It will calculate the conditional probability for each category and pick the category
	 * which has the highest conditional probability.
	 * @param tweetStatus
	 * @return
	 */
	public TweetCategoryType classify(TweetStatus tweetStatus);

}
