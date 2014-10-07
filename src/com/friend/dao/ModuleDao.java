package com.friend.dao;

/**
 * 
 * @author 宋启东 2014 07 18 16：12
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.friend.util.Pager;
import com.friend.util.Property;
import com.friend.vo.Module;
//import com.friend.vo.User;

public class ModuleDao extends BaseDao{
	private static List<Property> list;
	static{
		list = getListPropertyFromEntityXml("Module");
	}
	//创建一个模块
	public boolean createModule(Module module){
		// TODO Auto-generated method stub
		
		//String sql = "insert into t_module(id,module_name,create_time,user_id) values"
		//		+ "(null,?,?,?)";
		String sql = "insert into t_module(module_name,create_time,user_id) values"
					+"(?, ?, ?)";
		Object[] params = {module.getModuleName(),new Date(),module.getUser().getUserId()};
		//PreparedStatement ps=conn.prepareStatement(sql);
		int c = update(sql, params);
		//List<Map<String, Object>>
		//ResultSet rs=ps.executeUpdate();
		//Object[] params = {module.getModuleName(),module.getCreateTime()};
		
		//int c = update(sql, params);
		System.out.println(c);
		if(c>0)
			return true;
		else
			return false;
		
	}
	//找到所有的板块
	public List<Module> findAllModule(Pager pager){
		String sql = "select * from t_module order by create_time asc limit ?,?";
		//String sql="select * from t_topic where module_id=? order by publish_time desc limit ?,?";
		Object[] prams={((pager.getCurrentPage()-1)*pager.getPageSize()),pager.getPageSize()};
		//Map<String,Object> 封装到  Modules的List集合中
		return makemoduleListFromListMap(find(sql, prams));
	}
	public List<Module> makemoduleListFromListMap(List<Map<String,Object>> listMaps){
		List<Module> Modules = new ArrayList<Module>();
		for(Map<String,Object> map : listMaps){
			Module module = makemoduleListFromListMap(map);
			//添加到Modules集合
			Modules.add(module);
		}
		return Modules;
 	}
	//找到单个的模块；
	public Module makemoduleListFromListMap(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		if( map== null){
			return null;
		}
		Module module=new Module();
		doSetValue(map, module,list);
		return module;
	}
	//更新模块
	public Boolean updateModule(Module module)
	{
		String sql = "update t_module set module_name=?,create_time=?"
				+ "where id=?";
		int a=0;//影响的行数
		Object[] pramgrm={module.getModuleName(),
				module.getCreateTime(),
				module.getUser().getUserId()};
		a = update(sql, pramgrm);
		if(a>0){
			return true;
		}else
			return false;
		
	}
	/**
	 * 通过moduleid得到Module对象
	 * @param moduleid 模块编号
	 * @return 返回模块对象，没有找到返回null
	 */
	public Module getModuleById(int moduleid){
		String sql = "select * from t_module where id=? ";
		Object[] params = {moduleid};
		return makemoduleListFromListMap((findObject(sql, params)));
	}
	public Module getModuleByName(String moduleName){
		String sql = "select * from t_module where module_name=? ";
		Object[] params = {moduleName};
		return makemoduleListFromListMap((findObject(sql, params)));
	}
	//删除模块
	public Boolean deleteModule(Module module) {
		// TODO Auto-generated method stub
		int a=0;
		String sql = "delete from t_module where id=?";
		Object[] params = {module.getModuleId()};
		 a = update(sql, params);
		 if(a>0){
				return true;
			}else
				return false;
	}
 	/**
 	 * 该方法用于找到数据库中所有模块的数量
 	 * @param moduleid 模块编号
	 * @return 返回模块对象，没有找到返回null
 	 */
	public int getModuleCount(){
		String sql = "select count(*) count from t_module";
		Object[] params = {};
		return getCountFromTable(sql, params);
	}
}
