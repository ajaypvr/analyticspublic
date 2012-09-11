package com.vacationanalytics.classification.train;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;



import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.dao.TweetClassificationDAO;

public class NaiveBayeTraining {
	
	private static final Logger log = Logger.getLogger(NaiveBayeTraining.class.getName());
	
	private TweetClassificationDAO tweetClassificationDAO;
	
	private Map<TweetCategoryType, Map<String, Integer>> categoryAttributeCountMap = new HashMap<TweetCategoryType, Map<String, Integer>>();
	private Map<TweetCategoryType, Integer> categoryCountMap = new HashMap<TweetCategoryType, Integer>();
	
	private Set<TweetCategoryType> tweetCategories = new HashSet<TweetCategoryType>();
	
	
	public void setUpBayeTraining(){
		categoryAttributeCountMap = tweetClassificationDAO.getTweetCategoryWordCount();
		categoryCountMap = tweetClassificationDAO.getTweetCategoryCount();
		tweetCategories.add(TweetCategoryType.NOTSPAM);
		tweetCategories.add(TweetCategoryType.SPAM);
		
	}
	
	/**
	 * Takes a list of classified tweets and train the classifier
	 * @param tweetTrainingList
	 */
	public void train(List<TweetTrainingInstance> tweetTrainingList){
		
		
		for(TweetTrainingInstance tweetTrainingInstance: tweetTrainingList){
			
			//log.info("TweetInsance to classify = " + tweetInstance);
			
			String tweetText = tweetTrainingInstance.getTweetText();
			TweetCategoryType tweetCategory = tweetTrainingInstance.getTweetCategoryType();
			
			//Increment the category count
			Integer categoryCount = categoryCountMap.get(tweetCategory);
			if(categoryCount == null){
				categoryCount = 1;
			}else{
				categoryCount = categoryCount.intValue() + 1;
			}
			
			categoryCountMap.put(tweetCategory, categoryCount);
			
			Map<String,Integer> attributeMap = categoryAttributeCountMap.get(tweetCategory);
			if(attributeMap == null){
				attributeMap = new HashMap<String, Integer>();
				categoryAttributeCountMap.put(tweetCategory, attributeMap);
			}
			String[] tweetAttributes = tweetText.split(" ");
			
			for(String attribute : tweetAttributes){
				//increment the attribute count 
				Integer count = attributeMap.get(attribute);
				if(count == null){
					count = 1;
				}else{
					count = count.intValue() + 1;
				}
				attributeMap.put(attribute, count);
			}
		}
		
		saveTrainingResult();
		
		
	}
	
	public void saveTrainingResult(){
		tweetClassificationDAO.insertUpdateTweetCategoryCount(categoryCountMap);
		tweetClassificationDAO.insertUpdateTweetCategoryWordCount(categoryAttributeCountMap);
		
	}

	public void setTweetClassificationDAO(
			TweetClassificationDAO tweetClassificationDAO) {
		this.tweetClassificationDAO = tweetClassificationDAO;
	}
	
	

}
