package com.friend.web.servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.ModuleService;
import com.friend.service.biz.TopicService;
import com.friend.service.ibiz.IModule;
import com.friend.service.ibiz.ITopic;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.vo.Module;
import com.friend.vo.User;

/**
 * Servlet implementation class ModuleServlet
 */
@WebServlet("/module.do")
public class ModuleServlet extends FrameworkServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private Pager page;
	private IModule iModule;
	private ITopic iTopic;
	public ModuleServlet(){
		iModule = new ModuleService();
		iTopic = new TopicService();
	   page=new Pager();
	}
       
   //创建模块
    public String createModule(HttpServletRequest request, HttpServletResponse response)
    {
    	Module module=new Module();
    	User user=new User();
    	System.out.println("create");
    	String moduleName=request.getParameter("moduleName");
    
    	String userId=request.getParameter("userId");
    	user.setUserId(Integer.parseInt(userId));
    	module.setModuleName(moduleName);
    	module.setUser(user);
    	iModule.createModule( module);
    	try {
    		if(request.getParameter("toWhere")==null)
    			response.sendRedirect("module.do?action=findAllModule");
    		else
    			response.sendRedirect("module.do?action=findAllModule&toWhere=back");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    //删除模块
    public void deleteModule(HttpServletRequest request, HttpServletResponse response)
    {
    	Module module=new Module();
    	String id=request.getParameter("id");
    	module.setModuleId(Integer.parseInt(id));
    	iModule.deleteModule(module);
    	try {
    		if(request.getParameter("toWhere")==null)
    			response.sendRedirect("module.do?action=findAllModule");
    		else
    			response.sendRedirect("module.do?action=findAllModule&toWhere=back");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //罗列模块
	@SuppressWarnings("unchecked")
	public String findAllModule(HttpServletRequest request, HttpServletResponse response)
	{ 
			int currentPage=1;
			if(request.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
			int totalCount=iModule.getModuleCount();
			page.paging(currentPage, ParamUtil.TOPIC_PAGE_SIZE, totalCount);
//			List<Topic> topics=iTopic.makeTopicListFromModule(module,page);
//	    	  request.setAttribute("listTopics", topics);
//	    	  request.setAttribute("module", module);
			List<Module> modules = iModule.findAllModule(page);
			List<Module> newModules = new ArrayList<Module>();
		 request.setAttribute("page",page);
		for(Module module : modules){
			module.setTopicCount(iTopic.getTopicCountInModule(module));
			newModules.add(module);
		}
		request.setAttribute("listModules", newModules);
		
		if(request.getParameter("toWhere")!=null)
		{
			
			return "backClient/bbs/b_module.jsp";
		}
		else
			return "frontClient/bbs/module.jsp";
	}

}
