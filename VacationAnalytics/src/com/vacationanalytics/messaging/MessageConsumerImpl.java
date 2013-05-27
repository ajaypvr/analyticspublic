package com.vacationanalytics.messaging;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vacationanalytics.processor.StreamProcessor;
import com.vacationanalytics.tweets.TweetStatus;

/**
 * Consumer to read message and send it for further processing
 * @author ajaymangalam
 *
 */
public class MessageConsumerImpl implements MessageConsumer{
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageConsumerImpl.class);
	private Context jndiContext = null;
	private QueueConnectionFactory connectionFactory = null;
	private QueueConnection connection = null;
	private QueueSession session = null;
	private Queue queue = null;
	private String consumerName = null;
	
	private StreamProcessor streamProcessor;

	public MessageConsumerImpl(){
		setupConsumer();
	}
	
	private void setupConsumer(){
		consumerName = "TravelTweets";
		
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
           connectionFactory = (QueueConnectionFactory)jndiContext.lookup("ConnectionFactory");
           
           queue = (Queue)jndiContext.lookup("TravelTweets");
       } catch (NamingException e) {
    	   LOG.error("JNDI API lookup failed: " + e);
           e.printStackTrace();
           System.exit(1);
       }
       
		
	}
	
	

	public void waitForMessages(){
		
			try{
				//create a new subscriber to receive messages
		    	connection = connectionFactory.createQueueConnection();
		    	connection.start();	
				session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		        QueueReceiver queueReceiver = session.createReceiver(queue);
				
				 while(true){
			            
					 Message message = (Message)queueReceiver.receive();
					 if(message instanceof ObjectMessage){
						 ObjectMessage objectMessage = (ObjectMessage)message;
						 streamProcessor.processStream((TweetStatus)objectMessage.getObject());
					 }
					 
					 
		         }
				}catch (JMSException e) {
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

	public void setStreamProcessor(StreamProcessor streamProcessor) {
		this.streamProcessor = streamProcessor;
	}
	
	

}
