package com.friend.service.ibiz;

import java.util.ArrayList;

import com.friend.vo.Log;
import com.friend.vo.LogSort;
import com.friend.vo.User;

/**
 * 
 * @author 余瑞 2014-7-16 19:19
 *
 */

public interface ILogSort {
	
	public boolean updateLogFromSort();
	public ArrayList<User> listAllUserFromLogSort(LogSort logSort);
	public LogSort getLogSortById(int logSortId);
	public boolean createLogSort(LogSort logSort);
	public boolean deleteLogSort(LogSort logSort);
	public int getLogCount(LogSort logSort);
	public ArrayList<LogSort> listLogSort(User user);
	public void updateLogCount(LogSort logSort);
	public void reNameLogSort(LogSort inLs);
	public boolean isExitLogSort(LogSort logSort);
}
