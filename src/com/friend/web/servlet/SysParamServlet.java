package com.friend.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author 梅刚 2014-8-7
 * 后台系统参数管理的servlet
 */
@WebServlet("/sysParam.do")
public class SysParamServlet extends FrameworkServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String toSysSettingUI(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "backClient/sys/sysSetting.jsp";
	}

}
