/**
 * 
 */
package com.friend.vo;

import java.util.Date;
import java.util.List;

/**
 * @author szh 2014/7/17
 *
 */
public class Comment {
	private int commentId;
	private String content;
	private String author;
	private Date publishTime;
	private String commentType;
	private User user;
	private List<Reply> replyList;
	
	/**
	 * @return the commentId
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
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
	 * @return the commentType
	 */
	public String getCommentType() {
		return commentType;
	}

	/**
	 * @param commentType the commentType to set
	 */
	public void setCommentType(String commentType) {
		this.commentType = commentType;
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
	 * @return the replyList
	 */
	public List<Reply> getReplyList() {
		return replyList;
	}

	/**
	 * @param replyList the replyList to set
	 */
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	
}
