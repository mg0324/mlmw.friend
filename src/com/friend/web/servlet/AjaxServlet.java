package com.friend.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.AlbumService;
import com.friend.service.biz.FriendService;
import com.friend.service.biz.FriendSortService;
import com.friend.service.biz.LogSortService;
import com.friend.service.biz.ModuleService;
import com.friend.service.biz.PermissionService;
import com.friend.service.biz.RoleService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IAlbum;
import com.friend.service.ibiz.IFriend;
import com.friend.service.ibiz.IFriendSort;
import com.friend.service.ibiz.ILogSort;
import com.friend.service.ibiz.IModule;
import com.friend.service.ibiz.IPermission;
import com.friend.service.ibiz.IRole;
import com.friend.service.ibiz.IUser;
import com.friend.util.LogUtil;
import com.friend.vo.Album;
import com.friend.vo.Friend;
import com.friend.vo.LogSort;
import com.friend.vo.Permission;
import com.friend.vo.Role;
import com.friend.vo.User;
/**
 * 
 * @author 梅刚 2014-8-1
 * 在继承了frameWorkServlet后来回应ajax数据时，不知道何因
 * ，总是返回整个html
 *
 */
@WebServlet("/ajaxServlet")
public class AjaxServlet extends FrameworkServlet{
	private IUser iUser;
	private IAlbum iAlbum;
	private IModule iModule;
	private IFriend iFriend;
	private IFriendSort iFriendSort;
	private IPermission iPermission;
	private ILogSort iLogSort;
	private IRole iRole;



	public AjaxServlet(){
		iUser = new UserService();
		iAlbum = new AlbumService();
		iModule=new ModuleService();
		iFriend = new FriendService();
		iFriendSort = new FriendSortService();
		iLogSort = new LogSortService();
		iPermission = new PermissionService();
		iRole = new RoleService();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 响应ajax,验证用户名是否已存在
	 * @param req
	 * @param res
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String checkUserName(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String inName = req.getParameter("inName");
		PrintWriter out = res.getWriter();
		String data = "";
		if(iUser.isExistUserName(inName)){
			//用户名已存在
			data = "{\"msg\":\"T\"}";
		}else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		LogUtil.logger.info("返回json数据:"+inName +":"+data);
		return null;
		
	}
	public String checkModuleName(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String inName = req.getParameter("inName");
		PrintWriter out = res.getWriter();
		String data = "";
		if(iModule.isExistModule(inName)){
			//标题不存在
			data = "{\"msg\":\"F\"}";
		}else{
			data = "{\"msg\":\"T\"}";
		}
		out.print(data);
		out.close();
		LogUtil.logger.info("返回json数据:"+inName +":"+data);
		return null;
		
	}
	public String checkLogSortName(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sortName = req.getParameter("sortName");
		User user=(User)req.getSession().getAttribute("user");
		LogSort logSort=new LogSort();
		logSort.setSortName(sortName);
		logSort.setUser(user);
		PrintWriter out = res.getWriter();
		String data = "";
		if(iLogSort.isExitLogSort(logSort)){
			//标题存在
			data = "{\"msg\":\"T\"}";
		}else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		LogUtil.logger.info("返回json数据:"+logSort +":"+data);
		return null;
		
	}
	public String focusFriend(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		int userId = Integer.parseInt(request.getParameter("userId"));
		User userFriend = new User();
		userFriend.setUserId(userId);
		Friend friend = new Friend();
		friend.setUser((User)request.getSession().getAttribute("user"));
		friend.setUserFriend(userFriend);
		PrintWriter out = response.getWriter();
		String data = "";
		if (iFriend.focusFriend(friend)){
			data = "{\"msg\":\"T\"}";
		}else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		LogUtil.logger.info("已关注该好友："+userId+":"+data);
		return null;
	}
	public String unFocusFriend(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		int userId = Integer.parseInt(request.getParameter("userId"));
		User userFriend = new User();
		userFriend.setUserId(userId);
		Friend friend = new Friend();
		friend.setUser((User)request.getSession().getAttribute("user"));
		friend.setUserFriend(userFriend);
		PrintWriter out = response.getWriter();
		String data = "";
		if (iFriend.unFocusFriend(friend)){
			data = "{\"msg\":\"T\"}";
		}else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		LogUtil.logger.info("已关注该好友："+userId+":"+data);
		return null;
	}
	public String checkSortName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String sortName = request.getParameter("sortName");
		User user = (User)request.getSession().getAttribute("user"); 
		PrintWriter out = response.getWriter();
		String data = "";
		if (iFriendSort.isExistSortName(user, sortName)){
			data = "{\"msg\":\"T\"}";
		}
		else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		return null;
	}
	public String checkNickName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String userNickName = request.getParameter("userNickName"); 
		PrintWriter out = response.getWriter();
		String data = "";
		if (iFriend.isExistNickName(userNickName)){
			data = "{\"msg\":\"T\"}";
		}
		else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		return null;
	}
	/**
	 * 检查相册名是否已经存在
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public String checkAlbumName(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Album album = new Album();
		User user = new User();
		PrintWriter out = res.getWriter();
		String albumName = req.getParameter("albumName");
		album.setAlbumName(albumName);
		int userId = Integer.parseInt(req.getParameter("userId"));
		user.setUserId(userId);
		String data = "";
		if(iAlbum.checkAlbumName(user, album)){
			data = "{\"msg\":\"T\"}";
		}
		else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		return null;
	}
	/**
	 * 验证permissionId不重复
	 * @param req
	 * @param res
	 * @return
	 * @throws IOException
	 */
	public String checkPid(HttpServletRequest req, HttpServletResponse res) throws IOException{
		PrintWriter out = res.getWriter();
		String inPid = req.getParameter("inPid");
		Permission p = iPermission.getPermissionById(Integer.parseInt(inPid));
		String data = "";
		if(p!=null){
			data = "{\"msg\":\"T\"}";
		}
		else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		return null;
	}
	public String checkRid(HttpServletRequest req, HttpServletResponse res) throws IOException{
		PrintWriter out = res.getWriter();
		String inRid = req.getParameter("inRid");
		Role r = iRole.getRoleByRoleId(Integer.parseInt(inRid));
		String data = "";
		if(r!=null){
			data = "{\"msg\":\"T\"}";
		}
		else{
			data = "{\"msg\":\"F\"}";
		}
		out.print(data);
		out.close();
		return null;
	}
}
