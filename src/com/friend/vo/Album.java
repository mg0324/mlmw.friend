package com.friend.vo;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 李跃磊 2014 7.16 18:34
 *
 */
public class Album {
	private int albumId; //相册Id
	private String albumName; //相册名称
	private Date createTime;//相册创建时间
	private String albumDesc; //相册描述
	private User user; //相册拥有者
	private List<Photo> photoList;//相册里面的所有照片
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAlbumDesc() {
		return albumDesc;
	}
	public void setAlbumDesc(String albumDesc) {
		this.albumDesc = albumDesc;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Photo> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}
	
}
