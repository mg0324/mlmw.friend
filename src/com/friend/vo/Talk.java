/**
 * 
 */
package com.friend.vo;

import java.util.Date;
import java.util.List;
import com.friend.vo.Comment;

/**
 * @author szh 2014/7/17
 *
 */
public class Talk {
	private int talkId;
	private String content;
	private String author;
	private Date publishTime;
	private Date lastUpdateTime;
	private int commentCount;
	private int praiseCount;
	private int viewCount;
	private User user;
	private List<Comment> commentList;
	private boolean praiseFlag;
	
	/**
	 * @return the talkId
	 */
	public int getTalkId() {
		return talkId;
	}
	/**
	 * @param talkId the talkId to set
	 */
	public void setTalkId(int talkId) {
		this.talkId = talkId;
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
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}
	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	/**
	 * @return the praiseCount
	 */
	public int getPraiseCount() {
		return praiseCount;
	}
	/**
	 * @param praiseCount the praiseCount to set
	 */
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	/**
	 * @return the viewCount
	 */
	public int getViewCount() {
		return viewCount;
	}
	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
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
	 * @return the commentList
	 */
	public List<Comment> getCommentList() {
		return commentList;
	}
	/**
	 * @param commentList the commentList to set
	 */
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	/**
	 * @return the praiseFlag
	 */
	public boolean isPraiseFlag() {
		return praiseFlag;
	}
	/**
	 * @param praiseFlag the praiseFlag to set
	 */
	public void setPraiseFlag(boolean praiseFlag) {
		this.praiseFlag = praiseFlag;
	}
}
