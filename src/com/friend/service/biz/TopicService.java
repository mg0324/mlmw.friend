package com.friend.service.biz;

/**
 * @author 宋启东 2014 07 17 15：07
 */
import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.TopicDao;
import com.friend.service.ibiz.ITopic;
import com.friend.util.Pager;
import com.friend.vo.Module;
import com.friend.vo.Topic;

public class TopicService implements ITopic{
	private static TopicDao topicDao = BaseDaoFactory.getTopicDao();
	
	
	@Override
	public Topic publishTopic(Topic top) {
		// TODO Auto-generated method stub
		topicDao.publishTopic(top);
		return topicDao.getNewAddTopic();
	}

	@Override
	public void lookTopic(int id) {
		// TODO Auto-generated method stub
		
	}
	public List<Topic> makeTopicListFromModule(Module module,Pager pager)
	{
		return topicDao.makeTopicListFromModule( module,pager);
	}
	public void deleteTopic(Topic topic)
	{
		 topicDao.deleteTopic(topic);
	}
	public Topic getTopicById(int topicId)
	{
		return topicDao.getTopicById(topicId);
	}

	@Override
	public int getTopicCountInModule(Module module) {
		// TODO Auto-generated method stub
		return topicDao.getTopicCountInModule(module);
	}
	public void getCommentCount(Topic topic)
	{
		topicDao.getCommentCount(topic);
	}
	
}
