package com.friend.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Comment;
import com.friend.vo.Topic;
import com.friend.vo.TopicComment;

/**
 * 
 * @author 宋启东 2014 07 24 09：25
 *
 */
public class TopicCommentDao extends BaseDao {
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Comment");
	}
	/**
	 * 该方法用于添加一条回复
	 * 
	 * @param TalkComment
	 */
	public Boolean addComment(Comment com) {
		// TODO Auto-generated method stub

		String sql1 = "insert into t_comment(content,author,publish_time,comment_type,user_id) "
				+ "values(?,?,?,?,?)";

		Object[] params1 = { com.getContent(), com.getAuthor(), new Date(),
				com.getCommentType(), com.getUser().getUserId() };
		int a = 0;
		a = update(sql1, params1);
		if (a > 0)
			return true;
		else
			return false;
	}

	public Boolean addTopicComment(TopicComment topicComment) {
		int a = 0;
		String sql = "insert into t_topic_comment(topic_id,comment_id) values(?,?)";
		Object[] params = { topicComment.getTopic().getTopicId(),
				topicComment.getCommentId() };
		a = update(sql, params);
		if (a > 0)
			return true;
		else
			return false;
	}

	// 查找最新回帖
	public Comment lastComment() {
		String sql = "select * from t_comment order by id desc limit 1";
		System.out.println("last" + makecommentFromMap(findObject(sql, null)));
		return makecommentFromMap(findObject(sql, null));

	}

	// 删除话题评论
	public int deleteTopicComment(Comment comment) {
		String sql = "delete from t_omment where id=?";
		Object[] params = { comment.getCommentId() };
		int num = 0;
		num = update(sql, params);
		return num;
	}

	/**
	 * 修改评论，该方法暂不启用
	 * 
	 * @param TopicComment
	 * @return int
	 */
	/*
	 * public int updatecomment(Comment com) { String
	 * sql="update t_comment set content=?,publish_time=?" + " where id=?";
	 * Object[]
	 * params={com.getContent(),com.getPublishTime(),com.getCommentId()}; int
	 * num=0; num=update(sql, params); return num; }
	 */
	/**
	 * 该方法用于查看ID为arg0的话题的所有回复
	 */
	public List<Comment> lookComment(int TopicId,Pager pager) {
		String sql = "select * from t_comment where id IN(select comment_id from t_topic_comment where topic_id=?) limit ?,?";
		Object[] params = { TopicId ,((pager.getCurrentPage()-1)*pager.getPageSize()),pager.getPageSize()};
		List<Map<String, Object>> listMaps = find(sql, params);
		List<Comment> comments = makeCommentListFromListMap(listMaps);
		return comments;
	}

	/**
	 * 封装Comment对象
	 * 
	 * @param map
	 * @return
	 */
	private Comment makecommentFromMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Comment com = new Comment();
		if (map == null) {
			return null;
		}
		doSetValue(map, com, list);
		return com;
	}

	// 从listMap中得到comment集合
	public List<Comment> makeCommentListFromListMap(
			List<Map<String, Object>> listMaps) {
		List<Comment> comments = new ArrayList<Comment>();
		for (Map<String, Object> map : listMaps) {
			Comment comment = makecommentFromMap(map);
			comments.add(comment);
		}
		return comments;
	}

	/**
	 * 通过comment_id得到Comment对象
	 * 
	 * @param comment_id
	 *            评论编号
	 * @return 返回评论对象，没有找到返回null
	 */
	public Comment getCommentById(int comment_id) {
		String sql = "select * from t_comment where id=?";
		Object[] params = { comment_id};
		return makecommentFromMap((findObject(sql, params)));
	}
	
	public int getTopicCommentCountByTopic(Topic topic){
		String sql="select count(*) count from t_topic_comment where topic_id=?";
		Object[] params={topic.getTopicId()};
		int num=0;
		num=getCountFromTable(sql,params);
		return num;
	}
}
