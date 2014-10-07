package com.friend.vo;

import java.util.Date;


/**
 * 
 * @author 余瑞 2014-7-16 19:08
 *
 */

public class LogTransfer {
	
	private int logTransferId;
	private User transferUser; //日志转发者
	private User ownerUser;   //日志拥有者
	private Log log;
	private Date transferTime;
	public int getLogTransferId() {
		return logTransferId;
	}
	public void setLogTransferId(int logTransferId) {
		this.logTransferId = logTransferId;
	}
	public User getTransferUser() {
		return transferUser;
	}
	public void setTransferUser(User transferUser) {
		this.transferUser = transferUser;
	}
	public User getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public Date getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
}
