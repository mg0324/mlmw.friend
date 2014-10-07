package com.friend.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.CommentService;
import com.friend.service.biz.LogCommentService;
import com.friend.service.biz.LogPraiseService;
import com.friend.service.biz.LogService;
import com.friend.service.biz.LogSortService;
import com.friend.service.biz.LogTransferService;
import com.friend.service.biz.ReplyService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IComment;
import com.friend.service.ibiz.ILog;
import com.friend.service.ibiz.ILogComment;
import com.friend.service.ibiz.ILogPraise;
import com.friend.service.ibiz.ILogSort;
import com.friend.service.ibiz.ILogTransfer;
import com.friend.service.ibiz.IReply;
import com.friend.service.ibiz.IUser;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.util.StringUtil;
import com.friend.vo.Comment;
import com.friend.vo.Log;
import com.friend.vo.LogComment;
import com.friend.vo.LogPraise;
import com.friend.vo.LogSort;
import com.friend.vo.LogTransfer;
import com.friend.vo.Reply;
import com.friend.vo.User;

@WebServlet("/log.do")
public class LogServlet extends FrameworkServlet {
	private ILog iLog;
	private ILogSort iLogSort;
	private ILogPraise iLogPraise;
	private ILogComment iLogComment;
	private IReply iReply;
	private IUser iUser;
	private Pager page;
	private IComment iComment;
	private ILogTransfer iLogTransfer;
	private static final long serialVersionUID = 1L;

	public LogServlet() {
		iLog = new LogService();
		iLogSort = new LogSortService();
		iLogPraise = new LogPraiseService();
		iLogComment = new LogCommentService();
		iUser = new UserService();
		iReply = new ReplyService();
		iComment = new CommentService();
		page = new Pager();
		iLogTransfer =new LogTransferService();
	}

	
	/**
	 * xiaoqiang
	 * 发表评论
	 */
	public String publishComment(HttpServletRequest request,
			HttpServletResponse response){
		User user = null;
		String from = request.getParameter("from");
		user = (User) request.getSession().getAttribute("user");
		String content = request.getParameter("content");
		int logId = Integer.parseInt(request.getParameter("logId"));
		Log log = iLog.getLogById(logId);
		Comment comment = new Comment();
		comment.setAuthor(user.getUserNickName());
		comment.setUser(user);
		comment.setCommentType("log");
		comment.setContent(content);
		LogComment logComment=new LogComment();
		logComment.setLog(log);
		iLogComment.addComment(comment);
		Comment lastComment = iLogComment.lastComment();
		logComment.setComment(lastComment);
		iLogComment.addLogComment(logComment);
		if(from!=null){
			User friend = iUser.getUserById(Integer.parseInt(request.getParameter("friendId")));
			request.setAttribute("friend", friend);
			return "log.do?action=showLogInfo&from=friend&friendId="+friend.getUserId()+"&logId="+log.getLogId();
		}
		return "log.do?action=showLogInfo&logId="+log.getLogId();
	}
	/**
	 * zhouhao
	 * 修改日志分类名称
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public String reNameLogSort(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException{
		LogSort inLs = makeLogSortFromRequest(request);
		iLogSort.reNameLogSort(inLs);
		return "log.do?action=showLogList&logSortId=" + inLs.getLogSortId();
	}
	/**
	 * 移动日志
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public String moveLog(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Log log = makeLogFromRequest(request);
		log = iLog.getLogById(log.getLogId());
		LogSort logSort = makeLogSortFromRequest(request);
		logSort = iLogSort.getLogSortById(logSort.getLogSortId());
		iLog.moveLog(log, logSort);
		if(!"我的日志".equals(logSort.getSortName()))
			return "log.do?action=showLogList&logSortId="+logSort.getLogSortId();
		else
			return "log.do?action=showTransferLogList&logSortId="+logSort.getLogSortId();
	}
	/**
	 * zhouhao
	 * 日志置顶
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public String setLogTop(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		int logId = Integer.parseInt(request.getParameter("logId").toString());
		Log log = iLog.getLogById(logId);
		LogSort logSort =log.getLogSort();
		iLog.setLogTop(log);
		if(!"我的日志".equals(logSort.getSortName()))
			return "log.do?action=showLogList&logSortId="+logSort.getLogSortId();
		else
			return "log.do?action=showTransferLogList&logSortId="+logSort.getLogSortId();
	}
	
	/**
	 * 取消置顶
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public String cancelLogTop(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		int logId = Integer.parseInt(request.getParameter("logId").toString());
		Log log = iLog.getLogById(logId);
		LogSort logSort =log.getLogSort();
		iLog.cancelLogTop(log);
		if(!"我的日志".equals(logSort.getSortName()))
			return "log.do?action=showLogList&logSortId="+logSort.getLogSortId();
		else
			return "log.do?action=showTransferLogList&logSortId="+logSort.getLogSortId();
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	public String deleteLogSort(HttpServletRequest request,
			HttpServletResponse response) {
		int userId = Integer
				.parseInt(request.getParameter("userId").toString());
		int logSortId = Integer.parseInt(request.getParameter("logSortId"));
		iLogSort.deleteLogSort(iLogSort.getLogSortById(logSortId));
		return "log.do?action=toLogMain&userId="+ userId;
	}

	// 删除一篇日志
	public String deleteLog(HttpServletRequest request,
			HttpServletResponse response) {
		int logId = Integer.parseInt(request.getParameter("logId").toString());
		Log log = iLog.getLogById(logId);
		LogSort logSort =log.getLogSort();
		
		logSort.setLogCount(logSort.getLogCount()-1);
		iLogSort.updateLogCount(logSort);
		iLog.deleteLog(log);
		if(!"我的日志".equals(logSort.getSortName()))
			return "log.do?action=showLogList&logSortId="+logSort.getLogSortId();
		else
			return "log.do?action=showTransferLogList&logSortId="+logSort.getLogSortId();
	}
	/**
	 * request中应包含commentId
	 * @param req
	 * @param res
	 * @return
	 */
	public void addReply(HttpServletRequest req,HttpServletResponse res){
		Reply reply=makeReplyFromRequest(req);
		String logId = req.getParameter("logId");
		String from = req.getParameter("from");
		boolean bl1=iReply.addReply(reply);
		if(bl1){
			try {
				if(from!=null){
					User friend = iUser.getUserById(Integer.parseInt(req.getParameter("friendId")));
					req.setAttribute("friend", friend);
					res.sendRedirect("log.do?action=showLogInfo&from=friend&friendId="+friend.getUserId()+"&logId="+logId);
				}
				res.sendRedirect("log.do?action=showLogInfo&logId="+logId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	// 查看一条日志的具体内容·
	public String showLogInfo(HttpServletRequest request,
			HttpServletResponse response) {
		
		int logId = Integer.parseInt(request.getParameter("logId"));
		Log log = iLog.getLogById(logId);
		User owner=log.getAuthor();
		LogTransfer logTransfer=new LogTransfer();
		int num = iLogPraise.queryLogPraise(log);
		String from = request.getParameter("from");
		LogPraise logPraise=new LogPraise();
		logPraise.setLog(log);
		User user = (User) request.getSession().getAttribute("user");
		logPraise.setUser(user);
		int y=iLogPraise.isPraise(logPraise);
		User friend=new User();
		if(from!=null){
				friend= iUser.getUserById(Integer.parseInt(request.getParameter("friendId")));
				request.setAttribute("friend", friend);
		}
		
		int currentPage=1;
		if(request.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
		int totalCount = iLogComment.getTotalCount(log);
		page.paging(currentPage, ParamUtil.LOG_COMMENT_PAGE_SIZE, totalCount);
		List<Comment> commentList = iLogComment.getLogComments(log,page);
		page.setList(commentList);
		log.setCommentList(commentList);
		User transferUser=(User)request.getSession().getAttribute("user");
		

		logTransfer.setLog(log);
		logTransfer.setOwnerUser(owner);
		logTransfer.setTransferUser(transferUser);
		logTransfer.setTransferTime(new Date());
		int x=iLogTransfer.isTransfer(logTransfer);
		System.out.println(x+""+log.getLogTitle());
		request.setAttribute("PraiseNumber", num);
		request.setAttribute("log", log);
		request.setAttribute("page", page);
		//查看是否点赞
		if(request.getParameter("isPraise")!=null)
			request.setAttribute("isPraise",Integer.parseInt(request.getParameter("isPraise")));
		else if(y==0)
			request.setAttribute("isPraise", 1);
		else if(y==1)
			request.setAttribute("isPraise", 0);
		
		 //查看是否转载
		if(request.getParameter("isTransfer")!=null)
			request.setAttribute("isTransfer",Integer.parseInt(request.getParameter("isTransfer")));
		else if(x==0)
				request.setAttribute("isTransfer", 1);
			else if(x==1)
				request.setAttribute("isTransfer", 0);
		//拿到转载数量
		
		int transferNum=log.getTransferCount();
		request.setAttribute("transferNum",transferNum);
		//拿到赞数量
		int praiseNum = iLogPraise.queryLogPraise(log);
		request.setAttribute("praiseNum",praiseNum);

		System.out.println(from);

		if(from!=null){
			
			return "frontClient/friend/friendLogContent.jsp";
		}else{
			return "frontClient/log/logContent.jsp";
		}

		
	}

	/**
	 * 跳转到写日志界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String toAddLogUI(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		List<LogSort> logSorts = iLogSort.listLogSort(user);
		request.setAttribute("logSorts", logSorts);
		return "frontClient/log/addLog.jsp";
	}

	// 展示日志列表
	public String showLogList(HttpServletRequest request,
			HttpServletResponse response) {
		
		int logSortId = Integer.parseInt(request.getParameter("logSortId"));
		String from = request.getParameter("from");
		LogSort logSort = iLogSort.getLogSortById(logSortId);
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage").toString());
		
		
		User user = null;
		if(from!=null){
			user = iUser.getUserById(Integer.parseInt(request.getParameter("userId")));
		}else{
			user = (User) request.getSession().getAttribute("user");
		}
		
		
		int totalCount = iLogSort.getLogCount(logSort);
		page.paging(currentPage, ParamUtil.LOG_PAGE_SIZE, totalCount);
		List<Log> logs = iLog.listLogFromSort(logSort, page);
		System.out.print(user+"hahhhaw");
		List<LogSort> logSorts = (List<LogSort>) iLogSort.listLogSort(user);
		request.setAttribute("logSorts", logSorts);
		page.setList(logs);
		request.setAttribute("page", page);
		request.setAttribute("logSort", logSort);
		if(from!=null){
			request.setAttribute("friend", user);
			return "frontClient/friend/friendLogMain.jsp";
		}
		return "frontClient/log/logMain.jsp";
	}
	/**
	 * 展示转载日志列表
	 * @param request
	 * @param response
	 * @return
	 */
	public String showTransferLogList(HttpServletRequest request,
			HttpServletResponse response) {
		
		int logSortId = Integer.parseInt(request.getParameter("logSortId"));
		String from = request.getParameter("from");
		LogSort logSort = iLogSort.getLogSortById(logSortId);
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage").toString());
		
		User user = null;
		if(from!=null){
			user = iUser.getUserById(Integer.parseInt(request.getParameter("userId")));
		}else{
			user = (User) request.getSession().getAttribute("user");
		}
		List<Log> transferLogs=iLogTransfer.listAllTransferLog(user, page);
		int totalCount = iLogSort.getLogCount(logSort)+transferLogs.size();
		page.paging(currentPage, ParamUtil.LOG_PAGE_SIZE, totalCount);
		List<Log> logs = iLog.listTransferLogFromSort(logSort, page);
		List<LogSort> logSorts = (List<LogSort>) iLogSort.listLogSort(user);
		request.setAttribute("logSorts", logSorts);
		request.setAttribute("logSortId", logSortId);
		page.setList(logs);
		request.setAttribute("page", page);
		request.setAttribute("logSort", logSort);
		if(from!=null){
			request.setAttribute("friend", user);
			return "frontClient/friend/friendLogMain.jsp";
		}
		return "frontClient/log/logMain.jsp";
	}
	/**
	 * 发表日志
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public String createLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		User user = (User) request.getSession().getAttribute("user");
		Log log = makeLogFromRequest(request);
		String logSortId = request.getParameter("logSortId");
		LogSort logSort = iLogSort.getLogSortById(Integer.parseInt(logSortId));
		logSort.setLogCount(logSort.getLogCount()+1);
		log.setAuthor(user);
		log.setLogSort(logSort);
		Date d = new Date();
		log.setLastUpdateTime(d);
		log.setPublishTime(d);
		iLogSort.updateLogCount(logSort);
		iLog.addLog(log);
		if(!"我的日志".equals(logSort.getSortName()))
			return "log.do?action=showLogList&logSortId="+logSort.getLogSortId();
		else
			return "log.do?action=showTransferLogList&logSortId="+logSort.getLogSortId();
	}
	/**
	 * 获取要修改的日志
	 */
	public String updateLogUI(HttpServletRequest request, HttpServletResponse response)
	{
		int logId=Integer.parseInt(request.getParameter("logId"));
		
		User user = (User) request.getSession().getAttribute("user");
		List<LogSort> logSorts = iLogSort.listLogSort(user);
		request.setAttribute("logSorts", logSorts);
		Log log=iLog.getLogById(logId);
		request.setAttribute("logsort", log.getLogSort());
		request.setAttribute("log", log);
		return "frontClient/log/updateLog.jsp";
	}
	/**
	 * 修改日志
	 */
	public void updateLog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		int logId=Integer.parseInt(request.getParameter("logId"));
		String content=request.getParameter("content");
		String logTitle=request.getParameter("logTitle");
		
		Log log=iLog.getLogById(logId);
		log.setLogTitle(logTitle);
		log.setContent(content);
		log.setLastUpdateTime(new Date());
		iLog.updateLog(log);
		try {
			response.sendRedirect( "log.do?action=showLogInfo&logId="+logId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 从request中得到日志对象信息
	 * 
	 * @param r
	 * @return 日志对象
	 * @throws ServletException
	 */
	public Log makeLogFromRequest(HttpServletRequest r) throws ServletException {
		Log log = new Log();
		if (r.getParameter("logId") != null)
			log.setLogId(Integer.parseInt(r.getParameter("logId")));
		if (r.getParameter("logTitle") != null)
			log.setLogTitle(r.getParameter("logTitle"));
		if (r.getParameter("content") != null)
			log.setContent(r.getParameter("content"));
		if (r.getParameter("createTime") != null) {
			log.setPublishTime(StringUtil.stringConvertDateWithHMS(r.getParameter("createTime")));
		} else {
			log.setPublishTime(new Date());
		}

		if (r.getParameter("updateTime") != null) {
			log.setLastUpdateTime(StringUtil.stringConvertDateWithHMS(r.getParameter("updateTime")));
		} else {
			log.setLastUpdateTime(new Date());
		}

		if (r.getParameter("updateTime") != null) {
			log.setLastUpdateTime(StringUtil.stringConvertDateWithHMS(r.getParameter("createTime")));
		} else {
			log.setLastUpdateTime(new Date());
		}

		return log;

	}

	/**
	 * 从request中得到LogPraise信息
	 * 
	 * @param r
	 * @return 点赞对象
	 */
	public LogPraise makeLogPraiseFromRequest(HttpServletRequest r) {
		User user = new User();
		Log log = new Log();
		LogPraise logPraise = new LogPraise();
		if (r.getParameter("logId") != null)
			log=iLog.getLogById(Integer.parseInt(r.getParameter("logId")));

		logPraise.setPraiseTime(new Date());
		logPraise.setLog(log);
		logPraise.setUser(user);
		return logPraise;
	}

	/**
	 * 点赞
	 * 
	 * @param request
	 * @param response
	 * @return 跳转页面
	 */
	public void clickLogPraise(HttpServletRequest request,
			HttpServletResponse response) {
		int x;
		int logId=Integer.parseInt(request.getParameter("logId"));
		LogPraise logPraise=new LogPraise();
		logPraise=this.makeLogPraiseFromRequest(request);

		
		String from = request.getParameter("from");
		User user = null;
		if(from!=null){
			user = iUser.getUserById(Integer.parseInt(request.getParameter("userId")));
		}else{
			user = (User) request.getSession().getAttribute("user");
		}
		logPraise.setUser(user);
		int friendId=0;
		if(request.getParameter("friendId")!=null)
			friendId=Integer.parseInt(request.getParameter("friendId"));

		x=iLogPraise.isPraise(logPraise);
		if(x==0)
		{
			iLogPraise.addLogPraise(logPraise);
			try {

				if(from!=null){
					request.setAttribute("friend", user);
					response.sendRedirect("log.do?action=showLogInfo&from=friend&friendId="+friendId+"&isPraise=0&logId="+logId);
				}else{
					response.sendRedirect("log.do?action=showLogInfo&isPraise=0&logId="+logId);
				}	

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			iLogPraise.deleteLogPraise(logPraise);
			try {

				if(from!=null){
					request.setAttribute("friend", user);
					response.sendRedirect("log.do?action=showLogInfo&from=friend&friendId="+friendId+"&userId="+user.getUserId()+"&isPraise=1&logId="+logId);
				}else{
					response.sendRedirect("log.do?action=showLogInfo&isPraise=1&logId="+logId);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
	/**
	 * 查看某日志的点赞数
	 * 
	 * @param request
	 * @param response
	 * @return 跳转页面
	 * @throws ServletException
	 */
	public String checkLogPraiseNumber(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		int num = 0;
		num = iLogPraise.queryLogPraise(this.makeLogFromRequest(request));
		request.setAttribute("praiseNumber", num);
		return null;// 跳转到页面
	}

	// **********************************************************************
	public String createLogSort(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		String logSortName = request.getParameter("sortName");
		String from = request.getParameter("from");
		User user = (User)request.getSession().getAttribute("user");
		LogSort logSort = new LogSort();
		logSort.setUser(user);
		logSort.setSortName(logSortName);
		iLogSort.createLogSort(logSort);
		if(from!=null){
			if(from.equals("addLogUI")){
				return toAddLogUI(request, response);
			}
		}
		return toLogMain(request, response);

	}

	// 显示日志列表
	public String toLogMain(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		User user = null;
		String from = request.getParameter("from");
		if(from!=null){
			user = iUser.getUserById(Integer.parseInt(request.getParameter("userId")));
		}else{
			user = (User) request.getSession().getAttribute("user");
		}
		
		ArrayList<LogSort> logSorts = (ArrayList<LogSort>) iLogSort.listLogSort(user);
	
		if(logSorts.size()>0){
			//默认显示第一个日志分类
			LogSort ls = logSorts.get(0);
			if (iLogSort.getLogCount(ls) >0 ||iLogSort.getLogCount(ls) == 0){
				int currentPage=1;
				if(request.getParameter("currentPage") != null)
					currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
				List<Log> logs = iLog.listLogFromSort(ls, page);
				List<Log> transferLogs=iLogTransfer.listAllTransferLog(user, page);
				for(Log log:transferLogs)
				{
					logs.add(log);
				}
				int totalCount=logs.size();
				page.paging(currentPage, ParamUtil.LOG_PAGE_SIZE, totalCount);
				page.setList(logs);
				request.setAttribute("logSort", ls);
				request.setAttribute("page", page);
			}
		}
		request.setAttribute("logSorts", logSorts);
		if(from!=null){
			request.setAttribute("friend", user);
			return "frontClient/friend/friendLogMain.jsp";
		}
		return "frontClient/log/logMain.jsp";

	}
	

	/**
	 * 从request中封装logSort对象
	 * 
	 * @param r
	 * @return
	 * @throws ServletException
	 */
	public LogSort makeLogSortFromRequest(HttpServletRequest request)
			throws ServletException {
		LogSort logSort = new LogSort();
		if (request.getParameter("logSortId") != null)
			logSort.setLogSortId(Integer.parseInt(request.getParameter("logSortId")));
		if (request.getParameter("sortName") != null)
			logSort.setSortName(request.getParameter("sortName"));
		if (request.getParameter("logCount") != null)
			logSort.setSortName(request.getParameter("logCount"));
		if (request.getParameter("userId") != null)
			logSort.setUser(iUser.getUserById(Integer.parseInt(request
					.getParameter("userId").toString())));
		if (request.getParameter("createTime") != null) {
			logSort.setCreateTime(StringUtil.stringConvertDateWithHMS(request
					.getParameter("createTime")));
		} else {
			logSort.setCreateTime(new Date());
		}
		return logSort;
	}
	public User makeUserFromRequest(HttpServletRequest request){
		User u = new User();
		packageForm(request, u,"User.xml");
		return u;
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
	 * 转载日志
	 */
	public void transferLog(HttpServletRequest request, HttpServletResponse response)
	{
		int logId=Integer.parseInt(request.getParameter("logId"));
		System.out.println(request.getParameter("transferId"));
		int transferId=Integer.parseInt(request.getParameter("transferId"));
		int friendId=Integer.parseInt(request.getParameter("friendId"));
		LogTransfer logTransfer = new LogTransfer() ;
		Log log=iLog.getLogById(logId);
		User transferUser=iUser.getUserById(transferId);
		ArrayList<LogSort> logSorts = (ArrayList<LogSort>) iLogSort.listLogSort(transferUser);
		LogSort logSort=logSorts.get(0);
		logSort.setLogCount(logSort.getLogCount()+1);
		iLogSort.updateLogCount(logSort);
		User owner=log.getAuthor();
		logTransfer.setLog(log);
		logTransfer.setOwnerUser(owner);
		logTransfer.setTransferUser(transferUser);
		logTransfer.setTransferTime(new Date());
		int x=iLogTransfer.isTransfer(logTransfer);
		
		if(x==0)
		{
			iLogTransfer.transferLog(logTransfer);
			request.setAttribute("friend", owner);
			try {
				response.sendRedirect("log.do?action=showLogInfo&from=friend&friendId="+friendId+"&isTransfer=0&logId="+logId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				response.sendRedirect("log.do?action=showLogInfo&from=friend&userId="+friendId+"&isTransfer=1&logId="+logId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 删除转载
	 */
	public String deleteTransfer(HttpServletRequest request, HttpServletResponse response)
	{
		
		int logId=Integer.parseInt(request.getParameter("logId"));
		int transferId=Integer.parseInt(request.getParameter("userId"));
		LogTransfer logTransfer = new LogTransfer() ;
		Log log=iLog.getLogById(logId);
	
		User transferUser=iUser.getUserById(transferId);
		logTransfer.setLog(log);
		logTransfer.setTransferUser(transferUser);
		iLogTransfer.deleteLogPraise(logTransfer);
		ArrayList<LogSort> logSorts = (ArrayList<LogSort>) iLogSort.listLogSort(transferUser);
		LogSort logSort=logSorts.get(0);
		logSort.setLogCount(logSort.getLogCount()-1);
		iLogSort.updateLogCount(logSort);
		
		return "log.do?action=showTransferLogList&logSortId="+logSort.getLogSortId();
	}
}
