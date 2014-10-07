package com.friend.service.biz;

import java.util.ArrayList;
import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.FriendDao;
import com.friend.dao.FriendSortDao;
import com.friend.dao.UserDao;
import com.friend.service.ibiz.IFriend;
import com.friend.util.Pager;
import com.friend.vo.Friend;
import com.friend.vo.FriendSort;
import com.friend.vo.User;
/**
 * 
 * @author 周皓 2014/07/17 
 *
 */
public class FriendService implements IFriend {
	private static FriendDao friendDao = BaseDaoFactory.getFriendDao();
	private static FriendSortDao friendSortDao = BaseDaoFactory.getFriendSortDao();
	

	@Override
	public boolean addFriend(User user, User friendUser) {//添加好友到默认分组：我的好友
		// TODO Auto-generated method stub
		//8月5日上午修改
		if (friendDao.isExistFriend(user, friendUser))
		{
			System.out.println("请勿重复添加");
			return false;
		}
		if (user.getUserId() == friendUser.getUserId())
		{
			System.out.println("不能加自己为好友");
			return false;
		}
		FriendSortDao friendSortDao = new FriendSortDao();
		FriendSort friendSort = friendSortDao.getFriendSortById(friendSortDao.getDefaultFriendSortId(user, 1));
		//获取“我的好友”分组对象
		friendSortDao.addFriendNumber(friendSort);   //默认分组的好友个数加1
		return friendDao.addFriend(user, friendUser);
	}

	@Override
	public boolean setBlackFriend(Friend friend) {
		//8月5日上午修改（未完成），暂无问题
		// TODO Auto-generated method stub
		Friend newFriend = new Friend();
		int newSortId = friendSortDao.getDefaultFriendSortId(new UserDao().getUserById(friend.getUser().getUserId()),2);
		//获取当前好友关系所属用户的“黑名单”分组id
		newFriend.setFriendSort(friendSortDao.getFriendSortById(newSortId));  //新分组为黑名单
		System.out.println("黑名单id:"+newSortId);
		User user = new User();
		User userFriend = new User();
		user.setUserId(friend.getUser().getUserId());
		userFriend.setUserId(friend.getUserFriend().getUserId());
		int friendId = friendDao.getFriendIdByUserId(user, userFriend);
		System.out.println("朋友id:"+friendId);
		int friendSortId = friendDao.getFriendSortIdByUserId(user, userFriend);
		System.out.println("当前朋友分组id:"+friendSortId);
		FriendSort friendSort = new FriendSort();
		friend.setFriendId(friendId);
		//friend.setFriendSort(friendSortDao.getFriendSortById(sortId));
		friend.setFriendSort(friendSortDao.getFriendSortById(friendSortId));
		friendSort.setFriendSortId(friendSortId);//
		friendSortDao.subFriendNumber(friendSort);//
		friendDao.moveFriendSort(friend, newFriend); //从当前分组移动到黑名单分组
		friendDao.blackFriend(friend);   //当前好友关系的拉黑标记置为真
		friendSort.setFriendSortId(newSortId);//新朋友分组：黑名单
		return friendSortDao.addFriendNumber(friendSort);//
	}
	
	@Override
	public boolean focusFriend(Friend friend){
		User user = new User();
		User userFriend = new User();
		user.setUserId(friend.getUser().getUserId());
		userFriend.setUserId(friend.getUserFriend().getUserId());
		int friendId = friendDao.getFriendIdByUserId(user, userFriend);
		friend.setFriendId(friendId);
		return friendDao.focusFriend(friend);
	}
	
	@Override
	public boolean unFocusFriend(Friend friend){
		User user = new User();
		User userFriend = new User();
		user.setUserId(friend.getUser().getUserId());
		userFriend.setUserId(friend.getUserFriend().getUserId());
		int friendId = friendDao.getFriendIdByUserId(user, userFriend);
		friend.setFriendId(friendId);
		return friendDao.unFocusFriend(friend);
	}
	
	@Override
	public boolean moveFriendSort(Friend friend, FriendSort newFriendSort) {
		//8月5日修改，未完成(暂无问题)
		// TODO Auto-generated method stub
		Friend newFriend = new Friend();
		newFriend.setFriendSort(friendSortDao.getFriendSortById(newFriendSort.getFriendSortId()));
		//****************************************************************
		User user = new User();
		User userFriend = new User();
		user.setUserId(friend.getUser().getUserId());
		userFriend.setUserId(friend.getUserFriend().getUserId());
		int friendSortId = friendDao.getFriendSortIdByUserId(user, userFriend);
		//friend.setFriendSort(newFriendSort);
		int friendId = friendDao.getFriendIdByUserId(user, userFriend);
		friend.setFriendId(friendId);
		//****************************************************************
		FriendSortDao friendSortDao = new FriendSortDao();
		friend.setFriendSort(friendSortDao.getFriendSortById(friendSortId));
		FriendSort nowFriendSort = friendSortDao.getFriendSortById(friendSortId);
		friendSortDao.subFriendNumber(nowFriendSort);//当前分组人数减1
		friendDao.moveFriendSort(friend, newFriend);//
		int blackSortId = friendSortDao.getDefaultFriendSortId(user, 2); 
		if (blackSortId == friendSortId) friendDao.unBlackFriend(friend);
		//如果当前分组是黑名单，则拉黑标记置0；
		if (blackSortId == newFriendSort.getFriendSortId()) friendDao.blackFriend(friend);
		//如果新分组是黑名单，则拉黑标记置1
		return friendSortDao.addFriendNumber(newFriendSort);//新分组人数加1
	}

	/*@Override
	public ArrayList<User> findFriend(User user) {
		// TODO Auto-generated method stub
		return (ArrayList<User>)friendDao.findFriend(user);
	}
    //新增方法
	@Override
	public List<User> searchAllStrangerByTerm(User user, User terms, Pager pager) {
		// TODO Auto-generated method stub
		return friendDao.searchAllStrangerByTerm(user, terms, pager);
	}*/
	//新增方法
	@Override
	public int getSearchResultCount(User user, User terms){
		return friendDao.getSearchResultCount(user, terms);
	}
	@Override
	public List<User> searchAllStrangerByNickname(User user, User terms, Pager pager) {
		// TODO Auto-generated method stub
		return friendDao.searchAllStrangerByNickname(user, terms, pager);
	}
	@Override
	public List<User> searchAllStrangerBySex(User user, User terms, Pager pager) {
		// TODO Auto-generated method stub
		return friendDao.searchAllStrangerBySex(user, terms, pager);
	}
	@Override
	public List<User> searchAllStrangerByAge(User user, User terms, Pager pager) {
		// TODO Auto-generated method stub
		return friendDao.searchAllStrangerByAge(user, terms, pager);
	}
	@Override
	public List<User> searchAllStrangerByHomeTown(User user, User terms, Pager pager) {
		// TODO Auto-generated method stub
		return friendDao.searchAllStrangerByHomeTown(user, terms, pager);
	}
	/*@Override
	public ArrayList<Friend> listAllFriend(User user){
		return friendDao.listAllFriend(user);
	}*/
	
	@Override
	public Friend getFriendByUserAndUserFriend(User user, User userFriend){
		return friendDao.getFriendByUserAndUserFriend(user, userFriend);
	}

	@Override
	public ArrayList<Friend> listAllFriend(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExistNickName(String userNickName) {
		// TODO Auto-generated method stub
		return friendDao.isExistNickName(userNickName);
	}
}
