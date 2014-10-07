package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Comment;
import com.friend.vo.Log;
import com.friend.vo.LogComment;



/**
 * 
 * @author 余瑞  2014-7-16 19:26
 *
 */
public interface ILogComment {
	
	//public void addLogComment();
	public void deleteLogComment();
	public List<Comment> lookComment(int logId);
	public boolean addLogComment(LogComment logComment);
	public List<Comment> getLogComments(Log log, Pager page);
	public int getTotalCount(Log log);
	public void addComment(Comment comment);
	public Comment lastComment();
}
