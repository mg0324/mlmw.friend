package com.friend.dao;

import java.util.Date;

import org.junit.Test;

import com.friend.vo.LogSort;

public class TestLogSortDao{
	private LogSortDao logSortDao;
	private UserDao userDao;
	
	public TestLogSortDao(){
		logSortDao = new LogSortDao();
		userDao = new UserDao();

	}
	@Test
	public void testaddLogToSort(){
		LogSort logSort = new LogSort();
		logSort.setSortName("我的日志");
		logSort.setLogCount(0);
		logSort.setUser(userDao.getUserById(2));
		logSort.setCreateTime(new Date());
		
		System.out.println("添加成功");
		
		
	}
	
	@Test
	public void testdeleteLogFromSort(){
		LogSort logSort = new LogSort();
		logSort.setLogSortId(1);
		/*int num = logSortDao.deleteLogFromSort(logSort);*/
		System.out.println("添加成功");
		/*System.out.println("deleteLogFromSort " + num);*/
	}
	
	@Test
	public void testupdateLogFromSort(){
		LogSort logSort = new LogSort();
		logSort.setLogSortId(4);
		logSort.setLogCount(5);
		int num = logSortDao.updateLogFromSort(logSort);
		System.out.println("添加成功");
		System.out.println("updateLogFromSort " + num);
		
	}
	

}
