/**
 * 
 */
package com.friend.service.biz;

import com.friend.dao.ReplyDao;
import com.friend.service.ibiz.IReply;
import com.friend.util.DaoFactory;
import com.friend.vo.Reply;

/**
 * @author szh 2014/7/17
 *
 */
public class ReplyService implements IReply {
	private static ReplyDao replyDao;
	
	public ReplyService(){
		replyDao=(ReplyDao) DaoFactory.factory.get("replyDao");
	}

	@Override
	public boolean addReply(Reply reply) {
		int num=replyDao.addReply(reply);
		if(num>0)
			return true;
		else
			return false;
	}

	/**
	 * 删除回复时不考虑是否删除回复的回复
	 */
	@Override
	public boolean deleteReply(Reply reply) {
		int num=replyDao.deleteReply(reply);
		if(num>0)
			return true;
		else
			return false;
	}

}
