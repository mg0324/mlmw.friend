package com.friend.service.biz;



import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.PhotoDao;
import com.friend.service.ibiz.IPhoto;
import com.friend.util.Pager;
import com.friend.vo.Album;
import com.friend.vo.Photo;
/**
 * @author 李跃磊 2014 7.16 17:32
 */
public class PhotoService implements IPhoto{
	private static PhotoDao photoDao = BaseDaoFactory.getPhotoDao();
	
	@Override
	public void uploadPhoto(Photo photo) {
		// TODO Auto-generated method stu
		
		photoDao.addPhoto(photo);
		
	}

	@Override
	public void deletePhoto(int id) {
		// TODO Auto-generated method stub
		
		Photo photo = photoDao.getPhotoById(id);
		photoDao.deletePhoto(photo);
	}
	
	@Override
	public boolean deletePhoto(Album album){
		return photoDao.deletePhoto(album);
	}
	
	@Override
	public boolean movePhoto(int id, Album album) {
		// TODO Auto-generated method stub
		
		Photo photo = new Photo();
		photo.setPhotoId(id);
		if(photoDao.updatePhoto(photo, album)){
			return true;
		}
		return false;
	}
	
	/**
	 * 查看相册图片
	 * @param album
	 * @return 照片集合
	 */
	@Override
	public List<Photo> browseAlbum(Album album,Pager pager) {
		// TODO Auto-generated method stub
		
		return photoDao.listPhoto(album,pager);
	}

	@Override
	public int countPhoto(Album album) {
		// TODO Auto-generated method stub
		
		return photoDao.count(album);
	}

	@Override
	public List<Photo> browseAlbumAllPhoto(Album album) {
		// TODO Auto-generated method stub
		
		return photoDao.listAllPhoto(album);
	}
}
