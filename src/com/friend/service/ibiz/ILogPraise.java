package com.friend.service.ibiz;

import com.friend.vo.Log;
import com.friend.vo.LogPraise;
import com.friend.vo.LogTransfer;


/**
 * 
 * @author 余瑞 2014-7-16 19:27
 *
 */

public interface ILogPraise {
	
	public boolean addLogPraise(LogPraise logPraise);
	public void deleteLogPraise(LogPraise logPraise);
	public int queryLogPraise(Log log);
	public int isPraise(LogPraise logPraise);


}
