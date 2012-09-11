package com.mapinfopage.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mapinfopage.shared.Category;

public class CategoryListTabWidget
extends Composite {

	
	////////////////////////////////////////////////////////////
	// WIDGET SETUP
	////////////////////////////////////////////////////////////
	interface CategoryListTabWidgetUiBinder extends UiBinder<Widget, CategoryListTabWidget> {}
	private static CategoryListTabWidgetUiBinder uiBinder = GWT.create(CategoryListTabWidgetUiBinder.class);

	
	////////////////////////////////////////////////////////////
	// UI ELEMENTS
	////////////////////////////////////////////////////////////
	@UiField Image tabImage;
	@UiField Label tabText;

	
	////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	////////////////////////////////////////////////////////////
	public CategoryListTabWidget(Category category) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		this.tabImage.setUrl(category.getImageUrl());
		this.tabText.setText(category.getName());
		
	}

	
}
