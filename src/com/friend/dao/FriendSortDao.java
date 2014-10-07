package com.friend.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.LogUtil;
import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.FriendSort;
import com.friend.vo.User;

/**
 * 
 * @author 周皓 2014/07/17 
 *
 */
public class FriendSortDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("FriendSort");
	}
	private boolean isExistDefaultFriendSort(FriendSort friendSort,int flag){
		//检查当前用户是否已创建“我的好友”和“黑名单”两个默认好友分组
		//flag为区分两个默认分组的标记，值为1表示“我的好友”，为2表示“黑名单”
		String sortName = null;
		if (flag == 1) sortName = "我的好友";
		if (flag == 2) sortName = "黑名单";
		String sql = "select * from t_friend_sort where (sort_name = ?) "
					+ "and (user_id = ?)";
		Object[] params = {sortName, friendSort.getUser().getUserId()};
		List<Map<String,Object>> listMaps = find(sql, params);
		if (listMaps.size() != 1) return false;
		return true;
	}
	public boolean isExistSortName(User user, String sortName){
		String sql = "select count(*) count from t_friend_sort where user_id = ?"
				+ " and sort_name = ?";
		Object[] params = {user.getUserId(), sortName};
		int c = getCountFromTable(sql, params);
		if (c > 0){
			LogUtil.logger.info("该分组名已存在");
			return true;
		}
		return false;
	}
	public boolean createFriendSort(FriendSort friendSort){
		if ((friendSort.getSortName().equals("我的好友") && isExistDefaultFriendSort(friendSort, 1)) 
			|| (friendSort.getSortName().equals("黑名单") && isExistDefaultFriendSort(friendSort, 2)))
		{
			LogUtil.logger.info("该分组名已存在");
			return false;
		}
		String sql = "insert into t_friend_sort(sort_name, friend_count, user_id, create_time)"
					+ " values(?, ?, ?, ?)";
		Object[] params = {friendSort.getSortName(),
				0,
				friendSort.getUser().getUserId(),
				new Date()};
		int c = update(sql, params);
		LogUtil.logger.info(c + "行受影响");
		if ( c > 0) return true;
		return false;
	}
	public boolean deleteFriendSort(FriendSort friendSort){
		if (friendSort.getSortName().equals("我的好友") || friendSort.getSortName().equals("黑名单"))
		{
			LogUtil.logger.info("不能删除默认分组");
			return false;
		}
		String sql = "delete from t_friend_sort where id = ?";
		int c = update(sql, new Object[]{friendSort.getFriendSortId()});
		LogUtil.logger.info(c + "行受影响");
		if ( c > 0) return true; 
		return false;
	}
	public boolean setFriendSortName(FriendSort nowFriendSort, FriendSort newFriendSort){
		//8月5日上午修改
		if (newFriendSort.getSortName().equals("我的好友") 
				||newFriendSort.getSortName().equals("黑名单")) 
		{
			LogUtil.logger.info("分组名非法");
			return false;
		}
		if ((new FriendSortDao().getFriendSortById(nowFriendSort.getFriendSortId()).
				getSortName()).equals("我的好友") || 
			(new FriendSortDao().getFriendSortById(nowFriendSort.getFriendSortId()).
					getSortName()).equals("黑名单"))
		{
			LogUtil.logger.info("不能修改默认分组名");	
			return false;
		}
		String sql = "update t_friend_sort set sort_name = ? where id = ?";
		Object[] params = {newFriendSort.getSortName(), nowFriendSort.getFriendSortId()};
		int c = update(sql, params);
		LogUtil.logger.info(c + "行受影响");
		if ( c > 0) return true;
		return false;
	}
	public boolean addFriendNumber(FriendSort friendSort){
		//日志分组的好友个数+1,8月5日上午修改
		String sql = "select friend_count from t_friend_sort where id = ?";
		Object[] params1 = {friendSort.getFriendSortId()};
		List<Map<String,Object>> listMaps = find(sql, params1);
		int friendNumber = Integer.parseInt(listMaps.get(0).get("friend_count").toString()) + 1;
		sql = "update t_friend_sort set friend_count = ? where id = ?";
		Object[] params2 = {friendNumber, friendSort.getFriendSortId()};
		int c = update(sql, params2);
		LogUtil.logger.info(c + "行受影响");
		if (c > 0) return true;
		return false;
	}
	
	public boolean subFriendNumber(FriendSort friendSort){
		//日志分组的好友个数-1,8月5日上午修改
		String sql = "select friend_count from t_friend_sort where id = ?";
		Object[] params1 = {friendSort.getFriendSortId()};
		List<Map<String,Object>> listMaps = find(sql, params1);
		int friendNumber = Integer.parseInt(listMaps.get(0).get("friend_count").toString()) - 1;
		sql = "update t_friend_sort set friend_count = ? where id = ?";
		Object[] params2 = {friendNumber, friendSort.getFriendSortId()};
		int c = update(sql, params2);
		LogUtil.logger.info(c + "行受影响");
		if (c > 0) return true;
		return false;
	}
	
	public FriendSort getFriendSortById(int friendSortId){
		String sql = "select * from t_friend_sort where id = ?";
		Object[] params = {friendSortId};
		List<Map<String,Object>> listMaps = find(sql, params);
		if (listMaps.size() == 0 )
		{
			LogUtil.logger.info(listMaps+"分组不存在");
			return null;
		} 
		return ((ArrayList<FriendSort>)makeFriendSortListFromListMap(listMaps)).get(0);
	}
	//***********************************************************
	public int getDefaultFriendSortId(User user, int flag){
		//获取当前用户的默认好用分组("我的好友"和“黑名单”)id
		String friendSortName = null;
		if (flag == 1) friendSortName = "我的好友";
		if (flag == 2) friendSortName = "黑名单";
		String sql = "select id from t_friend_sort where (sort_name = ?) "
					+ "and (user_id = ?)";
		Object[] params = {friendSortName, user.getUserId()};
		List<Map<String,Object>> listMaps = find(sql, params);
		if (listMaps.size() != 1)
		{
			LogUtil.logger.info("获取默认好友分组id失败");
			return 0;
		}
		int sortId = Integer.parseInt(listMaps.get(0).get("id").toString());
		return sortId;
	}
	//***********************************************************
	
	public List<FriendSort> makeFriendSortListFromListMap(List<Map<String,Object>> listMaps){
		List<FriendSort> friendSorts = new ArrayList<FriendSort>();
		for(Map<String,Object> map : listMaps){
			FriendSort friendSort = makeFriendSortFromMap(map);
			//添加到users集合
			friendSorts.add(friendSort);
		}
		return friendSorts;
	}
	
	public FriendSort makeFriendSortFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		FriendSort friendSort = new FriendSort();
		doSetValue(map, friendSort, list);
		return friendSort;
	}
	
	public int getFriendSortCount(User user){
		String sql = "select count(*) count from t_friend_sort where user_id = ?";
		Object[] params = {user.getUserId()};
		int num = getCountFromTable(sql, params);
		return num;
	}
	
	public ArrayList<FriendSort> findAllFriendSort2(User user){
		//从t_friend_sort表中查找所有的分组
		String sql = null;
		Object[] params = null;
		if (user.getUserId() != 0)
		{
			sql = "select * from t_friend_sort where user_id = ? "
					+"order by id asc";
			//按好友个数升序排序
			params = new Object[]{user.getUserId()};
		}
		else
		{
			sql = "select * from t_friend_sort group by "
					+"user_id order by sort_name asc";
			//按所属用户id分组 分组名升序排序
		}
		List<Map<String,Object>> listMaps = find(sql, params);
		if (listMaps.size() == 0)
		{
			System.out.println("查找失败");
			return null;
		}
		return (ArrayList<FriendSort>)makeFriendSortListFromListMap(listMaps);
	}
	
	public ArrayList<FriendSort> findAllFriendSort(User user, Pager pager){
		//从t_friend_sort表中查找所有的分组
		String sql = "select * from t_friend_sort where user_id = ? "
				+"order by id asc limit ?, ?";
		//按好友个数升序排序
		Object[] params ={user.getUserId(), 
						(pager.getCurrentPage() - 1) * pager.getPageSize(),
						pager.getPageSize()};
		List<Map<String,Object>> listMaps = find(sql, params);
		if (listMaps.size() == 0)
		{
			LogUtil.logger.info("查找失败");
			return null;
		}
		return (ArrayList<FriendSort>)makeFriendSortListFromListMap(listMaps);
	}
	
	public boolean moveAllFriendTo(FriendSort nowFriendSort, FriendSort newFriendSort){
		//8月5日上午修改，未完成**************************************************
		String sql = "update t_friend set friend_sort_id = ? where "
					+ "friend_sort_id = ?";
		Object[] params = {newFriendSort.getFriendSortId(), nowFriendSort.getFriendSortId()};
		int c = update(sql, params);
		if ( c > 0) 
		{
			LogUtil.logger.info(c + "行受影响");
			return true;
		}
		return false;
	}
}
