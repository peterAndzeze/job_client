package com.job.admin.dictionary.dao;

import java.util.List;

import com.job.admin.common.PageModel;
import com.job.admin.dictionary.model.DictionaryInfoModel;

public interface DictionaryDao {
    /**
     * 
     * @Title: getDictionarysByParentId
     * @Description:查询子字典
     * @author:sunwei
     * @createTime:2017年8月1日上午9:48:07
     * @param parentId
     * @return
     */
    public PageModel getDictionarysByParentId(Long parentId,PageModel pageModel);
    /**
     * 
     * @Title: dictionaryInfoModels
     * @Description:
     * @author:sunwei
     * @createTime:2017年8月1日上午10:42:42
     * @param parentId
     * @return
     */
    public List<DictionaryInfoModel> dictionaryInfoModels(Long parentId);
    /**
     * 
     * @Title: addDictionary
     * @Description:添加字典
     * @author:sunwei
     * @createTime:2017年8月1日下午1:20:01
     * @param dictionaryInfoModel
     */
    public void addDictionary(DictionaryInfoModel dictionaryInfoModel);
    /**
     * 
     * @Title: updateDictionary
     * @Description:修改
     * @author:sunwei
     * @createTime:2017年8月1日下午1:21:20
     * @param dictionaryInfoModel
     */
    public void updateDictionary(DictionaryInfoModel dictionaryInfoModel);
    /**
     * 
     * @Title: deleteDictionary
     * @Description:删除
     * @author:sunwei
     * @createTime:2017年8月1日下午1:30:34
     * @param id
     */
    public void deleteDictionary(Long id);
    /**
     * 
     * @Title: queryInfoModelById
     * @Description: 查询明细
     * @author:sunwei
     * @createTime:2017年8月1日下午2:09:30
     * @param id
     * @return
     */
    public DictionaryInfoModel queryInfoModelById(Long id);
}
