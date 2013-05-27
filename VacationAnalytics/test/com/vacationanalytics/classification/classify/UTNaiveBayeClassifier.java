package com.vacationanalytics.classification.classify;

import java.util.HashMap;
import java.util.Map;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.dao.MockTweetClassificationDAO;
import com.vacationanalytics.classification.dao.TweetClassificationDAO;
import com.vacationanalytics.tweets.TweetStatus;

import junit.framework.Assert;
import junit.framework.TestCase;

public class UTNaiveBayeClassifier extends TestCase{

	private NaiveBayeClassifierImpl naiveBayeClassifier;
	TweetClassificationDAO tweetClassificationDAO;

	/**
	 * Setup
	 * 1. Create instance of NaiveBayeClassifier 
	 * 2. Inject the mock TweetClassificationDAO
	 * 3. Call setUpNaiveBayeClassifier
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		naiveBayeClassifier = new NaiveBayeClassifierImpl();
		tweetClassificationDAO = new MockTweetClassificationDAO();
		setupTweetClassificationDAO((MockTweetClassificationDAO)tweetClassificationDAO);
		naiveBayeClassifier.setTweetClassificationDAO(tweetClassificationDAO);
		naiveBayeClassifier.setUpNaiveBayeClassifier();
	}
	
	/**
	 * Pass in a spam tweet and test that the category for it come out to be spam
	 */
	public void testSpamCategory(){
		
		TweetStatus tweetStatus = new TweetStatus();
		String tweet = "Call us to get the best deal on vacation to rome";
		tweetStatus.setTweetText(tweet);
		TweetCategoryType tweetCategoryType  = naiveBayeClassifier.classify(tweetStatus);
		
		Assert.assertEquals(TweetCategoryType.SPAM, tweetCategoryType);
	}
	
	/**
	 * Pass in a tweet that is not a spam and test that the category for it come out to be notspam
	 */
	public void testNotSpamCategory(){
		
		TweetStatus tweetStatus = new TweetStatus();
		String tweet = "Really having a fun vacation in miami";
		tweetStatus.setTweetText(tweet);
		TweetCategoryType tweetCategoryType  = naiveBayeClassifier.classify(tweetStatus);
		
		Assert.assertEquals(TweetCategoryType.NOTSPAM, tweetCategoryType);
	}
	
	
	private void setupTweetClassificationDAO(MockTweetClassificationDAO mockTweetClassificationDAO){
		Map<TweetCategoryType, Map<String, Integer>> tweetCategoryWordCount = new HashMap<TweetCategoryType, Map<String, Integer>>();
		
		Map<String, Integer> spamCategoryMap = new HashMap<String, Integer>();
		spamCategoryMap.put("deal", 5);
		spamCategoryMap.put("best", 2);
		
		tweetCategoryWordCount.put(TweetCategoryType.SPAM, spamCategoryMap);
		
		Map<String, Integer> noSpamCategoryMap = new HashMap<String, Integer>();
		noSpamCategoryMap.put("best", 5);
		noSpamCategoryMap.put("fun", 10);
		
		tweetCategoryWordCount.put(TweetCategoryType.NOTSPAM, noSpamCategoryMap);
		mockTweetClassificationDAO.setTweetCategoryWordCount(tweetCategoryWordCount);
		
		Map<TweetCategoryType, Integer> tweetCategoryCount = new HashMap<TweetCategoryType, Integer>();
		tweetCategoryCount.put(TweetCategoryType.SPAM, 10);
		tweetCategoryCount.put(TweetCategoryType.NOTSPAM, 10);
		mockTweetClassificationDAO.setTweetCategoryCount(tweetCategoryCount);
		
	}
	
	

}
