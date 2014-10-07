package com.friend.service.biz;

import java.util.List;

import com.friend.dao.PermissionDao;
import com.friend.service.ibiz.IPermission;
import com.friend.util.Pager;
import com.friend.vo.Permission;
/**
 * 
 * @author 梅刚 2014-7-18
 *
 */
public class PermissionService implements IPermission{
	private PermissionDao permissionDao;
	public PermissionService(){
		permissionDao = new PermissionDao();
	}
	@Override
	public boolean addPermission(Permission p) {
		// TODO Auto-generated method stub
		return permissionDao.addPermission(p);
	}

	@Override
	public boolean deletePermission(Permission p) {
		// TODO Auto-generated method stub
		return permissionDao.deletePermission(p);
	}

	

	@Override
	public List<Permission> listPermission(Pager page) {
		// TODO Auto-generated method stub
		return permissionDao.listPermission(page);
	}
	@Override
	public int getPermissionCount(){
		return permissionDao.getPermissionCount();
	}
	@Override
	public List<Permission> listPermission(){
		return permissionDao.listPermission();
	}
	@Override
	public boolean updatePermission(Permission p) {
		// TODO Auto-generated method stub
		return permissionDao.updatePermission(p);
	}
	@Override
	public Permission getPermissionById(int pid) {
		// TODO Auto-generated method stub
		return permissionDao.getPermissionById(pid);
	}

}
