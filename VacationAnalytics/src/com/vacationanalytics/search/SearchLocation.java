package com.vacationanalytics.search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Searches the tweet text if the location name exist..
 * 
 * @author ajaymangalam
 *
 */
public class SearchLocation {
	
	String index = "index";
	String field = "location";
	String queries = null;
    int repeat = 0;
    boolean raw = false;
    boolean paging = true;
    int hitsPerPage = 10;
    private QueryParser parser;
    private Searcher searcher;
    
	public void init() {
		
		try{
			IndexReader reader = IndexReader.open(FSDirectory.open(new File(index)), true);
			
			searcher = new IndexSearcher(reader);
		    Analyzer analyzer = new CustomAnalyzer();
		    
		    parser = new AnalyzingQueryParser(Version.LUCENE_30, field, analyzer);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String searchLocation(String text) {
		
		try{
			Query query = parser.parse(text);
			searcher.search(query, null, 100);

			return doStreamingSearch(searcher, query);
			
		}catch(ParseException pe){
			pe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return null;
		
	}
	
	

	/**
	   * This method uses a custom HitCollector implementation which simply prints out
	   * the docId and score of every matching document. 
	   * 
	   *  This simulates the streaming search use case, where all hits are supposed to
	   *  be processed, regardless of their relevance.
	   */
	  public  String doStreamingSearch(final Searcher searcher, Query query) throws IOException {
		  TopScoreDocCollector collector = TopScoreDocCollector.create(5 * hitsPerPage, false);
		  searcher.search(query, collector);
		  ScoreDoc[] hits = collector.topDocs().scoreDocs;
		  
		  if(hits == null || hits.length == 0){
			  return null;
		  }else{
			  //return the first one
			  Document doc = searcher.doc(hits[0].doc);
		      String path = doc.get("path");
		      return path;
		  }
		  
	  }
	  
	  public static void main(String [] args) throws ParseException, IOException{
		  SearchLocation location = new SearchLocation();
		  location.init();
		  String result = location.searchLocation("This is going to vegas");
		  System.out.println("Search Result = " + result);
	  }

	  
}
