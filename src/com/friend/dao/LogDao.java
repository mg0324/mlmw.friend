package com.friend.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.pattern.LoggerPatternConverter;

import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.util.Property;
import com.friend.vo.Comment;
import com.friend.vo.Log;
import com.friend.vo.LogSort;
import com.friend.vo.Talk;
import com.friend.vo.User;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogDao extends BaseDao{
	private static List<Property> list;
	private static LogCommentDao logCommentDao = BaseDaoFactory.getLogCommentDao();
	private static LogPraiseDao logPraiseDao = BaseDaoFactory.getLogPraiseDao();
	static{
		list = getListPropertyFromEntityXml("Log");
	}
	
	/**
	 * 
	 * 发表日志
	 * 
	 */

	public boolean addLog(Log log){
		String sql="insert into t_log(log_title,content,comment_count,"
				+ "view_count,publish_time,last_update_time,transfer_count,top_flag,"
				+ "author_id,sort_id) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params ={log.getLogTitle(),
				log.getContent(),
				log.getCommentCount(),
				log.getViewCount(),
				log.getPublishTime(),
				log.getLastUpdateTime(),
				0,//转载次数
				"0",//是否置顶
				log.getAuthor().getUserId(),
				log.getLogSort().getLogSortId()};
		int num = update(sql,params);
		if (num > 0){
			return true;
		}
		return false;
	}

	
	/**
	 * 
	 * 删除日志
	 * 
	 */
	public void deleteLog(Log log){

		String sql ="delete from t_log where id = ?";
		Object[] params ={log.getLogId()};
		update(sql,params);
	}
	/**
	 * zhouhao
	 * 日志置顶
	 * @param log
	 * @return
	 */
	public boolean setLogTop(Log log) {
		// TODO Auto-generated method stub
		String sql = "update t_log set top_flag = ? where id = ?";
		Object[] params = {"1", log.getLogId()};
		int c = update(sql, params);
		if (c > 0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 修改日志
	 * 
	 */
	
	public boolean updateLog(Log log){
		String sql = "update t_log set content =? , log_title=? ,last_update_time=?where id = ?";
		Object[] params = {log.getContent(),
				log.getLogTitle(),
				log.getLastUpdateTime(),
				log.getLogId()};
		int num;
		num = update(sql,params);
		if(num==0){
			return false;
		}
		return true;
	}
	/**
	 * 拿到转发的日志列表
	 * @param logSort
	 * @param pager
	 * @return
	 */
	public List<Log> listTransferLogFromSort(LogSort logSort, Pager pager){
		String sql = "select * from t_log where sort_id = ? or id in(select log_id from t_log_transfer where transfer_id =?) order by top_flag desc ,publish_time desc limit ?, ?";
		Object[] params = {logSort.getLogSortId(),
						logSort.getUser().getUserId(),
						(pager.getCurrentPage() - 1) * pager.getPageSize(),
						pager.getPageSize()};
		List<Map<String, Object>> listMaps = find(sql, params);
		return makeLogListFromListMap(listMaps);
	}
	/**
	 * 拿到非转发的日志列表
	 * @param logSort
	 * @param pager
	 * @return
	 */
	public List<Log> listLogFromSort(LogSort logSort, Pager pager){
		String sql = "select * from t_log where sort_id = ? order by top_flag desc ,publish_time desc limit ?, ?";
		Object[] params = {logSort.getLogSortId(),
						(pager.getCurrentPage() - 1) * pager.getPageSize(),
						pager.getPageSize()};
		List<Map<String, Object>> listMaps = find(sql, params);
		return makeLogListFromListMap(listMaps);
	}
	
	public Log getLogById(int logId){
		String sql = "select * from t_log where id = ?";
		Object[] params = {logId};
		return makeLogFromMap(findObject(sql,params));
		
	}
	
			

	
	/**
	 * 移动日志
	 * @param log
	 * @param newLogSort
	 * @return
	 */
	public boolean moveLog(Log log, LogSort newLogSort){//新增方法
		String sql = "update t_log set sort_id=? where id=?";
		Object[] params = {newLogSort.getLogSortId(),log.getLogId()};
		int c = update(sql, params);
		if(c>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 数据库到vo的封装
	 * @param listMaps
	 * @return
	 */
	public List<Log> makeLogListFromListMap(List<Map<String,Object>> listMaps){
		List<Log> logs = new ArrayList<Log>();
		
		for(Map<String,Object> map : listMaps){
			Log log = makeLogFromMap(map);
			logs.add(log);
		}
		return logs;
	}
	
	public Log makeLogFromMap(Map<String, Object> map) {
		
		if(map == null){
			return null;
		}
		Log log = new Log();
		doSetValue(map, log, list);
		return log;	
	}
	/**
	 * 从数据库中完全封装好的log的集合
	 * @param listMaps
	 * @param user
	 * @return
	 */
	public List<Log> makeLogListFromListMap(List<Map<String,Object>> listMaps,User user){
		List<Log> logs = new ArrayList<Log>();
		for(Map<String,Object> map : listMaps){
			Log log = makeLogFromMap(map,user);
			logs.add(log);
		}
		return logs;
	}
	
	public Log makeLogFromMap(Map<String, Object> map,User user) {
		if(map == null){
			return null;
		}
		Log log = new Log();
		doSetValue(map, log, list);
		
		log.setCommentList(logCommentDao.getCommentByLog(log));
		if(logPraiseDao.getLogPraiseByUserAndLog(user,log)!=null)
			log.setPraiseFlag(true);
		
		return log;	
	}

	/**
	 * 取消置顶
	 * @param log
	 */
	public void cancelLogTop(Log log) {
		// TODO Auto-generated method stub
		String sql = "update t_log set top_flag = ? where id = ?";
		Object[] params = {"0", log.getLogId()};
		update(sql, params);
	}
	/**
	 * 个人主页显示好友日志 默认5条
	 * @param user
	 * @return
	 */
	public List<Log> getLogsByUser(User user){
		String sql = "select * from t_log where author_id in ( select user_friend_id from t_friend where user_id=? ) order by publish_time desc limit 0,"+ParamUtil.SELF_MAIN_LOG_SIZE;
		Object[] params={user.getUserId()};
		return makeLogListFromListMap(find(sql, params),user);
	}
}
