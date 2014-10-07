package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Permission;
import com.friend.vo.Role;
import com.friend.vo.RolePermission;

/**
 * 
 * @author 梅刚 2014-7-16 19:24
 *
 */
public interface IRolePermission {

	/**
	 * 将多个权限从一个角色中移除
	 * @param ps
	 * @param rp
	 * @return
	 */
	public boolean deletePermissionsFromRolePermission(List<Permission> ps,RolePermission rp);
	
	public Pager listRolePermission(Pager page);
	
	public List<Permission> getRolePermissionList(Role role);
	
	public boolean updateRolePermission(RolePermission rp);
	public boolean addRolePermission(RolePermission rp);
}
