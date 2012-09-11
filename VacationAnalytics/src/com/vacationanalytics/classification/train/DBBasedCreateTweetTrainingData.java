package com.vacationanalytics.classification.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vacationanalytics.search.dao.TweetSearchDAO;
import com.vacationanalytics.tweets.TweetStatus;

public class DBBasedCreateTweetTrainingData extends AbstractCreateTweetTrainingData{
	
	TweetSearchDAO tweetSearchDAO;

	/**
	 * Get the list from table
	 */
	@Override
	public List<TweetTrainingInstance> getTweetTrainingList() throws IOException {
		List<TweetTrainingInstance> tweetTrainingList = new ArrayList<TweetTrainingInstance>();
		List<TweetStatus> tweetStatusList = tweetSearchDAO.getTweetDestinationIdentifiedNotClassified();
		
		for(TweetStatus tweetStatus: tweetStatusList){
			TweetTrainingInstance tweetTrainingInstance = new TweetTrainingInstance();
			tweetTrainingInstance.setTweetText(tweetStatus.getTweetText());
			tweetTrainingInstance.setTweetId(tweetStatus.getTweetId());
			tweetTrainingList.add(tweetTrainingInstance);
			
		}
		
		return tweetTrainingList;
	}

	public void setTweetSearchDAO(TweetSearchDAO tweetSearchDAO) {
		this.tweetSearchDAO = tweetSearchDAO;
	}

	
	
	
	

}
