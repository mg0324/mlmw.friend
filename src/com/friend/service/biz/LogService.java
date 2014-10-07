package com.friend.service.biz;

import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.LogDao;
import com.friend.dao.LogSortDao;
import com.friend.service.ibiz.ILog;
import com.friend.util.Pager;
import com.friend.vo.Log;
import com.friend.vo.LogSort;
import com.friend.vo.User;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogService implements ILog{
	private static LogDao logDao = BaseDaoFactory.getLogDao();
	private static LogSortDao logSortDao = BaseDaoFactory.getLogSortDao();
	
	
	@Override
	public boolean commentLog(Log log) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateLog(Log log) {
		// TODO Auto-generated method stub
		return  logDao.updateLog(log);
	}

	@Override
	public boolean transfer(Log log) {
		// TODO Auto-generated method stub
		return false;//0;
	}

	@Override
	public boolean setTop(Log log) {
		// TODO Auto-generated method stub
		return false;//0;
	}

	@Override
	public boolean addLog(Log log) {
		// TODO Auto-generated method stub
		return logDao.addLog(log);
		 
		
	}

	@Override
	public boolean deleteLog(Log log) {
		// TODO Auto-generated method stub
		logDao.deleteLog(log);//logDao.deletelog(log);
		return true;
	}

	@Override
	public List<Log> findAllLog(LogSort logSort) {
		return findAllLog(logSort);
	}

	@Override
	public List<Log> listLogFromSort(LogSort logSort, Pager pager) {
		// TODO Auto-generated method stub
		return logDao.listLogFromSort(logSort, pager);
	}
	public List<Log> listTransferLogFromSort(LogSort logSort, Pager pager)
	{
		return logDao.listTransferLogFromSort(logSort, pager);
	}
	@Override
	public Log getLogById(int logId){
		return logDao.getLogById(logId);
	}

	@Override
	public void setLogTop(Log log) {
		// TODO Auto-generated method stub
		logDao.setLogTop(log);
	}

	@Override
	public void cancelLogTop(Log log) {
		// TODO Auto-generated method stub
		logDao.cancelLogTop(log);
	}

	@Override
	public boolean moveLog(Log log, LogSort newLogSort) {
		// TODO Auto-generated method stub
		//事物
		LogSort nowLogSort = logSortDao.getLogSortById(log.getLogSort().getLogSortId());
		nowLogSort.setLogCount(nowLogSort.getLogCount()-1);
		logSortDao.updateLogCount(nowLogSort);
		newLogSort.setLogCount(newLogSort.getLogCount()+1);
		logSortDao.updateLogCount(newLogSort);
		logDao.moveLog(log, newLogSort);
		return true;
	}

	@Override
	public List<Log> showFriendLog(User user) {
		// TODO Auto-generated method stub
		return logDao.getLogsByUser(user);
	}
}
	

	
	
	



	

	


