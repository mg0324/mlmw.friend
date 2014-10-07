/**
 * 
 */
package com.friend.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.PermissionService;
import com.friend.service.biz.RolePermissionService;
import com.friend.service.biz.RoleService;
import com.friend.service.ibiz.IPermission;
import com.friend.service.ibiz.IRole;
import com.friend.service.ibiz.IRolePermission;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.vo.Permission;
import com.friend.vo.Role;
import com.friend.vo.RolePermission;

/**
 * @author szh
 *
 */
@WebServlet("/role.do")
public class RoleServlet extends FrameworkServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IRole iRole;
	private Pager page;
	private IRolePermission iRolePermission;
	private IPermission iPermission;
	
	public RoleServlet(){
		iRole=new RoleService();
		page=new Pager();
		iRolePermission=new RolePermissionService();
		iPermission = new PermissionService();
	}
	
	public String toRoleUI(HttpServletRequest req,HttpServletResponse res){
		int currentPage=1;
		if(req.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(req.getParameter("currentPage").toString());
		int totalCount=iRole.getAllRoleCount();
		page.paging(currentPage, ParamUtil.ROLE_PAGE_SIZE, totalCount);
		
		List<Role> roleList=iRole.showAllRole(page);
		req.setAttribute("roleList", roleList);
		req.setAttribute("page", page);
		return "backClient/role/allRole.jsp";
	}
	
	public String toRoleInfo(HttpServletRequest req,HttpServletResponse res){
		List<Permission> permissionList=iPermission.listPermission();
		Role role = null;
		if(req.getParameter("roleId")!=null)
			role=iRole.getRoleByRoleId(Integer.parseInt(req.getParameter("roleId").toString()));
		List<Permission> rolePermissionList=iRolePermission.getRolePermissionList(role);
		
		req.setAttribute("permissionList", permissionList);
		req.setAttribute("rolePermissionList", rolePermissionList);
		req.setAttribute("role", role);
		
		return "backClient/role/roleInfo.jsp";
	}
	
	public String updateRole(HttpServletRequest req,HttpServletResponse res){
		String[] s=req.getParameterValues("rolePermission");
		String ss="";
		if(s!=null){
			for(int i=0;i<s.length;i++){
				ss+=s[i];
				if(i!=s.length-1)
					ss+=";";
			}
		}
		Role role=iRole.getRoleByRoleId(Integer.parseInt(req.getParameter("roleId").toString()));
		role.setRoleName(req.getParameter("roleName"));
		role.setRoleGrade(req.getParameter("roleGrade"));
		RolePermission rp=new RolePermission();
		rp.setRole(role);
		rp.setPermisssionIds(ss);
		iRole.updateRole(role);
		if(iRolePermission.updateRolePermission(rp)){
			req.setAttribute("res_message", "修改成功");
		}
		
		return toRoleUI(req, res);
	}
	
	public String addRole(HttpServletRequest req,HttpServletResponse res){
		String[] s=req.getParameterValues("rolePermission");
		String ss="";
		if(s!=null){
			for(int i=0;i<s.length;i++){
				ss+=s[i];
				if(i!=s.length-1)
					ss+=";";
			}
		}
		Role role=new Role();
		role.setRoleId(Integer.parseInt(req.getParameter("roleId").toString()));
		role.setRoleName(req.getParameter("roleName"));
		role.setRoleGrade(req.getParameter("roleGrade"));
		iRole.addRole(role);
		RolePermission rp=new RolePermission();
		rp.setRole(role);
		rp.setPermisssionIds(ss);
		if(iRolePermission.addRolePermission(rp)){
			req.setAttribute("res_message", "添加成功");
		}
		return toRoleUI(req, res);
	}
	
	public String toAddRole(HttpServletRequest req,HttpServletResponse res){
		List<Permission> permissionList=iPermission.listPermission();
		
		req.setAttribute("permissionList", permissionList);
		
		return "backClient/role/addRole.jsp";
	}
	
	public String deleteRole(HttpServletRequest req,HttpServletResponse res){
		Role role=iRole.getRoleByRoleId(Integer.parseInt(req.getParameter("roleId").toString()));
		if(iRole.deleteRole(role)){
			req.setAttribute("res_message", "删除成功");
		}
		
		return toRoleUI(req, res);
	}
}
