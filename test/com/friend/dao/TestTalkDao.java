package com.friend.dao;

import java.util.Date;

import org.junit.Test;

import com.friend.vo.Talk;
import com.friend.vo.User;

public class TestTalkDao {
	private TalkDao talkDao;
	private UserDao userDao;
	
	public TestTalkDao(){
		talkDao=new TalkDao();
		userDao = new UserDao();
	}
	@Test
	public void testAddTalk(){
		Talk talk=new Talk();
		User user=userDao.getUserById(1);
		talk.setAuthor(user.getUserNickName());
		talk.setContent("aaaaaaa");
		Date d=new Date();
		talk.setPublishTime(d);
		talk.setLastUpdateTime(d);
		talk.setUser(user);
		int num=talkDao.addTalk(talk);
		System.out.println("addTalk  "+num);
	}
	@Test
	public void testUpdateTalk(){
		Talk talk=new Talk();
		talk.setContent("sssssss");
		talk.setLastUpdateTime(new Date());
		talk.setTalkId(2);
		int num=talkDao.updateTalk(talk);
		System.out.println("updateTalk  "+num);
	}
	@Test
	public void testDeleteTalk(){
		Talk talk=new Talk();
		talk.setTalkId(2);
		int num=talkDao.deleteTalk(talk);
		System.out.println("deleteTalk  "+num);
	}

}
