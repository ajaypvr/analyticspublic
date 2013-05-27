package com.vacationanalytics.messaging;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vacationanalytics.tweets.TweetStatus;

/**
 * Connects to the destination queue and send the message as TextMessage
 * @author ajaymangalam
 *
 */
public class MessageCreatorImpl implements MessageCreator{
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageCreatorImpl.class);
	
	 private Context jndiContext = null;
	 private ConnectionFactory connectionFactory = null;
	 private Connection connection = null;
	 private Session session = null;
	 private Destination destination = null;
	 private MessageProducer producer = null;
	 private String destinationName = null;
	
	
	public MessageCreatorImpl(){
		setupCreator();
		
	}
	
	private void setupCreator(){
		destinationName = "TravelTweets";
		
		 /*
         * Create a JNDI API InitialContext object
         */
        try {
        	Properties props = new Properties();
        	props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        	props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
        	props.put("queue.TravelTweets", "TravelTweets");
        	//javax.naming.Context ctx = new InitialContext(props);
            jndiContext = new InitialContext(props);
        } catch (NamingException e) {
        	LOG.error("Could not create JNDI API context: " + e.toString());
            System.exit(1);
        }

        /*
         * Look up connection factory and destination.
         */
        try {
            connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
            destination = (Destination)jndiContext.lookup(destinationName);
        } catch (NamingException e) {
        	LOG.error("JNDI API lookup failed: " + e);
            e.printStackTrace();
            System.exit(1);
        }
	}
	
	

	public void sendMessage(TweetStatus message) {
		 /*
		 * Create connection. Create session from connection; false means
		 * session is not transacted. Create sender and text message. Send
		 * messages, varying text slightly. Send end-of-messages message.
		 * Finally, close connection.
		 */
		try {
		    connection = connectionFactory.createConnection();
		    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    producer = session.createProducer(destination);
		    ObjectMessage objectMessage = session.createObjectMessage(message);
		    
		    producer.send(objectMessage);
		    
		
		    /*
		     * Send a non-text control message indicating end of messages.
		     */
		    //producer.send(session.createMessage());
		} catch (JMSException e) {
			LOG.error("Exception occurred: " + e);
		} finally {
		    if (connection != null) {
		        try {
		            connection.close();
		        } catch (JMSException e) {
		        	LOG.error("Exception occurred: " + e);
		        }
		    }
		}
		
	}

}
