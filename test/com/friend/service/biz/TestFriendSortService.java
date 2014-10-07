package com.friend.service.biz;


import java.util.ArrayList;

import org.junit.Test;

import com.friend.vo.FriendSort;
import com.friend.vo.User;

/**
 * 
 * @author 周皓 2014/07/19 13:32
 *
 */
public class TestFriendSortService {
	
	private FriendSortService friendSortService;

	public TestFriendSortService(){
		friendSortService = new FriendSortService();
	}
	@Test
	public void testCreateFriendSort(){
		//User user = new User();
		//user.setUserId(2);
		FriendSort friendSort = new FriendSort();
		friendSort.setSortName("家人");
		friendSort.setUserId(2);
		System.out.println("zhouhao");
		boolean r = friendSortService.createFriendSort(friendSort);
		System.out.println(r);
	}
	@Test  
	public void testRenameFriendSort(){
		FriendSort friendSort = new FriendSort();
		friendSort.setFriendSortId(5);
		System.out.println("zhouhao");
		boolean r = friendSortService.renameFriendSort(friendSort, "family");
		System.out.println(r);
	}
	@Test
	public void testDeleteFriendSort(){
		FriendSort friendSort = new FriendSort();
		friendSort.setFriendSortId(6);
		System.out.println("zhouhao3");
		boolean r = friendSortService.deleteFriendSort(friendSort);
		System.out.println(r);
	}
	@Test
	public void testAddFriendToSort(){
		FriendSort friendSort = new FriendSort();
		friendSort.setFriendSortId(2);
		System.out.println("zhouhao");
		boolean r = friendSortService.addFriendToSort(friendSort, 5);
		System.out.println(r);
	}
	@Test
	public void testCutFriendFromSort(){
		FriendSort friendSort = new FriendSort();
		friendSort.setFriendSortId(5);
		System.out.println("zhouhao");
		boolean r = friendSortService.cutFriendFromSort(friendSort, 5);
		System.out.println(r);
	}
	@Test
	public void testFindAllFriendSort(){
		User user = new User();
		user.setUserId(2);
		System.out.println("zhouhao");
		ArrayList<FriendSort> friends = friendSortService.findAllFriendSort(user);
		System.out.println("id  分组名    好友个数    所属人id");
		for (FriendSort fs: friends)
		{
			System.out.println(fs.getFriendSortId()+"   "
					+fs.getSortName()+"   "+fs.getFriendCount()+"   "
					+fs.getUserId()+"   ");
		}	
	}//已测试通过
	@Test
	public void testListAllUserFromFriendSort(){
		FriendSort friendSort = new FriendSort();
		friendSort.setFriendSortId(1);
		System.out.println("zhouhao");
		ArrayList<User> users = friendSortService.listAllUserFromFriendSort(friendSort);
		for (User u: users)
		{
			System.out.println(u.getUserId()+" "+u.getUserName()+" "+u.getUserNickName()
						+" "+u.getEmail()+" "+u.getPassword()+" "+u.getSex());
		}
	}
}
