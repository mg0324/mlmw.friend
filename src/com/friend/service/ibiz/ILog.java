package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Log;
import com.friend.vo.LogSort;
import com.friend.vo.User;


/**
 * 
 * @author 余瑞  2014-7-16 18:52
 *
 */

public interface ILog {
	public boolean updateLog(Log log);//更新日志
	public boolean transfer(Log log);
	public boolean setTop(Log log);//置顶
	public boolean addLog(Log log);//发表日志
	public boolean deleteLog(Log log);//删除日志
	public List<Log> findAllLog(LogSort logSort); 
	public Log getLogById(int logId);
	public List<Log> listTransferLogFromSort(LogSort logSort, Pager pager);
	public List<Log> listLogFromSort(LogSort logSort, Pager pager);
	boolean commentLog(Log log);
	public void setLogTop(Log log);
	public void cancelLogTop(Log log);
	public boolean moveLog(Log log, LogSort logSort);
	public List<Log> showFriendLog(User user);
}












