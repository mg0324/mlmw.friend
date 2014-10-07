package com.friend.dao;
/**
 * @author 宋启东 2014 07 17 18：38
 */
import java.util.Date;

import org.junit.Test;

import com.friend.vo.Module;

public class TestModuleDao {
	
	// TODO Auto-generated constructor stub
	ModuleDao mdao = new ModuleDao();
	public void testCreateModule(){
		Module module = new Module();
		module.setModuleName("xxxx");
		module.setCreateTime(new Date());
		if(mdao.createModule(module))
			System.out.println("add success");
	}
	//@Test
//     public void TestupdateModule()
//	{
//		UserDao userDao = new UserDao();
//		Module module=new Module();
//		module.setModuleName("aaaa");
//		module.setCreateTime(new Date());
//		module.setUser(userDao.getUserById(7));
//		if(mdao.updateModule(module))
//			System.out.println("change success");
//	}
}
