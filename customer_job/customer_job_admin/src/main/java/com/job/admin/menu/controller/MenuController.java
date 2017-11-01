package com.job.admin.menu.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.customer.uuid.CoreUtil;
import com.job.admin.common.PageModel;
import com.job.admin.framework.springmvc.BaseController;
import com.job.admin.menu.model.MenuModel;
import com.job.admin.menu.service.MenuService;

/**
 * 
 * @Title: MenuController.java
 * @Package: com.job.admin.controller
 * @Description: 菜单工具类
 * @author: sunwei
 * @date: 2017年5月25日 上午9:30:36
 * @version: V1.0
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/getMenus", method = RequestMethod.POST)
    @ResponseBody
    public String getMenus(HttpServletRequest request,
	    HttpServletResponse response, Long parentId) {
	String menuModels = menuService.queryMenus(parentId);
	return jsonStrAndState(menuModels, true, "查询成功");
    }

    @RequestMapping("/main")
    @Override
    public String getPath(HttpServletRequest request) {
	request.getServletContext().setAttribute("uuid",
		CoreUtil.genUUIDString());
	request.getServletContext().setAttribute("timeStr",
		System.currentTimeMillis());
	return "page/menu/main";
    }

    /**
     * 
     * @Title: getMenuPage
     * @Description:分页查询
     * @author:sunwei
     * @createTime:2017年5月31日上午3:57:55
     * @param request
     * @param response
     * @param pageModel
     * @param menuModel
     * @return
     */
    @RequestMapping("/getPages")
    @ResponseBody
    public String getMenuPage(HttpServletRequest request,
	    HttpServletResponse response, PageModel pageModel,
	    MenuModel param) {
	MenuModel menuModel=menuService.queryMenuModelById(param.getId());
	List<MenuModel> menuModels=new ArrayList<MenuModel>();
	menuModels.add(menuModel);
	PageModel model=createPage(1, menuModels, 0, 0);
	return jsonStrData(model);
    }
    @RequestMapping("/menuInfo")
    @ResponseBody
    public String getMenuInfo(Long id){
	MenuModel menuModel=menuService.queryMenuModelById(id);
	return jsonStrData(menuModel);
    }
    
    /**
     * 
     * @Title: tree
     * @Description:返回菜单树
     * @author:sunwei
     * @createTime:2017年5月31日下午1:29:37
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("/menuTree")
    @ResponseBody
    public String tree(HttpServletRequest request,HttpServletResponse response,Long id){
	if(null==id){//一级菜单
	    id=0L;
	}
	return menuService.nenuTree(id);
    }
    /**
     * 
     * @Title: saveMenu
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年6月1日下午2:27:33
     * @return
     */
    @RequestMapping("/updateMenu")
    @ResponseBody
    public String updateMenu(HttpServletRequest request,HttpServletResponse response,MenuModel menuModel){
	try {
	    if(null==menuModel.getId()){//新增
		menuService.addMenu(menuModel);
	    }else{//修改
		menuService.updateMenu(menuModel);
	    }
	    return jsonStrAndState("", true, "操作成功");
	} catch (Exception e) {
	    return jsonStrAndState("", false, e.getMessage());
	}
    }
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public String deleteMenu(HttpServletRequest httpServletRequest,HttpServletResponse response,MenuModel menuModel){
	try {
	    menuService.deleteMenu(menuModel);
	    return jsonStrAndState("", true, "操作成功");
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return jsonStrAndState("", false, e.getMessage());
	}
	
    }
}
