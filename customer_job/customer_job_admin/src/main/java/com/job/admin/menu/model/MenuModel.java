package com.job.admin.menu.model;

import java.util.List;

import com.job.admin.common.BaseModel;
/**
 * 
 * @Title: MenuModel.java
 * @Package: com.job.admin.menu.model
 * @Description: 菜单对应类
 * @author: sunwei
 * @date: 2017年5月25日 上午9:31:47
 * @version: V1.0
 */
public class MenuModel extends BaseModel{
    private String menuName;
    private String display;
    private Long parentId;
    private Long ownerId;
    private Long ownerOriginId;
    private String leaf;
    private String path;
    private List<MenuModel> childMenuModels;
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getDisplay() {
        return display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public Long getOwnerOriginId() {
        return ownerOriginId;
    }
    public void setOwnerOriginId(Long ownerOriginId) {
        this.ownerOriginId = ownerOriginId;
    }
   
    public String getLeaf() {
        return leaf;
    }
    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }
    public List<MenuModel> getChildMenuModels() {
        return childMenuModels;
    }
    public void setChildMenuModels(List<MenuModel> childMenuModels) {
        this.childMenuModels = childMenuModels;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    
}
