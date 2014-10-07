package com.friend.service.biz;

import java.util.ArrayList;
import java.util.List;
import com.friend.dao.AlbumDao;
import com.friend.dao.BaseDaoFactory;
import com.friend.service.ibiz.IAlbum;
import com.friend.util.Pager;
import com.friend.vo.Album;
import com.friend.vo.User;

/**
 * 
 * @author 李跃磊 2014 7.16 18:47
 *
 */
public class AlbumService implements IAlbum{
	private static AlbumDao albumDao = BaseDaoFactory.getAlbumDao();
	@Override
	public Album createAlbum(Album album) {
		return albumDao.addAlbum(album);
	}

	@Override
	public List<Album> browseAlbum(User user,Pager pager) {
		// TODO Auto-generated method stub
		return albumDao.listAlbum(user,pager);
	}

	@Override
	public Album deleteAlbum(int id) {
		// TODO Auto-generated method stub
		Album album = new Album();
		album.setAlbumId(id);
		Album album1 = new Album();
		album1 = albumDao.deleteAlbum(album);
		return album1;
	}
	@Override
	public List<Integer> countPhoto(List<Album> albums) {
		// TODO Auto-generated method stub
		List<Integer> list= new ArrayList<Integer>();
		for(Album album:albums){
			list.add(albumDao.count(album));
		}
		return list;
	}
	@Override
	public Album getAlbumById(int albumId){
		// TODO Auto-generated method stub
		return albumDao.getAlbumById(albumId);
	}
	@Override
	public int getAlbumCount(User user) {
		// TODO Auto-generated method stub
		return albumDao.countAlbum(user);
	}
	public boolean checkAlbumName(User user,Album album) {
		// TODO Auto-generated method stub
		List<Album> albums = albumDao.getAlbumByUser(user);
		for(Album a:albums){	
			if(a.getAlbumName().equals(album.getAlbumName()))
			{
				return true;
			}
		}
		return false;
	}
}
