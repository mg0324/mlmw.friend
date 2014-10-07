package com.friend.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.friend.vo.Permission;

/**
 * 字符串工具类
 * @author 梅刚 2014-7-17 11:04
 *
 */
public class StringUtil {
	/**
	 * 将字符串类型的日期 转换 成Date类型
	 * @param dateString 字符串日期
	 * @return Date类型日期
	 */
	public static Date stringConvertDate(String dateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 字符串转日期带有时分秒的
	 * @param dateString
	 * @return
	 */
	public static Date stringConvertDateWithHMS(String dateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 通过文件名得到文件的扩展名
	 * @param fileName
	 * @return .jpg,.png
	 */
	public static String getFileExtention(String fileName){
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index,fileName.length());
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static String replaceRightLine(String path){
		return path.replace("\\", "/");
	}
	/**
	 * 取当前时间的年月日做为目录
	 * @return
	 */
	public static String getCurrentDateInDir(){
		String date = new Date().toLocaleString();
		int index = date.lastIndexOf(" ");
		return date.substring(0, index);
	}
	/**
	 * 将系统ISO-8859-1的乱码转成utf-8
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String handleISO88591ToUTF(String str) throws UnsupportedEncodingException{
		return new String(str.getBytes("ISO-8859-1"),"UTF-8");
	}
	/**
	 * 得到首字母大写的字符串
	 * @param str
	 * @return
	 */
	public static String getFirstUpperString(String str){
		return str.substring(0,1).toUpperCase() + str.substring(1,str.length());
	}
	/**
	 * 得到以分号间隔的permissionId字符串，从list中
	 * @param ps
	 * @return
	 */
	public static String getPermissionIdsFromList(List<Permission> ps){
		String permissionIds = ps.get(0).getPermissionId()+"";
		for(int i = 1;i<ps.size();i++){
			permissionIds += ";" + ps.get(i).getPermissionId();
		}
		return permissionIds;
	}
	/**
	 * 压缩日志内容，重要的
	 * @param content
	 * @return
	 */
	public static String cutImpContent(String content) {
		// TODO Auto-generated method stub
		return null;
	}
}
