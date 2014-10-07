/**
 * 
 */
package com.friend.service.ibiz;

import com.friend.vo.TalkComment;

/**
 * @author szh 2014/7/17
 *
 */
public interface ITalkComment {
	public boolean addTalkComment(TalkComment talkComment);
	public boolean deleteTalkComment(TalkComment talkComment);
}
