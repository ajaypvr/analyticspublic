package com.vacationanalytics.search;

import java.io.StringReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class LocationDocument {
		
	public static Document Document(String location){
		 
		 // make a new, empty document
		 Document doc = new Document();
		
		 doc.add(new Field("path", location, Field.Store.YES, Field.Index.NOT_ANALYZED));

		
		 // Add the contents of the location to a field named "location".  Specify a Reader,
		 // so that the text of the file is tokenized and indexed, but not stored.
		 // 
		 doc.add(new Field("location", new StringReader(location)));
		
		 // return the document
		 return doc;
	}

}
