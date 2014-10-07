package com.friend.service.biz;

import java.util.List;

import com.friend.dao.TopicCommentDao;
import com.friend.service.ibiz.ITopicComment;
import com.friend.util.DaoFactory;
import com.friend.util.Pager;
import com.friend.vo.Comment;
import com.friend.vo.Topic;
import com.friend.vo.TopicComment;

/**
 * 
 * @author 宋启东 2014 07 17 15：51
 *
 */
public class TopicCommentService implements ITopicComment{
	private static TopicCommentDao topicCommentDao;
	static{
		topicCommentDao = (TopicCommentDao) DaoFactory.factory.get("topicCommentDao");
	}
	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		topicCommentDao.addComment( comment);
	}
	public void addTopicComment(TopicComment topicComment)
	{
		topicCommentDao.addTopicComment(topicComment);
	}
	public List<Comment> lookComment(int topicId ,Pager pager)
	{
		return topicCommentDao.lookComment(topicId,pager);
	}
	public Comment lastComment()
	{
		return topicCommentDao.lastComment();
	}

	public void deleteTopicComment(Comment comment)
	{
		topicCommentDao.deleteTopicComment(comment);
	}
	@Override
	public int getTopicCommentCountByTopic(Topic topic) {
		return topicCommentDao.getTopicCommentCountByTopic(topic);
	}

}
