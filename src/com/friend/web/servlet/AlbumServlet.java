package com.friend.web.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friend.service.biz.AlbumService;
import com.friend.service.biz.PhotoService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IAlbum;
import com.friend.service.ibiz.IPhoto;
import com.friend.service.ibiz.IUser;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.vo.Album;
import com.friend.vo.User;

/**
 * @author 李跃磊 2014 7.16 19：20
 */
@WebServlet("/album.do")
public class AlbumServlet extends FrameworkServlet {
	private IAlbum iAlbum;
	private IPhoto iPhoto;
	private Pager pager;
	
	//得到所要查看的用户
	private IUser iUser;
	
	private static final long serialVersionUID = 1L;
	public AlbumServlet(){
		iAlbum = new AlbumService();
		iPhoto = new PhotoService();
		iUser = new UserService();
		pager = new Pager();
	}

	/**
	 * 创建相册
	 * @param r
	 * @param res
	 * @return 跳转到页面
	 * @throws ServletException
	 */
	public String createAlbum(HttpServletRequest r,HttpServletResponse res) throws ServletException{
		 iAlbum.createAlbum(this.makeAlbumFromRequest(r));
		 return "frontClient/album/dealwith.jsp";
		 }
	
	/**
	 * 查看相册
	 * @param r
	 * @param res
	 * @return 跳转到页面
	 * @throws ServletException
	 */
	public String browseAlbum(HttpServletRequest r,HttpServletResponse res) throws ServletException{
			
		List<Album> albumList = new ArrayList<Album>();
		User lookUser = this.makeUserFromRequest(r);
		User friend = iUser.getUserById(Integer.parseInt(r.getParameter("userId").toString()));
		int currentPage=1;
		if(r.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(r.getParameter("currentPage").toString());
		int totalCount=iAlbum.getAlbumCount(lookUser);
		pager.paging(currentPage, ParamUtil.ALBUM_PAGE_SIZE, totalCount);
		List<Album> list = iAlbum.browseAlbum(lookUser,pager);
		for(Album album: list){
			album.setPhotoList(iPhoto.browseAlbumAllPhoto(album));
			albumList.add(album);
		}
		r.setAttribute("albumList",albumList );
		r.getSession().setAttribute("albums", albumList );
		r.setAttribute("friend", friend);
		User loginUser = (User) r.getSession().getAttribute("user");
		if(loginUser == null){
			return "frontClient/user/login.jsp";
		}
		r.setAttribute("page",pager);
		if(lookUser.getUserId() == loginUser.getUserId()){
			return "frontClient/album/albumList.jsp";
		}else{
			return "frontClient/friend/friendAlbum.jsp";
		}
	}
	
	/**
	 * 删除相册
	 * @param r
	 * @param res
	 * @return
	 * @throws ServletException
	 */
	public String deleteAlbum(HttpServletRequest r,HttpServletResponse res) throws ServletException{
		
		Album album = new Album();
		if(r.getParameter("albumId")!=null)
			album.setAlbumId(Integer.parseInt(r.getParameter("albumId")));
		iPhoto.deletePhoto(album);		
		iAlbum.deleteAlbum(this.makeAlbumFromRequestById(r).getAlbumId());
		r.setAttribute("albumId",Integer.parseInt(r.getParameter("albumId")));
		return "frontClient/album/dealwith.jsp";
	}
	
	/**
	 * 从Request信息中得到相册对象
	 * @param r
	 * @return 相册对象
	 * @throws ServletException
	 */
	public Album makeAlbumFromRequest(HttpServletRequest r)throws ServletException{
		Album album = new Album();
		User user = new User();
		if(r.getParameter("userId")!=null)
			user.setUserId(Integer.parseInt(r.getParameter("userId")));
		if(r.getParameter("albumName")!=null)
			album.setAlbumName(r.getParameter("albumName"));
		if(r.getParameter("albumDesc")!=null)
			album.setAlbumDesc(r.getParameter("albumDesc"));
		album.setCreateTime(new Date());
		album.setUser(user);
		return album;
	}
	
	/**
	 * 从request中得到用户对象
	 * @param r
	 * @return 用户对象
	 * @throws ServletException
	 */
	public User makeUserFromRequest(HttpServletRequest r)throws ServletException{
		User user = new User();
		user.setUserId(Integer.parseInt(r.getParameter("userId")));  
		return user;
	}
	
	/**
	 * 得到相册Id把信息封装到一个Album对象
	 * @param r
	 * @return 相册对象
	 */
	public Album makeAlbumFromRequestById(HttpServletRequest r)	{
		Album album = new Album();
		if(r.getParameter("albumId")!=null)
		album.setAlbumId(Integer.parseInt(r.getParameter("albumId")));
		return album;
	}
	
}
