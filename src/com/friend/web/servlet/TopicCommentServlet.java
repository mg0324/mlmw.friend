package com.friend.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.AttachmentService;
import com.friend.service.biz.CommentService;
import com.friend.service.biz.ModuleService;
import com.friend.service.biz.TopicCommentService;
import com.friend.service.biz.TopicService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IAttachment;
import com.friend.service.ibiz.IComment;
import com.friend.service.ibiz.IModule;
import com.friend.service.ibiz.ITopic;
import com.friend.service.ibiz.ITopicComment;
import com.friend.service.ibiz.IUser;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.vo.Attachment;
import com.friend.vo.Comment;
import com.friend.vo.Module;
import com.friend.vo.Topic;
import com.friend.vo.TopicComment;
import com.friend.vo.User;

/**
 * Servlet implementation class commentServlet
 */
@WebServlet("/topicComment.do")
public class TopicCommentServlet extends FrameworkServlet {
	private static final long serialVersionUID = 1L;

	private ITopicComment topicCommentService;
	private ITopic topicService;
	private IUser userService;
	private IComment commentService;
	private IAttachment iAttachment;
	private IModule iModule;
	private Pager page;
	public TopicCommentServlet() {
		topicCommentService = new TopicCommentService();
		topicService = new TopicService();
		userService = new UserService();
		commentService = new CommentService();
		iAttachment = new AttachmentService();
		page=new Pager();
		iModule=new ModuleService();
	}
/**
 * 该方法用于查看评论内容
 * @param request
 * @param response
 * @return
 */
	public String lookComment(HttpServletRequest request,
			HttpServletResponse response) {
		int topicId = Integer.parseInt(request.getParameter("topicId")
				.toString());
		Topic topic = topicService.getTopicById(topicId);
		List<Attachment> atts = iAttachment.getAttListInTopic(topic);
		int currentPage=1;
 		if(request.getParameter("currentPage")!=null)
 			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
 		int totalCount=topicCommentService.getTopicCommentCountByTopic(topic);
 		page.paging(currentPage, ParamUtil.TPOIC_COMMENT_PAGE_SIZE, totalCount);
		List<Comment> comments = topicCommentService.lookComment(topicId,page);
		topic.setCommentList(comments);
		topic.setAttachmentList(atts);
		request.setAttribute("topic", topic);
		request.setAttribute("page",page);
		return "frontClient/bbs/topicComment.jsp";
			

	}
	/**
	 * 查看后台帖子
	 */
	public String lookTopic(HttpServletRequest request,
			HttpServletResponse response) {
		int topicId = Integer.parseInt(request.getParameter("topicId")
				.toString());
		Topic topic = topicService.getTopicById(topicId);
		int moduleId=Integer.parseInt(request.getParameter("moduleId")
				.toString());
		System.out.println();
		Module module= iModule.getModuleById(moduleId);
		List<Attachment> atts = iAttachment.getAttListInTopic(topic);
		topic.setAttachmentList(atts);
		request.setAttribute("topic", topic);
		request.setAttribute("module", module);
		return "backClient/bbs/b_topicContent.jsp";
	}
	/**
	 * 添加帖子评论
	 * 
	 * @param request
	 * @param response
	 */
	public void addComment(HttpServletRequest request,
			HttpServletResponse response) {
		String content = request.getParameter("content");
		int topicId = Integer.parseInt(request.getParameter("topicId"));
		Topic topic = topicService.getTopicById(topicId);
		int userId = Integer.parseInt(request.getParameter("userId"));
		User my = userService.getUserById(userId);
		String name = my.getUserName();
		Comment comment = new Comment();
		comment.setAuthor(name);
		comment.setUser(my);
		comment.setCommentType("topic");
		comment.setContent(content);
		TopicComment topicComment = new TopicComment();
		topicComment.setTopic(topic);
		topicCommentService.addComment(comment);
		topicService.getCommentCount(topic);
		Comment lastComment = topicCommentService.lastComment();
		if (lastComment == null)
			System.out.println("null");
		else
			System.out.println(lastComment.getCommentId());
		topicComment.setCommentId(lastComment.getCommentId());
		topicCommentService.addTopicComment(topicComment);
		try {
			response.sendRedirect("topicComment.do?action=lookComment&topicId="
					+ topicId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteComment(HttpServletRequest request,
			HttpServletResponse response) {
		int topicId = Integer.parseInt(request.getParameter("topicId")
				.toString());
		int commentId = Integer.parseInt(request.getParameter("commentId")
				.toString());
		Comment comment = commentService.getCommentById(commentId);
		topicCommentService.deleteTopicComment(comment);
		try {
			response.sendRedirect("topicComment.do?action=lookComment&topicId="
					+ topicId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
