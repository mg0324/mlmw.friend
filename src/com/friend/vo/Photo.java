package com.friend.vo;

import java.util.Date;

/**
 * 
 * @author 李跃磊 2014 7.16 17:19
 *
 */
public class Photo {
	private int photoId;// 照片id
	private String photoName;// 照片名称
	private String photoDesc;// 照片描述
	private Date uploadTime;// 上传时间
	private String photoPath;// 照片存储路径
	private User user; // 照片的上传者
	private Album album;// 所属相册

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoDesc() {
		return photoDesc;
	}

	public void setPhotoDesc(String photoDesc) {
		this.photoDesc = photoDesc;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date date) {
		this.uploadTime = date;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return "photoId:"+photoId + " -- photoName: " + photoName +""
				+ " -- photoDesc:" + photoDesc +
				"-- photoPath:"+photoPath;
	}
}
