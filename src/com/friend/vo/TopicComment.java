package com.friend.vo;
/**
 * 
 * @author 宋启东 2014 07 17 15：38
 *
 */
public class TopicComment {

	private int topicCommentId;//帖子编号
	private Topic topic;
	private int commentId;//评论编号
	
	public int getTopicCommentId() {
		return topicCommentId;
	}
	public void setTopicCommentId(int topicCommentId) {
		this.topicCommentId = topicCommentId;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
}
	

