package com.friend.service.ibiz;

import com.friend.util.Pager;
import com.friend.vo.Role;
import com.friend.vo.User;

/**
 * 
 * @author 梅刚 2014-7-16 19:24
 *
 */
public interface IUserRole {
	/**
	 * 授予一个角色给一个用户
	 * @param user 
	 * @param role
	 * @return
	 */
	public boolean grantRoleToUser(User user,Role role);
	/**
	 * 删除一个角色从一个用户
	 * @param user
	 * @param role
	 * @return
	 */
	public boolean deleteRoleFromUser(User user,Role role);
	public Pager listUserRole(Pager page);
}
