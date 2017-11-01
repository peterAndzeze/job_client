package com.job.admin.menu.dao;

import java.util.List;

import com.job.admin.menu.model.MenuModel;

public interface MenuDao {
    /**
     * 
     * @Title: queryMenus
     * @Description:查询所有菜单
     * @author:sunwei
     * @createTime:2017年5月25日上午10:13:58
     * @return
     */
    public List<MenuModel> queryMenus(Long parentId);
    /**
     * 
     * @Title: queryMenuModelById
     * @Description:单个菜单信息
     * @author:sunwei
     * @createTime:2017年5月25日上午10:14:59
     * @param id 主键编号
     * @return
     */
    public MenuModel queryMenuModelById(Long id);
    /**
     * 
     * @Title: getMenuModels
     * @Description:得到所有
     * @author:sunwei
     * @createTime:2017年5月25日下午4:21:18
     * @return
     */
    public List<MenuModel> getMenuModels();

    /**
     * 
     * @Title: updateMenu
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月1日下午2:28:57
     * @param menuModel
     */
    public boolean updateMenu(MenuModel menuModel); /**
     * 
     * @Title: addMenu
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年6月1日下午5:44:58
     * @param menuModel
     * @throws Exception
     */
    public boolean addMenu(MenuModel menuModel);
    
    /**
     * 
     * @Title: deleteMenu
     * @Description:删除节点
     * @author:sunwei
     * @createTime:2017年6月2日上午9:26:57
     * @param menuModel
     * @throws Exception
     */
    public boolean deleteMenu(MenuModel menuModel);
}
