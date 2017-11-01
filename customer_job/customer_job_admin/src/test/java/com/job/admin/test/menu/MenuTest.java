package com.job.admin.test.menu;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.job.admin.menu.dao.MenuDao;
import com.job.admin.menu.model.MenuModel;
import com.job.admin.menu.service.MenuService;
import com.job.admin.test.util.BaseJunit;

public class MenuTest extends BaseJunit {
    @Resource
    private MenuService menuService;
    @Resource
    private MenuDao menuDao;
    @Test
    public void getMenus(){
//	List<MenuModel> menuModels= menuDao.queryMenus(0L);
//	System.out.println(menuModels.size());
    }
    
    @Test
    public void getMenu(){
//	MenuModel menuModels=menuService.queryMenuModelById(1L);
//	System.out.println(menuModels.getMenuName());
    }
}
