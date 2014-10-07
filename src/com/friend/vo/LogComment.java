package com.friend.vo;


/**
 * 
 * @author 余瑞 2014-7-16 19:11
 *
 */

public class LogComment {
	
	private int logCommentId;
	private Log log;
	private Comment comment;
	public int getLogCommentId() {
		return logCommentId;
	}
	public void setLogCommentId(int logCommentId) {
		this.logCommentId = logCommentId;
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}  

}
