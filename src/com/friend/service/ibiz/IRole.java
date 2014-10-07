package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Role;

/**
 * 
 * @author 梅刚 2014-7-16 19:24
 *
 */
public interface IRole {
	public boolean addRole(Role role);
	/**
	 * 分页的role列表方法
	 * @param page 
	 * @return 
	 */
	public Pager listRole(Pager page);
	public boolean deleteRole(Role role);
	public boolean updateRole(Role role);
	public List<Role> showAllRole(Pager pager);
	public int getAllRoleCount();
	public Role getRoleByRoleId(int roleId);
	public List<Role> getAllRoles();
}
