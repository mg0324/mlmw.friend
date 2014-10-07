package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Album;
import com.friend.vo.User;

/**
 * 
 * @author 李跃磊 2014 7.16 18:40
 *
 */
public interface IAlbum {
	public Album createAlbum(Album album);
	public List<Album> browseAlbum(User user,Pager pager);
	public Album deleteAlbum(int id);
	public List<Integer> countPhoto(List<Album> albums);
	public Album getAlbumById(int albumId);
	public int getAlbumCount(User user);//得到用户相册个数
	public boolean checkAlbumName(User user, Album album);
}
