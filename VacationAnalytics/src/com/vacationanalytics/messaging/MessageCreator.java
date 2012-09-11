package com.vacationanalytics.messaging;

import com.vacationanalytics.tweets.TweetStatus;

public interface MessageCreator {
	
	public void sendMessage(TweetStatus message);

}
