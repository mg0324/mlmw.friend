package com.friend.service.biz;

import java.util.List;

import com.friend.dao.BaseDaoFactory;
import com.friend.dao.UserDao;
import com.friend.service.ibiz.IUser;
import com.friend.util.Pager;
import com.friend.vo.Role;
import com.friend.vo.User;
/**
 * 
 * @author 梅刚 2014-7-16 18:44
 *
 */
public class UserService implements IUser{
	private static UserDao userDao = BaseDaoFactory.getUserDao();

	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		return userDao.addUser(user);
	}

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return userDao.checkUser(user.getUserName(),user.getPassword());
	}

	@Override
	public User updatePassword(User user, String newPwd) {
		// TODO Auto-generated method stub
		userDao.updatePwd(user, newPwd);
		return userDao.getUserById(user.getUserId());
	}

	@Override
	public User uploadHead(User user) {
		// TODO Auto-generated method stub
		//等界面来了再说
		userDao.uploadHead(user);
		return userDao.getUserById(user.getUserId());
	}

	@Override
	public User updateUserInfo(User user) {
		// TODO Auto-generated method stub
		userDao.updateUserInfo(user);
		return userDao.getUserById(user.getUserId());
		
	}

	
	@Override
	public Pager getPage(Pager page) {
		// TODO Auto-generated method stub
		return userDao.getPage(page);
	}
	@Override
	public void setPasswordSafe(User user) {
		// TODO Auto-generated method stub
		userDao.setPasswordSafe(user);
	}
	@Override
	public boolean isExistUserName(String inName) {
		// TODO Auto-generated method stub
		if(userDao.getUserByUserName(inName)!=null){
			return true;
		}else{
			return false;
		}
			
	}
	@Override
	public List<User> getNewVIPs() {
		// TODO Auto-generated method stub
		
		return userDao.getNewVIPs();
	}
	@Override
	public User updateHomeTown(User user) {
		// TODO Auto-generated method stub
		return userDao.updateHomeTown(user);
	}
	@Override
	public User findBackPassword(User user) {
		// TODO Auto-generated method stub
		
		return userDao.getUserByUserName(user.getUserName());
	}
	@Override
	public User getUserById(int uid) {
		// TODO Auto-generated method stub
		return userDao.getUserById(uid);
	}
	@Override
	public List findAllUsers(Pager page,String orderByAsc) {
		// TODO Auto-generated method stub
		return userDao.findAllUsers(page,orderByAsc);
	}

	@Override
	public void hideUser(User user) {
		// TODO Auto-generated method stub
		userDao.hideUser(user);
		
	}

	@Override
	public void activeUser(User user) {
		// TODO Auto-generated method stub
		userDao.activeUser(user);
	}

	@Override
	public int countUser() {
		// TODO Auto-generated method stub
		return userDao.countUser();
	}

	@Override
	public boolean createAdminstor(User admin, Role role) {
		// TODO Auto-generated method stub
		return userDao.createAdminstor(admin,role);
	}

	@Override
	public List<User> getAllAdmins() {
		// TODO Auto-generated method stub
		return userDao.getAllAdmins();
	}

	@Override
	public boolean changeRole(User admin, List<Role> roles) {
		// TODO Auto-generated method stub
		return userDao.changeRole(admin,roles);
	}


}
