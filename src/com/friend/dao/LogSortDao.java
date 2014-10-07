package com.friend.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.LogUtil;
import com.friend.util.Property;
import com.friend.vo.Log;
import com.friend.vo.LogSort;
import com.friend.vo.User;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogSortDao extends BaseDao {
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("LogSort");
	}
	/**
	 * 
	 * 添加日志分类
	 * 
	 */
	public boolean addLogSort(LogSort logSort) {
		String sql = "insert into t_log_sort(sort_name,"
				+ "log_count,user_id,create_time)values(?,0,?,?)";

		Object[] params = { logSort.getSortName(), logSort.getUser().getUserId(),
				new Date() };
		int num = update(sql, params);
		if (num > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 删除日志分类
	 * 
	 */
	public boolean deleteLogSort(LogSort logSort) {
		String sql = "delete from t_log_sort where id = ?";
		if (logSort.getSortName().equals("我的日志"))
		{
			LogUtil.logger.info("默认日志分组不能删除");
			return false;
		}
		Object[] params = { logSort.getLogSortId() };
		int num = update(sql, params);
		if (num > 0){
			LogUtil.logger.info(num+"行受影响");
			return true;
		}
		return false;

	}

	/**
	 * 
	 * 修改日志分类
	 * 
	 */
	public int updateLogFromSort(LogSort logSort) {
		String sql = "update t_log_sort set log_count = ? where id = ?";
		Object[] params = { logSort.getLogCount(), logSort.getLogSortId() };
		int num = 0;
		num = update(sql, params);
		return num;
	}

	public LogSort getLogSortById(int logSortId) {
		String sql = "select * from t_log_sort where id = ?";
		Object[] params = { logSortId };
		return makeLogSortFromListMap(findObject(sql, params));
	}
	/**
	 * 是否创建同名的日志分类
	 */
	public LogSort isExitLogSort(LogSort logSort)
	{
		String sql="select * from t_log_sort where user_id=? and sort_name=?";
		Object[] params = { logSort.getUser().getUserId(),logSort.getSortName() };
		return makeLogSortFromListMap(findObject(sql, params));
	}
	/**
	 * 封装List<LogSort>
	 * 
	 * @param listMaps
	 * @return
	 */
	//查看列表日志内容
	public List<LogSort> listLogSort(User user) {
		String sql = "select * from t_log_sort where user_id=?";
		Object[] params = { user.getUserId() };

		List<Map<String, Object>> listMaps = find(sql, params);
		List<LogSort> logSorts = makeLogSortListFromListMap(listMaps);
		return logSorts;
	}

	public boolean createLogSort(LogSort logSort){//部分修改：方法名 返回值及内部代码
		String sql = "insert into t_log_sort(sort_name,"
				+ "log_count,user_id,create_time)values(?, ?, ?, ?)";
		System.out.println("dao:"+logSort.getUser().getUserId()+","+logSort.getSortName());
		Object[] params ={logSort.getSortName(),
				0,
				logSort.getUser().getUserId(),
				new Date()};
		int num = update(sql,params);
		if (num > 0){
			return true;
		}
		return false;
	}

	
	
	public int getLogCount(LogSort logSort){
		String sql = "select count(*) count from t_log where sort_id = ?";
		return getCountFromTable(sql, new Object[]{logSort.getLogSortId()});
	}
	/**
	 * 更新日志分类的logCount
	 * @param logSort
	 */
	public void updateLogCount(LogSort logSort) {
		// TODO Auto-generated method stub
		String sql = "update t_log_sort set log_count =? where id=?";
		Object[] params = {logSort.getLogCount(),logSort.getLogSortId()};
		update(sql, params);
	}
	/**
	 * 重命名日志分类
	 * @param inLs
	 */
	public void reNameLogSortName(LogSort inLs) {
		// TODO Auto-generated method stub
		String sql = "update t_log_sort set sort_name=? where id=?";
		Object[] params = {inLs.getSortName(),inLs.getLogSortId()};
		update(sql, params);
	}
	
	/**
	 * 封装LogSort
	 * 
	 * @param map
	 * @return
	 */
	public List<LogSort> makeLogSortListFromListMap(
			List<Map<String, Object>> listmaps) {
		List<LogSort> logSorts = new ArrayList<LogSort>();
		LogSort logSort = new LogSort();
		for (Map<String, Object> map : listmaps) {
			logSort = makeLogSortFromListMap(map);
			logSorts.add(logSort);
		}
		return logSorts;
	}

	public LogSort makeLogSortFromListMap(Map<String, Object> map) {
		if (map == null)
			return null;
		LogSort logSort = new LogSort();
		doSetValue(map, logSort, list);
		return logSort;
	}
}
