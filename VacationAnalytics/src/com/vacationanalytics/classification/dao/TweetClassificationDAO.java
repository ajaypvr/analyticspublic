package com.vacationanalytics.classification.dao;

import java.util.List;
import java.util.Map;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.train.TweetTrainingInstance;

/**
 * DAO to retrieve tweet information
 * 
 * @author ajaymangalam
 *
 */
public interface TweetClassificationDAO {
	
	/**
	 * Get the count of attribute within each category
	 */
	public Map<TweetCategoryType, Map<String, Integer>> getTweetCategoryWordCount();
	
	/**
	 * Insert the count of attribute within each category
	 */
	public void insertUpdateTweetCategoryWordCount(Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCountMap);
	
	/**
	 * Get the category type and it's respective count. The count is the total number of tweets that have been classified as 
	 * the given category. The classification would have taken in during training.
	 */
	public Map<TweetCategoryType, Integer> getTweetCategoryCount();
	
	/**
	 * Insert or update the number of instances that belongs to a particular category
	 * @param tweetCategoryCountMap
	 */
	public void insertUpdateTweetCategoryCount(Map<TweetCategoryType, Integer> tweetCategoryCountMap);
	
	/**
	 * Get the list of tweets that are used for training
	 * @return
	 */
	public List<TweetTrainingInstance> getTweetTraining();
	
	/**
	 * Insert the list of tweets that are used for training
	 * @param tweetInstanceList
	 */
	public void insertTweetTraining(List<TweetTrainingInstance> tweetInstanceList);
	
	

}
