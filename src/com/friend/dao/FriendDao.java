package com.friend.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.LogUtil;
import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Friend;
import com.friend.vo.FriendSort;
import com.friend.vo.User;

/**
 * 
 * @author 周皓 2014/07/17 
 *
 */
public class FriendDao extends BaseDao{
	private static List<Property> list;
	private static UserDao userDao = BaseDaoFactory.getUserDao();
	static{
		list = getListPropertyFromEntityXml("Friend");
	}
	public boolean isExistFriend(User user, User friendUser){
		String sql = "select * from t_friend where (user_id = ?) and (user_friend_id = ?)";
		Object[] params = {user.getUserId(), friendUser.getUserId()};
		List<Map<String, Object>> listMaps = find(sql, params);
		if (listMaps.size() != 1)
		{
			LogUtil.logger.info("好友关系不存在");
			return false;
		}
		LogUtil.logger.info("该好友关系已存在");
		return true;
	}
	//*******************************************************************
	public boolean addFriend(User user, User friendUser){	
		String sql = "insert into t_friend(user_id, user_friend_id, friend_sort_id, build_time, "
					+ "last_talk_time) values(?, ?, ?, ?, ?)";
		int sortId = new FriendSortDao().getDefaultFriendSortId(user, 1);
		if (sortId == 0)
		{
			LogUtil.logger.info("默认分组：我的好友 不存在");
			return false;
		}
		//获取当前用户的“我的好友”分组id
		Object[] params = {user.getUserId(), 
						friendUser.getUserId(), 
						sortId,    //添加好友：默认将该好友加入到“我的好友”分组
						new Date(),
						new Date()};
		int c = update(sql, params);
		if (c > 0) 
		{
			LogUtil.logger.info(c+"行受影响");
			return true;
		}
		return false;
	}
	
	public boolean moveFriendSort(Friend nowFriend, Friend newFriend){
		//8月5日修改,未完成，新分组和当前分组一定不相同
		if (nowFriend.getFriendSort().getFriendSortId() != 
				newFriend.getFriendSort().getFriendSortId())
		{
			String sql = "update t_friend set friend_sort_id = ? where id = ?";
			Object[] params = {newFriend.getFriendSort().getFriendSortId(),
								nowFriend.getFriendId()};
			int c = update(sql, params);
			LogUtil.logger.info(c+"行受影响");
			if (c > 0) return true;
		}
		LogUtil.logger.info("目的分组与当前分组相同");
		return false;
	}
	
	public boolean blackFriend(Friend friend){
		String sql = "update t_friend set black_flag = ? where id = ?";
		Object[] params = {"1", friend.getFriendId()};
		int c = update(sql, params);
		if (c > 0){
			LogUtil.logger.info(c + "行受影响");
			return true;
		}
		return false;
	}
	
	public boolean unBlackFriend(Friend friend){
		String sql = "update t_friend set black_flag = ? where id = ?";
		Object[] params = {"0", friend.getFriendId()};
		int c = update(sql, params);
		if (c > 0){
			LogUtil.logger.info(c + "行受影响");
			return true;
		}
		return false;
	}
	
	public int getSearchResultCount(User user, User terms) {
		// TODO Auto-generated method stub
		Object[] params = null;  //查找条件
		String sql = null;
		if (terms.getUserNickName() != null)   //按昵称查找
		{
			params = new Object[]{terms.getUserNickName(),
								   user.getUserId(),
								   user.getUserId()};
			sql = "select count(*) count from t_user where (user_nickname = ?) and"
					+ " (id not in(select user_friend_id from t_friend where user_id = ?))"
					+ "and (id != ?)";
		}
		else if (terms.getSex() != null)   //按性别查找
		{
			params = new Object[]{terms.getSex(),
								   user.getUserId(),
								   user.getUserId()};
			sql = "select count(*) count from t_user where (sex = ?) and (id not in"
					+ "(select user_friend_id from t_friend where user_id = ?))"
					+ "and (id != ?)";
		}else if (terms.getBirthday() != null)    //按年龄（生日）查找
		{
			String birth = terms.getBirthday().getYear() + 1900 + "%";
			params = new Object[]{birth,
								   user.getUserId(),
								   user.getUserId()};
			sql = "select count(*) count from t_user where (birthday like ?) and"
					+ " (id not in(select user_friend_id from t_friend where user_id = ?))"
					+ " and (id != ?)";
		}else if (terms.getHomeTown() != null)    //按籍贯查找
		{
			String homeTown = terms.getHomeTown() + "%";
			params = new Object[]{homeTown,
								   user.getUserId(),
								   user.getUserId()};
			sql = "select count(*) count from t_user where (home_town like ?) and (id not in"
					+ "(select user_friend_id from t_friend where user_id = ?))"
					+ " and (id != ?)";
		}
		else{
			LogUtil.logger.info("搜索条件为空！");
			return 0;
		}
		return getCountFromTable(sql, params);
	}
	//新增方法******************************************
	public List<User> searchAllStrangerByNickname(User user, User terms, Pager pager){ 
		//按条件查找陌生人用户（剔除本人及其所有好友， 分页）
		Object[] params = {terms.getUserNickName(),
						   user.getUserId(),
						   user.getUserId(),
						   (pager.getCurrentPage() - 1) * pager.getPageSize(),
						   pager.getPageSize()};
		String sql = "select * from t_user where (user_nickname = ?) and (id not in"
					+ "(select user_friend_id from t_friend where user_id = ?)) "
					+ "and (id != ?) limit ?, ?";
		List<User> users = userDao.makeUserListFromListMap(find(sql, params));
		return users;
	}
	public List<User> searchAllStrangerBySex(User user, User terms, Pager pager){ 
		//按条件查找陌生人用户（剔除本人及其所有好友， 分页）
		Object[] params = {terms.getSex(),
						   user.getUserId(),
						   user.getUserId(),
						   (pager.getCurrentPage() - 1) * pager.getPageSize(),
						   pager.getPageSize()};
		String sql = "select * from t_user where (sex = ?) and (id not in"
					+ "(select user_friend_id from t_friend where user_id = ?)) "
					+ "and (id != ?) limit ?, ?";
		List<User> users = userDao.makeUserListFromListMap(find(sql, params));
		return users;
	}
	public List<User> searchAllStrangerByAge(User user, User terms, Pager pager){ 
		//按条件查找陌生人用户（剔除本人及其所有好友， 分页）
		String birth = terms.getBirthday().getYear() + 1900 + "%";
		Object[] params = {birth,
						   user.getUserId(),
						   user.getUserId(),
						   (pager.getCurrentPage() - 1) * pager.getPageSize(),
						   pager.getPageSize()};
		String sql = "select * from t_user where (birthday like ?) and (id not in"
					+ "(select user_friend_id from t_friend where user_id = ?)) "
					+ "and (id != ?) limit ?, ?";
		List<User> users = userDao.makeUserListFromListMap(find(sql, params));
		return users;
	}
	public List<User> searchAllStrangerByHomeTown(User user, User terms, Pager pager){ 
		//按条件查找陌生人用户（剔除本人及其所有好友， 分页）
		String homeTown = terms.getHomeTown() + "%";
		Object[] params = {homeTown,
						   user.getUserId(),
						   user.getUserId(),
						   (pager.getCurrentPage() - 1) * pager.getPageSize(),
						   pager.getPageSize()};
		String sql = "select * from t_user where (home_town like ?) and (id not in"
					+ "(select user_friend_id from t_friend where user_id = ?)) "
					+ "and (id != ?) limit ?, ?";
		List<User> users = userDao.makeUserListFromListMap(find(sql, params));
		return users;
	}
	public Friend getFriendById(int friendId){
		String sql = "select * from t_friend where id = ?";
		Object[] params = {friendId};
		return makeFriendFromMap((findObject(sql, params)));
	}
	
	public Friend getFriendByUserAndUserFriend(User user, User userFriend){
		String sql = "select * from t_friend where (user_id = ?) "+
					"and (user_friend_id = ?)";
		Object[] params = {user.getUserId(), userFriend.getUserId()};
		List<Map<String, Object>> listMaps = find(sql, params);
		if (listMaps.size() == 1)
		{
			LogUtil.logger.info("获取到一个朋友关系id");
			return makeFriendFromMap(listMaps.get(0));
		}
		return null;
	}
	public ArrayList<Friend> makeFriendListFromListMap(List<Map<String,Object>> listMaps){
		ArrayList<Friend> friends = new ArrayList<Friend>();
		for(Map<String,Object> map : listMaps){
			Friend friend = makeFriendFromMap(map);
			//添加到users集合
			friends.add(friend);
		}
		return friends;
	}
	
	public Friend makeFriendFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		Friend friend = new Friend();
		doSetValue(map, friend, list);
		return friend;
	}/**/
	//******************************************************************
	
	public int getFriendIdByUserId(User user,User userFriend){
	 	//通过用户与他的朋友id获取朋友关系id
		if (isExistFriend(user, userFriend))
		{
			String sql = "select * from t_friend where (user_id = ?) "
						+"and (user_friend_id = ?)";
			Object[] params = {user.getUserId(), userFriend.getUserId()};
			List<Map<String, Object>> map = find(sql, params);
			return Integer.parseInt(map.get(0).get("id").toString());
		}
		return 0;
	}/**/
	
	public int getFriendSortIdByUserId(User user,User userFriend){
		//通过用户与他的朋友id获取朋友关系对应的分组id
		if (isExistFriend(user, userFriend)){
			String sql = "select * from t_friend where (user_id = ?) "
						+"and (user_friend_id = ?)";
			Object[] params = {user.getUserId(), userFriend.getUserId()};
			List<Map<String, Object>> map = find(sql, params);
			return Integer.parseInt(map.get(0).get("friend_sort_id").toString());
		}
		return 0;
	}
	//******************************************************************
	public ArrayList<Friend> listAllFriendFromSort(FriendSort friendSort, Pager pager){
		String sql = "select * from t_friend where friend_sort_id = ? limit ?, ?";
		Object[] params = {friendSort.getFriendSortId(),
							(pager.getCurrentPage() - 1) * pager.getPageSize(),
							pager.getPageSize()};
		List<Map<String, Object>> listMaps = find(sql, params);
		if ( listMaps.size() == 0)
		{
			LogUtil.logger.info("从分组获取好友关系失败");
			return null;
		}
		return makeFriendListFromListMap(listMaps);
	}
	
	public boolean focusFriend(Friend friend) {
		// TODO Auto-generated method stub
		String sql = "update t_friend set focus = ? where id = ?";
		int c = update(sql, new Object[]{1, friend.getFriendId()});
		if (c > 0){
			LogUtil.logger.info(c+"行受影响");
			return true;
		}
		return false;
	}
	public boolean unFocusFriend(Friend friend) {
		// TODO Auto-generated method stub
		String sql = "update t_friend set focus = ? where id = ?";
		int c = update(sql, new Object[]{0, friend.getFriendId()});
		if (c > 0){
			LogUtil.logger.info(c+"行受影响");
			return true;
		}
		return false;
	}
	public boolean isExistNickName(String userNickName) {
		// TODO Auto-generated method stub
		String sql = "select count(*) count from t_user where user_nickname = ?";
		int c = getCountFromTable(sql, new Object[]{userNickName});
		if (c > 0){
			LogUtil.logger.info("该昵称存在");
			return true;
		}
		return false;
	}
	
}
