package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Permission;

/**
 * 
 * @author 梅刚 2014-7-16 19:24
 *
 */
public interface IPermission {
	public boolean addPermission(Permission p);
	public boolean deletePermission(Permission p);
	public boolean updatePermission(Permission p);
	public List<Permission> listPermission(Pager page);
	public int getPermissionCount();
	public List<Permission> listPermission();
	public Permission getPermissionById(int pid);
}
