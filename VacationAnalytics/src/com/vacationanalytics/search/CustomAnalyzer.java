package com.vacationanalytics.search;


import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

/**
 * Analyzer that is used by Lucene for filtering words
 * @author ajaymangalam
 *
 */
public class CustomAnalyzer extends StandardAnalyzer{
	
	private static final String[] ADDITIONAL_STOP_WORDS = {
	      "should", "would", "from", "up", "i", "s", "it", "his", "has", "he",
	      "she", "her", "said", "been", "being", "final", "now", "hour", "minute", "second",
	      "stop", "start", "first", "third", "fast", "slow", "large", "small", "#", "?", "*", "@", "!", "-", "&", ")" ,"(",
	      ":", "'"
	  };
	  
	  private static Set<String> MERGED_STOP_WORDS;
	  
	  static {
		  MERGED_STOP_WORDS = new HashSet<String>(); 
		  MERGED_STOP_WORDS.addAll((Collection<? extends String>) StandardAnalyzer.STOP_WORDS_SET);
		  MERGED_STOP_WORDS.addAll(Arrays.asList(ADDITIONAL_STOP_WORDS));
	  }
	  
	  public CustomAnalyzer() {
	      super(Version.LUCENE_30, CustomAnalyzer.MERGED_STOP_WORDS);
	  }
	  @Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		  
		  return new PorterStemFilter(super.tokenStream(fieldName, reader));
	  }
	  
	  

}
