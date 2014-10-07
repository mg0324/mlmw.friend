package com.friend.service.ibiz;

import java.util.List;

import com.friend.util.Pager;
import com.friend.vo.Module;

/**
 * 
 * @author 宋启东 2014 07 18 16：13
 *
 */
public interface IModule {
 public Module createModule(Module module);//创建模块
 public void deleteModule(Module module);//删除模块
 public Module getModuleById(int moduleid);//通过ID找到模块
 public Boolean updateModule(Module module);//更新模块
 public List findAllModule(Pager page);//找到所有模块
 public Boolean isExistModule(String moduleName);
 public int getModuleCount();//找到模块总数量
}
