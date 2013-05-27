package com.vacationanalytics.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.vacationanalytics.messaging.MessageConsumer;
import com.vacationanalytics.search.dao.TweetSearchDAO;

/**
 * Creates the index that is used during searching.
 * 
 * @author ajaymangalam
 *
 */
public class IndexLocation {
	
	private IndexLocation() {}

	  static final File INDEX_DIR = new File("index");
	  
	  /** Index list of strings */
	  public static void main(String[] args) {
	    
		

	    if (INDEX_DIR.exists()) {
	      System.out.println("Cannot save index to '" +INDEX_DIR+ "' directory, please delete it first");
	      System.exit(1);
	    }
	    
	    List<String> locationList = getLocationToIndex();
	    
	    Date start = new Date();
	    try {
	      IndexWriter writer = new IndexWriter(FSDirectory.open(INDEX_DIR), new StandardAnalyzer(Version.LUCENE_CURRENT), true, IndexWriter.MaxFieldLength.LIMITED);
	      System.out.println("Indexing to directory '" +INDEX_DIR+ "'...");
	      indexLocations(writer, locationList);
	      System.out.println("Optimizing...");
	      writer.optimize();
	      writer.close();

	      Date end = new Date();
	      System.out.println(end.getTime() - start.getTime() + " total milliseconds");

	    } catch (IOException e) {
	      System.out.println(" caught a " + e.getClass() +
	       "\n with message: " + e.getMessage());
	    }
	  }

	  static void indexLocations(IndexWriter writer, List<String> locationList) throws IOException {
		    for(String location : locationList){
		    writer.addDocument(LocationDocument.Document(location));	
		    }
	  }
	  
	  public static List<String> getLocationToIndex(){
		  
		  XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("com/vacationanalytics/spring/resources/applicationContextStreamConsumer.xml"));

		  TweetSearchDAO tweetSearchDAO = (TweetSearchDAO)beanFactory.getBean("tweetSearchDAOImpl");
		  
		  return tweetSearchDAO.getDestinationLocation();
		  
	  }

}
