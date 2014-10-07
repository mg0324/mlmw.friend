package com.friend.web.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.CommentService;
import com.friend.service.biz.LogCommentService;
import com.friend.service.biz.LogService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IComment;
import com.friend.service.ibiz.ILog;
import com.friend.service.ibiz.ILogComment;
import com.friend.service.ibiz.IUser;
import com.friend.util.StringUtil;
import com.friend.vo.Comment;
import com.friend.vo.Log;
import com.friend.vo.LogComment;
import com.friend.vo.User;



@WebServlet("/logComment.do")
public class LogCommentServlet extends FrameworkServlet{
	private static final long serialVersionUID = 1L;
	private ILogComment iLogComment;
	private IComment iComment;
	private ILog iLog;
	private IUser iUser;
	public LogCommentServlet(){
		iLogComment = new LogCommentService();
		iComment = new CommentService();
		iLog =new LogService();
		iUser = new UserService();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String lookComment(HttpServletRequest request,
			HttpServletResponse response) {
		int logId = Integer.parseInt(request.getParameter("logId").toString());
		Log log = iLog.getLogById(logId);
		
		List<Comment> comments = iLogComment.lookComment(logId);
		log.setCommentList(comments);
		request.setAttribute("log", log);
		return "frontClient/log/showLog.jsp";
	}
	public String addComment(HttpServletRequest request,
			HttpServletResponse response){//新增方法
		Comment comment = makeCommentFromRequest(request);
		User user = (User)request.getSession().getAttribute("user");
		comment.setAuthor(user.getUserNickName());
		//User域已从页面补全
		//*********************************************
		//将评论插入数据表，同时根据发表时间取出该条记录，以获取comment_id
		Date publishTime = new Date();
		comment.setPublishTime(publishTime);
		comment.setCommentType("log");
		boolean r = iComment.addComment(comment);
		if (r)
			comment = iComment.getCommentByPublishTime(comment);
		//*********************************************
		int logId = Integer.parseInt(request.getParameter("logId").toString());
		LogComment logComment = new LogComment();
		logComment.setLog(iLog.getLogById(logId));
		logComment.setComment(comment);
		iLogComment.addLogComment(logComment);
		return "front/log/";
	}
	public Comment makeCommentFromRequest(HttpServletRequest request){  //增加方法
		Comment comment = new Comment();
		if (request.getParameter("id") != null)
			comment.setCommentId(Integer.parseInt(request.getParameter("id").toString()));
		if (request.getParameter("content") != null)
			comment.setContent(request.getParameter("content").toString());
		if (request.getParameter("author") != null)
			comment.setAuthor(request.getParameter("author").toString());
		if (request.getParameter("publishTime") != null)
			comment.setPublishTime(StringUtil.stringConvertDate(request.getParameter("publishTime").toString()));
		if (request.getParameter("commentType") != null)
			comment.setCommentType(request.getParameter("commentType").toString());
		if (request.getParameter("userId") != null)
			comment.setUser(iUser.getUserById(Integer.parseInt(request.getParameter("userId").toString())));
		return comment;
	}
}
