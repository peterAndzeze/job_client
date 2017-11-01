package com.job.admin.dictionary.service;

import java.util.List;

import com.job.admin.common.PageModel;
import com.job.admin.dictionary.model.DictionaryInfoModel;

public interface DictionaryService {
    /**
     * 
     * @Title: getDictionarysByParentId
     * @Description:根据子节点
     * @author:sunwei
     * @createTime:2017年8月1日上午9:40:44
     * @param parentId
     * @return
     */
    public PageModel getDictionarysByParentId(Long id,PageModel pageModel);
    /**
     * 
     * @Title: dictionaryTree
     * @Description:获取字典树
     * @author:sunwei
     * @createTime:2017年8月1日上午10:31:03
     * @param parentId
     * @return
     */
    public String dictionaryTree(Long parentId);
    
    /**
     * 
     * @Title: addDictionary
     * @Description:新增
     * @author:sunwei
     * @createTime:2017年8月1日上午11:53:12
     * @param dictionaryInfoModel
     */
    public void addDictionary(DictionaryInfoModel dictionaryInfoModel) throws Exception ;
    /**
     * 
     * @Title: updateDictionary
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年8月1日上午11:53:49
     * @param dictionaryInfoModel
     */
    public void updateDictionary(DictionaryInfoModel dictionaryInfoModel) throws Exception ;
    /**
     * 
     * @Title: queryById
     * @Description:查询明细
     * @author:sunwei
     * @createTime:2017年8月1日下午1:33:29
     * @param id
     * @return
     */
    public DictionaryInfoModel queryById(Long id);
    /**
     * 
     * @Title: queryByParenId
     * @Description:集合
     * @author:sunwei
     * @createTime:2017年8月1日下午2:16:01
     * @param parentId
     * @return
     */
    public List<DictionaryInfoModel> queryByParenId(long parentId);
    /**
     * 
     * @Title: deleteInfoModel
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年8月1日下午2:31:35
     * @param dictionaryInfoModel
     */
    public void deleteInfoModel(DictionaryInfoModel dictionaryInfoModel) throws Exception ;
    
    
}

