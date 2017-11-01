package com.job.admin.dictionary.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.job.admin.common.PageModel;
import com.job.admin.dictionary.dao.DictionaryDao;
import com.job.admin.dictionary.model.DictionaryInfoModel;
import com.job.admin.framework.mybatis.MybatisDao;
/**
 * 
 * @Title: DictionaryDaoImpl.java
 * @Package: com.job.admin.dictionary.dao.impl
 * @Description: 字典
 * @author: sunwei
 * @date: 2017年7月31日 下午8:25:21
 * @version: V1.0
 */
@Repository
public class DictionaryDaoImpl extends MybatisDao<DictionaryInfoModel> implements DictionaryDao {
    /**
     * 
     * @Title: getDictionarysByParentId
     * @Description:查询子字典
     * @author:sunwei
     * @createTime:2017年8月1日上午9:48:35
     * @see com.job.admin.dictionary.dao.DictionaryDao#getDictionarysByParentId(java.lang.Long)
     * @param parentId
     * @return
     */
    @Override
    public PageModel getDictionarysByParentId(Long parentId,PageModel pageModel) {
	pageModel.setCountSql("DictionaryMapper.pageListCount");
	PageModel model=queryPageByMap("DictionaryMapper.pageList", createParamter("parentId", parentId), pageModel);
	return model;
    }

    @Override
    public List<DictionaryInfoModel> dictionaryInfoModels(Long parentId) {
	return queryModels("DictionaryMapper.list", createParamter("parentId", parentId));
    }

    @Override
    public void addDictionary(DictionaryInfoModel dictionaryInfoModel) {
	saveModel("DictionaryMapper.save", dictionaryInfoModel);
    }

    @Override
    public void updateDictionary(DictionaryInfoModel dictionaryInfoModel) {
	updateModel("DictionaryMapper.update", dictionaryInfoModel);
	
    }

    @Override
    public void deleteDictionary(Long id) {
	deleteModel("DictionaryMapper.delete", createParamter("id", id));
    }

    @Override
    public DictionaryInfoModel queryInfoModelById(Long id) {
	return queryModeByProperties("DictionaryMapper.info", createParamter("id", id));
    }
    
    

}
