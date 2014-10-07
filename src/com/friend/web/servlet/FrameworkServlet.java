package com.friend.web.servlet;

/**
 * @author 梅刚 2014-7-16 15:17
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.friend.util.DaoFactory;
import com.friend.util.LogUtil;
import com.friend.util.ParamUtil;
import com.friend.util.Property;
import com.friend.util.StringUtil;
import com.friend.util.XmlParseUtil;

public abstract class FrameworkServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//编码过滤
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String url = request.getRequestURL().toString() +"?"+ request.getQueryString();
		//System.out.println(new Date().toLocaleString() +":"+ url);
		//LogUtil.logger.info(url);
		runMethod(request, response);
	}
	/**
	 * 1.分析url得到要执行的方法名
	 * @param r
	 */
	public String getActionNameFromUrl(HttpServletRequest r){
		return r.getParameter("action");
	}
	/**
	 * 根据方法名，使用反射得到该方法对象
	 * @param r
	 * @return
	 */
	public Method getMethodByActionName(HttpServletRequest r){
		Method m = null;
		try {
			m = this.getClass().getMethod(getActionNameFromUrl(r), new Class[]{HttpServletRequest.class,HttpServletResponse.class});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	/**
	 * 使用invoke来运行要执行的Method对象
	 * @param r
	 */
	public void runMethod(HttpServletRequest r,HttpServletResponse resp){
		Method m = getMethodByActionName(r);
		if(m==null){
			//如果请求的方法不存在
			try {
				PrintWriter out = resp.getWriter();
				String errorHtml = "<head><meta charset='utf-8'/><title>404</title></head><h2><center>404</center></h2>"
						+ "<hr/><center>您输入的请求有误，无法访问到资源，请重新输入!!</center>";
				out.print(errorHtml);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String res;
			try {
				res = (String) m.invoke(this, r,resp);
				if(res!=null){
					r.getRequestDispatcher(res).include(r, resp);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 封装表单
	 * @param r
	 * @param obj
	 * @param entityXml
	 */
	public void packageForm(HttpServletRequest r, Object obj,String entityXml) {
		Method method = null;
		try {
			String basePath = DaoFactory.CLASSPATH +"/"+ ParamUtil.PROJECT_VO_PACKAGE +"/";
			List<Property> list = XmlParseUtil.getVoForm(basePath +entityXml);
			for(Property p : list){
				String voProperty = p.getVoProperty();
				String type = p.getType();
				//单属性值
				if(type!=null){
					if(type.equals("int") && r.getParameter(voProperty)!=null){
						method = obj.getClass().getMethod("set"+StringUtil.getFirstUpperString(voProperty), int.class);
						method.invoke(obj,Integer.parseInt(r.getParameter(voProperty).toString().trim()));
						LogUtil.logger.info("jsp请求的简单数据封装到对象中,反射方法:"+method);
					}else if(type.equals("string") && r.getParameter(voProperty)!=null){
						method = obj.getClass().getMethod("set"+StringUtil.getFirstUpperString(voProperty), String.class);
						method.invoke(obj,r.getParameter(voProperty));
						LogUtil.logger.info("jsp请求的简单数据封装到对象中,反射方法:"+method);
					}else if(type.equals("date")){
						method = obj.getClass().getMethod("set"+StringUtil.getFirstUpperString(voProperty), Date.class);
						if(r.getParameter(voProperty)!=null)
							method.invoke(obj,StringUtil.stringConvertDate(r.getParameter(voProperty).toString().trim()));
						else
							method.invoke(obj,new Date());
						LogUtil.logger.info("jsp请求的简单数据封装到对象中,反射方法:"+method);
					}
				}
				
			}
		} catch (DocumentException |
				NoSuchMethodException | SecurityException 
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
