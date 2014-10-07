package com.friend.dao;




import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Comment;
import com.friend.vo.Log;
import com.friend.vo.LogComment;
import com.friend.vo.Reply;


/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogCommentDao extends BaseDao{
	private static ReplyDao replyDao = BaseDaoFactory.getReplyDao();
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Comment");
	}
	
	/**
	 * 添加一条评论
	 * @param com
	 * @return
	 */
	public Boolean addComment(Comment com) {
		// TODO Auto-generated method stub

		String sql = "insert into t_comment(content,author,publish_time,comment_type,user_id) "
				+ "values(?,?,?,?,?)";

		Object[] params = { com.getContent(), com.getAuthor(), new Date(),
				com.getCommentType(), com.getUser().getUserId() };
		int a = 0;
		a = update(sql, params);
		if (a > 0)
			return true;
		else
			return false;
	}

	public Boolean addLogComment(LogComment logComment) {
		int a = 0;
		String sql = "insert into t_log_comment(log_id,comment_id) values(?,?)";
		Object[] params = { logComment.getLog().getLogId(),
				logComment.getComment().getCommentId() };
		a = update(sql, params);
		if (a > 0)
			return true;
		else
			return false;
	}

	// 查找最新回帖
	public Comment lastComment() {
		String sql = "select * from t_comment order by id desc limit 1";
		System.out.println("last" + makeLogCommentFromMap(findObject(sql, null)));
		return makeLogCommentFromMap(findObject(sql, null));

	}
	
	
	public int deleteLogComment(LogComment logComment){
		String sql = "delete from t_log_comment where id = ?";
		Object[] params = {logComment.getLogCommentId()};
		int num = 0;
		num = update(sql,params);
		return num;
	}
	
	
	public List<Comment> lookComment(int logId) {
		String sql = "select * from t_comment where id IN(select comment_id from t_log_comment where log_id=?)";
		Object[] params = { logId };
		List<Map<String, Object>> listMaps = find(sql, params);
		List<Comment> comments = makeLogCommentListFromListMap(listMaps);
		return comments;
	}
	
	
	
	
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	
	public Comment getLogSortById(int comment_id){
		String sql = "select * from t_comment where id = ?";
		Object[] params = {comment_id};
		return makeLogCommentFromMap(findObject(sql,params));
		
	}


	public List<Comment> makeLogCommentListFromListMap(List<Map<String,Object>> listMaps){
		List<Comment> logComments = new ArrayList<Comment>();
		for(Map<String,Object> map : listMaps){
			Comment logComment = makeLogCommentFromMap(map);
			logComments.add(logComment);
		}
			return logComments;
	}
	
	
	
	
	private Comment makeLogCommentFromMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Comment com = new Comment();
		if (map == null) {
			return null;
		}
		doSetValue(map, com, list);
		List<Reply> replyList = replyDao.getReplyByCommentId(com);
		com.setReplyList(replyList);
		return com;
	}


	/**
	 * meigang
	 * 得到日志的评论
	 * @param log
	 * @return
	 */
	
	public List<Comment> getComments(Log log,Pager page) {
		// TODO Auto-generated method stub
		String sql = "select * from t_comment where id IN"
				+ "(select comment_id from t_log_comment where log_id=?) "
				+ "limit ?,?";
		Object[] params = {log.getLogId(),
				(page.getCurrentPage()-1)*page.getPageSize(),
				page.getPageSize()};
		return makeLogCommentListFromListMap(find(sql, params));
	}


	
	public List<Comment> getCommentByLog(Log log) {
		// TODO Auto-generated method stub
		String sql = "select * from t_comment where id in"
				+ " (select comment_id from t_log_comment where log_id = ?) ";
		Object[] params = {log.getLogId()};
		return makeLogCommentListFromListMap(find(sql, params));
	}


	/**
	 * meigang
	 * 返回日志下的评论条数
	 * @param log
	 * @return
	 */
	public int getLogCommentCount(Log log) {
		// TODO Auto-generated method stub
		String sql = "select count(*) count from t_comment where id IN"
				+ "(select comment_id from t_log_comment where log_id = ?) ";
		Object[] params = {log.getLogId()};
		return getCountFromTable(sql, params);
	}



	

}










