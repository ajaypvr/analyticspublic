package com.vacationanalytics.classification.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.train.TweetTrainingInstance;

public class MockTweetClassificationDAO implements TweetClassificationDAO{
	
	private Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCount = new HashMap<TweetCategoryType, Map<String, Integer>>();
	

	private Map<TweetCategoryType, Integer> tweetCategoryCount = new HashMap<TweetCategoryType, Integer>();
	
	@Override
	public Map<TweetCategoryType, Map<String, Integer>> getTweetCategoryWordCount() {
		
		return tweetCategoryWordCount;
	}

	@Override
	public void insertUpdateTweetCategoryWordCount(
			Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCountMap) {
		tweetCategoryWordCount.putAll(tweetCategoryWordCountMap) ;
		
	}

	@Override
	public Map<TweetCategoryType, Integer> getTweetCategoryCount() {
		
		
		return tweetCategoryCount;
	}

	@Override
	public void insertUpdateTweetCategoryCount(
			Map<TweetCategoryType, Integer> tweetCategoryCountMap) {
		tweetCategoryCount.putAll(tweetCategoryCountMap);
		
	}

	@Override
	public List<TweetTrainingInstance> getTweetTraining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertTweetTraining(
			List<TweetTrainingInstance> tweetInstanceList) {
		// TODO Auto-generated method stub
		
	}

	
	public void setTweetCategoryWordCount(
			Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCount) {
		this.tweetCategoryWordCount = tweetCategoryWordCount;
	}

	public void setTweetCategoryCount(
			Map<TweetCategoryType, Integer> tweetCategoryCount) {
		this.tweetCategoryCount = tweetCategoryCount;
	}
	

}
