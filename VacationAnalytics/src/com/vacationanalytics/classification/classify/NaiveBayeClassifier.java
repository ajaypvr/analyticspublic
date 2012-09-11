package com.vacationanalytics.classification.classify;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.dao.TweetClassificationDAO;
import com.vacationanalytics.classification.train.NaiveBayeTraining;
import com.vacationanalytics.tweets.TweetStatus;



public class NaiveBayeClassifier {
	

	private static final Logger log = Logger.getLogger(NaiveBayeTraining.class.getName());
	
	private TweetClassificationDAO tweetClassificationDAO;
	
	private Map<TweetCategoryType, Map<String, Integer>> categoryAttributeCountMap = new HashMap<TweetCategoryType, Map<String, Integer>>();
	private Map<TweetCategoryType, Integer> categoryCountMap = new HashMap<TweetCategoryType, Integer>();
	
	private Set<TweetCategoryType> tweetCategories = new HashSet<TweetCategoryType>();
	
	
	public void setUpNaiveBayeClassifier(){
		categoryAttributeCountMap = tweetClassificationDAO.getTweetCategoryWordCount();
		categoryCountMap = tweetClassificationDAO.getTweetCategoryCount();
		tweetCategories.add(TweetCategoryType.NOTSPAM);
		tweetCategories.add(TweetCategoryType.SPAM);
		
	}
	
	public TweetCategoryType classify(TweetStatus tweetStatus){
		log.info("Start TweetClassifier -> classify");
		
		String tweetText = tweetStatus.getTweetText();
		String[] attributes = tweetText.split(" ");
		
		double bestProbability = 0;
		TweetCategoryType bestTweetCategory = null;
		
		//find out the probability for each category for the tweetInstance
		for(TweetCategoryType tweetCategory: tweetCategories){
			double conditionalProbability = 0;
			int numberOfInstancesForCategory = categoryCountMap.get(tweetCategory);
			
			Map<String, Integer> attributeMap = categoryAttributeCountMap.get(tweetCategory);
			for(String attribute : attributes){
				double attributeProbability = 0;
				
				if(attributeMap.containsKey(attribute)){
					int attributeCount = attributeMap.get(attribute);
					attributeProbability = (double)attributeCount/(double)numberOfInstancesForCategory;
				}else{
					//for attributes that have not appeared in the training set
					attributeProbability = 1d/(double)(getTotalTrainedSetCount() + 1);
				}
				
				if(conditionalProbability == 0){
					conditionalProbability = attributeProbability;
				}else{
					conditionalProbability = conditionalProbability * attributeProbability;
				}
			}
			
			double categoryProbability = conditionalProbability * (double)numberOfInstancesForCategory/(double)getTotalTrainedSetCount();
			
			log.info("classify: category = " + tweetCategory);
			log.info("classify: conditional probability = " + categoryProbability);
			
			if(categoryProbability > bestProbability){
				bestProbability = categoryProbability;
				bestTweetCategory = tweetCategory;
			}
			
		}
		
		return bestTweetCategory;
	}
	
	private int getTotalTrainedSetCount(){
		
		int count = 0;
		for(TweetCategoryType tweetCategoryType : categoryCountMap.keySet()){
			count = count + categoryCountMap.get(tweetCategoryType);
		}
		
		return count;
		
	}

	public void setTweetClassificationDAO(TweetClassificationDAO tweetClassificationDAO) {
		this.tweetClassificationDAO = tweetClassificationDAO;
	}
	
	

}
