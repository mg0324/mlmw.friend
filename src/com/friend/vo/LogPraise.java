package com.friend.vo;

import java.util.Date;

/**
 * 
 * @author 余瑞 2014-7-16 19:16
 *
 */

public class LogPraise {
	
	private int logPraiseId;
	private Log log;
	private User user;
	private Date praiseTime;
	public int getLogPraiseId() {
		return logPraiseId;
	}
	public void setLogPraiseId(int logPraiseId) {
		this.logPraiseId = logPraiseId;
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getPraiseTime() {
		return praiseTime;
	}
	public void setPraiseTime(Date praiseTime) {
		this.praiseTime = praiseTime;
	}
}
