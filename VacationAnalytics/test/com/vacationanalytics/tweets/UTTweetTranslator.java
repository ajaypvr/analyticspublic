package com.vacationanalytics.tweets;

import twitter4j.Status;
import junit.framework.Assert;
import junit.framework.TestCase;

public class UTTweetTranslator extends TestCase{

	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
	}
	
	public void testTranslateToTweetStatus(){
		
		Status status = new MockStatus();
		TweetStatus tweetStatus = TweetTranslator.translateToTweetStatus(status);

		Assert.assertEquals(status.getId(), tweetStatus.getTweetId());
		Assert.assertEquals(status.getUser().getId(), tweetStatus.getUserId());
		Assert.assertEquals(status.getText(), tweetStatus.getTweetText());
		Assert.assertEquals(status.getUser().getLocation(), tweetStatus.getUserLocation());
		Assert.assertEquals(status.getUser().getName(), tweetStatus.getUserName());
	}
	

}
