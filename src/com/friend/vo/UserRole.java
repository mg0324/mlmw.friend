package com.friend.vo;
/**
 * 
 * @author 梅刚 2014-7-18
 *
 */
public class UserRole {
	private int userRoleId;
	private User user;
	private Role role;
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
