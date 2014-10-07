/**
 * 
 */
package com.friend.service.biz;

import com.friend.dao.TalkPraiseDao;
import com.friend.service.ibiz.ITalkPraise;
import com.friend.util.DaoFactory;
import com.friend.vo.TalkPraise;

/**
 * @author szh 2014/7/17
 *
 */
public class TalkPraiseService implements ITalkPraise {
	private static TalkPraiseDao talkPraiseDao;
	
	static {
		talkPraiseDao=(TalkPraiseDao) DaoFactory.factory.get("talkPraiseDao");
	}

	/**
	 * 调用时应注意更新对应Talk的praise_count
	 */
	@Override
	public boolean addTalkPraise(TalkPraise talkPraise) {
		int num=talkPraiseDao.addTalkPraise(talkPraise);
		if(num>0)
			return true;
		else
			return false;
	}

	/**
	 * 调用时应注意更新对应Talk的praise_count
	 */
	@Override
	public boolean deleteTalkPraise(TalkPraise talkPraise) {
		int num=talkPraiseDao.deleteTalkPraise(talkPraise);
		if(num>0)
			return true;
		else
			return false;
	}

}
