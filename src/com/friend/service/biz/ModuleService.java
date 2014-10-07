package com.friend.service.biz;
/**
 * 
 * @author 宋启东 2014 07 18 16：13
 *
 */
import java.util.List;

import org.dom4j.DocumentException;

import com.friend.dao.ModuleDao;
import com.friend.service.ibiz.IModule;
import com.friend.util.DaoFactory;
import com.friend.util.Pager;
import com.friend.vo.Module;

public class ModuleService implements IModule{
	private static ModuleDao moduleDao;
	static{
		try {
			DaoFactory.loadModuleDaoToFactory("bbs");
			moduleDao = (ModuleDao) DaoFactory.factory.get("moduleDao");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  
	@Override
	public  Module createModule(Module module) {
		// TODO Auto-generated method stub
		
		moduleDao.createModule(module);
		return module;
	}

	@Override
	public void deleteModule(Module module) {
		// TODO Auto-generated method stub
		moduleDao.deleteModule(module);
		
	}

	@Override
	public Module getModuleById(int moduleid) {
		// TODO Auto-generated method stub
		return moduleDao.getModuleById(moduleid);
		
	}

	@Override
	public Boolean updateModule(Module module) {
		// TODO Auto-generated method stub
		moduleDao.updateModule(module);
		return null;
	}

	@Override
	public List<Module> findAllModule(Pager page) {
		// TODO Auto-generated method stub
		return moduleDao.findAllModule( page);
		
	}
	public Boolean isExistModule(String moduleName)
	{
		if(moduleDao.getModuleByName(moduleName)==null)
		{
			return true;
		}
		else
			return false;
	}

	@Override
	public int getModuleCount() {
		// TODO Auto-generated method stub
		return moduleDao.getModuleCount();
	}
}
