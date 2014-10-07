package com.friend.vo;


import java.util.Date;
import java.util.List;


/**
 * 
 * @author 余瑞 2014-7-16 19:02
 *
 */

public class LogSort {

	private int logSortId;
	private String sortName;
	private int logCount;
	private User user;
	private Date createTime;	
	private List<Log> logList;
	
	
	public List<Log> getLogList() {
		return logList;
	}
	public void setLogList(List<Log> logList) {
		this.logList = logList;
	}
	public int getLogCount() {
		return logCount;
	}
	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}
	public int getLogSortId() {
		return logSortId;
	}
	public void setLogSortId(int logSortId) {
		this.logSortId = logSortId;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	

	
	

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


}
