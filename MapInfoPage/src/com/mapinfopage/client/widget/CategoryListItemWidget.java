package com.mapinfopage.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mapinfopage.shared.Recommendation;

public class CategoryListItemWidget
extends Composite {

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface CategoryListItemWidgetUiBinder extends UiBinder<Widget, CategoryListItemWidget> {}
	private static CategoryListItemWidgetUiBinder uiBinder = GWT.create(CategoryListItemWidgetUiBinder.class);

	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField FocusPanel itemClickHandlerContainer;
	@UiField Image itemImage;
	@UiField Label itemName;
	@UiField Label itemDescription;

	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public CategoryListItemWidget(final Recommendation recommendation) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		this.itemImage.setUrl(recommendation.getImageUrl());
		this.itemName.setText(recommendation.getEntityName());
		this.itemDescription.setText(recommendation.getDescription());
		
		this.itemClickHandlerContainer.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(recommendation.getEntityUrl(), "_blank", "");
			}
		});
		
	}

	
}
