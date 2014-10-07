package com.friend.service.biz;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.LogPraiseDao;
import com.friend.service.ibiz.ILogPraise;
import com.friend.util.DaoFactory;
import com.friend.vo.Log;
import com.friend.vo.LogPraise;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */
public class LogPraiseService implements ILogPraise{
	public static LogPraiseDao  logPraiseDao = BaseDaoFactory.getLogPraiseDao();
	
	
	@Override
	public boolean addLogPraise(LogPraise logPraise) {
		// TODO Auto-generated method stub
		return logPraiseDao.addLogParise(logPraise);
	}

	@Override
	public void deleteLogPraise(LogPraise logPraise) {
		// TODO Auto-generated method stub
		  logPraiseDao.deleteLogPraise(logPraise);
	}
	
	@Override
	public int queryLogPraise(Log log) {
		// TODO Auto-generated method stub
		return logPraiseDao.selectLogPraise(log);
	}	
	public int isPraise(LogPraise logPraise)
	{
		return logPraiseDao.isPraise(logPraise);
	}
}


