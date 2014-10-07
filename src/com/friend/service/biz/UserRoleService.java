package com.friend.service.biz;

import com.friend.dao.UserRoleDao;
import com.friend.service.ibiz.IUserRole;
import com.friend.util.Pager;
import com.friend.vo.Role;
import com.friend.vo.User;
/**
 * 
 * @author 梅刚 2014-7-18
 *
 */
public class UserRoleService implements IUserRole{
	private UserRoleDao userRoleDao;
	public UserRoleService(){
		userRoleDao = new UserRoleDao();
	}
	@Override
	public boolean grantRoleToUser(User user, Role role) {
		// TODO Auto-generated method stub
		return userRoleDao.addUserRole(user,role);
	}

	@Override
	public boolean deleteRoleFromUser(User user, Role role) {
		// TODO Auto-generated method stub
		return userRoleDao.deleteUserRole(user,role);
	}

	@Override
	public Pager listUserRole(Pager page) {
		// TODO Auto-generated method stub
		return null;
	}

}
