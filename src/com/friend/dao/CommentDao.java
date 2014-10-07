/**
 * 
 */
package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Property;
import com.friend.vo.Comment;
import com.friend.vo.Reply;
import com.friend.vo.Talk;

/**
 * @author szh 2014/7/17
 *
 */
public class CommentDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Comment");
	} 
	/**
	 * 添加评论
	 * @param comment 所要添加的Comment对象
	 * @return 返回改变的行数
	 */
	public int addComment(Comment comment){
		String sql="insert into t_comment(content,author,publish_time,comment_type,user_id)"
				+ " values(?,?,?,?,?)";
		Object[] params={
				comment.getContent(),
				comment.getAuthor(),
				comment.getPublishTime(),
				comment.getCommentType(),
				comment.getUser().getUserId()
		};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 删除评论
	 * @param comment 所要删除的Comment
	 * @return 改变的行数
	 */
	public int deleteComment(Comment comment){
		String sql="delete from t_comment where id=?";
		Object[] params={comment.getCommentId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 通过commentId查询说说
	 * @param commentId 所要查询的Comment的id
	 * @return 一个Comment对象
	 */
	public Comment getCommentById(int commentId) {
		String sql="select * from t_comment where id=?";
		Object[] params={commentId};
		return makeCommentFromMap2(findObject(sql, params));
	}
	
	public Comment getCommentByPublishTime(Comment comment){
		String sql="select * from t_comment where publish_time=?";
		Object[] params={comment.getPublishTime()};
		return makeCommentFromMap(findObject(sql, params));
	}
	
	/**
	 * 得到说说的评论
	 * @param talk
	 * @return List<Comment>
	 */
	public List<Comment> getCommentListByTalkId(Talk talk){
		String sql1="select comment_id from t_talk_comment where talk_id=?";
		Object[] params1={talk.getTalkId()};
		List<Map<String, Object>> commentIdList=find(sql1,params1);
		List<Comment> commentList=new ArrayList<Comment>();
		CommentDao commentDao=new CommentDao();
		
		for(Map<String, Object> map:commentIdList){
			int i=Integer.parseInt(map.get("comment_id").toString());
			String sql2="select * from t_comment where id=?";
			Object[] params2={i};
			Comment comment=commentDao.makeCommentFromMap(findObject(sql2, params2));
			commentList.add(comment);
		}
		
		return commentList;
	}

	/**
	 * 从map中得到Comment
	 * @param map 从数据库中查询数据封装而成的map
	 * @return 一个封装好的Comment
	 */
	public Comment makeCommentFromMap(Map<String,Object> map) {

		if(map == null) {
			return null;
		}
		
		Comment comment=new Comment();
		doSetValue(map, comment, list);
		
		ReplyDao replyDao=new ReplyDao();
		List<Reply> replyList=replyDao.getReplyByCommentId(comment);
		comment.setReplyList(replyList);
			
		return comment;
	}
	
	/**
	 * 专门用来新建Comment
	 * @param map
	 * @return
	 */
	public Comment makeCommentFromMap2(Map<String,Object> map) {

		if(map == null) {
			return null;
		}
		
		Comment comment=new Comment();
		doSetValue(map, comment, list);
			
		return comment;
	}
	
	/**
	 * 从listMap中得到Comment集合
	 * @param listMaps 一个包含从数据库中查询数据封装而成的map的List
	 * @return 一个包含Comment的List
	 */
	public List<Comment> makeCommentListFromListMap(List<Map<String,Object>> listMaps){
		List<Comment> comments = new ArrayList<Comment>();
		for(Map<String,Object> map : listMaps){
			Comment comment = makeCommentFromMap(map);
			comments.add(comment);
		}
		return comments;
 	}
	
}
