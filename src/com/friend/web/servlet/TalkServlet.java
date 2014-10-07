package com.friend.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.CommentService;
import com.friend.service.biz.ReplyService;
import com.friend.service.biz.TalkCommentService;
import com.friend.service.biz.TalkPraiseService;
import com.friend.service.biz.TalkService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IComment;
import com.friend.service.ibiz.IReply;
import com.friend.service.ibiz.ITalk;
import com.friend.service.ibiz.ITalkComment;
import com.friend.service.ibiz.ITalkPraise;
import com.friend.service.ibiz.IUser;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.vo.Comment;
import com.friend.vo.Reply;
import com.friend.vo.Talk;
import com.friend.vo.TalkComment;
import com.friend.vo.TalkPraise;
import com.friend.vo.User;

/**
 * @author szh 2014/7/20
 */
@WebServlet("/talk.do")
public class TalkServlet extends FrameworkServlet {
	private static final long serialVersionUID = 1L;
	private ITalk iTalk;
	private IComment iComment;
	private IReply iReply;
	private ITalkComment iTalkComment;
	private ITalkPraise iTalkPraise;
	private IUser iUser;
	private Pager page;
	
	public TalkServlet(){
		iTalk=new TalkService();
		iComment=new CommentService();
		iReply=new ReplyService();
		iTalkComment=new TalkCommentService();
		iTalkPraise=new TalkPraiseService();
		iUser = new UserService();
		page=new Pager();
	}

	/**
	 * 从request中得到talk
	 * @param req
	 * @return
	 */
	public Talk makeTalkFromRequest(HttpServletRequest req){
		Talk talk=new Talk();
		if(req.getParameter("talkId")!=null)
			talk.setTalkId(Integer.parseInt(req.getParameter("talkId")));
		if(req.getParameter("content")!=null)
			talk.setContent(req.getParameter("content"));
		User user=(User) req.getSession().getAttribute("user");
		talk.setUser(user);
		talk.setAuthor(user.getUserNickName());
		
		return talk;
	}
	
	/**
	 * 添加说说
	 * @param req
	 * @param res
	 * @return
	 */
	public String addTalk(HttpServletRequest req,HttpServletResponse res){
		Talk talk=makeTalkFromRequest(req);
		talk.setPublishTime(new Date());
		talk.setLastUpdateTime(new Date());
		boolean bl=iTalk.addTalk(talk);
		try {
			res.sendRedirect("talk.do?action=showTalk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除说说
	 * @param req
	 * @param res
	 * @return
	 */
	public String deleteTalk(HttpServletRequest req,HttpServletResponse res){
		Talk talk=makeTalkFromRequest(req);
		boolean bl=iTalk.deleteTalk(talk);
		if(bl){
			try{
			  res.sendRedirect("talk.do?action=showTalk");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	/**
	 * 更新说说
	 * @param req
	 * @param res
	 * @return
	 */
	public String updateTalk(HttpServletRequest req,HttpServletResponse res){
		Talk talk=makeTalkFromRequest(req);
		talk.setLastUpdateTime(new Date());
		boolean bl=iTalk.updateTalk(talk);
//		if(bl==true)
//			return "talk.do?action=showTalk";
//		else
//			return null;   //错误页面！！！
		try {
			res.sendRedirect("talk.do?action=showTalk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询说说和评论，以及回复(此方法应在说说页面刷新时调用，如打开说说主页，添加说说，删除说说后)
	 * @param req
	 * @param res
	 * @return
	 */
	public String showTalk(HttpServletRequest req,HttpServletResponse res){
		User user=(User) req.getSession().getAttribute("user");
		int currentPage=1;
		if(req.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(req.getParameter("currentPage").toString());
		int totalCount=iTalk.getTalkCountByUser(user);
		page.paging(currentPage, ParamUtil.TALK_PAGE_SIZE, totalCount);
		List<Talk> talkList=iTalk.showTalk(user,page);
		req.setAttribute("talkList", talkList);
		req.setAttribute("page", page);
		return "frontClient/talk/talkList.jsp";
	}
	
	/**
	 * 从request中得到Comment
	 * @param req
	 * @return
	 */
	public Comment makeCommentFromRequest(HttpServletRequest req){
		Comment comment=new Comment();
		if(req.getParameter("commentId")!=null)
			comment.setCommentId(Integer.parseInt(req.getParameter("commentId"))); 
		if(req.getParameter("content")!=null)
			comment.setContent(req.getParameter("content"));
		if(req.getParameter("commentType")!=null)
			comment.setCommentType(req.getParameter("commentType"));
		comment.setPublishTime(new Date());
		User user=(User) req.getSession().getAttribute("user");
		comment.setUser(user);
		comment.setAuthor(user.getUserNickName());
		return comment;
	}
	
	/**
	 * request中应包含talkId
	 * @param req
	 * @param res
	 * @return
	 */
	public String addComment(HttpServletRequest req,HttpServletResponse res){
		Comment comment=makeCommentFromRequest(req);
		boolean bl1=iComment.addComment(comment);
		Comment newComment=iComment.getCommentByPublishTime(comment);
		Talk talk=makeTalkFromRequest(req);
		TalkComment talkComment=new TalkComment();
		talkComment.setTalk(talk);
		talkComment.setComment(newComment);
		boolean bl2 = false;
		if(bl1){
			bl2=iTalkComment.addTalkComment(talkComment);
			iTalk.addCommentCount(talk);
		}
		if(bl2){
			try{
				if("selfMain".equals(isFrom(req))){
					res.sendRedirect("user.do?action=toSelfMainUI");
				}
				else if("friendMain".equals(isFrom(req))){
					String userId=req.getParameter("userId");
					res.sendRedirect("friend.do?action=toFriendMainUI&userId="+userId);
				}
				else
					res.sendRedirect("talk.do?action=showTalk");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null; //错误页面！
	}
	
	/**
	 * 从request中得到Reply
	 * @param req
	 * @return
	 */
	public Reply makeReplyFromRequest(HttpServletRequest req){
		Reply reply=new Reply();
		if(req.getParameter("content")!=null)
			reply.setContent(req.getParameter("content"));
		if(req.getParameter("lastReplayerId")!=null){
			reply.setLastReplayer(iUser.getUserById(Integer.parseInt(req.getParameter("lastReplayerId"))));
		}
		if(req.getParameter("commentId")!=null){
			reply.setComment(iComment.getCommentById(Integer.parseInt(req.getParameter("commentId"))));
		}
		reply.setPublishTime(new Date());
		User user=(User) req.getSession().getAttribute("user");
		reply.setUser(user);
		reply.setAuthor(user.getUserNickName());
		return reply;
	}
	
	/**
	 * request中应包含commentId
	 * @param req
	 * @param res
	 * @return
	 */
	public String addReply(HttpServletRequest req,HttpServletResponse res){
		Reply reply=makeReplyFromRequest(req);
		boolean bl1=iReply.addReply(reply);
		
		if(bl1){
			try{
				if("selfMain".equals(isFrom(req)))
					res.sendRedirect("user.do?action=toSelfMainUI");
				else if("friendMain".equals(isFrom(req))){
					String userId=req.getParameter("userId");
					res.sendRedirect("friend.do?action=toFriendMainUI&userId="+userId);
				}
				else
					res.sendRedirect("talk.do?action=showTalk");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null; //错误页面！
	}
	
	/**
	 * 从request中得到TalkPraise
	 * @param req
	 * @return
	 */
	public TalkPraise makeTalkPraiseFromRequest(HttpServletRequest req){
		TalkPraise talkPraise=new TalkPraise();
		if(req.getParameter("talkId")!=null)
			talkPraise.setTalk(iTalk.getTalkByTaikId(Integer.parseInt(req.getParameter("talkId"))));
		talkPraise.setPraiseTime(new Date());
		talkPraise.setUser((User) req.getSession().getAttribute("user"));
		return talkPraise;
	}
	
	/**
	 * 点赞
	 * @param req
	 * @param res
	 * @return
	 */
	public String addTalkPraise(HttpServletRequest req,HttpServletResponse res){
		TalkPraise talkPraise=makeTalkPraiseFromRequest(req);
		
		iTalkPraise.addTalkPraise(talkPraise);
		iTalk.addPraiseCount(talkPraise.getTalk());
		try{
			if("selfMain".equals(isFrom(req))) {
				res.sendRedirect("user.do?action=toSelfMainUI");
			}
			else if("friendMain".equals(isFrom(req))){
				String userId=req.getParameter("userId");
				res.sendRedirect("friend.do?action=toFriendMainUI&userId="+userId);
			}
			else
				res.sendRedirect("talk.do?action=showTalk");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 判断是否是从selfMain过来的请求
	 * @param req
	 * @return
	 */
	private String isFrom(HttpServletRequest req){
		String from = req.getParameter("from");
		
		return from;
	}
	/**
	 * 取消赞
	 * @param req
	 * @param res
	 * @return
	 */
	public String deleteTalkPraise(HttpServletRequest req,HttpServletResponse res){
		TalkPraise talkPraise=new TalkPraise();
		TalkService talkService=new TalkService();
		TalkPraiseService talkPraiseService=new TalkPraiseService();
		User user=(User) req.getSession().getAttribute("user");
		Talk talk=makeTalkFromRequest(req);
		talkPraise.setTalk(talk);
		talkPraise.setUser(user);
		if(talkPraiseService.deleteTalkPraise(talkPraise)){
			talkService.deletePraiseCount(talk);
			try{
				if("selfMain".equals(isFrom(req)))
					res.sendRedirect("user.do?action=toSelfMainUI");
				else if("friendMain".equals(isFrom(req))){
					String userId=req.getParameter("userId");
					res.sendRedirect("friend.do?action=toFriendMainUI&userId="+userId);
				}
				else
					res.sendRedirect("talk.do?action=showTalk");
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 显示好友说说，只限五条
	 * @param req
	 * @param res
	 * @return
	 */
	public String showFriendTalk(HttpServletRequest req,HttpServletResponse res){
		User user=(User) req.getAttribute("user");
		List<Talk> friendTalkList=iTalk.showFriendTalk(user);
		req.setAttribute("friendTalkList", friendTalkList);
		return "";
	}
	
	/**
	 * 显示特别关注好友的说说，有分页
	 */
	public String showFocusFriendTalk(HttpServletRequest req,HttpServletResponse res){
		User user=(User) req.getAttribute("user");
		int currentPage=1;
		if(req.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(req.getParameter("currentPage").toString());
		int totalCount=iTalk.getFocusFriendTalkCountByUser(user);
		page.paging(currentPage, ParamUtil.TALK_PAGE_SIZE, totalCount);
		List<Talk> focusFriendTalkList=iTalk.showFocusFriendTalk(user, page);
		req.setAttribute("focusFriendTalkList", focusFriendTalkList);
		req.setAttribute("page", page);
		return "";
	}
}
