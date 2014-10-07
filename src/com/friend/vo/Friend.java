package com.friend.vo;

import java.util.Date;

/**
 * 
 * @author 周皓 2014/07/17 23:54
 *
 */
public class Friend {
	private int friendId;
	private User user;
	private User userFriend;
	private FriendSort friendSort;
	private Date buildTime;
	private Date lastTalkTime;
	private String focus;  //新增属性
	private String blackFlag;  //新增属性
	
	public String getFocus() {
		return focus;
	}
	public void setFocus(String focus) {
		this.focus = focus;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUserFriend() {
		return userFriend;
	}
	public void setUserFriend(User userFriend) {
		this.userFriend = userFriend;
	}
	public FriendSort getFriendSort() {
		return friendSort;
	}
	public void setFriendSort(FriendSort friendSort) {
		this.friendSort = friendSort;
	}
	public Date getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}
	public Date getLastTalkTime() {
		return lastTalkTime;
	}
	public void setLastTalkTime(Date lastTalkTime) {
		this.lastTalkTime = lastTalkTime;
	}
	public String getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(String blackFlag) {
		this.blackFlag = blackFlag;
	}
}
