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

/**
 * @author szh 2014/7/17
 *
 */
public class ReplyDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Reply");
	}
	/**
	 * 添加回复
	 * @param reply 所要添加的回复
	 * @return 返回改变的行数
	 */
	public int addReply(Reply reply){
		String sql="insert into t_reply(content,author,publish_time,user_id,comment_id,last_replayer_id)"
				+ " values(?,?,?,?,?,?)";
		Object[] params={
				reply.getContent(),
				reply.getAuthor(),
				reply.getPublishTime(),
				reply.getUser().getUserId(),
				reply.getComment().getCommentId(),
				reply.getLastReplayer().getUserId()
		};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 删除回复
	 * @param reply 所要删除的回复
	 * @return 返回改变的行数
	 */
	public int deleteReply(Reply reply){
		String sql="delete from t_reply where id=?";
		Object[] params={reply.getReplyId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 通过replyId查询回复
	 * @param replyId 所要查询的回复的id
	 * @return 返回一个Reply对象
	 */
	public Reply getReplyById(int replyId) {
		String sql="select * from t_reply where id=?";
		Object[] params={replyId};
		return makeReplyFromMap(findObject(sql, params));
	}
	
	/**
	 * 通过commentId得到List<Reply>
	 * @param comment
	 * @return
	 */
	public List<Reply> getReplyByCommentId(Comment comment){
		String sql="select * from t_reply where comment_id=? order by publish_time";
		Object[] params={comment.getCommentId()};
		List<Reply> replyList=makeReplyListFromListMap(find(sql, params));
		return replyList;
	}
	
	/**
	 * 从map中得到Reply
	 * @param map 从数据库中查询出的数据封装而成的map
	 * @return 一个封装好的Reply
	 */
	public Reply makeReplyFromMap(Map<String,Object> map) {
		
		if(map == null) {
			return null;
		}
		
		Reply reply=new Reply();
		doSetValue(map, reply, list);
		 
		return reply;
	}
		
	/**
	 * 从listMap中得到Reply集合
	 * @param listMaps 一个包含从数据库中查询出的数据封装而成的map的List
	 * @return 一个包含Reply的List
	 */
	public List<Reply> makeReplyListFromListMap(List<Map<String,Object>> listMaps){
		List<Reply> replys = new ArrayList<Reply>();
		for(Map<String,Object> map : listMaps){
			Reply reply = makeReplyFromMap(map);
			replys.add(reply);
		}
		return replys;
 	}
}
