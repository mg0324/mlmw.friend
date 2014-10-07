package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Log;
import com.friend.vo.LogTransfer;
import com.friend.vo.User;


/**
 * 
 * @author 余瑞 2014-7-16 19:23
 *
 */

public interface ILogTransfer {
	
	public int transferLog(LogTransfer logTransfer);
	public int isTransfer(LogTransfer logTransfer);
	public void deleteLogPraise(LogTransfer logTransfer);
	public List<Log> listAllTransferLog(User user, Pager pager);
}
