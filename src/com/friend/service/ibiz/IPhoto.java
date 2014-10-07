package com.friend.service.ibiz;
import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Album;
import com.friend.vo.Photo;

/**
 * 
 * @author 李跃磊 2014 7.16 17:28
 *
 */
public interface IPhoto {
	public void uploadPhoto(Photo photo);
	public void deletePhoto(int id);
	public boolean movePhoto(int id,Album album);
	public List<Photo> browseAlbum(Album album,Pager pager);
	public List<Photo> browseAlbumAllPhoto(Album album);/*9.16*/
	public boolean deletePhoto(Album album);
	public int countPhoto(Album album);

}
