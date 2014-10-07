package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Log;
import com.friend.vo.LogTransfer;
import com.friend.vo.User;


/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogTransferDao extends BaseDao{
	private static List<Property> list;
	private static List<Property> list1;
	static{
		list = getListPropertyFromEntityXml("LogTransfer");
		list1 = getListPropertyFromEntityXml("Log");
	}
	
	/**
	 * 添加转发记录
	 * @param logTransfer
	 * @return 是否转发成功
	 */
	public int addTransferLog(LogTransfer logTransfer){
		String sql = "insert into t_log_transfer(transfer_id,owner_id,log_id,transfer_time)values(?,?,?,?)";
		Object[] params = {logTransfer.getTransferUser().getUserId(),
				logTransfer.getOwnerUser().getUserId(),
				logTransfer.getLog().getLogId(),
				logTransfer.getTransferTime()};
		int num = 0;
		num = update(sql,params);
		if(num > 0){
			String sql1="update t_log set transfer_count=transfer_count+1 where id=?";
			Object [] param={logTransfer.getLog().getLogId()};
			update (sql1,param);
			return 1;
		}
		return 0;
	}
	/**
	 * 查看个人所有的转载日志
	 */
	
	 public List<Log> listAllTransferLog(User user, Pager pager){
		String sql = "select * from t_log where id in(select log_id from t_log_transfer where transfer_id=? order by transfer_time desc) limit ?, ?";
		Object[] params = {user.getUserId(),
						(pager.getCurrentPage() - 1) * pager.getPageSize(),
						pager.getPageSize()};
		List<Map<String, Object>> listMaps = find(sql, params);
		return makeLogListFromListMap(listMaps);
	}
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
			doSetValue(map, log, list1);
			return log;	
		}
	 /**
	 * 查看是否转载
	 */
	public int isTransfer(LogTransfer logTransfer)
	{
		String sql="select * from t_log_transfer where log_id=? and transfer_id=?";
		Object [] params={logTransfer.getLog().getLogId(),
			logTransfer.getTransferUser().getUserId()
		};
		System.out.print(logTransfer.getLog().getLogId()+"ha"+logTransfer.getTransferUser().getUserId());
		LogTransfer logtransfer=makeLogTransferFromMap(findObject(sql,params));
		if(logtransfer==null)
		{
			System.out.print("0没有转发");
			return 0;
		}
		else 
		{
			System.out.print("1有转发");
			return 1;
		}
		
	}

	/**
	 * 删除转发记录
	 * @param logTransfer
	 * @return 1或0 1表示删除陈功，0表示删除失败
	 */
	public void deleteLogPraise(LogTransfer logTransfer){
		String sql = "delete from t_log_transfer where transfer_id = ? and log_id=?";
		Object[] params = {logTransfer.getTransferUser().getUserId(),
				logTransfer.getLog().getLogId()
				};
		String sql1="update t_log set transfer_count=transfer_count-1 where id=?";
		Object [] param={logTransfer.getLog().getLogId()};
		update(sql,params);
		update (sql1,param);
			
	}
	public LogTransfer getLogTransferById(int logTransferId){
		String sql = "select * from t_log where id = ?";
		Object[] params = {logTransferId};
		return makeLogTransferFromMap(findObject(sql,params));
		
	}


	public List<LogTransfer> makeLogTransferListFromListMap(List<Map<String,Object>> listMaps){
		List<LogTransfer> logTransfers = new ArrayList<LogTransfer>();
		for(Map<String,Object> map : listMaps){
			LogTransfer logTransfer = makeLogTransferFromMap(map);
			logTransfers.add(logTransfer);
		}
			return logTransfers;
	}
	
	
	public LogTransfer makeLogTransferFromMap(Map<String, Object> map) {
		if(map == null){
			return null;
		}
		LogTransfer logTransfer = new LogTransfer();
		doSetValue(map, logTransfer, list);
		return logTransfer;
	}

}
