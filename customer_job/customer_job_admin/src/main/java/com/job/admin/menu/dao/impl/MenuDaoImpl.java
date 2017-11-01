package com.job.admin.menu.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.job.admin.framework.mybatis.MybatisDao;
import com.job.admin.menu.dao.MenuDao;
import com.job.admin.menu.model.MenuModel;
/**
 * 
 * @Title: MenuDaoImpl.java
 * @Package: com.job.admin.menu.dao
 * @Description: 菜单
 * @author: sunwei
 * @date: 2017年5月25日 上午10:32:26
 * @version: V1.0
 */
@Repository("menuDao")
public class MenuDaoImpl extends MybatisDao<MenuModel> implements MenuDao{
    
    @Override
    public List<MenuModel> queryMenus(Long parentId) {
	return queryModels("MenuModelMapper.queryMenus", createParamter("parentId", parentId));
    }

    @Override
    public MenuModel queryMenuModelById(Long id) {
	return queryModeByProperties("MenuModelMapper.queryMenuModelById", createParamter("id", id));
    }
    /**
     * 
     * @Title: getMenuModels
     * @Description:得到所有
     * @author:sunwei
     * @createTime:2017年5月25日下午4:21:39
     * @see com.job.admin.menu.dao.MenuDao#getMenuModels()
     * @return
     */
    public List<MenuModel> getMenuModels(){
	return queryModels("MenuModelMapper.getMenuModels");
    }
    
    /**
     * 
     * @Title: updateMenu
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月1日下午2:28:57
     * @param menuModel
     */
    public boolean updateMenu(MenuModel menuModel){
	 return updateModel("MenuModelMapper.updateMenu", menuModel);
	 
    }
    /**
     * 
     * @Title: addMenu
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年6月1日下午5:44:58
     * @param menuModel
     * @throws Exception
     */
    @Override
    public boolean addMenu(MenuModel menuModel){
	 return saveModel("MenuModelMapper.saveMenu", menuModel);
    }
    /**
     * 
     * @Title: deleteMenu
     * @Description:删除节点
     * @author:sunwei
     * @createTime:2017年6月2日上午9:26:57
     * @param menuModel
     * @throws Exception
     */
    @Override
    public boolean deleteMenu(MenuModel menuModel){
	return deleteModel("MenuModelMapper.deleteMenu", menuModel);
    }
}
