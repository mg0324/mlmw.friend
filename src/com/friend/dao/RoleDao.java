package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Role;
import com.friend.vo.User;

/**
 * 
 * @author 梅刚 2014-7-18
 *
 */
public class RoleDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Role");
	}
	/**
	 * 添加一条role记录
	 * @param role
	 */
	public boolean addRole(Role role) {
		// TODO Auto-generated method stub
		String sql = "insert into t_role values"
				+ "(?,?,?)";
		Object[] params = {role.getRoleId(),
				role.getRoleName(),
				role.getRoleGrade()};
		if(update(sql, params) > 0){
			//插入成功
			return true;
		}else{
			//失败
			return false;
		}
	}
	/**
	 * 删除一条role记录
	 * @param role
	 * @return
	 */
	public boolean deleteRole(Role role) {
		// TODO Auto-generated method stub
		String sql = "delete from t_role where id=?";
		Object[] params = {role.getRoleId()};
		int n=0;
		n=update(sql, params);
		if(n > 0)
			return true;
		else
			return false;
	}
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public boolean updateRole(Role role) {
		// TODO Auto-generated method stub
		String sql = "update t_role set role_name=?,role_grade=? "
				+ "where id=?";
		Object[] params = {role.getRoleName(),
				role.getRoleGrade(),
				role.getRoleId()};
		if(update(sql, params) > 0)
			return true;
		else
			return false;
	}
	

	/**
	 * 通过roleId得到Role对象
	 * @param roleId 角色编号
	 * @return 返回角色对象，没有找到返回null
	 */
	public Role getRoleById(int roleId){
		String sql = "select * from t_role where id=?";
		Object[] params = {roleId};
		return makeRoleFromMap((findObject(sql, params)));
	}
	
	
	/**
	 * 封装List<Role>
	 * @param listMaps
	 * @return
	 */
	public List<Role> makeRoleListFromListMap(List<Map<String,Object>> listMaps){
		List<Role> roles = new ArrayList<Role>();
		for(Map<String,Object> map : listMaps){
			Role role = makeRoleFromMap(map);
			roles.add(role);
		}
		return roles;
 	}
	/**
	 * 封装Role
	 * @param map
	 * @return
	 */
	public Role makeRoleFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		Role role = new Role();
		//封装好map中可能有的属性
		doSetValue(map, role, list);
		//****
		
		return role;
	}

	/**
	 * 得到所有角色
	 */
	public List<Role> getAllRole(Pager pager){
		String sql="select * from t_role limit ?,?";
		Object[] params={((pager.getCurrentPage()-1)*pager.getPageSize()),pager.getPageSize()};
		return makeRoleListFromListMap(find(sql, params));
	}
	
	/**
	 * 得到所有角色总数
	 */
	public int getAllRoleCount(){
		String sql="select count(*) count from t_role";
		
		return getCountFromTable(sql, null);
	}
	/**
	 * 得到所有角色的集合
	 * @return
	 */
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		String sql = "select * from t_role order by id";
		return makeRoleListFromListMap(find(sql, null));
	}
	//通过user得到他有的角色集合
	public List<Role> getRolesByUser(User user) {
		// TODO Auto-generated method stub
		String sql = "select * from t_role where id in ("
				+ "select role_id from t_user_role where user_id = ?)";
		Object[] params = {user.getUserId()};
		return makeRoleListFromListMap(find(sql, params));
	}
}
