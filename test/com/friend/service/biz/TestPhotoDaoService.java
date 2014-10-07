package com.friend.service.biz;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.friend.dao.TestAlbumDao;
import com.friend.vo.Album;
import com.friend.vo.Photo;
import com.friend.vo.User;

public class TestPhotoDaoService{
//	测试
	public static void main(String[] args){
		Photo p = new Photo();
		Album a = new Album();
		User u = new User();
		TestAlbumDao tad = new TestAlbumDao();
		List<Album> list = new ArrayList<Album>();

//		list=tad.TestListAlbum();
//		System.out.println(list.toString());
//		for(Album album:list){
//			System.out.println(album.getAlbumName());
//		}
		
		
	}
	@Test
	public void testGetDir(){
		String date = new Date().toLocaleString();
		System.out.println(date);
		int index = date.lastIndexOf(" ");
		System.out.println(date.substring(0, index));
	}

}
