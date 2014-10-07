/**
 * 
 */
package com.friend.vo;

import java.util.Date;

/**
 * @author szh 2014/7/17
 *
 */
public class TalkPraise {
	private int talkPraiseId;
	private User user;
	private Talk talk;
	private Date praiseTime;

	/**
	 * @return the talkPraiseId
	 */
	public int getTalkPraiseId() {
		return talkPraiseId;
	}

	/**
	 * @param talkPraiseId the talkPraiseId to set
	 */
	public void setTalkPraiseId(int talkPraiseId) {
		this.talkPraiseId = talkPraiseId;
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
	 * @return the praiseTime
	 */
	public Date getPraiseTime() {
		return praiseTime;
	}

	/**
	 * @param praiseTime the praiseTime to set
	 */
	public void setPraiseTime(Date praiseTime) {
		this.praiseTime = praiseTime;
	}
}
