package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Role;
import com.friend.vo.User;
/**
 * 
 * @author 梅刚 2014-7-16 18:44
 *
 */
public interface IUser {
	public User registerUser(User user);
	public User login(User user);
	public User updatePassword(User user,String newPwd);
	public User uploadHead(User user);
	public User updateUserInfo(User user);
	public User findBackPassword(User user);
	public void setPasswordSafe(User user);
	/**
	 * 判断用户名是否已存在
	 * @param inName
	 * @return
	 */
	public boolean isExistUserName(String inName);
	/**
	 * 得到最新的会员，默认显示8个
	 * @return
	 */
	public List<User> getNewVIPs();
	
	public User getUserById(int uid);
	public User updateHomeTown(User user);
	
	
	//后台
	public Pager getPage(Pager page);
	public List findAllUsers(Pager page,String orderByAsc);
	//用于屏蔽用户
	public void hideUser(User user);
	//用于激活用户
	public void activeUser(User user);
	//用于找到用户总人数
	public int countUser();
	/**
	 * 创建新的下级管理员
	 * @param admin
	 * @param role
	 * @return
	 */
	public boolean createAdminstor(User admin, Role role);
	/**
	 * 得到管理员用户集合
	 * @return
	 */
	public List<User> getAllAdmins();
	//变更角色
	public boolean changeRole(User admin, List<Role> roles);
	
	
}
