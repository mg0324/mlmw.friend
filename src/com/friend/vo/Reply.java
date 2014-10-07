/**
 * 
 */
package com.friend.vo;

import java.util.Date;

/**
 * @author szh 2014/7/17
 *
 */
public class Reply {
	private int replyId;
	private String content;
	private String author;
	private Date publishTime;
	private Comment comment;
	private User user;
	private User lastReplayer;//所回复的人的id
	
	/**
	 * @return the replyId
	 */
	public int getReplyId() {
		return replyId;
	}

	/**
	 * @param replyId the replyId to set
	 */
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the lastReplayer
	 */
	public User getLastReplayer() {
		return lastReplayer;
	}

	/**
	 * @param lastReplayer the lastReplayer to set
	 */
	public void setLastReplayer(User lastReplayer) {
		this.lastReplayer = lastReplayer;
	}

}
