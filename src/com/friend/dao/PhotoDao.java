package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.LogUtil;
import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Album;
import com.friend.vo.Photo;

/**
 * 
 * @author 李跃磊 2014 7.16 19:29
 *
 */
public class PhotoDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Photo");
	}

	/**
	 * 增加图片
	 * @param photo 图片对象
	 * @return 获得增加图片Photo
	 */
	public Photo addPhoto(Photo photo){
		String sql = "insert into t_photo(photo_name,photo_desc,"
				+ "upload_time,photo_path,album_id)values(?,?,?,?,?)";
		Object[] params = {photo.getPhotoName(),
				photo.getPhotoDesc(),
				photo.getUploadTime(),
				photo.getPhotoPath(),
				photo.getAlbum().getAlbumId()};
		int a = update(sql,params);
		if(a>0){
			return photo;
		}
		return null;
	}
	
	/**
	 * 根据图片删除图片
	 * @param photo 图片对象
	 * @return 删除照片对象
	 */
	public Photo deletePhoto(Photo photo){
		String sql = "delete from t_photo where id=?";
		Object[] params = {photo.getPhotoId()};
		int n = update(sql,params);
		if(n>0){
			return photo;
		}
		return null;
	}
	
	/**
	 * 从相册表中删除
	 * @param album
	 * @return 删除是否成功
	 */
	public boolean deletePhoto(Album album){
		String sql="delete from t_photo where album_id=?";
		Object[] params={album.getAlbumId()};
		int n=update(sql,params);
		if(n>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 修改照片所在的相册
	 * @param photo
	 * @return 是否修改成功
	 */
	public boolean updatePhoto(Photo photo,Album album){
		String sql ="update t_photo set album_id=? where id=?";
		Object[] params = {album.getAlbumId(),photo.getPhotoId()};
		int n = update(sql,params);
		if(n>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据相册得到相册内的指定张数的照片对象Photo
	 * @param album 相册对象
	 * @param pager 
	 * @return 该相册内的图片集合
	 */
	public List<Photo> listPhoto(Album album, Pager pager){
		String sql = "select * from t_photo where album_id=? limit ?,?";
		Object[] params={album.getAlbumId(),
				((pager.getCurrentPage()-1)*pager.getPageSize()),
				pager.getPageSize()};
		
		 return makeListPhotoFromListMap(find(sql,params));
	}
	
	/**
	 *得到所有在相册所有照片 
	 * @param album 相册对象
	 * @return 照片集合
	 */
	public List<Photo> listAllPhoto(Album album){
		String sql = "select * from t_photo where album_id=?";
		Object[] params={album.getAlbumId()};
		 return makeListPhotoFromListMap(find(sql,params));
	}
	
	/**
	 * 得到照片集合
	 * @param 键值对集合
	 * @return 图片集合
	 */
	public List<Photo> makeListPhotoFromListMap(List<Map<String,Object>> maps){
		if(maps==null){
			return null;
		}
		List<Photo> photos = new ArrayList<Photo>();
		for(Map<String,Object> map: maps){
			Photo photo = makePhotoFromMap(map);
			photos.add(photo);
		}
		return photos;
	}
	
	/**
	 * 从map里面的到图片对象
	 * @param map 键值对对象
	 * @return 得到照片
	 */
	public Photo makePhotoFromMap(Map<String,Object> map){
		if(map==null){
			return null;
		}
		Photo photo = new Photo();
		doSetValue(map, photo, list);
		return photo;
	}
	
	/**
	 * 根据图片的Id得到图片对象
	 * @param photoId 照片Id
	 * @return 照片对象
	 */
	public Photo getPhotoById(int photoId){
		String sql ="select * from t_photo where id=?";
		Object[] params = {photoId};
		return makePhotoFromMap(findObject(sql,params));
	}

	/**
	 * 计算相册的照片数目
	 * @param album
	 * @return 该相册拥有的照片数目
	 */
	public int count(Album album) {
		// TODO Auto-generated method stub
		String sql = "select count(*) count from t_photo where album_id=?";
		Object[] params = {album.getAlbumId()};
		int count = getCountFromTable(sql, params);
		LogUtil.logger.info("相册内的图片数："+ count);
		return count;
	}
}
