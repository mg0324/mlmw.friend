/**
 * 
 */
package com.friend.service.biz;

import com.friend.dao.TalkCommentDao;
import com.friend.service.ibiz.ITalkComment;
import com.friend.util.DaoFactory;
import com.friend.vo.TalkComment;

/**
 * @author szh 2014/7/17
 *
 */
public class TalkCommentService implements ITalkComment {
	private static TalkCommentDao talkCommentDao;
	
	static{
		talkCommentDao=(TalkCommentDao) DaoFactory.factory.get("talkCommentDao");
	}
	
	@Override
	public boolean addTalkComment(TalkComment talkComment) {
		int num=talkCommentDao.addTalkComment(talkComment);
		if(num>0)
			return true;
		else
			return false;
	}

	/**
	 * 一般不会调用此方法,deleteComment()和deleteTalk()都能级联删除
	 */
	@Override
	public boolean deleteTalkComment(TalkComment talkComment) {
		int num=talkCommentDao.deleteTalkComment(talkComment);
		if(num>0)
			return true;
		else
			return false;
	}

}
