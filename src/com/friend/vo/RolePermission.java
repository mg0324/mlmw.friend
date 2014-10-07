package com.friend.vo;
/**
 * 
 * @author 梅刚 2014-7-18
 *
 */
public class RolePermission {
	private int rolePermissionId;
	private Role role;
	private String permisssionIds;
	public int getRolePermissionId() {
		return rolePermissionId;
	}
	public void setRolePermissionId(int rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getPermisssionIds() {
		return permisssionIds;
	}
	public void setPermisssionIds(String permisssionIds) {
		this.permisssionIds = permisssionIds;
	}
	
	
}
