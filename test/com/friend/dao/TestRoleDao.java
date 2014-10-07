package com.friend.dao;

import org.junit.Before;
import org.junit.Test;

import com.friend.vo.Role;

/**
 * 
 * @author 梅刚 2014-7-18
 *
 */
public class TestRoleDao {
	private RoleDao roleDao;
	@Before
	public void before(){
		roleDao = new RoleDao();
	}
	@Test
	public void testAddRole(){
		Role role = new Role();
		role.setRoleId(120);
		role.setRoleName("xxxx");
		role.setRoleGrade("2");
		if(roleDao.addRole(role)){
			System.out.println("success");
		}else{
			System.out.println("error");
		}
	}
	@Test
	public void testUpdateRole(){
		Role role = roleDao.getRoleById(120);
		role.setRoleName("yyyy22222");
		role.setRoleGrade("3");
		if(roleDao.updateRole(role)){
			System.out.println("success");
		}else{
			System.out.println("error");
		}
	}
	@Test
	public void testDeleteRole(){
		Role role = roleDao.getRoleById(120);
		if(roleDao.deleteRole(role)){
			System.out.println("success");
		}else{
			System.out.println("error");
		}
	}
}
