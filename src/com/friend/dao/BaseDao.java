package com.friend.dao;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.dom4j.DocumentException;

import com.friend.util.DBConnect;
import com.friend.util.DaoFactory;
import com.friend.util.LogUtil;
import com.friend.util.ParamUtil;
import com.friend.util.Property;
import com.friend.util.StringUtil;
import com.friend.util.XmlParseUtil;


/**
 * 
 * @author 梅刚 2014-7-17
 * 基本的dao,封装DBUtils的CRUD和一些常用的方法，如分页。
 *
 */
public class BaseDao{
	private static Connection conn = null;
	/**
	 * 统一的增删改方法
	 * update操作:insert,delete,update
	 * @param sql 带问号的sql语句
	 * @param params 按顺序构造的给问号赋值的参数列表
	 * @return 返回影响的行数
	 */
	public int update(String sql,Object[] params){
		int c = 0;
		conn = DBConnect.getConnect();
		QueryRunner query = new QueryRunner();
		try {
			c = query.update(conn, sql, params);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return c;
	}
	
	
	/**
	 * 找到一个对象的<key,value>
	 * @param sql 查询的sql语句，可以带?号
	 * @param params 给问号赋值
	 * @return 查找到的对象的<key,value>
	 */
	@SuppressWarnings("deprecation")
	public Map<String,Object> findObject(String sql,Object[] params){
		conn = DBConnect.getConnect();
		QueryRunner query = new QueryRunner();
		Map<String,Object> map = null;
		
		try {
			map = query.query(conn, sql, params, new MapHandler());
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
		return map;
	}
	/**
	 * 从select count(*) count from t_xxx where ?? 注意要去别名为count
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int getCountFromTable(String sql,Object[] params){
		conn = DBConnect.getConnect();
		QueryRunner query = new QueryRunner();
		Map<String,Object> map = null;
		int c = 0;
		try {
			map = query.query(conn, sql, params, new MapHandler());
			if(map.size()==1){
				c = new Integer(map.get("count").toString());
			}
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
		return c;
	}
	/**
	 * 得到主键自增长的表中的最大的id
	 * @param sql
	 * @param params
	 * @return
	 */
	public int getMaxId(String sql,Object[] params){
		conn = DBConnect.getConnect();
		QueryRunner query = new QueryRunner();
		Map<String,Object> map = null;
		int c = 0;
		try {
			map = query.query(conn, sql, params, new MapHandler());
			if(map.size()==1){
				c = new Integer(map.get("maxId").toString());
			}
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
		return c;
	}
	/**
	 * 统一的查询，得到list<Map<Strng,Object>>集合,也就是从数据库中查出的键值对集合
	 * @param sql 查询的sql语句，带有?
	 * @param params 给问号赋值的参数列表
	 * @return 返回查询得到的list集合
	 */
	@SuppressWarnings({"deprecation"})
	public List<Map<String,Object>> find(String sql,Object[] params){
		conn = DBConnect.getConnect();
		QueryRunner query = new QueryRunner();
		List<Map<String,Object>> list = null;
		try {
			list = query.query(conn, sql, params, new MapListHandler());
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
		return list;
	}
	/**
	 * 从Entity.xml文件中，将数据库查询得到的Map<String,Object>封装成指定的对象
	 * @param map
	 * @param obj
	 * @param xmlName
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("rawtypes")
	public void doSetValue(Map<String, Object> map, Object obj,List<Property> list){
		
		for(Property p : list){
			String tableColumn = p.getTableColumn();
			String type = p.getType();
			String voProperty = p.getVoProperty();
			String daoName = p.getDaoName();
			String entityClass = p.getEntityClass();
			//反射得到set方法
			Method method = null;
			try {
				if(map.get(tableColumn)!=null){
					if(type.equals("string")){
						method = obj.getClass().getMethod("set"+StringUtil.getFirstUpperString(voProperty), String.class);
						method.invoke(obj, map.get(tableColumn).toString());
					}else if(type.equals("date")){
						method = obj.getClass().getMethod("set"+StringUtil.getFirstUpperString(voProperty), Date.class);
						method.invoke(obj, StringUtil.stringConvertDateWithHMS(map.get(tableColumn).toString().trim()));
					}else if(type.equals("int")){
						method = obj.getClass().getMethod("set"+StringUtil.getFirstUpperString(voProperty), int.class);
						method.invoke(obj, Integer.parseInt(map.get(tableColumn).toString().trim()));
					}
					if(entityClass!=null){
						Class className;
						try {
							className = Class.forName(entityClass);
							method = obj.getClass().getMethod("set"+StringUtil.getFirstUpperString(voProperty), className);
							Method getEntityById = DaoFactory.factory.get(daoName).getClass().getMethod("get"+StringUtil.getFirstUpperString(type)+"ById", int.class);
							method.invoke(obj, getEntityById.invoke(DaoFactory.factory.get(daoName), Integer.parseInt(map.get(tableColumn).toString().trim())));
							LogUtil.logger.info("反射dao方法:"+getEntityById);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}						
				}
				LogUtil.logger.info("反射set方法:"+method);
			} catch (NoSuchMethodException | SecurityException 
					| IllegalAccessException
					| IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 通过实体名加载xml文件内容到List<Property>集合中
	 * @param voName
	 * @return
	 */
	public static List<Property> getListPropertyFromEntityXml(String voName){
		List<Property> list = null;
		try {
			list = XmlParseUtil.table2vo(DaoFactory.CLASSPATH + "/" + ParamUtil.PROJECT_VO_PACKAGE + "/" + voName+".xml");
			LogUtil.logger.info("加载Vo.xml文件:"+voName);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
