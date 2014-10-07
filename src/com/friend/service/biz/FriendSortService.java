package com.friend.service.biz;

import java.util.ArrayList;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.FriendDao;
import com.friend.dao.FriendSortDao;
import com.friend.dao.UserDao;
import com.friend.service.ibiz.IFriendSort;
import com.friend.util.DaoFactory;
import com.friend.util.Pager;
import com.friend.vo.Friend;
import com.friend.vo.FriendSort;
import com.friend.vo.User;
/**
 * 
 * @author 周皓 2014/07/17 23:13
 *
 */
public class FriendSortService implements IFriendSort {
	private static FriendSortDao friendSortDao = BaseDaoFactory.getFriendSortDao();
	
	@Override
	public boolean createFriendSort(FriendSort friendSort) {
		// TODO Auto-generated method stub
		return friendSortDao.createFriendSort(friendSort);
	}
	@Override
	public boolean isExistSortName(User user, String sortName){
		return friendSortDao.isExistSortName(user, sortName);
	}
	@Override
	public boolean renameFriendSort(FriendSort friendSort, FriendSort newFriendSort){
		return friendSortDao.setFriendSortName(friendSort, newFriendSort);
	}
	
	@Override
	public boolean deleteFriendSort(FriendSort friendsort){
		FriendSort friendSort = friendSortDao.getFriendSortById(friendsort.getFriendSortId());;
		int userId = friendSort.getUser().getUserId();
		User user = new User();
		user.setUserId(userId);
		if (friendSortDao.getDefaultFriendSortId(user, 1) == friendSort.getFriendSortId()||
				friendSortDao.getDefaultFriendSortId(user, 2) == friendSort.getFriendSortId())
		{
			System.out.println("系统默认分组不能删除");
			return false;
		}
		FriendDao friendDao = new FriendDao();
		int friendCount = friendSort.getFriendCount();
		//int friendCount = friendDao.getFriendCount(friendSort);  //获取当前好友分组内好友个数
		if (friendCount > 0)
			//在删除分组之前，将当前分组的所有好友移动到“我的好友“分组
		{
			System.out.println("在删除分组之前，将组内所有好友移动到“我的好友“分组");
			int myFriendSortId = friendSortDao.getDefaultFriendSortId(user, 1);
			//默认分组“我的好友”id
			FriendSort newFriendSort = new FriendSort();
			newFriendSort.setFriendSortId(myFriendSortId);
			friendSortDao.moveAllFriendTo(friendSort, newFriendSort);
			//将当前分组的所有好友移动到“我的好友分组”
			for (int i = 0; i < friendCount; i++){
				friendSortDao.subFriendNumber(friendSort);
				friendSortDao.addFriendNumber(newFriendSort);
			}
		}
		return friendSortDao.deleteFriendSort(friendSort);
	}
	@Override
	public boolean addFriendToSort(FriendSort friendSort) {
		// TODO Auto-generated method stub
		return friendSortDao.addFriendNumber(friendSort);
	}

	@Override
	public boolean cutFriendFromSort(FriendSort friendSort) {
		// TODO Auto-generated method stub
		return friendSortDao.subFriendNumber(friendSort);
	}
	
	//***********************************************************
	@Override
	public ArrayList<FriendSort> findAllFriendSort(User user){
		return friendSortDao.findAllFriendSort2(user);
	}
	@Override
	public ArrayList<FriendSort> findAllFriendSort(User user, Pager pager){
		return friendSortDao.findAllFriendSort(user, pager);
	}
	@Override
	public int getFriendSortCount(User user){
		return friendSortDao.getFriendSortCount(user);
	}
	@Override
	public ArrayList<User> listAllUserFromFriendSort(FriendSort friendSort, Pager pager){	
		
		ArrayList<Friend> friends = new FriendDao().listAllFriendFromSort(friendSort, pager);
		if (friends != null){
			ArrayList<User> users = new ArrayList<User>();
			UserDao userDao = new UserDao();
			User user = null;
			for (Friend friend: friends){
				user = userDao.getUserById(friend.getUserFriend().getUserId());
				//这里可以优化
				user.setIsFocused(friend.getFocus());//将对该好友的关注标记绑定在在该好友的属性中
				
				user.setIsBlacked(friend.getBlackFlag());//将对该好友的拉黑标记绑定在该好友的属性中
				users.add(user);
			}
			return users;
		}
		return null;
	}
	/*@Override
	public ArrayList<User> listAllUserFromFriendSort2(0){
		return new FriendDao()
	}
	@Override
	public ArrayList<User> listAllFriendUser(User user){
		ArrayList<User> allFriends = null;
		ArrayList<FriendSort> allFriendSorts = findAllFriendSort(user);
		for (FriendSort fs: allFriendSorts){
			for (User u: listAllUserFromFriendSort(fs)){
				
			}
		}
		return null;
	}*/
	public FriendSort getFriendSortById(int friendSortId){
		return friendSortDao.getFriendSortById(friendSortId);
	}

}
