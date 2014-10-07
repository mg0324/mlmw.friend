package com.friend.service.ibiz;

import java.util.ArrayList;
import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Friend;
import com.friend.vo.FriendSort;
import com.friend.vo.User;

/**
 * @author 周皓 2014/07/17 23:53
 */
public interface IFriend {
	
	public boolean addFriend(User user, User friendUser);
	public boolean setBlackFriend(Friend friend);
	//public List<User> findFriend(User user);
	public ArrayList<Friend> listAllFriend(User user);
	public Friend getFriendByUserAndUserFriend(User user, User userFriend);
	public boolean moveFriendSort(Friend friend, FriendSort friendSort);
	public boolean focusFriend(Friend friend);
	public boolean unFocusFriend(Friend friend);
	//public List<User> searchAllStrangerByTerm(User user, User terms, Pager pager);
	public int getSearchResultCount(User user, User terms);
	public List<User> searchAllStrangerByNickname(User user, User terms, Pager pager);
	public List<User> searchAllStrangerBySex(User user, User terms, Pager pager);
	public List<User> searchAllStrangerByAge(User user, User terms, Pager pager);
	public List<User> searchAllStrangerByHomeTown(User user, User terms, Pager pager);
	public boolean isExistNickName(String userNickName);
}
