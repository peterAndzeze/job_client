package com.job.admin.menu.service;

import com.job.admin.menu.model.MenuModel;

/**
 * 
 * @Title: MenuService.java
 * @Package: com.job.admin.menu.service
 * @Description: 菜单操作类
 * @author: sunwei
 * @date: 2017年5月25日 上午9:52:24
 * @version: V1.0
 */
public interface MenuService {
    /**
     * 
     * @Title: queryMenus
     * @Description:查询所有菜单
     * @author:sunwei
     * @createTime:2017年5月25日上午10:13:58
     * @return
     */
    public String queryMenus(Long parentId);
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
     * @Title: nenuTree
     * @Description: 根据id 查询菜单树
     * @author:sunwei
     * @createTime:2017年5月31日下午1:31:52
     * @param id
     * @return
     */
    public String nenuTree(Long id);
    /**
     * 
     * @Title: updateMenu
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月1日下午2:28:57
     * @param menuModel
     */
    public void updateMenu(MenuModel menuModel) throws Exception;
    /**
     * 
     * @Title: addMenu
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年6月1日下午5:44:58
     * @param menuModel
     * @throws Exception
     */
    public void addMenu(MenuModel menuModel) throws Exception;
    /**
     * 
     * @Title: deleteMenu
     * @Description:删除节点
     * @author:sunwei
     * @createTime:2017年6月2日上午9:26:57
     * @param menuModel
     * @throws Exception
     */
    public void deleteMenu(MenuModel menuModel) throws Exception;
}
