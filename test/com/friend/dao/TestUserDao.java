package com.friend.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.friend.vo.User;
/**
 * 
 * @author 梅刚 2014-7-17 8:38
 *
 */
public class TestUserDao {
	private UserDao userDao;
	public TestUserDao() {
		// TODO Auto-generated constructor stub
		userDao = new UserDao();
	}
	@Test
	public void testAddUser(){
		User user = new User();
		user.setUserName("bieming");
		user.setPassword("xiaohei123");
		user.setEmail("1092017732@qq.com");
		user.setSex("男");
		user.setCreateTime(new Date());
		user = userDao.addUser(user);
		if(user!=null){
			System.out.println("添加成功");
			System.out.println(user.getPassword());
		}
	}
	@Test
	public void testAddUser2(){
		User user = new User();
		user.setUserName("tiantian");
		user.setPassword("tiantian123");
		user.setEmail("1092017732@qq.com");
		user.setSex("女");
		user.setCreateTime(new Date());
		user = userDao.addUser(user);
		if(user!=null){
			System.out.println("添加成功");
			System.out.println(user.getPassword());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFind(){
		List<User> users = userDao.findAllUsers();
		for(User user : users){
			System.out.println(user.toString());
		}
	}
	
}
