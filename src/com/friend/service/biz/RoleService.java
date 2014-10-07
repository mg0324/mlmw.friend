package com.friend.service.biz;

import java.util.List;

import com.friend.dao.RoleDao;
import com.friend.service.ibiz.IRole;
import com.friend.util.Pager;
import com.friend.vo.Role;
/**
 * 
 * @author 梅刚 2014-7-18 9:55
 *
 */
public class RoleService implements IRole{
	private RoleDao roleDao;
	public RoleService(){
		roleDao = new RoleDao();
	}
	@Override
	public boolean addRole(Role role) {
		// TODO Auto-generated method stub
		return roleDao.addRole(role);
	}

	@Override
	public Pager listRole(Pager page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRole(Role role) {
		// TODO Auto-generated method stub
		return roleDao.deleteRole(role);
	}

	@Override
	public boolean updateRole(Role role) {
		// TODO Auto-generated method stub
		return roleDao.updateRole(role);
	}
	@Override
	public List<Role> showAllRole(Pager pager) {
		// TODO Auto-generated method stub
		return roleDao.getAllRole(pager);
	}
	@Override
	public int getAllRoleCount() {
		// TODO Auto-generated method stub
		return roleDao.getAllRoleCount();
	}
	@Override
	public Role getRoleByRoleId(int roleId) {
		return roleDao.getRoleById(roleId);
	}
	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return roleDao.getAllRoles();
	}

}
