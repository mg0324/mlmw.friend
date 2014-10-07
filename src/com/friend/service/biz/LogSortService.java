package com.friend.service.biz;

import java.util.ArrayList;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.LogSortDao;
import com.friend.service.ibiz.ILogSort;
import com.friend.vo.Log;
import com.friend.vo.LogSort;
import com.friend.vo.User;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogSortService implements ILogSort{
	private static LogSortDao logSortDao = BaseDaoFactory.getLogSortDao(); 

	

	@Override
	public boolean deleteLogSort(LogSort logSort) {
		// TODO Auto-generated method stub
		return logSortDao.deleteLogSort(logSort);
	}

	@Override
	public boolean updateLogFromSort() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public LogSort getLogSortById(int logSortId) {
		// TODO Auto-generated method stub
		return logSortDao.getLogSortById(logSortId);
	}

	

	@Override
	public ArrayList<User> listAllUserFromLogSort(LogSort logSort) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<LogSort> listLogSort(User user) {
		// TODO Auto-generated method stub
		return (ArrayList<LogSort>)logSortDao.listLogSort(user);
	}

	@Override
	public boolean createLogSort(LogSort logSort) {//修改：方法名和调用方法名
		// TODO Auto-generated method stub
		return logSortDao.createLogSort(logSort);
	}

	
	@Override
	public int getLogCount(LogSort logSort){
		return logSortDao.getLogCount(logSort);
	}

	@Override
	public void updateLogCount(LogSort logSort) {
		// TODO Auto-generated method stub
		logSortDao.updateLogCount(logSort);
	}

	@Override
	public void reNameLogSort(LogSort inLs) {
		// TODO Auto-generated method stub
		logSortDao.reNameLogSortName(inLs);
	}
	public boolean isExitLogSort(LogSort logSort)
	{
		if(logSortDao.isExitLogSort(logSort)==null)
		{
			return false;
		}
		else
			return true;
	}
}
