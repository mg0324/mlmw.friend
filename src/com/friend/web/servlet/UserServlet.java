package com.friend.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.friend.service.biz.FriendSortService;
import com.friend.service.biz.LogPraiseService;
import com.friend.service.biz.LogService;
import com.friend.service.biz.LogSortService;
import com.friend.service.biz.TalkService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IFriendSort;
import com.friend.service.ibiz.ILog;
import com.friend.service.ibiz.ILogPraise;
import com.friend.service.ibiz.ILogSort;
import com.friend.service.ibiz.ITalk;
import com.friend.service.ibiz.IUser;
import com.friend.util.MD5;
import com.friend.util.StringUtil;
import com.friend.util.ValidateCode;
import com.friend.vo.FriendSort;
import com.friend.vo.Log;
import com.friend.vo.LogSort;
import com.friend.vo.Talk;
import com.friend.vo.User;

/**
 * 
 * @author 梅刚 2014-7-21
 *
 */
@WebServlet("/user.do")
public class UserServlet extends FrameworkServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IUser iUser;
	private ITalk iTalk;
	private ILog iLog;
	private ILogSort iLogSort;
	private IFriendSort iFriendSort;
	private ILogPraise iLogPraise;
	public UserServlet(){
		iUser = new UserService();
		iFriendSort = new FriendSortService();
		iTalk = new TalkService();
		iLog = new LogService();
		iLogSort=new LogSortService();
		iLogPraise=new LogPraiseService();
	}
	/**
	 * 跳转到找回密码界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findPwdUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		return "frontClient/user/findPwd.jsp";
	}
	/**
	 * 找回密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findPwd(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		User user = makeUserFromRequest(request);
		String inCode = request.getParameter("inCode");
		String res_info = "";
		if(inCode.toUpperCase().equals(
				request.getSession().getAttribute(ValidateCode.RANDOMCODEKEY)
				.toString().toUpperCase()
				)){
			//验证码输入正确
			//给密码MD5加密
			user = iUser.findBackPassword(user);
			if(user.getPwdQuestion()!=null){
				request.getSession().setAttribute("backUser", user);
				return "frontClient/user/checkPwdSafe.jsp";
			}else{
				res_info = "很抱歉,您没有设置密保!";
				request.setAttribute("res_info", res_info);
				return "frontClient/user/findPwd.jsp";
			}
			
		}else{
			res_info = "验证码输入不正确!";
			request.setAttribute("res_info", res_info);
			request.setAttribute("res_user", user);
			return "frontClient/user/findPwd.jsp";
		}
		
	}
	/**
	 * 找回密码之提交密保
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findPwdBySafe(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		User user = makeUserFromRequest(request);
		User backUser = (User) request.getSession().getAttribute("backUser");
		if(user.getPwdAnswer().equals(backUser.getPwdAnswer())){
			//密保正确
			return "frontClient/user/updatePwd.jsp";
		}
		request.setAttribute("res_info", "密保回答不正确!");
		return "frontClient/user/checkPwdSafe.jsp";
	}
	/**
	 * 找回密码之重置密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findPwdResetPwd(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		User user = makeUserFromRequest(request);
		iUser.updatePassword(user, user.getPassword());
		request.setAttribute("res_info", "密码重置成功,请登录!");
		request.getSession().removeAttribute("backUser");
		return "frontClient/user/login.jsp";
	}
	/**
	 * 跳转到注册界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regUserUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		return "frontClient/user/register.jsp";
	}
	/**
	 * 跳转到登录界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		return "frontClient/user/login.jsp";
	}
	/**
	 * 跳转到首页
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toIndexUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		List<User> vips = iUser.getNewVIPs();
		request.setAttribute("vips", vips);
		return "frontClient/user/main.jsp";
	}
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regUser(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User inUser = makeUserFromRequest(request);
		String inCode = request.getParameter("inCode");
		//验证码比较不区分大小写
		if(inCode.toUpperCase().
				equals(request.getSession().
						getAttribute(ValidateCode.RANDOMCODEKEY)
						.toString().toUpperCase())){
			//给密码MD5加密
			inUser.setPassword(MD5.getMD5(inUser.getPassword()));
			User user = iUser.registerUser(inUser);
			if(user!=null){
				//注册成功并跳转到主页面
				FriendSort fs = new FriendSort();
				fs.setUser(user);
				fs.setSortName("我的好友");
				iFriendSort.createFriendSort(fs);
				fs.setSortName("黑名单");
				iFriendSort.createFriendSort(fs);
				LogSort logSort=new LogSort();
				logSort.setUser(user);
				logSort.setSortName("我的日志");
				iLogSort.createLogSort(logSort);
				request.getSession().setAttribute("user", user);
				return toIndexUI(request, response);
			}else{
				//注册失败
				request.setAttribute("res_user", inUser);
				return "frontClient/user/register.jsp";
			}
		}else{
			request.setAttribute("register_info", "*验证码输入有误*");
			request.setAttribute("res_user", inUser);
			return "frontClient/user/register.jsp";
		}
	}
	
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String warning = "";
		//判断验证码是否正确
		String inCode = request.getParameter("inCode");
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
				//判断该用户是否可用
				if(user.getStatus()==0){
					//不可用
					warning = "该账户不可用，请联系管理员激活";
					request.setAttribute("login_info", warning);
					request.setAttribute("res_user", inUser);
					return "frontClient/user/login.jsp";
				}else{
					request.getSession().setAttribute("user", user);
					return toIndexUI(request, response);
				}
				
			}else{
				warning = "*用户名或密码错误*";
				request.setAttribute("login_info", warning);
				request.setAttribute("res_user", inUser);
				return "frontClient/user/login.jsp";
			}
		}else{
			//不正确
			warning = "*验证码输入有误*";
			request.setAttribute("login_info", warning);
			request.setAttribute("res_user", inUser);
			return "frontClient/user/login.jsp";
		}
		
		
	}
	/**
	 * 
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
		return "frontClient/user/login.jsp";
		
	}
	
	/**
	 * 跳转到个人主页界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toSelfMainUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user=(User) request.getSession().getAttribute("user");
		if(user == null)
			return "frontClient/user/login.jsp";
		List<Talk> friendTalkList=iTalk.showFriendTalk(user);
		request.setAttribute("friendTalkList", friendTalkList);
		
		/* 由于fckeditor的内容无法正确压缩，所以这里就不显示好友的日志了
		 * List<Log> friendLogList = iLog.showFriendLog(user);
		//压缩日志内容，只显示50个字的内容
		List<Log> logs = new ArrayList<Log>();
		for(Log log : friendLogList){
			String content = log.getContent();
			content = StringUtil.cutImpContent(content);
			log.setContent(content);
			//LogPraise logPraise=null;
			//logPraise.setLog(log);
			//logPraise.setUser(user);
			//iLogPraise.isPraise(logPraise);
			logs.add(log);
		}
		request.setAttribute("friendLogList", logs);*/
		return "frontClient/user/selfMain.jsp";
	}
	
	/**
	 * 跳转到个人信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String toSelfInfoUI(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		return "frontClient/user/selfInfo.jsp";
		
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String fillSelfInfo(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = makeUserFromRequest(request);
		user = iUser.updateUserInfo(user);
		//更新session中的user
		request.getSession().setAttribute("user", user);
		request.setAttribute("alert_info", "完善信息成功");
		return "frontClient/user/selfInfo.jsp";
	}
	/**
	 * 更新出生地
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateHomeTown(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = makeUserFromRequest(request);
		//封装好homeTown
		user.setHomeTown(request.getParameter("province")+"-"+
				request.getParameter("city")+"-"+
				request.getParameter("town"));
		
		user = iUser.updateHomeTown(user);
		//更新session中的user
		request.getSession().setAttribute("user", user);
		request.setAttribute("alert_info", "出生地修改成功");
		return "frontClient/user/selfInfo.jsp";
	}
	
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = makeUserFromRequest(request);
		String newPassword = request.getParameter("newPassword");
		//md5加密
		user.setPassword(MD5.getMD5(user.getPassword()));
		newPassword = MD5.getMD5(newPassword);
		user = iUser.updatePassword(user, newPassword);
		//更新session中的user
		request.getSession().setAttribute("user", user);	
		request.setAttribute("alert_info", "密码修改成功");
		return "frontClient/user/selfInfo.jsp";
	}
	/**
	 * 设置密保
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String setPwdSafe(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = makeUserFromRequest(request);
		iUser.setPasswordSafe(user);
		request.setAttribute("alert_info", "密保设置成功");
		return "frontClient/user/selfInfo.jsp";
	}
	
	
	/**
	 * 上传头像
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String uploadHead(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = new User();
		String res_info = "";
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> items= upload.parseRequest(request);
			for(FileItem item : items){
				if(item.isFormField()){
					String fieldName = item.getFieldName();
					if(fieldName.equals("userId")){
						user.setUserId(Integer.parseInt(item.getString()));
					}else if(fieldName.equals("userId")){
						user.setUserId(Integer.parseInt(item.getString()));
					}
				}
				if(!item.isFormField()){
					//说明是上传的文件
					String fileName = item.getName();
					//大于2M的头像不予上传
					if(item.getSize() > 1024*1024*2){
						res_info += "图像文件超出2M,请换一张!\n";
					}
					//控制文件的拓展名，允许.jpg,.png,.gif的图片文件
					String fileExtention = StringUtil.getFileExtention(fileName);
					if(!(fileExtention.equals(".jpg")
					|| fileExtention.equals(".png")
					|| fileExtention.equals(".gif"))){
						//不允许上传
						res_info += "抱歉，不支持的头像类型。\n 暂时只支持.jpg,.png,.gif \n";
					}
					if(res_info.length()>0){
						//文件不满足要求
						request.setAttribute("alert_info", res_info);
						return "frontClient/user/selfInfo.jsp";
					}else{
						//开始上传文件--上传到服务器目录Friend/upload/userName/head/目录下
						String root = request.getSession().getServletContext().getRealPath("/");//D:\Tomcat 7.0\wtpwebapps\Friend\
						root = StringUtil.replaceRightLine(root);
						String dbFilePath = "upload/" +user.getUserId() + "/head/";
						String uploadDir = root + dbFilePath;
						//创建目录
						File dir = new File(uploadDir);
						if(!dir.isDirectory()){
							dir.mkdirs();
						}
						//得到uuid的文件名，防止重名
						String uuidFileName = UUID.randomUUID().toString() + fileExtention;
						String filePath = uploadDir + uuidFileName;
						dbFilePath = dbFilePath + uuidFileName;
						File file = new File(filePath);
						try {
							//上传到服务器
							item.write(file);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						user.setHead(dbFilePath);
						
					}
				}
				
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将文件路径存储到数据库中
		user = iUser.uploadHead(user);
		if(user!=null){
			res_info = "图像上传成功!";
		}
		//更新session中的user
		request.getSession().setAttribute("user", user);	
		request.setAttribute("alert_info", res_info);
		return "frontClient/user/selfInfo.jsp";
	
	}
	
	
	
	
	
	
	
	/**
	 * 从request请求中封装得到User
	 * @param r
	 * @return
	 */
	public User makeUserFromRequest(HttpServletRequest r){
		User u = new User();
		packageForm(r, u,"User.xml");
		return u;
	}
	
}
