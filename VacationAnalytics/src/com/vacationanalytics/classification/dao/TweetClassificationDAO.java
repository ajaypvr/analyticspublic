package com.vacationanalytics.classification.dao;

import java.util.List;
import java.util.Map;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.train.TweetTrainingInstance;

public interface TweetClassificationDAO {
	
	public Map<TweetCategoryType, Map<String, Integer>> getTweetCategoryWordCount();
	
	public void insertUpdateTweetCategoryWordCount(Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCountMap);
	
	public Map<TweetCategoryType, Integer> getTweetCategoryCount();
	
	public void insertUpdateTweetCategoryCount(Map<TweetCategoryType, Integer> tweetCategoryCountMap);
	
	public List<TweetTrainingInstance> getTweetTraining();
	
	public void insertTweetTraining(List<TweetTrainingInstance> tweetInstanceList);
	
	

}
