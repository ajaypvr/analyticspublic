package com.vacationanalytics.test;

import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

public class StringAnalyzer {
	
	private static Set<String> excludeCharacter = new HashSet<String>();
	
	static {
		excludeCharacter.add("#");
		excludeCharacter.add("(");
		excludeCharacter.add(")");
		excludeCharacter.add(":");
	}
	
	public static void main(String[] args) throws Exception{
		StringReader reader = new StringReader("A more important Than who runs the country when barackonama is on vacation  is who runs it when he s NOT");
		
		StringBuffer buffer = new StringBuffer();
		cleanString(reader, buffer);
		
		System.out.println(buffer.toString());
	}
	
	
	public static void cleanString(Reader reader, StringBuffer buffer) throws Exception{
		
		
		StreamTokenizer tokenizer = new StreamTokenizer(reader);
		
		while(tokenizer.nextToken() != StreamTokenizer.TT_EOF){
			if(tokenizer.sval != null){
				//System.out.println(tokenizer.ttype);
				//System.out.println(tokenizer.sval);
				if(tokenizer.ttype == '\'' || tokenizer.ttype == '"'){
					System.out.println("quote matched");
					cleanString(new StringReader(tokenizer.sval), buffer);
				}else{
					buffer.append(tokenizer.sval);
				}
				buffer.append(" ");
			}
			
		}
		
		
	}

}
