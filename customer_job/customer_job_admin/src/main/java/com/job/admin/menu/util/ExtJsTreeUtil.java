package com.job.admin.menu.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * 树形结构EXTJS支持类
 * @author KOBE
 * CreateTime:2012-12-03
 */
public class ExtJsTreeUtil {
	private static Logger log = Logger.getLogger(ExtJsTreeUtil.class);
	/**
	 * 将一个树形数据转换成ExtJs TreeNode格式
	 * 带安全检查
	 * @param obj 树形数据
	 * @param levCnt 层记数
	 * @param childPropNames 子属性名称
	 * @param otherPropNames 其他属性名称
	 * @return 转换成的ExtJs TreeNode格式
	 * @throws Exception 异常
	 */
	@SuppressWarnings("rawtypes")
	public static Map convToTreeNodeWithSecurity(Object obj, Integer levCnt, String[] childPropNames, String... otherPropNames) throws Exception{
		return createTreeNode(true, false, levCnt, obj, childPropNames, null, null, null, otherPropNames);
	}
	
	/**
	 * 将一个树形数据转换成ExtJs TreeNode格式
	 * @param obj 树形数据
	 * @param levCnt 层记数
	 * @param childPropNames 子属性名称
	 * @param otherPropNames 其他属性名称
	 * @return 转换成的ExtJs TreeNode格式
	 * @throws Exception 异常
	 */
	@SuppressWarnings("rawtypes")
	public static Map convToTreeNode(Object obj, Integer levCnt, String[] childPropNames, String... otherPropNames) throws Exception{
		return createTreeNode(false, false, levCnt, obj, childPropNames, null, null, null, otherPropNames);
	}
	
	/**
	 * 将一个树形数据转换成ExtJs TreeNode格式
	 * 多种类型的子节点
	 * @param obj 树形数据
	 * @param levCnt 层记数
	 * @param childPropNames 子属性名称
	 * @param childIconCls 子图表图标样式
	 * @param otherPropNames 其他属性名称
	 * @return 转换成的ExtJs TreeNode格式
	 * @throws Exception 异常
	 */
	@SuppressWarnings("rawtypes")
	public static Map convToTreeNode(Object obj, Integer levCnt, String[] childPropNames, String[] childIconCls, String... otherPropNames)
			throws Exception{
		return createTreeNode(false, false, levCnt, obj, childPropNames, childIconCls, null, null, otherPropNames);
	}
	
	/**
	 * 将一个树形数据转换成ExtJs 带复选框的TreeNode格式
	 * @param obj 树形数据
	 * @param levCnt 层记数
	 * @param childPropNames 子属性名称
	 * @param otherPropNames 其他属性名称
	 * @return 树形数据转换成的ExtJs 带复选框的TreeNode格式
	 * @throws Exception 异常
	 */
	@SuppressWarnings("rawtypes")
	public static Map convToCheckTreeNode(Object obj, Integer levCnt, String[] childPropNames, String... otherPropNames) throws Exception{
		return createTreeNode(false, true, levCnt, obj, childPropNames, null, null, null, otherPropNames);
	}
	
	/**
	 * 将一个树形数据转换成ExtJs 带复选框的TreeNode格式
	 * 可带多个子属性参数
	 * @param obj 树形数据
	 * @param levCnt 层记数
	 * @param childPropNames 子属性名称
	 * @param idSet pkid集合
	 * @param otherPropNames 其他属性名称
	 * @return 转换成的ExtJs TreeNode格式
	 * @throws Exception 异常
	 */
	@SuppressWarnings("rawtypes")
	public static Map convToCheckTreeNode(Object obj, Integer levCnt, String[] childPropNames, Set<String> idSet, String... otherPropNames) 
			throws Exception{
		return createTreeNode(false, true, levCnt, obj, childPropNames, null, null, idSet, otherPropNames);
	}

	/**
	 * 创建树节点
	 * @param isSec 是否经过权限过滤
	 * @param withChecked 是否检查
	 * @param obj 对象
	 * @param levCnt 层记数
	 * @param childPropNames 子属性名称
	 * @param childIconCls 子图标样式
	 * @param iconClsIdx 子图标样式索引
	 * @param idSet id集合
	 * @param otherPropNames 其他属性名称
	 * @return 树节点
	 * @throws IllegalAccessException 非法存取异常
	 * @throws InvocationTargetException 调用目标异常
	 * @throws NoSuchMethodException 没有此方法异常
	 * @throws Exception 异常
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map createTreeNode(boolean isSec, boolean withChecked, Integer levCnt, Object obj,
			String[] childPropNames, String[] childIconCls, Integer iconClsIdx, Set<String> idSet, String... otherPropNames)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, Exception {
		if(null==levCnt || levCnt<=0) {
			return null;
		}		
		Map node = new HashMap();
		Long pkid = (Long)PropertyUtils.getProperty(obj, "id");
		String text = (String)PropertyUtils.getProperty(obj, "menuName");
		node.put("id",pkid);//pkid
		node.put("text",text);//text
		node.put("qtip", text);//tip
		node.put("sortIdx",PropertyUtils.getProperty(obj, "sortIdx"));
		if(null!=PropertyUtils.getProperty(obj, "class")) {
			node.put("className",PropertyUtils.getProperty(obj, "class.name"));
		}
		/*
		 * 带checkbox
		 */
		if(withChecked) {
			node.put("checked", false);
		}
		if(null!=idSet && idSet.contains(pkid)){
			log.debug("contains text="+text);
			node.put("checked", true);
		}
		/*
		 * 加入图标
		 */
		String iconCls = (String)PropertyUtils.getProperty(obj, "iconCls");
		if(null!=iconClsIdx && null!=childIconCls && childIconCls.length>0 && iconClsIdx<childIconCls.length){
			String theCls = childIconCls[iconClsIdx];
			if(null!=theCls && !"".equals(theCls)){
				iconCls = theCls;
			}
		}
		//System.out.println("iconCls="+iconCls);
		if(null!=iconCls) {
			node.put("iconCls",iconCls);
		}
		/*
		 * 其它属性
		 */
		for(String otherPropName : otherPropNames){
			node.put(otherPropName, PropertyUtils.getProperty(obj, otherPropName));
		}
		/*
		 * 处理子节点
		 */
		boolean hasSubs = false;
		Collection<Map> children = new ArrayList<Map>();
		for(int i=0; i<childPropNames.length; i++){
			String childPropName = childPropNames[i];
			Collection subCol = null;
			try{
				subCol = (Collection)PropertyUtils.getProperty(obj, childPropName);
				if(null!=subCol && subCol.size()>0) {
					hasSubs = true;
				}
			}catch (Exception e){}
			if(null!=subCol){
				for(Object child : subCol){
					Map childNode = createTreeNode(isSec, withChecked, levCnt-1, child, childPropNames, 
							childIconCls, i, idSet, otherPropNames);
					if(null!=childNode) {
						children.add(childNode);
					}
				}
				if(hasSubs){
					node.put("leaf",false);//not leaf
					if(children.size()>0) {
						node.put("children",children);//children
					}
				}else{
					node.put("leaf",true);//leaf
				}
			}else{
				node.put("leaf",true);//leaf
			}
		}
		return node;
	}
	
	/**
	 * 将一个树形数据转换成ExtJs ColumnTreeNode格式
	 * @param obj 树形数据
	 * @param childPropName 子属性名称
	 * @param propNames 属性名称
	 * @return 树形数据转换成的ExtJs ColumnTreeNode格式
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map convToColumnTreeNode(Object obj, String childPropName, String... propNames ){
		Map node = new HashMap();
		node.put("uiProvider", "col");
		
		try{
			for(String propName : propNames){
				Object value = PropertyUtils.getProperty(obj, propName);
				node.put(propName, value);
			}
			node.put("sortIdx", PropertyUtils.getProperty(obj, "sortIdx"));
			String iconCls = (String)PropertyUtils.getProperty(obj, "iconCls");
			if(null!=iconCls) {
				node.put("iconCls",iconCls);
			}
			
			Collection col = (Collection)PropertyUtils.getProperty(obj, childPropName);
			if(null!=col && col.size()>0){
				node.put("leaf", false);
				Collection<Map> children = new ArrayList<Map>();
				for(Object child:col){
					Map childNode = convToColumnTreeNode(child, childPropName, propNames);
					children.add(childNode);
				}
				node.put("children", children);
			}else{
				node.put("leaf", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return node;
	}
}