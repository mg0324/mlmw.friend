package com.friend.vo;

import java.util.Date;
import java.util.List;
/**
 * 
 * @author 周皓 2014/07/17 23:54
 *
 */
public class FriendSort {
	private int friendSortId;
	private String sortName;
	private int friendCount;
	private User user;
	private Date createTime;
	private List<Friend> friendList;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getFriendSortId() {
		return friendSortId;
	}
	public void setFriendSortId(int friendSortId) {
		this.friendSortId = friendSortId;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public int getFriendCount() {
		return friendCount;
	}
	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Friend> getFriendList() {
		return friendList;
	}
	public void setFriendList(List<Friend> friendList) {
		this.friendList = friendList;
	}
}
