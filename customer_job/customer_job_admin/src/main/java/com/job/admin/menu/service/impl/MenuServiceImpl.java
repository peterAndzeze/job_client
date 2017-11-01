package com.job.admin.menu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.customer.json.JsonUtil;
import com.job.admin.common.CustmerJobConst;
import com.job.admin.framework.exception.JobException;
import com.job.admin.menu.dao.MenuDao;
import com.job.admin.menu.model.MenuModel;
import com.job.admin.menu.service.MenuService;
import com.job.admin.menu.util.MenuUtil;
/**
 * 
 * @Title: MenuServiceImpl.java
 * @Package: com.job.admin.menu.service.impl
 * @Description: 菜单服务类
 * @author: sunwei
 * @date: 2017年5月25日 下午2:50:42
 * @version: V1.0
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;
    /**
     * 
     * @Title: queryMenus
     * @Description:获取菜单集合
     * @author:sunwei
     * @createTime:2017年5月25日下午2:54:11
     * @see com.job.admin.menu.service.MenuService#queryMenus(java.lang.Long)
     * @param parentId 父节点编号
     * @return 
     */
    @Override
    public String queryMenus(Long parentId) {
	List<MenuModel> menuModels= menuDao.queryMenus(parentId);
	String menuStr=MenuUtil.createMenus(menuModels, parentId);
	return menuStr;
    }
    /**
     * 
     * @Title: queryMenuModelById
     * @Description:查询菜单明细
     * @author:sunwei
     * @createTime:2017年5月25日下午3:02:21
     * @see com.job.admin.menu.service.MenuService#queryMenuModelById(java.lang.Long)
     * @param id 菜单编号
     * @return 菜单信息
     */
    @Override
    public MenuModel queryMenuModelById(Long id) {
	return menuDao.queryMenuModelById(id);
    }
    /**
     * 
     * @Title: getMenuModels
     * @Description:得到所有
     * @author:sunwei
     * @createTime:2017年5月25日下午4:20:58
     * @see com.job.admin.menu.service.MenuService#getMenuModels()
     * @return
     */
    @Override
    public String  nenuTree(Long id){
	List<MenuModel> menuModels=menuDao.queryMenus(id);
	List<Map<String, Object>> tree=new ArrayList<Map<String, Object>>();
	for (MenuModel menuModel : menuModels) {
	    Map<String, Object> node=new HashMap<String, Object>();
	    node.put("id", menuModel.getId());
	    node.put("text", menuModel.getMenuName());
	    node.put("parentId", menuModel.getParentId());
	    if(menuModel.getLeaf().equals(CustmerJobConst.IS_LEAF)){
		node.put("leaf", true);
	    }else{
		node.put("leaf", false);
		
	    }
	    node.put("path", menuModel.getPath());
	    tree.add(node);
	}
	return JsonUtil.objectToJson(tree);
    }
    /**
     * 
     * @Title: updateMenu
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月1日下午2:28:57
     * @param menuModel
     */
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void updateMenu(MenuModel menuModel) throws Exception{
	try {
	    menuDao.updateMenu(menuModel);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("【"+menuModel.getId()+"】修改失败！！！");
	}
    }
    
    
    /**
     * 
     * @Title: addMenu
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年6月1日下午5:44:58
     * @param menuModel
     * @throws JobException 
     * @throws Exception
     */
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void addMenu(MenuModel menuModel) throws Exception {
	try {
	    menuModel.setLeaf(CustmerJobConst.IS_LEAF);
	    menuDao.addMenu(menuModel);
	    MenuModel model=new MenuModel();
	    model.setId(menuModel.getParentId());
	    model.setLeaf(CustmerJobConst.IS_NOT_LEAF);//
	    menuDao.updateMenu(model);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    throw new Exception("添加失败！！！");
	}
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
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void deleteMenu(MenuModel menuModel) throws Exception{
	try {
	    menuDao.deleteMenu(menuModel);
	    List<MenuModel> menuModels=menuDao.queryMenus(menuModel.getParentId());
	    if(menuModels.size()==0){
	        MenuModel model=new MenuModel();
	        model.setId(menuModel.getParentId());
	        model.setLeaf(CustmerJobConst.IS_LEAF);
	        menuDao.updateMenu(model);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception("【"+menuModel.getId()+"】删除失败");
	}
    }
    
}
