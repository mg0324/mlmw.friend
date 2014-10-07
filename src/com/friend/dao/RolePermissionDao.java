package com.friend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.friend.util.DaoFactory;
import com.friend.util.Property;
import com.friend.util.StringUtil;
import com.friend.util.XmlParseUtil;
import com.friend.vo.Permission;
import com.friend.vo.Role;
import com.friend.vo.RolePermission;


/**
 * 
 * @author 梅刚 2014-7-18 16:30
 *
 */
public class RolePermissionDao extends BaseDao{
	private static List<Property> list;
	private static PermissionDao permissionDao = BaseDaoFactory.getPermissionDao();
	static{
		list = getListPropertyFromEntityXml("RolePermission");
	}
	/**
	 * 将permission的id组织成1;2;3;以分号间隔的字符串
	 * @param ps
	 * @param role
	 * @return
	 */
	public boolean addRolePermission(RolePermission rp) {
		// TODO Auto-generated method stub
		String sql = "insert into t_role_permission(role_id,permission_ids) "
				+ "values(?,?)";
		Object[] params = {rp.getRole().getRoleId(),rp.getPermisssionIds()};
		if(update(sql, params) > 0)
			return true;
		else 
			return false;
	}
	
	/**
	 * 通过rpid得到UserRole对象
	 * @param rpid 用户角色记录编号
	 * @return 返回角色权限对象，没有找到返回null
	 */
	public RolePermission getRolePermissionById(int rpid){
		String sql = "select * from t_role_permission where id=?";
		Object[] params = {rpid};
		return makeRolePermissionFromMap(findObject(sql, params));
	}
	
	
	/**
	 * 封装List<RolePermission>
	 * @param listMaps
	 * @return
	 */
	public List<RolePermission> makeRolePermissionFromListMap(List<Map<String,Object>> listMaps){
		List<RolePermission> rps = new ArrayList<RolePermission>();
		for(Map<String,Object> map : listMaps){
			RolePermission rp = makeRolePermissionFromMap(map);
			rps.add(rp);
		}
		return rps;
 	}
	/**
	 * 封装RolePermission
	 * @param map
	 * @return
	 */
	public RolePermission makeRolePermissionFromMap(Map<String,Object> map){
		//如果没有数据，是null的话，直接返回null
		if(map == null){
			return null;
		}
		RolePermission rp = new RolePermission();
		doSetValue(map, rp, list);
		return rp;
	}
	
	public List<Permission> getPermissionListByRoleId(Role role){
	
		String sql = "select permission_ids from t_role_permission where role_id = ?";
		Object[] params = {role.getRoleId()};
		Map<String,Object> pids = findObject(sql, params);
		if(pids.get("permission_ids")==null){
			return null;
		}
		String pds = pids.get("permission_ids").toString();
		if(pds.equals("")){
			return null;
		}
		else{
			String ps ="(" + pds.replace(";", ",") +")";
			sql = "select * from t_permission where id in "+ps;
			return permissionDao.makePermissionListFromListMap(find(sql, null));
		}
	}
	
	public int updateRolePermission(RolePermission rp){
		String sql="update t_role_permission set permission_ids=? where role_id=?";
		Object[] params={rp.getPermisssionIds(),rp.getRole().getRoleId()};
		
		return update(sql, params);
	}
}
