package com.friend.vo;

import java.util.Date;
import java.util.List;

/**
 * 日志类
 * @author 余瑞 2014-7-16 18：52
 *
 */

public class Log {
	private int logId;//日志id
	private int viewCount;
	private int commentCount;
	private String logTitle;//日志标题
	private String content;//日志内容
	private Date publishTime;//日志发表时间
	private Date lastUpdateTime;
	private int transferCount;//转载日志
	private String topFlag;//置顶标示
	private LogSort logSort;//日志分类
	private boolean praiseFlag;
	private User author;//日志上传者
	private List<Comment> commentList;
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public boolean getPraiseFlag() {
		return praiseFlag;
	}
	public void setPraiseFlag(boolean praiseFlag) {
		this.praiseFlag = praiseFlag;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getLogTitle() {
		return logTitle;
	}
	public void setLogTitle(String logTitle) {
		this.logTitle = logTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public int getTransferCount() {
		return transferCount;
	}
	public void setTransferCount(int transferCount) {
		this.transferCount = transferCount;
	}
	public String getTopFlag() {
		return topFlag;
	}
	public void setTopFlag(String topFlag) {
		this.topFlag = topFlag;
	}
	public LogSort getLogSort() {
		return logSort;
	}
	public void setLogSort(LogSort logSort) {
		this.logSort = logSort;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
}
