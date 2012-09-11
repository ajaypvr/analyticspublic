package com.mapinfopage.server.facebook;

import java.util.Date;

public class Test {
	
	public static void main(String [] args){
		
		long time1 = 1331698599l * 1000l;
		long time2 = 1331748089l * 1000l;

		long time3 = 1295203137 * 1000l;
		
		System.out.println("Time 1 = " + new Date(time1));
		System.out.println("Time 2 = " + new Date(time2));
		System.out.println("Time 3 = " + new Date(time3));
	}

}
