package com.friend.service.biz;


import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.LogCommentDao;
import com.friend.service.ibiz.ILogComment;
import com.friend.util.Pager;
import com.friend.vo.Comment;
import com.friend.vo.Log;
import com.friend.vo.LogComment;

/**
 * 
 * @author 余瑞 2014-7-16 19:55
 *
 */

public class LogCommentService implements ILogComment{
	private static LogCommentDao logCommentDao = BaseDaoFactory.getLogCommentDao();
	

	@Override
	public List<Comment> lookComment(int logId) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public boolean addLogComment(LogComment logComment){
		//Comment comment = logCommentDao.glogComment.getCommentId()
		return logCommentDao.addLogComment(logComment);
	} 

	@Override
	public void deleteLogComment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comment> getLogComments(Log log,Pager page) {
		// TODO Auto-generated method stub
		return logCommentDao.getComments(log,page);
	}

	@Override
	public int getTotalCount(Log log) {
		// TODO Auto-generated method stub
		return logCommentDao.getLogCommentCount(log);
	}

	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		logCommentDao.addComment(comment);
	}

	@Override
	public Comment lastComment() {
		// TODO Auto-generated method stub
		return logCommentDao.lastComment();
	}
}
