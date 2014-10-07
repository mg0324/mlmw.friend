/**
 * 
 */
package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Talk;
import com.friend.vo.User;

/**
 * @author szh 2014/7/17
 *
 */
public interface ITalk {
	public boolean addTalk(Talk talk);
	public boolean deleteTalk(Talk talk);
	public boolean updateTalk(Talk talk);
	public List<Talk> showTalk(User user,Pager pager);
	public boolean addViewCount(Talk talk);
	public boolean addPraiseCount(Talk talk);
	public boolean addCommentCount(Talk talk);
	public boolean deletePraiseCount(Talk talk);
	public boolean deleteCommentCount(Talk talk);
	public Talk getTalkByTaikId(int talkId);
	public int getTalkCountByUser(User user);
	public List<Talk> showFriendTalk(User user);
	public List<Talk> showFocusFriendTalk(User user,Pager pager);
	public List<Talk> showFriendMainTalk(User user,User friend,Pager pager);
	public int getFocusFriendTalkCountByUser(User user);
}
