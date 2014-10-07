package com.friend.util;

import java.sql.Connection;

import org.junit.Test;

/**
 * 
 * @author 梅刚 2014-7-16 18:37
 *
 */
public class TestDBConnect {
	@Test
	public void testGetConnection(){
		System.out.println("xxx");
		Connection conn = DBConnect.getConnect();
		System.out.println(conn);
		DBConnect.close();
	}
}
