package com.mapinfopage.shared;

import java.io.Serializable;
import java.util.List;


/**
 * Information about one location.
 */
public class LocationInformationDO
implements Serializable {

	
	////////////////////////////////////////////////////////////
	// PRIVATE CLASS VARIABLES
	////////////////////////////////////////////////////////////
	private static final long serialVersionUID = -437827240924287215L;

	
	////////////////////////////////////////////////////////////
	// PRIVATE INSTANCE VARIABLES
	////////////////////////////////////////////////////////////
	private List<TableInformationDO> tableInformationList;
	private List<Category> categoryList;
	
	
	////////////////////////////////////////////////////////////
	// GETTER/SETTER METHODS
	////////////////////////////////////////////////////////////
	public List<TableInformationDO> getTableInformationList() {
		return tableInformationList;
	}
	public void setTableInformationList(
			List<TableInformationDO> tableInformationList) {
		this.tableInformationList = tableInformationList;
	}
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	
}
