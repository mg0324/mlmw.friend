package com.friend.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Module;
import com.friend.vo.Topic;
/**
 * 
 * @author 宋启东 2014 07 21 20：34
 *
 */
//发表
//查看
public class TopicDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Topic");
	}
	/**
	 *该方法用于创建一个话题 
	 * @return bolean
	 */
	public boolean publishTopic(Topic top){
		String sql="insert into t_topic(topic_title,content,publish_time,"
				+ "last_update_time,module_id,user_id,status,comment_num)"
				+ "values(?,?,?,?,?,?,?,0)";
		Object [] prarms={top.getTopicTitle(),
						top.getContent(),
						new Date(),
						new Date(),
						top.getModule().getModuleId(),
						top.getUser().getUserId(),
						top.getStatus()};
		int c=update(sql, prarms);
		if(c>0)
			return true;  
		else
			return false;
		
	}
	/**
	 * 该方法用于增加回帖数量
	 * @param topicId
	 * @return
	 */
	public void getCommentCount(Topic topic)
	{
		String sql="update t_topic set comment_num=comment_num+1 where id=?";
		Object [] prarms={topic.getTopicId()};
		update(sql,prarms);
		
	}
	
	/**
	 *该方法用于查看一个话题 
	 * @return void
	 */
	
	//通过topicId查询话题
		public Topic getTopicById(int topicId ) {
			String sql="select * from t_topic where id=? ";
			Object[] params={topicId};
			return makeTopicFromMap(findObject(sql, params));
		}
		
		//从map中得到Topic
		public Topic makeTopicFromMap(Map<String,Object> map) {
			if(map == null) {
				return null;
			}
			Topic topic=new Topic();
			doSetValue(map, topic, list);
			return topic;
		}
		
		//从listMap中得到Topic集合
		public List<Topic> makeTopicListFromModule(Module module, Pager pager ){
			String sql="select * from t_topic where module_id=? order by publish_time desc limit ?,?";
			Object[] pramgrm={module.getModuleId(),((pager.getCurrentPage()-1)*pager.getPageSize()),pager.getPageSize()};
			List<Map<String,Object>> listMaps = find(sql,pramgrm );
			List<Topic> topics = new ArrayList<Topic>();
			for(Map<String,Object> map : listMaps){
				Topic topic = makeTopicFromMap(map);
				topics.add(topic);
			}
			return topics;
	 	}
		public Boolean deleteTopic (Topic topic)
		{
			int a=0;
			String sql="delete from t_topic where id=?";
			Object[] pramgrm={topic.getTopicId()};
			 a = update(sql, pramgrm);
			 if(a>0){
					return true;
				}else
					return false;
		}
		/**
		 * 得到最新加入的帖子
		 * @return
		 */
		public Topic getNewAddTopic() {
			// TODO Auto-generated method stub
			String sql = "select * from t_topic order by id desc limit 0,1";
			return makeTopicFromMap(findObject(sql, null));
		}
		/**
		 * 得到module下的topicCount,千万别忘记取count的别名
		 * @param module
		 * @return
		 */
		public int getTopicCountInModule(Module module) {
			// TODO Auto-generated method stub
			String sql = "select count(*) count from t_topic where module_id=?";
			Object[] params = {module.getModuleId()};
			return getCountFromTable(sql, params);
		}
		
		
}
