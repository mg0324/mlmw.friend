/**
 * 
 */
package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Property;
import com.friend.vo.Comment;
import com.friend.vo.Talk;
import com.friend.vo.TalkComment;

/**
 * @author szh 2014/7/17
 *
 */
public class TalkCommentDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("TalkComment");
	}
	/**
	 * 添加说说评论
	 * @param talkComment 所要添加的TalkComment对象
	 * @return 改变的行数
	 */
	public int addTalkComment(TalkComment talkComment){
		String sql="insert into t_talk_comment(talk_id,comment_id) "
				+ "values(?,?)";
		Object[] params={talkComment.getTalk().getTalkId(),talkComment.getComment().getCommentId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 删除说说评论
	 * @param talkComment 所要删除的TalkComment对象
	 * @return 改变的行数
	 */
	public int deleteTalkComment(TalkComment talkComment){
		String sql="delete from t_talk_comment where id=?";
		Object[] params={talkComment.getTalkCommentId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 通过talkCommentId查询说说评论
	 * @param talkCommentId 所要查询的TalkComment的id
	 * @return 一个TalkComment
	 */
	public TalkComment getTalkCommentById(int talkCommentId) {
		String sql="select * from t_talk_comment where id=?";
		Object[] params={talkCommentId};
		return makeTalkCommentFromMap(findObject(sql, params));
	}
	
	/**
	 * 通过talkId查询说说评论
	 * @param talk 所要查询的说说
	 * @return 一个包含TalkComment的List
	 */
	public List<TalkComment> getTalkCommentByTalkId(Talk talk){
		String sql="select * from t_talk_comment where talk_id=?";
		Object[] params={talk.getTalkId()};
		return makeTalkCommentListFromListMap(find(sql, params));
	}
	
	/**
	 * 通过comment_id查询TalkComment (如果没有级联删除，就必须在删除Comment之前删除TalkComment，通过此方法可以找到TalkComment)
	 * @param comment
	 * @return
	 */
	public TalkComment getTalkCommentByCommentId(Comment comment){
		String sql="select * from t_talk_comment where comment_id=?";
		Object[] params={comment.getCommentId()};
		return makeTalkCommentFromMap(findObject(sql, params));
	}
		
	/**
	 * 从map中得到TalkComment
	 * @param map 从数据库查询数据封装而成的map
	 * @return 一个封装好的TalkComment
	 */
	public TalkComment makeTalkCommentFromMap(Map<String,Object> map) {
		
		if(map == null) {
			return null;
		}
		
		TalkComment talkComment=new TalkComment();
		doSetValue(map, talkComment, list);

		return talkComment;
	}
		
	/**
	 * 从listMap中得到TalkComment集合
	 * @param listMaps 一个包含从数据库查询数据封装而成的map的List
	 * @return 一个包含TalkComment的List
	 */
	public List<TalkComment> makeTalkCommentListFromListMap(List<Map<String,Object>> listMaps){
		List<TalkComment> talkComments = new ArrayList<TalkComment>();
		for(Map<String,Object> map : listMaps){
			TalkComment talkComment=makeTalkCommentFromMap(map);
			talkComments.add(talkComment);
		}
		return talkComments;
 	}
	
}
