package com.friend.service.biz;

import java.util.List;

import com.friend.dao.RolePermissionDao;
import com.friend.service.ibiz.IRolePermission;
import com.friend.util.Pager;
import com.friend.vo.Permission;
import com.friend.vo.Role;
import com.friend.vo.RolePermission;
/**
 * 
 * @author 梅刚 2014-7-18 16:29
 *
 */
public class RolePermissionService implements IRolePermission{
	private RolePermissionDao rpdao;
	public RolePermissionService(){
		rpdao = new RolePermissionDao();
	}
	
	@Override
	public Pager listRolePermission(Pager page) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Permission> getRolePermissionList(Role role) {
		return rpdao.getPermissionListByRoleId(role);
	}
	@Override
	public boolean updateRolePermission(RolePermission rp) {
		int n=rpdao.updateRolePermission(rp);
		if(n>0)
			return true;
		else
			return false;
	}
	@Override
	public boolean deletePermissionsFromRolePermission(List<Permission> ps,
			RolePermission rp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addRolePermission(RolePermission rp) {
		return rpdao.addRolePermission(rp);
	}

}
