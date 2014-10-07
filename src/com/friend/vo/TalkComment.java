/**
 * 
 */
package com.friend.vo;

/**
 * @author szh 2014/7/17
 *
 */
public class TalkComment {
	private int talkCommentId;
	private Talk talk;
	private Comment comment;

	/**
	 * @return the talkCommentId
	 */
	public int getTalkCommentId() {
		return talkCommentId;
	}

	/**
	 * @param talkCommentId the talkCommentId to set
	 */
	public void setTalkCommentId(int talkCommentId) {
		this.talkCommentId = talkCommentId;
	}

	/**
	 * @return the talk
	 */
	public Talk getTalk() {
		return talk;
	}

	/**
	 * @param talk the talk to set
	 */
	public void setTalk(Talk talk) {
		this.talk = talk;
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

}
