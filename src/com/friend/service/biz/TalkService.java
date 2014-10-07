package com.friend.service.biz;

import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.TalkDao;
import com.friend.service.ibiz.ITalk;
import com.friend.util.Pager;
import com.friend.vo.Talk;
import com.friend.vo.User;
/**
 * @author szh 2014/7/17
 *
 */
public class TalkService implements ITalk {
	private static TalkDao talkDao = BaseDaoFactory.getTalkDao();
	

	@Override
	public boolean addTalk(Talk talk) {
		int num=talkDao.addTalk(talk);
		if(num>0)
			return true;
		else
			return false;
	}

	/**
	 * 删除说说时，会删除TalkComment,Reply,Comment
	 */
	@Override
	public boolean deleteTalk(Talk talk) {
		int num=talkDao.deleteTalk(talk);
		if(num>0)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateTalk(Talk talk) {
		int num=talkDao.updateTalk(talk);
		if(num>0)
			return true;
		else
			return false;
	}
	
	@Override
	public List<Talk> showTalk(User user,Pager pager){
		
		List<Talk> talkList=talkDao.getTalkByUserId(user,pager);
		return talkList;
	}

	@Override
	public boolean addViewCount(Talk talk) {
		int num=talkDao.addViewCount(talk);
		if(num>0)
			return true;
		else
			return false;
	}

	@Override
	public boolean addPraiseCount(Talk talk) {
		int num=talkDao.addPraiseCount(talk);
		if(num>0)
			return true;
		else
			return false;
	}

	@Override
	public boolean addCommentCount(Talk talk) {
		int num=talkDao.addCommentCount(talk);
		if(num>0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deletePraiseCount(Talk talk) {
		int num=talkDao.deletePraiseCount(talk);
		if(num>0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteCommentCount(Talk talk) {
		int num=talkDao.deleteCommentCount(talk);
		if(num>0)
			return true;
		else
			return false;
	}

	@Override
	public Talk getTalkByTaikId(int talkId) {
		return talkDao.getTalkById(talkId);
	}

	@Override
	public int getTalkCountByUser(User user) {
		return talkDao.getTalkCountByUser(user);
	}

	@Override
	public List<Talk> showFriendTalk(User user) {
		return talkDao.getTalkByUserId1(user);
	}

	@Override
	public List<Talk> showFocusFriendTalk(User user,Pager pager) {
		return talkDao.getTalkByUserId2(user, pager);
	}

	@Override
	public int getFocusFriendTalkCountByUser(User user) {
		return talkDao.getFocusFriendTalkCountByUser(user);
	}

	@Override
	public List<Talk> showFriendMainTalk(User user, User friend, Pager pager) {
		return talkDao.getTalkByUserId3(user, friend, pager);
	}
	
	
}
