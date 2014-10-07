package com.friend.util;

import org.dom4j.DocumentException;
import org.junit.Test;

/**
 * 
 * @author 梅刚 2014-7-23
 *
 */
public class TestStringUtil {
	@Test
	public void testGetFileExtention(){
		String fileName = "1.jpg.jpgxx";
		int index = fileName.lastIndexOf(".");
		
		String extention = fileName.substring(index,fileName.length());
		System.out.println(extention);
	}
	@Test
	public void testXml1() throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		String url = TestStringUtil.class.getClass().getResource("/").getPath();
		System.out.println(url);
		XmlParseUtil.loadModuleDao(url+"daoFactory.xml","user");
		XmlParseUtil.loadModuleDao(url+"daoFactory.xml", "bbs");
		
		System.out.println(DaoFactory.factory.toString());
	}
	@Test
	public void testGetFirstUpper(){
		String str = StringUtil.getFirstUpperString("userName");
		System.out.println(str);
	}
}
