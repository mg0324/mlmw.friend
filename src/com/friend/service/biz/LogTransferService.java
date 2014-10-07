package com.friend.service.biz;

import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.LogTransferDao;
import com.friend.service.ibiz.ILogTransfer;
import com.friend.util.Pager;
import com.friend.vo.Log;
import com.friend.vo.LogTransfer;
import com.friend.vo.User;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */
public class LogTransferService implements ILogTransfer{

	private static LogTransferDao logTransferDao = BaseDaoFactory.getLogTransferDao();
	
	
	@Override
	public int transferLog(LogTransfer logTransfer) {
		// TODO Auto-generated method stub
		
		return logTransferDao.addTransferLog(logTransfer);
	}
	public int isTransfer(LogTransfer logTransfer)
	{
		return logTransferDao.isTransfer(logTransfer);
	}
	public void deleteLogPraise(LogTransfer logTransfer)
	{
		logTransferDao.deleteLogPraise(logTransfer);
	}
	public List<Log> listAllTransferLog(User user, Pager pager)
	{
		return logTransferDao.listAllTransferLog(user, pager);
	}
}
