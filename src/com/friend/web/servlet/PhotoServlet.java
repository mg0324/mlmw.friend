package com.friend.web.servlet;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.friend.service.biz.AlbumService;
import com.friend.service.biz.PhotoService;
import com.friend.service.biz.UserService;
import com.friend.service.ibiz.IAlbum;
import com.friend.service.ibiz.IPhoto;
import com.friend.service.ibiz.IUser;
import com.friend.util.LogUtil;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.util.StringUtil;
import com.friend.vo.Album;
import com.friend.vo.Photo;
import com.friend.vo.User;

/**
 * @author 李跃磊 2014 7.16 19:15
 */
@WebServlet("/photo.do")
public class PhotoServlet extends FrameworkServlet {
	private IPhoto iPhoto;
	private IAlbum iAlbum;
	private Pager pager;
	
	//得到所查看的人
	private IUser iUser;
	
	private static final long serialVersionUID = 1L;
	public PhotoServlet(){
		iPhoto = new PhotoService();
		iAlbum = new AlbumService();
		iUser = new UserService();
		pager = new Pager();
	}
	
	/**
	 *  上传图片
	 * @param r
	 * @param resp
	 * @return 跳转的页面
	 * @throws ServletException
	 */
	public String uploadPhoto(HttpServletRequest r,HttpServletResponse resp) 
			throws Exception{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String userId = r.getParameter("userId");
		String albumId = r.getParameter("albumId");
		Photo photo = new Photo();
		List<FileItem> items;
		items = upload.parseRequest(r);
		for(FileItem item : items){
			LogUtil.logger.info("fileupload:"+item.toString());
			if(!item.isFormField()){
				String fileName = item.getName();
				photo.setPhotoName(fileName);
				photo.setUploadTime(new Date());
				String root = r.getSession().getServletContext().getRealPath("/");//D:\Tomcat 7.0\wtpwebapps\Friend\
				//上传到upload/userId/photo/2014-7-25/file.jpg
				String currentDate = StringUtil.getCurrentDateInDir();
				String extention = StringUtil.getFileExtention(fileName);
				String uploadDir = "upload/"+ userId + "/photo/" + currentDate + "/";
				String relativePath = uploadDir + UUID.randomUUID()+ extention;
				photo.setPhotoPath(relativePath);
				String serverDirPath = root + uploadDir;
				File dir = new File(serverDirPath);
				if(!(dir.isDirectory() && dir.exists())){
					dir.mkdirs();
				}
				File file = new File(root+relativePath);
				item.write(file);
			}
		}
		Album a = new Album();
		a.setAlbumId(Integer.parseInt(albumId));
		photo.setAlbum(a);
		//把photo写到t_photo表中
		iPhoto.uploadPhoto(photo);
		r.setAttribute("photo_info", "上传照片成功!");
		return "frontClient/album/albumList.jsp";
	}
	
	/**
	 * 删除照片
	 * @param r
	 * @param resp
	 * @return 获得跳转的jsp页面名称
	 * @throws ServletException 
	 */
	public String deletePhoto(HttpServletRequest r,HttpServletResponse resp) throws ServletException{
		
		iPhoto.deletePhoto(this.makePhotoFromRequestById(r).getPhotoId());
		int currentPage=1;
		if(r.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(r.getParameter("currentPage").toString());
		int totalCount = 0;
		totalCount=iPhoto.countPhoto(this.makeAlbumFromRequest(r));
		pager.paging(currentPage, 8, totalCount);
		r.setAttribute("photoList",iPhoto.browseAlbum(this.makeAlbumFromRequest(r),pager));
		r.setAttribute("album",iAlbum.getAlbumById(Integer.parseInt(r.getParameter("albumId"))));
		r.setAttribute("page", pager);
		return "frontClient/album/photolist.jsp";
	}
	
	/**
	 * 移动照片
	 * @param r
	 * @param resp
	 * @return 获得跳转的jsp页面名称
	 * @throws ServletException
	 */
	public String movePhoto(HttpServletRequest r,HttpServletResponse resp) throws ServletException{
		Photo photo = new Photo();
		photo =this.makePhotoFromRequestById(r);
		Album album = new Album();
		album =this.makeAlbumFromRequest(r);
		r.setAttribute("album", album);
		if(iPhoto.movePhoto(photo.getPhotoId(), album)){
			return "frontClient/album/dealwith.jsp";
		}
		return null;
	}
	
	/**
	 * 浏览相册内的图片
	 * @param r
	 * @param resp
	 * @return 
	 * @throws ServletException
	 */
	public String browseAlbum(HttpServletRequest r,HttpServletResponse resp) throws ServletException{
		int currentPage=1;
		if(r.getParameter("currentPage")!=null)
			currentPage=Integer.parseInt(r.getParameter("currentPage").toString());
		int totalCount = 0;
		totalCount=iPhoto.countPhoto(this.makeAlbumFromRequest(r));
		pager.paging(currentPage, 8, totalCount);
		User friend = null;
		if(r.getParameter("userId")!=null)
			friend=iUser.getUserById(Integer.parseInt(r.getParameter("userId").toString()));
		r.setAttribute("photoList",iPhoto.browseAlbum(this.makeAlbumFromRequest(r),pager));
		r.setAttribute("album",iAlbum.getAlbumById(Integer.parseInt(r.getParameter("albumId"))));
		r.setAttribute("page", pager);
		r.setAttribute("friend", friend);
		if(r.getParameter("from")!=null)
			return "frontClient/friend/friendPhoto.jsp";
		else
			return "frontClient/album/photolist.jsp";
	}
	
	/**
	 * 从request中得到相册信息
	 * @param r
	 * @return 相册对象
	 * @throws ServletException
	 */
	public Album makeAlbumFromRequest(HttpServletRequest r)throws ServletException{
		Album album = new Album();
		album.setAlbumId(Integer.parseInt(r.getParameter("albumId")));
		return album;
	}
	
	/**
	 * 从request中得到照片的信息
	 * @param r
	 * @return 照片对象
	 */
	public Photo makePhotoFromRequest(HttpServletRequest r){
		Photo photo = new Photo();
		User user = new User();
		Album album = new Album();
		if(r.getParameter("userId")!=null)
			user.setUserId(Integer.parseInt(r.getParameter("userId")));
		if(r.getParameter("albumId")!=null)
			album.setAlbumId(Integer.parseInt(r.getParameter("albumId")));
		if(r.getParameter("photoName")!=null)
			photo.setPhotoName(r.getParameter("photoName"));
		if(r.getParameter("photoDesc")!=null)
			photo.setPhotoDesc(r.getParameter("photoDesc"));
		if(r.getParameter("photoPath")!=null)
			photo.setPhotoDesc(r.getParameter("photoPath"));
		photo.setUploadTime(new Date());
		photo.setUser(user);
		photo.setAlbum(album);
		return photo;
	}
	
	/**
	 * 根据照片的id得到照片对象
	 * @param r
	 * @return 照片对象
	 */
	public Photo makePhotoFromRequestById(HttpServletRequest r){
		Photo photo = new Photo();
		if(r.getParameter("photoId")!=null)
			photo.setPhotoId(Integer.parseInt(r.getParameter("photoId")));
		return photo;
	}

}
