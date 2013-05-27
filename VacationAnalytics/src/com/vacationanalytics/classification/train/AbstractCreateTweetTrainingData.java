package com.vacationanalytics.classification.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.vacationanalytics.classification.TweetCategory.TweetCategoryType;
import com.vacationanalytics.classification.dao.TweetClassificationDAO;

/**
 * Class used to categorized the tweets that are used as training data for the baye classifier
 * @author ajaymangalam
 *
 */
public abstract class AbstractCreateTweetTrainingData {
	
	private TweetClassificationDAO tweetClassificationDAO;
	
	
	
	/**
	 * Iterates through list of tweets and for each tweet take the input from user
	 * for categorization of the tweet.
	 */
	public void train() throws IOException{
		
		
		List<TweetTrainingInstance> tweetTrainingList = getTweetTrainingList();
		List<TweetTrainingInstance> classifiedTweets = new ArrayList<TweetTrainingInstance>();
		
		for(TweetTrainingInstance tweetTrainingInstance : tweetTrainingList) {
			
			try{
				
				String tweetText = tweetTrainingInstance.getTweetText();
				System.out.println("Tweet = " +  tweetText);
				
				Scanner scanner = new Scanner(System.in);
				int userInput = scanner.nextInt();
				tweetTrainingInstance.setTweetText(tweetText);
				if(userInput == 1){
					//it's not a spanm
					
					tweetTrainingInstance.setTweetCategoryType(TweetCategoryType.NOTSPAM);
					classifiedTweets.add(tweetTrainingInstance);
				}else if( userInput == -1 ){
					//it's  a spam
					tweetTrainingInstance.setTweetCategoryType(TweetCategoryType.SPAM);
					classifiedTweets.add(tweetTrainingInstance);
				}else if(userInput == 0){
					break;
				}
				
				
			}catch(Exception e){
				//IGNORE THIS TWEET
				//System.out.println(Arrays.asList(tokens));
				e.printStackTrace();
				continue;
			}
		}
		
		tweetClassificationDAO.insertTweetTraining(classifiedTweets);
		
		
		
	}
	
	public abstract List<TweetTrainingInstance> getTweetTrainingList() throws IOException;

	public void setTweetClassificationDAO(TweetClassificationDAO tweetClassificationDAO) {
		this.tweetClassificationDAO = tweetClassificationDAO;
	}

	
	
	

}
