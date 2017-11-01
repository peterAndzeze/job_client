package com.job.admin.menu.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.job.admin.menu.model.MenuModel;

public class TreeUtil {
    
    public void createTree(){
	
    }
    
    public static String createMenus(List<MenuModel> menuModels,Long parentId){//一级自定义节点0
	Iterator<MenuModel> iterator=menuModels.iterator();
	MenuModel menuModel=null;
	while (iterator.hasNext()) {
	    menuModel=iterator.next();
	    if(menuModel.getParentId()==parentId){//二级节点
		iterator.remove();//删除
		isHaveChilds(menuModels, menuModel);
	    }
	}
	return null;
    }
    /**
     * 
     * @Title: isHaveChilds
     * @Description:得到子节点
     * @author:sunwei
     * @createTime:2017年5月26日上午8:51:18
     * @param menuModels
     * @return
     */
    public static void isHaveChilds(List<MenuModel> menuModels,MenuModel menuModel){
	Iterator<MenuModel> iterator=menuModels.iterator();
	while(iterator.hasNext()){
	    MenuModel childMenuModel=iterator.next();
	    if(childMenuModel.getParentId()==menuModel.getId()){
		List<MenuModel> childModels=null;
		if(null==menuModel.getChildMenuModels()){
		    childModels=new ArrayList<MenuModel>();
		    childModels.add(childMenuModel);
		    menuModel.setChildMenuModels(childModels);
		}else{
		    menuModel.getChildMenuModels().add(childMenuModel);
		}
	    }
	}
    }
}
