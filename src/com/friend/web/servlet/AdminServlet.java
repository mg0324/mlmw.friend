package com.friend.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.PermissionService;
import com.friend.service.biz.RoleService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IPermission;
import com.friend.service.ibiz.IRole;
import com.friend.service.ibiz.IUser;
import com.friend.util.MD5;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.util.ValidateCode;
import com.friend.vo.Permission;
import com.friend.vo.Role;
import com.friend.vo.User;

/**
 * 
 * @author 梅刚 2014-8-6
 * 后台的userServlet
 *
 */
@WebServlet("/admin.do")
public class AdminServlet extends FrameworkServlet{
	private IUser iUser;
	private IRole iRole;
	private IPermission iPermission;
	private Pager page;
	public AdminServlet(){
		iUser = new UserService();
		iPermission = new PermissionService();
		iRole = new RoleService();
		page = new Pager();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*******************后台**********************/
	public String toBackIndexUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "backClient/login.jsp";
	}
	
	public String login(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String warning = "";
		//判断验证码是否正确
		String inCode = request.getParameter("inCode");
		if(inCode==null){
			warning = "*验证码输入有误*";
			request.setAttribute("login_info", warning);
			return "backClient/login.jsp";
		}
		User inUser = makeUserFromRequest(request);
		if(inCode.toUpperCase().
				equals(request.getSession().
						getAttribute(ValidateCode.RANDOMCODEKEY)
						.toString().toUpperCase())){
			//给密码MD5加密
			inUser.setPassword(MD5.getMD5(inUser.getPassword()));
			User user = iUser.login(inUser);
			//正确
			if(user!=null){
				boolean isAdmin = false;
				//输入都正确，判断是否有管理员权限
				for(Role role : user.getRoleList()){
					if(Integer.parseInt(role.getRoleGrade())==4){
						//是超级管理员
						isAdmin = true;
						user.setAdminGrade(4);//4-表示是超级管理员
					}else if(Integer.parseInt(role.getRoleGrade())==3){
						isAdmin = true;
						if(role.getRoleId()==102){
							//用户管理员
							user.setAdminGrade(3);//3-表示是用户管理员
						}else if(role.getRoleId()==103){
							//系统管理员
							user.setAdminGrade(2);//2-表示是系统管理员
						}else if(role.getRoleId()==104){
							//论坛管理员
							user.setAdminGrade(1);//1-表示是论坛管理员
						}
					}
				}
				if(isAdmin){
					request.getSession().setAttribute("user", user);
					return "backClient/main.jsp";
				}else{
					warning = "*该用户没有权限*";
					request.setAttribute("login_info", warning);
					request.setAttribute("res_user", inUser);
					return "backClient/login.jsp";
				}
				
			}else{
				warning = "*用户名或密码错误*";
				request.setAttribute("login_info", warning);
				request.setAttribute("res_user", inUser);
				return "backClient/login.jsp";
			}
		}else{
			//不正确
			warning = "*验证码输入有误*";
			request.setAttribute("login_info", warning);
			request.setAttribute("res_user", inUser);
			return "backClient/login.jsp";
		}
		
		
	}
	/**
	 * 管理员注销
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			//将session清空
			request.getSession().removeAttribute("user");
			request.getSession().invalidate();
		}
		return "backClient/login.jsp";
		
	}
	/**
	 * 屏蔽用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String toHiddenUserUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int currentPage=1;
		if(request.getParameter("currentPage")!=null){
		 currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
		}
		int totalCount=iUser.countUser();
		page.paging(currentPage, ParamUtil.TOPIC_PAGE_SIZE, totalCount);
		List<User> users=iUser.findAllUsers(page,"status");
		request.setAttribute("users",users);
		request.setAttribute("page",page);
		return "backClient/user/hideUser.jsp";
		
	}
	//用于屏蔽用户
	public String hideUser(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		int userId=Integer.parseInt(request.getParameter("userId"));
		User user=iUser.getUserById(userId);
		iUser.hideUser(user);
		return toHiddenUserUI(request, response);
	}
	//用于用户的解除屏蔽
	public String activeUser(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		int userId=Integer.parseInt(request.getParameter("userId"));
		User user=iUser.getUserById(userId);
		iUser.activeUser(user);
		return toHiddenUserUI(request, response);
		
	}
	/**
	 * 到后台首页
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toBackUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		return "backClient/welcome.jsp";
	}
	public String toAdminManageUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//到下级管理员管理界面角色授权 和 解除角色
		List<Role> roles = iRole.getAllRoles();
		//查询出是管理员的用户到集合中
		List<User> admins = iUser.getAllAdmins();
		
		request.setAttribute("roles",roles);
		request.setAttribute("admins",admins);
		return "backClient/user/adminManage.jsp";
	}
	public String toPermissionUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int totalCount = iPermission.getPermissionCount();
		page.paging(currentPage, ParamUtil.PERMISSION_PAGE_SIZE, totalCount);
		List<Permission> permissions = iPermission.listPermission(page);
		request.setAttribute("permissions", permissions);
		request.setAttribute("page", page); 
		return "backClient/user/permission.jsp";
	}
	/**
	 * 修改权限
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePermission(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Permission p = makePermissionFromRequest(request);
		if(iPermission.updatePermission(p)){
			request.setAttribute("res_message", "修改成功");
		}
		return toPermissionUI(request, response);
	}
	/**
	 * 增加权限
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPermission(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Permission p = makePermissionFromRequest(request);
		if(iPermission.addPermission(p)){
			request.setAttribute("res_message", "添加成功");
		}
		return toPermissionUI(request, response);
	}
	//删除
	public String deletePermission(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Permission p = makePermissionFromRequest(request);
		if(iPermission.deletePermission(p)){
			request.setAttribute("res_message", "删除成功");
		}
		return toPermissionUI(request, response);
	}
	

	/**
	 * 创建下级管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String createNewManager(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User admin = makeUserFromRequest(request);
		admin.setPassword(MD5.getMD5(admin.getPassword()));
		String roleId = request.getParameter("roleId");
		System.out.println(admin.getUserName()+":"+admin.getPassword()+":"+roleId);
		Role role = iRole.getRoleByRoleId(Integer.parseInt(roleId));
		if(iUser.createAdminstor(admin,role)){
			//创建成功
			request.setAttribute("res_info", "下级管理员创建成功");
		}
		
		
		return toAdminManageUI(request, response);
	}
	/**
	 * 变更管理员的角色
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String changeRole(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User admin = makeUserFromRequest(request);
		String[] roleIds = request.getParameterValues("roleId");
		admin = iUser.getUserById(admin.getUserId());
		List<Role> roles = new ArrayList<Role>();
		for(String roleId : roleIds){
			Role role = iRole.getRoleByRoleId(Integer.parseInt(roleId));
			roles.add(role);
		}
		if(iUser.changeRole(admin,roles)){
			//完成角色变更
			request.setAttribute("res_info", "角色变更操作成功");
		}
		return toAdminManageUI(request, response);
	}
	
	private User makeUserFromRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		User user = new User();
		if(request.getParameter("userId")!=null)
			user.setUserId(Integer.parseInt(request.getParameter("userId")));
		if(request.getParameter("userName")!=null)
			user.setUserName(request.getParameter("userName"));
		if(request.getParameter("password")!=null)
			user.setPassword(request.getParameter("password"));
		return user;
	}
	/**
	 * 从request中封装permission
	 * @param request
	 * @return
	 */
	private Permission makePermissionFromRequest(HttpServletRequest r) {
		// TODO Auto-generated method stub
		Permission p = new Permission();
		if(r.getParameter("permissionId")!=null)
			p.setPermissionId(Integer.parseInt(r.getParameter("permissionId")));
		if(r.getParameter("permissionName")!=null)
			p.setPermissionName(r.getParameter("permissionName"));
		if(r.getParameter("permissionNote")!=null)
			p.setPermissionNote(r.getParameter("permissionNote"));
		if(r.getParameter("permissionType")!=null)
			p.setPermissionType(r.getParameter("permissionType"));
		return p;
	}
	
}
