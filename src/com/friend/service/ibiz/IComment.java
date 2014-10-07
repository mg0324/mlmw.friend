/**
 * 
 */
package com.friend.service.ibiz;

import com.friend.vo.Comment;

/**
 * @author szh 2014/7/17
 *
 */
public interface IComment {
	public boolean addComment(Comment comment);
	public boolean deleteComment(Comment comment);
	public Comment getCommentById(int id);
	public Comment getCommentByPublishTime(Comment comment);
}
