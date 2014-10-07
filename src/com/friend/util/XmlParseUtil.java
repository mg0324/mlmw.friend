package com.friend.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author 梅刚 2014-8-2 xml的解析工具
 *
 */
public class XmlParseUtil {
	/**
	 * 得到dom4j解析的文档对象
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseXml(String xmlPath) throws DocumentException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		return reader.read(new File(xmlPath));
	}
	/**
	 * 装载user模块的dao到DaoFactory
	 * @param xmlPath
	 * @throws DocumentException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void loadModuleDao(String xmlPath,String moduleName) throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		Document document = parseXml(xmlPath);
		// 得到根节点
		Element root = document.getRootElement();
		// 使用迭代，遍历xml文档。
		for (Iterator i = root.elementIterator(); i.hasNext();) {
			Element module = (Element) i.next();
			String name = module.attributeValue("name");
			if(name.equals(moduleName)){
				for (Iterator j = module.elementIterator(); j.hasNext();) {
					Element dao = (Element) j.next();
					String daoName = dao.attributeValue("name");
					String clazz = dao.attributeValue("class");
					Class c = Class.forName(clazz);
					DaoFactory.factory.put(daoName, c.newInstance());
					LogUtil.logger.info("初始化Dao:"+clazz);
				}
			}
		}
	}
	/**
	 * 将表中读出的Map根据xml中的配置封装成实体对象的加载xml属性到Property工具类中
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Property> table2vo(String xmlPath) throws DocumentException{
		Document document = parseXml(xmlPath);
		Element root = document.getRootElement();
		List<Property> list = new ArrayList<Property>();
		for(Iterator i = root.elementIterator();i.hasNext();){
			Element property = (Element) i.next();
			String tableColumn = property.attributeValue("tableColumn");
			String voProperty = property.attributeValue("voProperty");
			String type = property.attributeValue("type");
			String entityClass = property.attributeValue("entityClass");
			Property p = new Property();
			p.setTableColumn(tableColumn);
			p.setVoProperty(voProperty);
			p.setType(type);
			p.setEntityClass(entityClass);
			for (Iterator j = property.elementIterator(); j.hasNext();) {
				Element dao = (Element) j.next();
				String daoName = dao.attributeValue("daoName");
				p.setDaoName(daoName);
			}
			list.add(p);
		}
		return list;
	}
	/**
	 * 从实体xml文件中读出voProperty和type来将jsp发过来的数据封装到对象中的加载xml属性到Property中
	 * @param xmlPath
	 * @return
	 * @throws DocumentException
	 */
	public static List<Property> getVoForm(String xmlPath) throws DocumentException{
		Document document = parseXml(xmlPath);
		Element root = document.getRootElement();
		List<Property> list = new ArrayList<Property>();
		for(Iterator i = root.elementIterator();i.hasNext();){
			Element property = (Element) i.next();
			String voProperty = property.attributeValue("voProperty");
			String type = property.attributeValue("type");
			Property p = new Property();
			p.setVoProperty(voProperty);
			p.setType(type);
			list.add(p);
		}
		return list;
	}
}


