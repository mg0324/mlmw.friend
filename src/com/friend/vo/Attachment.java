package com.friend.vo;

import java.util.Date;

/**
 * 
 * @author 宋启东 2014 07 17 15：10
 *
 */
public class Attachment {
	private int attachmentId;
	private String attachmentName;
	private double attachmentSize;
	private Date uploadTime;
	private String attachmentPath;
	private User user;
	private Topic topic;
public Topic getTopic() {
	return topic;
}
public void setTopic(Topic topic) {
	this.topic = topic;
}
public int getAttachmentId() {
	return attachmentId;
}
public void setAttachmentId(int attachmentId) {
	this.attachmentId = attachmentId;
}
public String getAttachmentName() {
	return attachmentName;
}
public void setAttachmentName(String attachmentName) {
	this.attachmentName = attachmentName;
}
public double getAttachmentSize() {
	return attachmentSize;
}
public void setAttachmentSize(double attachmentSize) {
	this.attachmentSize = attachmentSize;
}
public Date getUploadTime() {
	return uploadTime;
}
public void setUploadTime(Date uploadTime) {
	this.uploadTime = uploadTime;
}
public String getAttachmentPath() {
	return attachmentPath;
}
public void setAttachmentPath(String attachmentPath) {
	this.attachmentPath = attachmentPath;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
}
