package com.friend.vo;

import java.util.List;

/**
 * 
 * @author 梅刚 2014-7-16 19:16
 *
 */
public class Role {
	private int roleId;
	private String roleName;
	private String roleGrade;
	private List<Permission> permissionList;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleGrade() {
		return roleGrade;
	}
	public void setRoleGrade(String roleGrade) {
		this.roleGrade = roleGrade;
	}
	public List<Permission> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	
	
}
