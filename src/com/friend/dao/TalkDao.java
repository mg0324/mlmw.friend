package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.util.Property;
import com.friend.vo.Comment;
import com.friend.vo.Talk;
import com.friend.vo.User;

/**
 * 
 * @author szh 2014/7/17
 *
 */
public class TalkDao extends BaseDao {
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Talk");
	}
	/**
	 * 添加说说
	 * @param talk 所要添加的Talk
	 * @return 改变的行数
	 */
	public int addTalk(Talk talk) {
		String sql="insert into t_talk(content,author,publish_time,last_update_time,user_id)"
				+ " values(?,?,?,?,?)";
		Object[] params={
				talk.getContent(),
				talk.getAuthor(),
				talk.getPublishTime(),
				talk.getLastUpdateTime(),
				talk.getUser().getUserId()
		};
		int num=0;
		num=update(sql, params);
		return num;
	}
	/**
	 * 删除说说(会级联删除TalkComment，并删除Comment，级联删除Reply)
	 * @param talk 所要删除的Talk
	 * @return 改变的行数
	 */
	public int deleteTalk(Talk talk) {
		String sql="select comment_id from t_talk_comment where talk_id=?";
		Object[] params={talk.getTalkId()};
		List<Map<String, Object>> list=find(sql, params);
		String sql1="delete from t_talk where id=?";
		int num=0;
		num=update(sql1, params);
		String sql2="delete from t_comment where id=?";
		for(Map<String, Object> map:list){
			Object[] params2={Integer.parseInt(map.get("comment_id").toString())};
			update(sql2, params2);
		}
		return num;
	}
	/**
	 * 修改说说
	 * @param talk 所要修改的Talk
	 * @return 改变的行数
	 */
	public int updateTalk(Talk talk) {
		String sql="update t_talk set content=?,last_update_time=?"
				+ " where id=?";
		Object[] params={talk.getContent(),talk.getLastUpdateTime(),talk.getTalkId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	/**
	 * 通过talkId查询说说
	 * @param talkId 所要查询说说的id
	 * @return 一个Talk
	 */
	public Talk getTalkById(int talkId) {
		String sql="select * from t_talk where id=?";
		Object[] params={talkId};
		return makeTalkFromMap2(findObject(sql, params));
	}
	/**
	 * 个人主页显示好友说说
	 * @param user
	 * @return
	 */
	public List<Talk> getTalkByUserId1(User user){
		String sql="select * from t_talk where user_id in ( select user_friend_id from t_friend where user_id=? ) order by last_update_time desc limit 0,"+ParamUtil.SELF_MAIN_TALK_SIZE;
		Object[] params={user.getUserId()};
		return makeTalkListFromListMap(find(sql, params), user);
	}
	
	/**
	 * 特别关注的好友说说
	 * @param user
	 * @return
	 */
	public List<Talk> getTalkByUserId2(User user,Pager pager){
		String sql="select * from t_talk where user_id in ( select user_friend_id from t_friend where user_id=? and focus=1 ) order by last_update_time desc limit ?,?";
		Object[] params={user.getUserId(),((pager.getCurrentPage()-1)*pager.getPageSize()),pager.getPageSize()};
		return makeTalkListFromListMap(find(sql, params), user);
	}
	
	/**
	 * 好友个人主页显示说说
	 * @param user
	 * @param friend
	 * @param pager
	 * @return
	 */
	public List<Talk> getTalkByUserId3(User user,User friend,Pager pager){
		String sql="select * from t_talk where user_id=? limit ?,?";
		Object[] params={friend.getUserId(),((pager.getCurrentPage()-1)*pager.getPageSize()),pager.getPageSize()};
		return makeTalkListFromListMap(find(sql, params), user);
	}
	
	/**
	 * 通过userId查询Talk
	 * @param user 当前user
	 * @return 一个包含Talk的List
	 */
	public List<Talk> getTalkByUserId(User user,Pager pager){
		String sql="select * from t_talk where user_id=? order by publish_time desc limit ?,?";
		Object[] params={user.getUserId(),((pager.getCurrentPage()-1)*pager.getPageSize()),pager.getPageSize()};
		return makeTalkListFromListMap(find(sql, params),user);
	}
	
	/**
	 * 从map中得到Talk
	 * @param map 从数据库查询数据封装而成的map
	 * @return 一个封装好的Talk
	 */
	public Talk makeTalkFromMap(Map<String,Object> map,User user) {

		if(map == null) {
			return null;
		}
		Talk talk=new Talk();
		
		doSetValue(map, talk, list);
		
		CommentDao commentDao=new CommentDao();
		List<Comment> commentList=commentDao.getCommentListByTalkId(talk);
		talk.setCommentList(commentList);
		TalkPraiseDao talkPraiseDao=new TalkPraiseDao();
		if(talkPraiseDao.getTalkPraiseByUserAndTalk(user, talk)!=null)
				talk.setPraiseFlag(true);
		return talk;
	}
	
	/**
	 * 专门用来新建talk
	 * @param map
	 * @return
	 */
	public Talk makeTalkFromMap2(Map<String,Object> map) {

		if(map == null) {
			return null;
		}
		
		Talk talk=new Talk();

		doSetValue(map, talk, list);
		
		return talk;
	}
	
	/**
	 * 从listMap中得到Talk集合
	 * @param listMaps 一个包含从数据库查询数据封装而成的map的List
	 * @return 一个包含Talk的List
	 */
	public List<Talk> makeTalkListFromListMap(List<Map<String,Object>> listMaps,User user){
		List<Talk> talks = new ArrayList<Talk>();
		for(Map<String,Object> map : listMaps){
			Talk talk = makeTalkFromMap(map,user);
			talks.add(talk);
		}
		
		return talks;
 	}

	/**
	 * 浏览次数加一
	 * @param talk
	 * @return
	 */
	public int addViewCount(Talk talk){
		String sql="update t_talk set view_count=view_count+1 where id=?";
		Object[] params={talk.getTalkId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 说说点赞次数加一
	 * @param talk
	 * @return
	 */
	public int addPraiseCount(Talk talk){
		String sql="update t_talk set praise_count=praise_count+1 where id=?";
		Object[] params={talk.getTalkId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 评论次数加一
	 * @param talk
	 * @return
	 */
	public int addCommentCount(Talk talk){
		String sql="update t_talk set comment_count=comment_count+1 where id=?";
		Object[] params={talk.getTalkId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 说说点赞次数减一
	 * @param talk
	 * @return
	 */
	public int deletePraiseCount(Talk talk){
		String sql="update t_talk set praise_count=praise_count-1 where id=?";
		Object[] params={talk.getTalkId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 评论次数减一
	 * @param talk
	 * @return
	 */
	public int deleteCommentCount(Talk talk){
		String sql="update t_talk set comment_count=comment_count-1 where id=?";
		Object[] params={talk.getTalkId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	public int getTalkCountByUser(User user){
		String sql="select count(*) count from t_talk where user_id=?";
		Object[] params={user.getUserId()};
		int num=0;
		num=getCountFromTable(sql, params);
		return num;
	}
	
	/**
	 * 得到特别关注好友的说说总数
	 */
	public int getFocusFriendTalkCountByUser(User user){
		String sql="select count(*) count from t_talk where user_id in ( select user_friend_id from t_friend where user_id=? and focus=1 ) order by last_update_time desc";
		Object[] params={user.getUserId()};
		int num=0;
		num=getCountFromTable(sql, params);
		return num;
	}
}
