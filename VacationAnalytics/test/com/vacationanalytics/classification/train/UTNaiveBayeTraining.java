package com.vacationanalytics.classification.train;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.dao.MockTweetClassificationDAO;
import com.vacationanalytics.classification.dao.TweetClassificationDAO;

public class UTNaiveBayeTraining extends TestCase{

	private NaiveBayeTraining naiveBayeTraining;
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
		
		naiveBayeTraining = new NaiveBayeTraining();
		tweetClassificationDAO = new MockTweetClassificationDAO();
		naiveBayeTraining.setTweetClassificationDAO(tweetClassificationDAO);
		naiveBayeTraining.setUpBayeTraining();
	}
	
	
	/**
	 * Test training of Spam text
	 */
	
	public void testSpamTraining(){
		List<TweetTrainingInstance> tweetTrainingInstanceList = new ArrayList<TweetTrainingInstance>();
		TweetTrainingInstance first = new TweetTrainingInstance();
		first.setTweetText("call us for the best deal for vacation");
		first.setTweetCategoryType(TweetCategoryType.SPAM);
		tweetTrainingInstanceList.add(first);
		
		naiveBayeTraining.train(tweetTrainingInstanceList);
		
		Map<TweetCategoryType, Integer> tweetCategoryCount = tweetClassificationDAO.getTweetCategoryCount();
		
		Assert.assertEquals(true, tweetCategoryCount.containsKey(TweetCategoryType.SPAM));
		Assert.assertEquals(new Integer(1), tweetCategoryCount.get(TweetCategoryType.SPAM));
		
	}
	
	/**
	 * Test training of text that is not spam
	 */
	public void testNotSpamTraining(){
		
		List<TweetTrainingInstance> tweetTrainingInstanceList = new ArrayList<TweetTrainingInstance>();
		TweetTrainingInstance first = new TweetTrainingInstance();
		first.setTweetText("Really having a fun vacation in miami");
		first.setTweetCategoryType(TweetCategoryType.NOTSPAM);
		tweetTrainingInstanceList.add(first);
		
		naiveBayeTraining.train(tweetTrainingInstanceList);
		
		Map<TweetCategoryType, Integer> tweetCategoryCount = tweetClassificationDAO.getTweetCategoryCount();
		
		Assert.assertEquals(true, tweetCategoryCount.containsKey(TweetCategoryType.NOTSPAM));
		Assert.assertEquals(new Integer(1), tweetCategoryCount.get(TweetCategoryType.NOTSPAM));
		
		
	}

}
