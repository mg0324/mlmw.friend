package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Comment;



import com.friend.vo.Topic;
import com.friend.vo.TopicComment;

import java.util.List;

import com.friend.vo.Comment;

/**
 * 
 * @author 宋启东 2014 07 17 15：43 
 *
 */
public interface ITopicComment {
	public void addComment(Comment comment); 
	public void addTopicComment(TopicComment topicComment);
	public List<Comment> lookComment(int topicId,Pager pager);
	public Comment lastComment();
	public void deleteTopicComment(Comment comment);
	public int getTopicCommentCountByTopic(Topic topic);
}
