package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Property;
import com.friend.vo.Log;
import com.friend.vo.LogPraise;
import com.friend.vo.User;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogPraiseDao extends BaseDao{
	
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("LogPraise");
	}
	/**
	 * 添加点赞记录
	 * @param logPraise
	 * @return 1或0 1表示增加成功，0表示增加失败
	 */
	public boolean addLogParise(LogPraise logPraise){
		String sql = "insert into t_log_praise (log_id,user_id,praise_time) values(?,?,?)";
		Object[] params = {logPraise.getLog().getLogId(),
				logPraise.getUser().getUserId(),
				logPraise.getPraiseTime()};
		int num = 0;
		num = update(sql,params);
		if(num > 0){
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 删除点赞记录
	 * @param logPraise
	 * @return 1或0 1表示删除陈功，0表示删除失败
	 */
	public void deleteLogPraise(LogPraise logPraise){
		String sql = "delete from t_log_praise where user_id = ? and log_id=?";
		Object[] params = {logPraise.getUser().getUserId(),
				logPraise.getLog().getLogId()
				};
		update(sql,params);
			
	}
	/**
	 * 查看是否点赞
	 */
	public int isPraise(LogPraise logPraise)
	{
		String sql="select * from t_log_praise where log_id=? and user_id=?";
		Object [] params={logPraise.getLog().getLogId(),
			logPraise.getUser().getUserId()
		};
		LogPraise logpraise=makeLogpraiseFromMap(findObject(sql,params));
		if(logpraise==null)
			return 0;
		else 
			return 1;
		
	}
	/**
	 * 查看某日志的点赞记录
	 * @param log
	 * @return 该条日志的点赞的记录数
	 */
	public int selectLogPraise(Log log) {
		// TODO Auto-generated method stub
		String sql = "select count(*) count from t_log_praise where log_id=?";
		Object[] params = {log.getLogId()};
		int n = getCountFromTable(sql,params);
		if(n > 0){
			System.out.println(n);
			return n;
		}
		return 0;
	}
	
	
	
	public LogPraise getLogpraiseById(int logPraiseId) {
		String sql="select * from t_log_priase where id=?";
		Object[] params={logPraiseId};
		return makeLogpraiseFromMap(findObject(sql,params));
	}
	
	
	/**
	 * 封装List<LogSort>
	 * @param listMaps
	 * @return
	 */
	public List<LogPraise> makeLogpraiseListFromListMap(List<Map<String,Object>> listMaps){
		List<LogPraise> logPraises = new ArrayList<LogPraise>();
		for(Map<String,Object> map : listMaps){
			LogPraise logPraise =makeLogpraiseFromMap(map);
			logPraises.add(logPraise);
		}
		return logPraises;
 	}
	/**
	 * 封装Logpraise
	 * @param map
	 * @return
	 */
	public LogPraise makeLogpraiseFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		LogPraise logPraise = new LogPraise();
		doSetValue(map, logPraise, list);
		return logPraise;
	}
	/**
	 * 
	 * @param user
	 * @param log
	 * @return
	 */
	public LogPraise getLogPraiseByUserAndLog(User user, Log log) {
		// TODO Auto-generated method stub
		String sql="select * from t_log_praise where user_id=? and log_id=?";
		Object[] params={user.getUserId(),log.getLogId()};
		return makeLogpraiseFromMap(findObject(sql, params));
	}
}
