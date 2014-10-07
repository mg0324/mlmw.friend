package com.friend.util;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

/**
 * 
 * @author 梅刚 2014-8-2
 * dao的工厂
 *
 */
public class DaoFactory {
	/**
	 * 使用线程安全的hashMap
	 */
	public static Map<String, Object> factory = new HashMap<>();
	public static String CLASSPATH = "";
	/**
	 * 加载模块用到的dao到factory中
	 * @param moduleName 模块名user,friend,talk,log,album,bbs
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws DocumentException
	 */
	public static void loadModuleDaoToFactory(String moduleName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DocumentException{
		String xmlPath = CLASSPATH+"/daoFactory.xml";
		
		XmlParseUtil.loadModuleDao(xmlPath,moduleName);
	}
}
