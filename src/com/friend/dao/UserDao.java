package com.friend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.DBConnect;
import com.friend.util.Pager;
import com.friend.util.ParamUtil;
import com.friend.util.Property;
import com.friend.vo.Role;
import com.friend.vo.User;

/**
 * 
 * @author 梅刚 2014-7-16 18:44
 *
 */
public class UserDao extends BaseDao{
	private RoleDao roleDao = BaseDaoFactory.getRoleDao();
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("User");
	}
	/**
	 * 直接使用dbutils的QueryRunner来完成insert操作
	 * @param user
	 * @return
	 */
	/*public User addUser(User user) {
		// TODO Auto-generated method stub
		conn = DBConnect.getConnect();
		QueryRunner query = new QueryRunner();
		String sql = "insert into t_user(username,password,sex,email,create_time) values"
				+ " (?,?,?,?,?)";
		int c = 0;
		try {
			c = query.update(conn, sql,user.getUserName(),user.getPassword(),
						user.getSex(),user.getEamil(),user.getCreateTime());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//注册成功就返回注册的user对象
		if(c>0){
			return user;
		}
		return null;
	}*/
	/**
	 * 调用了BaseDao中的insert方法，来完成添加
	 * @param user
	 * @return
	 */
	public User addUser(User user){
		String defaultHead = "upload/default_head.jpg";
		String sql = "insert into t_user(username,user_nickname,password,sex,email,birthday,create_time,head) values"
				+ " (?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUserName(),
						user.getUserNickName(),
						user.getPassword(),
						user.getSex(),
						user.getEmail(),
						user.getBirthday(),
						user.getCreateTime(),
						defaultHead};
		int c = update(sql, params);
		if(c > 0){
			return getUserByUserName(user.getUserName());
		}
		return null;
	}
	/**
	 * 检查用户名，密码是否正确。
	 * @param userName 输入的用户名
	 * @param password 输入的密码
	 * @return 如果正确的话，就返回user的全对象，反之返回null
	 */
	public User checkUser(String userName, String password) {
		// TODO Auto-generated method stub
		String sql = "select * from t_user where username=? and password=?";
		Object[] params = {userName,password};
		return makeUserFromMap(findObject(sql, params));
	}
	/**
	 * 修改密码
	 * @param user
	 * @param newPwd
	 * @return
	 */
	public boolean updatePwd(User user, String newPwd) {
		// TODO Auto-generated method stub
		String sql = "update t_user set password=? where id=?";
		Object[] params = {newPwd,user.getUserId()};
		int c = update(sql, params);
		if(c>0)
			return true;
		else
			return false;
	}
	/**
	 * 通过uid得到User对象
	 * @param uid 用户编号
	 * @return 返回用户对象，没有找到返回null
	 */
	public User getUserById(int uid){
		String sql = "select * from t_user where id=?";
		Object[] params = {uid};
		return makeUserFromMap((findObject(sql, params)));
	}
	/**
	 * 通过用户名来查找得到User对象
	 * @param uid
	 * @return
	 */
	public User getUserByUserName(String uname){
		String sql = "select * from t_user where username=?";
		Object[] params = {uname};
		return makeUserFromMap((findObject(sql, params)));
	}
	
	
	/**
	 * 从listMaps中封装出User对象的集合
	 * @param listMaps
	 * @return
	 */
	public List<User> makeUserListFromListMap(List<Map<String,Object>> listMaps){
		List<User> users = new ArrayList<User>();
		for(Map<String,Object> map : listMaps){
			User user = makeUserFromMap(map);
			//添加到users集合
			users.add(user);
		}
		return users;
 	}
	/**
	 * 从Map<String,Object>中得到User对象
	 * @param map 存放键值对的map集合
	 * @return 返回封装好的user对象,如果没有查到数据返回null
	 */
	public User makeUserFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		User user = new User();
		doSetValue(map, user, list);
		//把他有的角色也封装进去
		List<Role> roles = roleDao.getRolesByUser(user);
		user.setRoleList(roles);
		return user;
	}

	
	/**
	 * 完善个人信息
	 * @param user
	 * @return 
	 */
	public boolean updateUserInfo(User user) {
		// TODO Auto-generated method stub
		String sql = "update t_user set user_nickname=?,birthday=?,"
				+ "self_write=?,phone=?,"
				+ "hobbies=?"
				+ "where id=?";
		Object[] params = {user.getUserNickName(),
				user.getBirthday(),
				user.getSelfWrite(),
				user.getPhone(),
				user.getHobbies(),
				user.getUserId()};
		int c = update(sql, params);
		if(c > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 得到User的pager对象
	 * @param page
	 * @return
	 */
	public Pager getPage(Pager page) {
		// TODO Auto-generated method stub
		String sql = "select count(*) count from t_user";
		page.setTotalCount(getCountFromTable(sql, null));
		sql = "select * from t_user order by id asc limit ?,?";
		Object[] params = {(page.getCurrentPage()-1)*page.getPageSize(),
						page.getCurrentPage()*page.getPageSize()};
		List<User> users = makeUserListFromListMap(find(sql, params));
		page.setList(users);
		return page;
	}
	/**
	 * 填写pwdQuestion,pwdAnswer
	 * @param user
	 */
	public void setPasswordSafe(User user) {
		// TODO Auto-generated method stub
		String sql = "update t_user set pwd_question=?,pwd_answer=?"
				+ " where id=?";
		Object[] params = {user.getPwdQuestion(),
						user.getPwdAnswer(),
						user.getUserId()};
		update(sql, params);
	}
	/**
	 * 更新数据库的head path路径
	 * @param user
	 */
	public void uploadHead(User user) {
		// TODO Auto-generated method stub
		String sql = "update t_user set head=? where id=?";
		Object[] params = {user.getHead(),
				user.getUserId()};
		update(sql, params);
	}
	/**
	 * 得到最新的8个注册用户
	 * @return
	 */
	public List<User> getNewVIPs() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM t_user WHERE id NOT IN("
				+ "SELECT user_id FROM t_user_role WHERE role_id IN ("
				+ "SELECT id FROM t_role WHERE role_grade>=3"
				+ ")"
				+ ") order by create_time desc limit 0,"+ParamUtil.INDEX_VIP_USER_SIZE;
		return makeUserListFromListMap(find(sql, null));
	}
	/**
	 * 更新hometown
	 * @param user
	 * @return
	 */
	public User updateHomeTown(User user) {
		// TODO Auto-generated method stub
		String sql = "update t_user set home_town=? "
				+ "where id=?";
		Object[] params = {user.getHomeTown(),
				user.getUserId()};
		update(sql,params);
		return getUserById(user.getUserId());
	}
	/**
	 * 该方法用于展示所有用户，并实现分页功能
	 * @return
	 */
	public List findAllUsers(Pager page,String orderByAsc){
		String sql = "select * from t_user order by "+orderByAsc+" asc limit ?,?";
		Object[] params = {(page.getCurrentPage()-1)*page.getPageSize(),
				page.getCurrentPage()*page.getPageSize()};
		List<Map<String,Object>> listMaps = find(sql, params);
		//Map<String,Object> 封装到  User的List集合中
		List<User> users = makeUserListFromListMap(listMaps);
		return users;
	}

/**
	 * 该方法用于屏蔽用户
	 * @param user
	 */
	public void hideUser(User user){
		String sql="update t_user set status=0 where id=?";
		Object[] params={user.getUserId()};
		update(sql,params);	
	}
	
	/**
	 * 该方法用于解除屏蔽用户
	 * @param user
	 */
	public void activeUser(User user){
		String sql="update t_user set status=1 where id=?";
		Object[] params={user.getUserId()};
		update(sql,params);	
	}
	/**
	 * 查找用户总人数
	 * @return
	 */
	public int countUser()
	{
		String sql = "select count(*) count from t_user";
		Object[] params = {};
		return getCountFromTable(sql, params);
	}
	public int getMaxUserId(){
		String sql = "select max(id) maxId from t_user";
		return getMaxId(sql,null);
	}
	/**
	 * 创建新的管理员，并授权 带有事物的
	 * @param admin
	 * @param role
	 * @return
	 */
	public boolean createAdminstor(User admin, Role role) {
		// TODO Auto-generated method stub
		Connection conn =  DBConnect.getConnect();
		boolean b = true;
		try {
			conn.setAutoCommit(false);
			String sql1 = "insert into t_user(username,user_nickname,password,sex,birthday,create_time,head,status)"
					+ "values(?,?,?,?,?,?,?,?)";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, admin.getUserName());
			ps1.setString(2,"admin");
			ps1.setString(3, admin.getPassword());
			ps1.setString(4, "男");
			ps1.setString(5, new Date().toLocaleString());
			ps1.setString(6, new Date().toLocaleString());
			ps1.setString(7, "upload/default_head.jpg");
			ps1.setInt(8, 1);
			ps1.execute();
			String sql2 = "insert into t_user_role(user_id,role_id) values(?,?)";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1,getMaxUserId()+1);
			ps2.setInt(2, role.getRoleId());
			ps2.execute();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				b = false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();	
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	public List<User> getAllAdmins() {
		// TODO Auto-generated method stub
		String sql = "select * from t_user where id in ("
				+ "select user_id from t_user_role where role_id in("
				+ "select id from t_role where role_grade>=3))";
		return makeUserListFromListMap(find(sql, null));
	}
	/**
	 * 变更角色
	 * @param admin
	 * @param roles
	 * @return
	 */
	public boolean changeRole(User admin, List<Role> roles) {
		// TODO Auto-generated method stub
		Connection conn =  DBConnect.getConnect();
		boolean b = true;
		try {
			conn.setAutoCommit(false);
			
			List<Integer> noRoleIds = new ArrayList<Integer>();//新的角色集合
			List<Integer> subRoleIds = new ArrayList<Integer>();//删除的角色集合
			List<Integer> haveRoleIds = new ArrayList<Integer>();//用户原来的角色集合
			List<Role> adminRoles = admin.getRoleList();
			//原来的角色id列表
			int[] adminRoleIds = new int[adminRoles.size()];
			for(int i=0;i<adminRoles.size();i++){
				adminRoleIds[i] = adminRoles.get(i).getRoleId();
			}
			//改变后的角色列表
			int[] changeRoleIds = new int[roles.size()];
			for(int i=0;i<roles.size();i++){
				changeRoleIds[i] = roles.get(i).getRoleId();
			}
		
			//找到之后的角色列表中是否有以前的角色存在并记录下来
			for(int i=0;i<changeRoleIds.length;i++){
				boolean isHave = false;
				for(int j=0;j<adminRoleIds.length;j++){
					if(changeRoleIds[i]==adminRoleIds[j]){
						//说明存在原来的角色
						isHave = true;
					}
				}
				if(isHave){
					haveRoleIds.add(changeRoleIds[i]);
				}else{
					noRoleIds.add(changeRoleIds[i]);//要加入的角色
				}
			}
			
			//原来拥有的角色与重复的角色对比
			if(haveRoleIds.size()==0){
				//就说明原来的角色都应该被删除掉了
				for(Role role : adminRoles){
					subRoleIds.add(role.getRoleId());
				}
			}else{
				for(Role beforeRole : adminRoles){
					int bId = beforeRole.getRoleId();
					boolean isHave = false;
					for(int sameRoleId : haveRoleIds){
						if(bId == sameRoleId){
							isHave = true;
						}
					}
					if(!isHave){
						subRoleIds.add(bId);
					}
				}
			}
			//执行sql操作，将noRoles中role加入到t_user_role中，将subRole中的role
			//删除在t_user_role中
			for(int rid : noRoleIds){
				String sql = "insert into t_user_role(user_id,role_id) values(?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, admin.getUserId());
				ps.setInt(2, rid);
				ps.execute();
			}
			for(int rid : subRoleIds){
				String sql = "delete from t_user_role where user_id=? and role_id=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, admin.getUserId());
				ps.setInt(2, rid);
				ps.execute();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				b = false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();	
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	
}
