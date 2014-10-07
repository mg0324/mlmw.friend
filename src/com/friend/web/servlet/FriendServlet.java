package com.friend.web.servlet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.FriendService;
import com.friend.service.biz.FriendSortService;
import com.friend.service.biz.TalkService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IFriend;
import com.friend.service.ibiz.IFriendSort;
import com.friend.service.ibiz.ITalk;
import com.friend.service.ibiz.IUser;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.vo.Friend;
import com.friend.vo.FriendSort;
import com.friend.vo.Talk;
import com.friend.vo.User;

/**
 * 
 * @author 周皓 2014/07/21 23:18
 *
 */
@WebServlet("/friend.do")
public class FriendServlet extends FrameworkServlet {
	private static final long serialVersionUID = 1L;
	private IFriend iFriend;
	private IFriendSort iFriendSort;
	private Pager page;
	private IUser iUser;
	
	//显示好友主页的说说
	private ITalk iTalk;
	
	public FriendServlet(){
		iFriend = new FriendService();
		iFriendSort = new FriendSortService();
		page = new Pager();
		iUser = new UserService();
		iTalk = new TalkService();
	}
		
	/**
	 * @param request
	 * @param response
	 * @return
	 */
	public String showFriendSort(HttpServletRequest request,HttpServletResponse response){
		User user = (User)request.getSession().getAttribute("user");
		if ( user != null)
		{
			int currentPage=1;
			if(request.getParameter("currentPage") != null)
				currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
			int totalCount=iFriendSort.getFriendSortCount(user);
			page.paging(currentPage, 5, totalCount);
			ArrayList<FriendSort> friendSorts = iFriendSort.findAllFriendSort(user, page);
			request.setAttribute("friendSorts", friendSorts);
			request.setAttribute("page", page);
			return "frontClient/friend/friendSorts.jsp";
		}
		return null;
	}
	
	public String createSort(HttpServletRequest request,HttpServletResponse response){
		//表单验证
		long token = Long.parseLong(request.getParameter("creToken"));
		long sessionToken = 0;
		if (request.getSession().getAttribute("creToken") != null)
			sessionToken = Long.parseLong(request.getSession().getAttribute("creToken") + "");
		if ( token == sessionToken){  //第一次提交
			FriendSort friendSort = makeFriendSortFromRequest(request);
			friendSort.setUser((User)request.getSession().getAttribute("user"));
			iFriendSort.createFriendSort(friendSort);
			request.getSession().setAttribute("creToken", System.currentTimeMillis());
		}
		return showFriendSort(request, response);
	}

	public String deleteSort(HttpServletRequest request,HttpServletResponse response){
		//表单验证
		long token = Long.parseLong(request.getParameter("delToken"));
		long sessionToken = 0;
		if (request.getSession().getAttribute("delToken") != null)
			sessionToken = Long.parseLong(request.getSession().getAttribute("delToken") + "");
		if ( token == sessionToken){  //第一次提交
			FriendSort friendSort = makeFriendSortFromRequest(request);
			iFriendSort.deleteFriendSort(friendSort);
			request.getSession().setAttribute("delToken", System.currentTimeMillis());
		}
		return showFriendSort(request, response);
	}
	
	public String reNameSort(HttpServletRequest request,HttpServletResponse response){
		//表单验证
		FriendSort friendSort = makeFriendSortFromRequest(request);
		String newName = request.getParameter("sortName");
		FriendSort newFriendSort = new FriendSort();
		newFriendSort.setSortName(newName);
		iFriendSort.renameFriendSort(friendSort, newFriendSort);
		return showFriendSort(request, response);
	}
	
	public String showFriends(HttpServletRequest request,HttpServletResponse response){
		//如果当前分组为黑名单时，拉黑按钮的显示问题 布局问题
		int friendSortId = Integer.parseInt(request.getParameter("sortId"));
		FriendSort fs = iFriendSort.getFriendSortById(friendSortId);
		if (fs.getFriendCount() == 0) return showFriendSort(request, response);
		int currentPage=1;
		if(request.getParameter("currentPage") != null)
			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
		int totalCount = fs.getFriendCount();
		page.paging(currentPage, 12, totalCount);
		ArrayList<User> users = iFriendSort.listAllUserFromFriendSort(fs, page);
		if ( users != null )
		{
			request.setAttribute("users", users);
			request.setAttribute("page", page);
			request.setAttribute("friendSort", fs);
			User user = (User)request.getSession().getAttribute("user");
			if (user != null)
			{
				ArrayList<FriendSort> friendSorts = iFriendSort.findAllFriendSort(user);
				for (int i = 0; i < friendSorts.size(); i++){//在移动分组下拉列表中剔除当前分组
					if (friendSorts.get(i).getFriendSortId() == fs.getFriendSortId()){
						friendSorts.remove(i);
						break;
					}
				}
				request.setAttribute("friendSorts", friendSorts);
			}
			return "frontClient/friend/friends.jsp";
		}
		return null;
	}
	
	public String setBlack(HttpServletRequest request,HttpServletResponse response){
		//好友被拉黑后的实际效果（如好友动态的显示顺序）
		User user = (User)request.getSession().getAttribute("user");
		int userFriendId = Integer.parseInt(request.getParameter("userId"));
		Friend friend = new Friend();
		friend.setUser(iUser.getUserById(user.getUserId()));
		friend.setUserFriend(iUser.getUserById(userFriendId));
		iFriend.setBlackFriend(friend);
		return showFriendSort(request, response);
	}
	
	public String goSearch(HttpServletRequest request,HttpServletResponse response){
		return "frontClient/friend/searchUser.jsp";
	}
	
	//新增方法
	public String searchByNickname(HttpServletRequest request,HttpServletResponse response){
		//在收缩结果中剔除本人和好友
		User terms = makeUserFromRequest(request);
		terms.setBirthday(null);
		User user = (User)request.getSession().getAttribute("user");
		if (request.getParameter("transcoding") != null){  //因为分页部分的提交方式为超链接，会导致中文乱码问题，所以这里要转码
			String nickName = terms.getUserNickName();
			try {
				nickName = new String(nickName.getBytes("ISO-8859-1"),"UTF-8");
				terms.setUserNickName(nickName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int totalCount = iFriend.getSearchResultCount(user, terms);
		page.paging(currentPage, 16, totalCount);
		List<User> users = iFriend.searchAllStrangerByNickname(user, terms, page);
		request.setAttribute("users", users);
		request.setAttribute("page", page);
		request.setAttribute("method", "searchByNickname");
		request.setAttribute("param", terms.getUserNickName());
		return "frontClient/friend/userList.jsp"; 
	}
	public String searchBySex(HttpServletRequest request,HttpServletResponse response){
		//在收缩结果中剔除本人和好友
		User terms = makeUserFromRequest(request);
		terms.setBirthday(null);
		User user = (User)request.getSession().getAttribute("user");
		if (request.getParameter("transcoding") != null){
			String sex = terms.getSex();
			try {
				sex = new String(sex.getBytes("ISO-8859-1"),"UTF-8");
				terms.setSex(sex); 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int totalCount = iFriend.getSearchResultCount(user, terms);
		page.paging(currentPage, 16, totalCount);
		List<User> users = iFriend.searchAllStrangerBySex(user, terms, page);
		request.setAttribute("users", users);
		request.setAttribute("page", page);
		request.setAttribute("method", "searchBySex");
		request.setAttribute("param", terms.getSex());
		return "frontClient/friend/userList.jsp"; 
	}
	public String searchByAge(HttpServletRequest request,HttpServletResponse response){
		//在收缩结果中剔除本人和好友
		User terms = new User();
		int age = Integer.parseInt(request.getParameter("age"));
		Date birthday = new Date();
		birthday.setYear(birthday.getYear() - age);
		terms.setBirthday(birthday); 
		User user = (User)request.getSession().getAttribute("user");
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int totalCount = iFriend.getSearchResultCount(user, terms);
		page.paging(currentPage, 16, totalCount);
		List<User> users = iFriend.searchAllStrangerByAge(user, terms, page);
		request.setAttribute("users", users);
		request.setAttribute("page", page);
		request.setAttribute("method", "searchByAge");
		request.setAttribute("param", age + "");
		return "frontClient/friend/userList.jsp"; 
	}
	public String searchByHomeTown(HttpServletRequest request,HttpServletResponse response){
		//在收缩结果中剔除本人和好友
		User terms = makeUserFromRequest(request);
		terms.setBirthday(null);
		User user = (User) request.getSession().getAttribute("user");
		if (request.getParameter("transcoding") != null){
			String homeTown = terms.getHomeTown();
			try {
				homeTown = new String(homeTown.getBytes("ISO-8859-1"),"UTF-8");
				terms.setHomeTown(homeTown);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int totalCount = iFriend.getSearchResultCount(user, terms);
		page.paging(currentPage, 16, totalCount);
		List<User> users = iFriend.searchAllStrangerByHomeTown(user, terms, page);
		request.setAttribute("users", users);
		request.setAttribute("page", page);
		request.setAttribute("method", "searchByHomeTown");
		request.setAttribute("param", terms.getHomeTown());
		return "frontClient/friend/userList.jsp"; 
	}
	
	public String addFriend(HttpServletRequest request,HttpServletResponse response){
		//互相添加，提示信息
		User user1 = (User)request.getSession().getAttribute("user");
		User user2 = makeUserFromRequest(request);
		iFriend.addFriend(user1, user2);
		iFriend.addFriend(user2, user1);
		return "frontClient/friend/searchUser.jsp";//showFriendSort(request, response);
	}
	
	public String moveTo(HttpServletRequest request,HttpServletResponse response){
		//在移动分组下拉列表中不显示当前分组名称
		FriendSort friendSort = makeFriendSortFromRequest(request);
		int userFriendId = Integer.parseInt(request.getParameter("userId"));
		User user = (User) request.getSession().getAttribute("user");
		User userFriend = new User();
		userFriend.setUserId(userFriendId);
		Friend friend = iFriend.getFriendByUserAndUserFriend(user, userFriend);
		iFriend.moveFriendSort(friend, friendSort);
		return showFriendSort(request, response);
	}/**/
	
	/**
	 * 好友主页
	 * @param request
	 * @param response
	 * @return
	 */
	public String toFriendMainUI(HttpServletRequest request, HttpServletResponse response){
		User user=(User) request.getSession().getAttribute("user");
		User friend=iUser.getUserById(Integer.parseInt(request.getParameter("userId")));
		int currentPage=1;
		if(request.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(request.getParameter("currentPage").toString());
		int totalCount=iTalk.getFocusFriendTalkCountByUser(friend);
		page.paging(currentPage, ParamUtil.TALK_PAGE_SIZE , totalCount);
		List<Talk> friendTalkList=iTalk.showFriendMainTalk(user, friend, page);
		request.setAttribute("friendTalkList", friendTalkList);
		request.setAttribute("page", page);
		request.setAttribute("friend", friend);
		return "frontClient/friend/friendMain.jsp";
	}
	
	/**
	 * 显示好友信息
	 * @param request
	 * @param response
	 * @return
	 */
	public String toFriendInfo(HttpServletRequest req,HttpServletResponse res){
		User friend=iUser.getUserById(Integer.parseInt(req.getParameter("userId")));
		req.setAttribute("friend", friend);
		return "frontClient/friend/friendInfo.jsp";
	}
	
	public FriendSort makeFriendSortFromRequest(HttpServletRequest r){
		FriendSort fs = new FriendSort();
		if (r.getParameter("sortId") != null)
			fs.setFriendSortId(Integer.parseInt(r.getParameter("sortId")));
		if (r.getParameter("sortName") != null)
			fs.setSortName(r.getParameter("sortName"));
		if (r.getParameter("friendCount") != null)
			fs.setFriendCount(Integer.parseInt(r.getParameter("friendCount")));

		return fs;
	}
	
	public Friend makeFriendFromRequest(HttpServletRequest request, HttpServletResponse response){
		Friend f = new Friend();
		if (request.getParameter("friendId") != null)
			f.setFriendId(Integer.parseInt(request.getParameter("friendId").toString()));
		
		return f;
	}
	
	public User makeUserFromRequest(HttpServletRequest r){
		User u = new User();
		packageForm(r, u,"User.xml");
		return u;
	}
}
