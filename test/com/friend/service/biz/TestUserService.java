package com.friend.service.biz;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.friend.dao.UserDao;
import com.friend.service.ibiz.IUser;
import com.friend.util.Pager;
import com.friend.vo.User;
/**
 * 
 * @author 梅刚 2014-7-17
 *
 */
public class TestUserService {
	private IUser iuser;
	private UserDao userDao;
	@Before
	public void before(){
		iuser = new UserService();
		userDao = new UserDao();
	}
	
	@Test
	public void testRegisterUser(){
		User user = new User();
		user.setCreateTime(new Date());
		user.setUserName("测试UserService");
		user.setPassword("1111");
		user.setSex("女");
		user.setEmail("xxx@ss.com");
		iuser.registerUser(user);
	}
	@Test
	public void testLogin(){
		User user = new User();
		user.setUserName("测试UserService");
		user.setPassword("11112");
		User resUser = iuser.login(user);
		if(resUser!=null){
			System.out.println("登录成功");
		}else{
			System.out.println("用户名密码错误");
		}
	}
	
	@Test
	public void testUpdatePwd(){
		User user = userDao.getUserById(1);
		//iuser.updatePassword(user, "nishuole");
	}
	@Test
	public void testGetPage(){
		Pager page = new Pager();
		page.setCurrentPage(3);
		page.setPageSize(5);
		page = iuser.getPage(page);
		page.paging(page.getCurrentPage(), page.getPageSize(), page.getTotalCount());
		
		List<User> users = page.getList();
		for(User user : users){
			System.out.println(user.toString());
		}
	}
}
