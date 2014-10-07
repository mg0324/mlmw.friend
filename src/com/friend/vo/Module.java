package com.friend.vo;

import java.util.Date;
import java.util.List;

/**
 * @
 * 
 * @author 宋启东 2014 07 16 19：55
 *
 */
public class Module {
	private int moduleId;// 模块ID
	private String moduleName;// 模块名
	private Date createTime;// 创建时间
	private int topicCount;
	private String photoPath;
	private User user;// 指明这个模块是由谁创建的
	private List<Topic> topicList;//
	
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}

}
