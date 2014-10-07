/**
 * 
 */
package com.friend.service.biz;

import com.friend.dao.CommentDao;
import com.friend.service.ibiz.IComment;
import com.friend.util.DaoFactory;
import com.friend.vo.Comment;

/**
 * @author szh 2014/7/17
 *
 */
public class CommentService implements IComment {
	private static CommentDao commentDao;
	
	static {
		commentDao = (CommentDao) DaoFactory.factory.get("commentDao");
	}
	
	/**
	 * 发表说说评论时，主外键约束，
	 * 先addComment()，后addTalkComment()。其他评论一概按此要求。
	 * 尽量不要单独使用addComment()!
	 */
	@Override
	public boolean addComment(Comment comment) {
		int num=commentDao.addComment(comment);
		if(num>0)
			return true;
		else
			return false;
	}

	/**
	 * 由于级联删除，可以直接删除，不必考虑主外键约束。
	 */
	@Override
	public boolean deleteComment(Comment comment) {
		int num=commentDao.deleteComment(comment);
		if(num>0)
			return true;
		else
			return false;
	}

	public Comment getCommentById(int id)
	{
		return commentDao.getCommentById(id);
	}


	@Override
	public Comment getCommentByPublishTime(Comment comment) {
		return commentDao.getCommentByPublishTime(comment);
	}
}
