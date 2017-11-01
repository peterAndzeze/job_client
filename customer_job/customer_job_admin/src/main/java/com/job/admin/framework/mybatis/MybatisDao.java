package com.job.admin.framework.mybatis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

import com.job.admin.common.BaseModel;
import com.job.admin.common.PageModel;

/**
 * 
 * @Title: MybatisDao.java
 * @Package: com.job.admin.framework.mybatis
 * @Description: mybatis访问工具类
 * @author: sunwei
 * @date: 2017年5月22日 上午10:26:44
 * @version: V1.0
 */
public class MybatisDao<T extends BaseModel>{
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     * 
     * @Title: queryModel
     * @Description:根据业务对象查询
     * @author:sunwei
     * @createTime:2017年5月22日上午10:40:49
     * @param sqlId 执行sql编号
     * @param model 业务model
     * @return model
     */
    public T queryModel(String sqlId,BaseModel model){
	return sqlSessionTemplate.selectOne(sqlId, model);
    }
    /**
     * 
     * @Title: queryModeByProperties
     * @Description:根据参数查询
     * @author:sunwei
     * @createTime:2017年5月25日下午3:08:26
     * @param sqlId sql语句编号
     * @param parameter 参数
     * @return
     */
    public T queryModeByProperties(String sqlId,Map<String, Object> parameter){
	return sqlSessionTemplate.selectOne(sqlId, parameter);
    }
    
    /**
     * 
     * @Title: queryModels
     * @Description:查询数据集合
     * @author:sunwei
     * @createTime:2017年5月25日下午3:32:42
     * @param sqlId
     * @param parameter
     * @return
     */
    public List<T> queryModels(String sqlId,Map<String, Object> parameter){
	return sqlSessionTemplate.selectList(sqlId, parameter);
    }
    /**
     * 
     * @Title: queryModels
     * @Description:
     * @author:sunwei
     * @createTime:2017年5月25日下午3:33:22
     * @param sqlId
     * @return
     */
    public List<T> queryModels(String sqlId){
	return sqlSessionTemplate.selectList(sqlId);
    }
    /**
     * 
     * @Title: createParamter
     * @Description:组装参数
     * @author:sunwei
     * @createTime:2017年5月25日下午3:36:16
     * @param key
     * @param value
     * @return
     */
    protected  Map<String, Object> createParamter(String key,Object value){
	Map<String,Object> map=new HashMap<String, Object>();
	map.put(key, value);
	return map;
    }
    /**
     * 
     * @Title: createParamters
     * @Description:多参数
     * @author:sunwei
     * @createTime:2017年5月25日下午3:40:35
     * @param properties
     * @param values
     * @return
     */
    protected  Map<String, Object> createParamters(String[] properties,Object...values){
	Map<String,Object> map=new HashMap<String, Object>();
	for (int i = 0; i < properties.length; i++) {
	    map.put(properties[i], values[i]);
	}
	return map;
    }
    /**
     * 
     * @Title: updateModel
     * @Description:更新实体类
     * @author:sunwei
     * @createTime:2017年6月1日下午2:32:07
     * @param sqlId
     * @param model
     */
    protected boolean updateModel(String sqlId,BaseModel model){
	int i=sqlSessionTemplate.update(sqlId, model);
	if(i>0){
	    return true;
	}else{
	    return false;
	}
    }
    /**
     * 
     * @Title: updateSql
     * @Description:多属性修改
     * @author:sunwei
     * @createTime:2017年6月1日下午2:36:05
     * @param sqlId
     * @param paremters
     * @return
     */
    protected boolean updateSql(String sqlId,Map<String, Object> paremters){
	int i=sqlSessionTemplate.update(sqlId, paremters);
	if(i>0){
	    return true;
	}else{
	    return false;
	}
    }
    protected int updateSqlReturn(String sqlId,Map<String, Object> paremters){
   	int i=sqlSessionTemplate.update(sqlId, paremters);
   	return i;
     }
    /**
     * 
     * @Title: saveModel
     * @Description:保存
     * @author:sunwei
     * @createTime:2017年6月1日下午5:46:59
     * @param sqlId
     * @param model
     * @return
     */
    protected boolean saveModel(String sqlId,BaseModel model){
	int i=sqlSessionTemplate.insert(sqlId, model);
	if(i>0){
	    return true;
	}else{
	    return false;
	}
    }
    /**
     * 
     * @Title: saveModelReturnPk
     * @Description:返回主键
     * @author:sunwei
     * @createTime:2017年8月23日上午11:50:29
     * @param sqlId
     * @param model
     * @return
     */
    protected int saveModelReturnPk(String sqlId,BaseModel model){
	int i=sqlSessionTemplate.insert(sqlId,model);
	return i;
    }
    
    
    /**
     * 
     * @Title: saveModel
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月1日下午5:47:58
     * @param sqlId
     * @param paramters
     * @return
     */
    protected boolean saveModel(String sqlId,Map<String, Object> paramters){
	int i=sqlSessionTemplate.insert(sqlId, paramters);
	if(i>0){
	    return true;
	}else{
	    return false;
	}
    }
    /**
     * 
     * @Title: deleteModel
     * @Description:删除对象
     * @author:sunwei
     * @createTime:2017年6月2日上午9:32:30
     * @param sqlId
     * @param model
     * @return
     */
    protected boolean deleteModel(String sqlId,BaseModel model){
	int i=sqlSessionTemplate.delete(sqlId, model);
	if(i>0){
	    return true;
	}else{
	    return false;
	}
    }
    /**
     * 
     * @Title: deleteModel
     * @Description:删除对象
     * @author:sunwei
     * @createTime:2017年6月2日上午9:33:52
     * @param sqlId
     * @param paramters
     * @return
     */
    protected boolean deleteModel(String sqlId,Map<String, Object> paramters){
	int i=sqlSessionTemplate.delete(sqlId, paramters);
	if(i>0){
	    return true;
	}else{
	    return false;
	}
    }
    protected int deleteModelReturnCount(String sqlId,Map<String, Object> paramters){
  	return sqlSessionTemplate.delete(sqlId, paramters);
      }
    /**
     * 
     * @Title: queryPage
     * @Description:根据数据查询分页数据
     * @author:sunwei
     * @createTime:2017年6月2日下午1:48:27
     * @param sqlId
     * @param paramters
     * @param pageModel
     * @return
     */
    protected PageModel queryPageByMap(String sqlId,Map<String, Object> paramters,PageModel pageModel){
	int count=sqlSessionTemplate.selectOne(pageModel.getCountSql(), paramters);
	paramters.put("start", pageModel.getStart());
	paramters.put("limit", pageModel.getLimit());
	List<T> dbModels=sqlSessionTemplate.selectList(sqlId, paramters);
	pageModel.setRecords(dbModels);
	pageModel.setRowCount(count);
	return pageModel;
    }
    /**
     * 
     * @Title: queryByProperty
     * @Description:
     * @author:sunwei
     * @createTime:2017年8月7日下午2:35:35
     * @param sqlId
     * @param paramter
     * @return
     */
    protected Object queryByProperty(String sqlId,Object paramter){
	return sqlSessionTemplate.selectOne(sqlId, paramter);
    }
    
    /**
     * 
     * @Title: queryByProperty
     * @Description:
     * @author:sunwei
     * @createTime:2017年8月7日下午2:35:35
     * @param sqlId
     * @param paramter
     * @return
     */
    protected List<Map<String,Object>> queryReturnMap(String sqlId,Map<String, Object> paramter){
	return sqlSessionTemplate.selectList(sqlId, paramter);
    }
    
    
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
