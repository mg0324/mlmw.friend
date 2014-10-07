package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Property;
import com.friend.vo.Role;
import com.friend.vo.User;
import com.friend.vo.UserRole;

/**
 * 
 * @author 梅刚 2014-7-18 16:00
 *
 */
public class UserRoleDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("UserRole");
	}
	/**
	 * 添加一条UserRole记录
	 * @param user
	 * @param role
	 * @return
	 */
	public boolean addUserRole(User user, Role role) {
		// TODO Auto-generated method stub
		String sql = "insert into t_user_role(user_id,role_id) vaules(?,?)";
		Object[] params = {user.getUserId(),role.getRoleId()};
		if(update(sql, params) > 0)
			return true;
		else
			return false;
	}
	/**
	 * 删除一条UserRole记录
	 * @param user 得到user_id
	 * @param role 得到role_id
	 * @return
	 */
	public boolean deleteUserRole(User user, Role role) {
		// TODO Auto-generated method stub
		String sql = "delete from t_user_role where user_id=? and role_id=?";
		Object[] params = {user.getUserId(),role.getRoleId()};
		if(update(sql,params) > 0)
			return true;
		else
			return false;
	}
	

	/**
	 * 通过urid得到UserRole对象
	 * @param urid 用户角色记录编号
	 * @return 返回用户角色对象，没有找到返回null
	 */
	public UserRole getUserRoleById(int urid){
		String sql = "select * from t_user_role where id=?";
		Object[] params = {urid};
		return makeUserRoleFromMap(findObject(sql, params));
	}
	
	
	/**
	 * 封装List<UserRole>
	 * @param listMaps
	 * @return
	 */
	public List<UserRole> makeUserRoleFromListMap(List<Map<String,Object>> listMaps){
		List<UserRole> urs = new ArrayList<UserRole>();
		for(Map<String,Object> map : listMaps){
			UserRole ur = makeUserRoleFromMap(map);
			urs.add(ur);
		}
		return urs;
 	}
	/**
	 * 封装UserRole
	 * @param map
	 * @return
	 */
	public UserRole makeUserRoleFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		UserRole ur = new UserRole();
		doSetValue(map, ur, list);

		return ur;
	}

}
