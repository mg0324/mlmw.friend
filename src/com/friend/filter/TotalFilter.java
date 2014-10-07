package com.friend.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.friend.util.DaoFactory;
import com.friend.util.LogUtil;
import com.friend.vo.User;
/**
 * 过滤器
 * @author 梅刚 2014-7-26
 *
 */
@WebFilter("*.do")
public class TotalFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest r = (HttpServletRequest) req;
		HttpServletResponse resp = (HttpServletResponse) res;
		r.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String url = r.getRequestURL().toString() +"?"+ r.getQueryString();
		//filterNoLoginUser(r, resp);
		//放过不用登陆就可以访问的方法。
		boolean allowGo = allowPath(r);
		if(!allowGo){
			filterNoLoginUser(r, resp);
		}
		chain.doFilter(req, resp);
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		//初始化log4j环境
		String classPath = arg0.getServletContext().getRealPath("/WEB-INF/classes");
		DaoFactory.CLASSPATH = classPath;
		LogUtil.logger = Logger.getLogger(TotalFilter.class);
		PropertyConfigurator.configure(classPath + "/log4j.properties");
	}
	/**
	 * 过滤没有登录的用户
	 * @param r
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void filterNoLoginUser(HttpServletRequest r,HttpServletResponse resp) 
			throws ServletException, IOException{
		User u = (User) r.getSession().getAttribute("user");
		if(u==null){
			//用户没有登录
			LogUtil.logger.info("拦截到未登陆用户");
			r.getRequestDispatcher("user.do?action=loginUI").forward(r, resp);
		}
	}
	/**
	 * 放过一些直接可以访问的链接
	 * 如：注册，登陆，忘记密码，和后台入口。
	 * @param r
	 * @return
	 */
	private boolean allowPath(HttpServletRequest r) {
		String[] actionNames = {"regUser","login","findPwd","logout","toBackIndexUI"};
		String actionTarget = r.getParameter("action");
		boolean allowGo = false;
		for(String an : actionNames){
			if(actionTarget.contains(an)){
				allowGo = true;
				break;
			}
		}
		return allowGo;
	}
}
