package com.friend.service.ibiz;

import java.util.ArrayList;

import com.friend.util.Pager;
import com.friend.vo.Friend;
import com.friend.vo.FriendSort;
import com.friend.vo.User;
/**
 * 
 * @author 周皓 2014/07/17 23:13
 *
 */
public interface IFriendSort {
	public boolean createFriendSort(FriendSort friendSort);
	public boolean deleteFriendSort(FriendSort friendSort);
	public ArrayList<FriendSort> findAllFriendSort(User user);
	public FriendSort getFriendSortById(int friendSortId);
	//新增方法
	public ArrayList<FriendSort> findAllFriendSort(User user, Pager pager);
	int getFriendSortCount(User user);
	public boolean addFriendToSort(FriendSort friendSort);
	public boolean cutFriendFromSort(FriendSort friendSort);
	public boolean renameFriendSort(FriendSort friendSort, FriendSort newFriendSort);
	public ArrayList<User> listAllUserFromFriendSort(FriendSort friendSort, Pager pager);
	//ArrayList<User> listAllFriendUser(User user);
	boolean isExistSortName(User user, String sortName);
}
