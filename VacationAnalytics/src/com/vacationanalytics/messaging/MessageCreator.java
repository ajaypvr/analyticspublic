package com.vacationanalytics.messaging;

import com.vacationanalytics.tweets.TweetStatus;

/**
 * Creates the message and sends it for further processing
 * @author ajaymangalam
 *
 */
public interface MessageCreator {
	
	public void sendMessage(TweetStatus message);

}
