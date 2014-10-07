package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Album;
import com.friend.vo.User;

/**
 * 
 * @author 李跃磊 2014 7.16 19:30
 *
 */
public class AlbumDao extends BaseDao {
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Album");
	}
	/**
	 * 增加相册
	 * @param album 相册
	 * @return 增加的相册对象Album
	 */
	public Album addAlbum(Album album){
		String sql="insert into t_album(album_name,album_desc,create_time,user_id)values(?,?,?,?)";
		Object[] params={album.getAlbumName(),
				album.getAlbumDesc(),
				album.getCreateTime(),
				album.getUser().getUserId()};
		int a = update(sql,params);
		if(a>0){
			return album;
		}
		return null;
	}
	/**
	 * 删除相册
	 * @param album 传入的相册
	 * @return  删除的相册对象
	 */
	public Album deleteAlbum(Album album)
	{
		String sql ="delete from t_album where id =?";
		System.out.println("相册Id"+album.getAlbumId());
		Object[] params = {album.getAlbumId()};
		int b = update(sql,params);
		if(b>0){
			return album;
		}
		return null;
	}
	/**
	 * 根据用户User 得到用户的自己的相册Album对象集合
	 * @param user 相册拥有者 即用户
	 * @return Album集合
	 */
	public List<Album> listAlbum(User user,Pager pager)
	{
		String sql="select * from t_album where user_id=? order by create_time limit ?,?";
		Object[] params = {user.getUserId(),
				((pager.getCurrentPage()-1)*pager.getPageSize()),
				pager.getPageSize()};
		return makeAlbumsFromMap(find(sql, params));
	}

	/**
	 * 根据List传入的对象 获得List中所有对象 即Album集合	
	 * @param listmaps
	 * @return 相册Album集合
	 */
	public  List<Album> makeAlbumsFromMap(List<Map<String,Object>> listmaps){
		List<Album> albums = new ArrayList<Album>();
		for(Map<String,Object> map : listmaps){
			Album album = new Album();
			album = makeAlbumFromMap(map);
			albums.add(album);
		}
		return albums;
 	}
	/**
	 * 根据相册id得到相册对象
	 * @param album_id 相册id
	 * @return 根据相册id得到相册对象
	 */
	public Album getAlbumById(int album_id){
		String sql="select * from t_album where id=?";
		Object[] params = {album_id};
		return makeAlbumFromMap(findObject(sql,params));
	}
	
	/**
	 *  根据传入的键值对获得单个Album
	 * @param map 键值对
	 * @return 得到单个相册对象
	 */
	public Album makeAlbumFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		Album album = new Album();
		doSetValue(map, album, list);
		return album;
	}
	/**
	 * 查询相册拥有的照片数
	 * @param album
	 * @return 照片数
	 */
	public int count(Album album) {
		// TODO Auto-generated method stub
		String sql ="select count(*) count from t_photo where album_id=?";
		Object[] params = {album.getAlbumId()};
		return getCountFromTable(sql,params);
	}
	
	/**
	 * 计算用户相册个数
	 * @param user
	 * @return 相册个数
	 */
	public int countAlbum(User user){
		String sql = "select count(*) count from t_album where user_id=?";
		Object[] params = {user.getUserId()};
		return getCountFromTable(sql,params);
	}
	/**
	 * 根据用户得到用户的所有相册对象集合
	 * @param user
	 * @return 相册对象集合
	 */
	public List<Album> getAlbumByUser(User user){
		String sql="select * from t_album where user_id=?";
		Object[] params = {user.getUserId()};
		List<Map<String,Object>> listMaps = find(sql, params);
		List<Album> albums =makeAlbumsFromMap(listMaps);
		return albums;
	}
}
	
