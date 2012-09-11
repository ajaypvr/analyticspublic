package com.mapinfopage.client.widget;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * One cell inside the information table.
 */
public class InfoTableCell
extends Composite {

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface InfoTableCellUiBinder extends UiBinder<Widget, InfoTableCell> {}
	private static InfoTableCellUiBinder uiBinder = GWT.create(InfoTableCellUiBinder.class);

	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField Image imageField;
	@UiField Label nameField;
	@UiField Label textField;
	@UiField Label dateTimeField;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private int top;

	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public InfoTableCell(String imageURL,
	                     String name,
	                     String text,
	                     Date dateTime) {
		
		initWidget(uiBinder.createAndBindUi(this));
		initUI(imageURL,
               name,
               text,
               dateTime);
		this.top = 0;
		
	}

	
	////////////////////////////////////////////////////////////
	// GETTER/SETTER METHODS
	////////////////////////////////////////////////////////////
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}	

	
	////////////////////////////////////////////////////////////
	// UI STATE HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Initialize the user interface for the first time.
	 */
	private void initUI(String imageURL,
                        String name,
                        String text,
                        Date dateTime) {
		
		this.imageField.setUrl(imageURL);
		this.nameField.setText(name);
		this.textField.setText(text);
		
		DateTimeFormat dtf = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		this.dateTimeField.setText(dtf.format(dateTime));
		
	}

	
}
