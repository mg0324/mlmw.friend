package com.friend.dao;

import java.util.Date;

import java.util.List;

import org.junit.Test;

import com.friend.vo.Album;
import com.friend.vo.User;
	/**
	 * 
	 * @author 李跃磊 2014 7.18 16:50
	 *
	 */
public class TestAlbumDao {
		private AlbumDao albumDao;
		public TestAlbumDao(){
			albumDao = new AlbumDao();
		}
		/**
		 * 测试AblumDao的addAlbum方法
		 * @return 获得相册对象
		 */
		@Test
		public void testAddAlbum(){
			TestUserDao tud = new TestUserDao();
			Album a = new Album();

			a.setAlbumName("nihao");
			a.setAlbumDesc("smile");
			a.setCreateTime(new Date());
//	 		a.setPhotoList(null);
			albumDao = new AlbumDao();
			if(albumDao.addAlbum(a)!=null){
				System.out.println("添加相册成功");
			}
		}
 
		/**
		 * 测试AlbumDao的deleteAlbum方法
		 * @return 相册对象
		 */
		@Test
		public void TestDeleteAlbum(){
			AlbumDao albumDao = new AlbumDao();
			Album a = new Album();
			a.setAlbumId(2);
			if((albumDao.deleteAlbum(a))!=null){
				System.out.println("删除相册成功");
			}
		}
 
		/**
		 *测试AlbumDao的listAlbum方法 
		 * @return 相册List集合
		 */
	
		/*public List<Album> TestListAlbum(){
			User u = new User();
			albumDao = new AlbumDao();
			u.setUserId(1);
			System.out.println("用户id"+u.getUserId());
			List<Album> albums = albumDao.listAlbum(u);
			return albums;	
		}*/
}
