package com.friend.util;
/**
 * 
 * @author 梅刚 2014-8-2
 * table2vo的工具类
 *
 */
public class Property{
	private String tableColumn;
	private String voProperty;
	private String type;
	private String daoName;
	private String entityClass;

	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	public String getVoProperty() {
		return voProperty;
	}
	public void setVoProperty(String voProperty) {
		this.voProperty = voProperty;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDaoName() {
		return daoName;
	}
	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}
	
	
}
