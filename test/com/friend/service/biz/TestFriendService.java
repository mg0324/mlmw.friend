package com.friend.service.biz;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.friend.dao.FriendDao;
import com.friend.dao.FriendSortDao;
import com.friend.service.biz.FriendService;
import com.friend.vo.Friend;
import com.friend.vo.FriendSort;
import com.friend.vo.User;

/**
 * 
 * @author 周皓 2014/07/21 22:08
 *
 */
public class TestFriendService {
	private FriendService friendService;
	public TestFriendService(){
		friendService = new FriendService();
	}
	@Test
	public void testAddFriend(){
		User u1 = new User();
		u1.setUserId(2);
		User u2 = new User();
		u2.setUserId(3);
		System.out.println("zhouhao");
		boolean r = friendService.addFriend(u1, u2);
		System.out.println(r);
	}
	@Test
	public void testSetBlackFriend(){
		Friend friend = new Friend();
		//friend.setFriendId(2);
		System.out.println("zhouhao");
		friend = new FriendDao().getFriendById(2);
		System.out.println(friendService.setBlackFriend(friend));
	}
	@Test
	public void testMoveFriend(){
		Friend friend = new Friend();
		FriendSort friendSort = new FriendSort();
		friend = new FriendDao().getFriendById(2);
		friendSort = new FriendSortDao().getFriendSortById(3);
		System.out.println("zhouhao");
		boolean r = friendService.moveFriend(friend, friendSort);
		System.out.println(r);
	}
	@Test
	public void testFindFriend(){
		User user = new User();
		user.setUserNickName("呵呵");
		//user.setSex("女");
		//user.setHomeTown("河南省");
		//user.setBirthday(new Date(1992, 1, 1));
		System.out.println("zhouhao");
		ArrayList<User> users = friendService.findFriend(user);
		System.out.println("id 用户名      密码    性别 eamil    注册时间");
		for (User u: users)
		{
			System.out.println(u.getUserId()+"  "+u.getUserName()
					+"  "+u.getPassword()+"  "+u.getSex()+"  "+u.getEmail()
					+"  "+u.getCreateTime());
		}
	}//已测试通过
	
}
