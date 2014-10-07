/**
 * 
 */
package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Property;
import com.friend.vo.Talk;
import com.friend.vo.TalkPraise;
import com.friend.vo.User;

/**
 * @author szh 2014/7/17
 *
 */
public class TalkPraiseDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("TalkPraise");
	}
	/**
	 * 说说点赞
	 * @param talkPraise 点赞添加一个TalkPraise
	 * @return 改变的行数
	 */
	public int addTalkPraise(TalkPraise talkPraise){
		String sql="insert into t_talk_praise(talk_id,user_id,priase_time)"
				+ " values(?,?,?)";
		Object[] params={
				talkPraise.getTalk().getTalkId(),
				talkPraise.getUser().getUserId(),
				talkPraise.getPraiseTime()
		};
		int num=0;
		num=update(sql, params);
		return num;
	}

	/**
	 * 说说取消赞
	 * @param talkPraise 取消赞删除一个TalkPraise
	 * @return 改变的行数
	 */
	public int deleteTalkPraise(TalkPraise talkPraise){
		String sql="delete from t_talk_praise where user_id=? and talk_id=?";
		Object[] params={talkPraise.getUser().getUserId(),talkPraise.getTalk().getTalkId()};
		int num=0;
		num=update(sql, params);
		return num;
	}
	
	/**
	 * 通过talkPraiseId查询说说点赞
	 * @param talkPraiseId 点赞TalkPraise的id
	 * @return 一个TalkPraise对象
	 */
	public TalkPraise getTalkPraiseById(int talkPraiseId) {
		String sql="select * from t_talk_praise where id=?";
		Object[] params={talkPraiseId};
		return makeTalkPraiseFromMap(findObject(sql, params));
	}
	
	/**
	 * 通过talkId查询说说点赞
	 * @param talk
	 * @return
	 */
	public List<TalkPraise> getTalkPraiseListByTalkId(Talk talk){
		String sql="select * from t_talk_praise where talk_id=?";
		Object[] params={talk.getTalkId()};
		List<TalkPraise> talkPraiseList=makeTalkPraiseListFromListMap(find(sql, params));
		return talkPraiseList;
	}
		
	/**
	 * 从map中得到TalkPraise
	 * @param map 封装好的map
	 * @return 一个TalkPraise对象
	 */
	public TalkPraise makeTalkPraiseFromMap(Map<String,Object> map) {
		
		if(map == null) {
			return null;
		}
		
		TalkPraise talkPraise=new TalkPraise();
		doSetValue(map, talkPraise, list);
		
		return talkPraise;
	}
		
	/**
	 * 从listMap中得到TalkPraise集合
	 * @param listMaps 一个包含封装好的map的List
	 * @return 一个包含TalkPraise的List
	 */
	public List<TalkPraise> makeTalkPraiseListFromListMap(List<Map<String,Object>> listMaps){
		List<TalkPraise> talkPraises = new ArrayList<TalkPraise>();
		for(Map<String,Object> map : listMaps){
			TalkPraise talkPraise=makeTalkPraiseFromMap(map);
			talkPraises.add(talkPraise);
		}
		return talkPraises;
 	}
	
	public TalkPraise getTalkPraiseByUserAndTalk(User user,Talk talk){
		String sql="select * from t_talk_praise where user_id=? and talk_id=?";
		Object[] params={user.getUserId(),talk.getTalkId()};
		return makeTalkPraiseFromMap(findObject(sql, params));
	}
}
