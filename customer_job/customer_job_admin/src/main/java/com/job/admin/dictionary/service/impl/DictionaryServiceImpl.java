package com.job.admin.dictionary.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.customer.json.JsonUtil;
import com.job.admin.common.CustmerJobConst;
import com.job.admin.common.PageModel;
import com.job.admin.dictionary.dao.DictionaryDao;
import com.job.admin.dictionary.model.DictionaryInfoModel;
import com.job.admin.dictionary.service.DictionaryService;
import com.job.admin.framework.exception.JobException;
import com.job.admin.menu.model.MenuModel;
import com.job.admin.menu.util.DateUtil;
/**
 * 
 * @Title: DictionaryServiceImpl.java
 * @Package: com.job.admin.dictionary.service.impl
 * @Description: 字典服务
 * @author: sunwei
 * @date: 2017年7月31日 下午8:23:55
 * @version: V1.0
 */
@Service
public class DictionaryServiceImpl implements DictionaryService{
    @Autowired
    private DictionaryDao dictionaryDao;
    /**
     * 
     * @Title: getDictionarysByParentId
     * @Description:获取子节点
     * @author:sunwei
     * @createTime:2017年8月1日上午9:51:54
     * @see com.job.admin.dictionary.service.DictionaryService#getDictionarysByParentId(java.lang.Long)
     * @param parentId
     * @return
     */
    @Override
    public PageModel getDictionarysByParentId(Long parentId,PageModel pageModel) {
	return dictionaryDao.getDictionarysByParentId(parentId,pageModel);
    }

    @Override
    public String dictionaryTree(Long parentId) {
	List<DictionaryInfoModel> dictionaryInfoModels = dictionaryDao.dictionaryInfoModels(parentId);
	List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
	for (DictionaryInfoModel infoModel : dictionaryInfoModels) {
	    Map<String, Object> node = new HashMap<String, Object>();
	    node.put("id", infoModel.getId());
	    node.put("text", infoModel.getDisplay());
	    node.put("parentId", infoModel.getParentId());
	    if (infoModel.getIsLeaf().equals(CustmerJobConst.IS_LEAF)) {
		node.put("leaf", true);
	    } else {
		node.put("leaf", false);

	    }
	    tree.add(node);
	}
	return JsonUtil.objectToJson(tree);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void addDictionary(DictionaryInfoModel dictionaryInfoModel) throws Exception {
	try {
	    dictionaryInfoModel.setCreator("0000");
	    dictionaryInfoModel.setOperator("00000");
	    dictionaryInfoModel.setIsLeaf(CustmerJobConst.IS_LEAF);
	    dictionaryDao.addDictionary(dictionaryInfoModel);
	    DictionaryInfoModel infoModel=new DictionaryInfoModel();
	    infoModel.setId(dictionaryInfoModel.getParentId());
	    infoModel.setIsLeaf(CustmerJobConst.IS_NOT_LEAF);
	    updateDictionary(infoModel);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new JobException("添加失败");
	}
    }

    @Override
    public void updateDictionary(DictionaryInfoModel dictionaryInfoModel) {
	dictionaryInfoModel.setOperator("00000");
	dictionaryDao.updateDictionary(dictionaryInfoModel);
    }

    @Override
    public DictionaryInfoModel queryById(Long id) {
	return dictionaryDao.queryInfoModelById(id);
    }

    @Override
    public List<DictionaryInfoModel> queryByParenId(long parentId) {
	return dictionaryDao.dictionaryInfoModels(parentId);
    }
    
    
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    @Override
    public void deleteInfoModel(DictionaryInfoModel dictionaryInfoModel) throws Exception {
	try {
	    dictionaryDao.deleteDictionary(dictionaryInfoModel.getId());
	    List<DictionaryInfoModel> infoModels=dictionaryDao.dictionaryInfoModels(dictionaryInfoModel.getParentId());
	    if(infoModels.size()==0){
		DictionaryInfoModel infoModel=new DictionaryInfoModel();
		infoModel.setIsLeaf(CustmerJobConst.IS_LEAF);
		infoModel.setParentId(dictionaryInfoModel.getParentId());
		updateDictionary(infoModel);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new JobException("删除失败");
	}
	
    }
}
