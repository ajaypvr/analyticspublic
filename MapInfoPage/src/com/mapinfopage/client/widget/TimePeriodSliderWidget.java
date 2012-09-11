package com.mapinfopage.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TimePeriodSliderWidget
extends Composite {

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface TimePeriodSliderWidgetUiBinder extends UiBinder<Widget, TimePeriodSliderWidget> {}
	private static TimePeriodSliderWidgetUiBinder uiBinder = GWT.create(TimePeriodSliderWidgetUiBinder.class);

	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField DivElement sliderContainer;
	@UiField DivElement rulesContainer;
	@UiField DivElement labelsContainer;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private TimePeriodSliderValueChangeHandler sliderValueChangeHandler;
	private int currentSliderValue;

	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public TimePeriodSliderWidget() {
		
		initWidget(uiBinder.createAndBindUi(this));
		initUI();

	}
	
	
	////////////////////////////////////////////////////////////
	// PUBLIC INTERFACE METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Return the current slider value as a 0-based integer.
	 */
	public int getCurrentSliderValue() {
		return this.currentSliderValue;
	}
	
	
	/**
	 * Set the ValueChangeHandler to be called when the slider
	 * value changes.
	 */
	public void setSliderValueChangeHandler(TimePeriodSliderValueChangeHandler handler) {
		this.sliderValueChangeHandler = handler;
	}
	
	
	////////////////////////////////////////////////////////////
	// EVENT HANDLER METHODS
	////////////////////////////////////////////////////////////
	private void recordSliderValueChanged(int newValue) {
		
		this.currentSliderValue = newValue;
		if (null != this.sliderValueChangeHandler) {
			this.sliderValueChangeHandler.valueChanged(this.currentSliderValue);
		}
		
	}
	
		
	////////////////////////////////////////////////////////////
	// UI STATE HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Initialize the user interface for the first time.
	 */
	private void initUI() {
		
		initSlider();
		
	}

	
	/**
	 * Initialize the slider.
	 */
	private void initSlider() {
				
		// Create the slider widget
		buildSlider(this.rulesContainer,
				    this.labelsContainer,
				    this.sliderContainer,
				    this);
				
	}

	

	
	////////////////////////////////////////////////////////////
	// SLIDER API HELPER METHODS
	////////////////////////////////////////////////////////////
	/**
	 * Build the JavaScript slider object.
	 */
	private final native JavaScriptObject buildSlider(Element rulesContainer,
			                                          Element labelsContainer,
			                                          Element sliderContainer,
			                                          TimePeriodSliderWidget thiz) /*-{

		var thisObject = this;
		$wnd.dojo.ready( function() {

		    var rules = new $wnd.dijit.form.HorizontalRule({
		        container: "bottomDecoration",
		        count: 8,
		        style: "height: 8px;"
		    }, rulesContainer);
		    
	    	var labels = new $wnd.dijit.form.HorizontalRuleLabels({
		         container: "bottomDecoration",
		         labels: [ "1 Day",
		         	       "1 Week",
		         	       "2 Weeks",
		         	       "1 Month",
		         	       "2 Months",
		         	       "3 Months",
		         	       "6 Months",
		         	       "1 Year" ],
		         labelStyle: "padding-top: 1em; font-size: 75%;"
		     }, labelsContainer);
		    
	    	var slider = new $wnd.dijit.form.HorizontalSlider({
	    			minimum: 0,
	            	maximum: 7,
	            	discreteValues: 8,
	            	onChange: function(newValue) {
	            		thiz.@com.mapinfopage.client.widget.TimePeriodSliderWidget::recordSliderValueChanged(I)(newValue);
	            	}
	    		},
	            sliderContainer);

		});
	                		
	}-*/;
	
	
}
