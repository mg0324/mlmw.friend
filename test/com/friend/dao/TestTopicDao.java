package com.friend.dao;

import java.util.Date;

import org.junit.Test;

import com.friend.vo.Module;
import com.friend.vo.Topic;
import com.friend.vo.User;

/**
 * 
 * @author 宋启东 2014 07 18 16：46
 *
 */
public class TestTopicDao {
	  
      private TopicDao tpd=new TopicDao();
      @Test
      public void testTopicDao(){
  		Topic tp = new Topic();
  		Module m=new Module();
  		m.setModuleId(14);
  		tp.setTopicTitle("张三");
  	    tp.setContent("你好");
  		tp.setPublishTime(new Date());
  		tp.setLastUpdateTime(new Date());
  		tp.setModule(m);
  		//tp.getModule().setModuleId(14);
  		tp.setStatus(2);
  		tpd.publishTopic(tp);
  		if(tp!=null){
  			System.out.println("添加成功");

  		}
  	}
}
