package com.mapinfopage.client.page;

public class SliderTimePeriod {

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private String label;
	private int days;
	
	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public SliderTimePeriod(String label,
			               int days) {
		this.label = label;
		this.days = days;
	}

	
	////////////////////////////////////////////////////////////
	// GETTER METHODS
	////////////////////////////////////////////////////////////
	public String getLabel() {
		return this.label;
	}
	
	public int getDays() {
		return this.days;
	}


}
