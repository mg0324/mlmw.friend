package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.LogUtil;
import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Permission;


/**
 * 
 * @author 梅刚 2014-7-18
 *
 */
public class PermissionDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Permission");
	}
	/**
	 * 添加一个permission记录
	 * @param p
	 * @return
	 */
	public boolean addPermission(Permission p) {
		// TODO Auto-generated method stub
		String sql = "insert into t_permission values(?,?,?,?)";
		Object[] params = {p.getPermissionId(),
				p.getPermissionName(),
				p.getPermissionNote(),
				p.getPermissionType()};
		if(update(sql, params) > 0)
			return true;
		else
			return false;
	}
	/**
	 * 删除一条permission记录
	 * @param p
	 * @return
	 */
	public boolean deletePermission(Permission p) {
		// TODO Auto-generated method stub
		String sql = "delete from t_permission where id=?";
		Object[] params = {p.getPermissionId()};
		if(update(sql, params) > 0)
			return true;
		else
			return false;
	}
	/**
	 * 更新一条permission记录
	 * @param p
	 * @return
	 */
	/*public boolean updatePermission(Permission p) {
		// TODO Auto-generated method stub
		String sql = "update t_permission set permission_name=?,"
				+ "permission_note=?,permission_type=? where "
				+ "id=?";
		Object[] params = {p.getPermissionName(),
				p.getPermissionNote(),
				p.getPermissionType(),
				p.getPermissionId()};
		if(update(sql, params) > 0)
			return true;
		else
			return false;
	}*/
	public boolean updatePermission(Permission p){
		String sql = "update t_permission set permission_name = ?,"
					+ " permission_note = ? where id = ?";
		Object[] params = {p.getPermissionName(),
							p.getPermissionNote(),
							p.getPermissionId()};
		int c = update(sql, params);
		if (c > 0){
			LogUtil.logger.info(c+"行受影响");
			return true;
		}
		return false;
	}
	
	/**
	 * 通过pid得到Permission对象
	 * @param pid 权限编号
	 * @return 返回权限对象，没有找到返回null
	 */
	public Permission getPermissionById(int pid){
		String sql = "select * from t_permission where id=?";
		Object[] params = {pid};
		return makePermissionFromMap(findObject(sql, params));
	}
	
	
	/**
	 * 封装List<Role>
	 * @param listMaps
	 * @return
	 */
	public List<Permission> makePermissionListFromListMap(List<Map<String,Object>> listMaps){
		List<Permission> ps = new ArrayList<Permission>();
		for(Map<String,Object> map : listMaps){
			Permission p = makePermissionFromMap(map);
			ps.add(p);
		}
		return ps;
 	}
	
	public List<Permission> listPermission(Pager pager){
		String sql = "select * from t_permission limit ?, ?";
		Object[] params = {(pager.getCurrentPage() - 1) * pager.getPageSize(),
							pager.getPageSize()};
		return makePermissionListFromListMap(find(sql, params));
	}
	
	public List<Permission> listPermission() {
		// TODO Auto-generated method stub
		String sql = "select * from t_permission";
		return makePermissionListFromListMap(find(sql, null));
	}
	public int getPermissionCount(){
		String sql = "select count(*) count from t_permission";
		return getCountFromTable(sql, null);
	}
	/**
	 * 封装Permission
	 * @param map
	 * @return
	 */
	public Permission makePermissionFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		Permission p = new Permission();
		doSetValue(map, p, list);
		
		return p;
	}

}
