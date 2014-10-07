package com.friend.vo;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 梅刚 2014-7-16 17:13
 *
 */
public class User{

	private int userId;
	private String userName;
	private String userNickName;
	private String password;
	private String sex;
	private Date birthday;
	private String head;
	private String hobbies;
	private Date createTime;
	private String selfWrite;
	private String phone;
	private String homeTown;
	private String email;
	private String pwdQuestion;
	private String pwdAnswer;
	private String isFocused;  //新增属性，表示是否被关注
	private String isBlacked;
	
	private int adminGrade;
	public int getAdminGrade() {
		return adminGrade;
	}
	public void setAdminGrade(int adminGrade) {
		this.adminGrade = adminGrade;
	}
	public String getIsBlacked() {
		return isBlacked;
	}
	public void setIsBlacked(String isBlacked) {
		this.isBlacked = isBlacked;
	}
	private int status;//新增属性，表示是否被屏蔽
	public String getIsFocused() {
		return isFocused;
	}
	public void setIsFocused(String isFocused) {
		this.isFocused = isFocused;
	}
	private List<Role> roleList;
	private List<Friend> friendList;
	private List<Talk> talkList;
	private List<Comment> commentList;
	private List<Reply> replyList;
	private List<LogSort> logSortList;
	private List<Log> logList;
	private List<Album> albumList;
	private List<Photo> photoList;
	private List<Module> moduleList;
	private List<Topic> topicList;
	public List<Friend> getFriendList() {
		return friendList;
	}
	public void setFriendList(List<Friend> friendList) {
		this.friendList = friendList;
	}
	public List<Talk> getTalkList() {
		return talkList;
	}
	public void setTalkList(List<Talk> talkList) {
		this.talkList = talkList;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	public List<LogSort> getLogSortList() {
		return logSortList;
	}
	public void setLogSortList(List<LogSort> logSortList) {
		this.logSortList = logSortList;
	}
	public List<Log> getLogList() {
		return logList;
	}
	public void setLogList(List<Log> logList) {
		this.logList = logList;
	}
	public List<Album> getAlbumList() {
		return albumList;
	}
	public void setAlbumList(List<Album> albumList) {
		this.albumList = albumList;
	}
	public List<Photo> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}
	public List<Module> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}
	public List<Topic> getTopicList() {
		return topicList;
	}
	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getHomeTown() {
		return homeTown;
	}
	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}
	
	public String getPwdQuestion() {
		return pwdQuestion;
	}
	public void setPwdQuestion(String pwdQuestion) {
		this.pwdQuestion = pwdQuestion;
	}
	public String getPwdAnswer() {
		return pwdAnswer;
	}
	public void setPwdAnswer(String pwdAnswer) {
		this.pwdAnswer = pwdAnswer;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public String getSelfWrite() {
		return selfWrite;
	}
	public void setSelfWrite(String selfWrite) {
		this.selfWrite = selfWrite;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
