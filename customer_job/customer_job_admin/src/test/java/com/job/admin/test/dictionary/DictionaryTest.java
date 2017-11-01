package com.job.admin.test.dictionary;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.customer.json.JsonUtil;
import com.job.admin.common.PageModel;
import com.job.admin.dictionary.model.DictionaryInfoModel;
import com.job.admin.dictionary.service.DictionaryService;
import com.job.admin.test.util.BaseJunit;

public class DictionaryTest extends BaseJunit{
    @Autowired
    private DictionaryService dictionaryService; 
    @Test
    public void getDictionarys(){
	PageModel pageModel=new PageModel();
	PageModel model=dictionaryService.getDictionarysByParentId(0L,pageModel);
	System.out.println(JsonUtil.objectToJson(model));
    }
    
    @Test
    public void getDictionaryById(){
	DictionaryInfoModel model=dictionaryService.queryById(1L);
	System.out.println(JsonUtil.objectToJson(model));
    }
    @Test
    public void treeStr(){
	String jsonStr=dictionaryService.dictionaryTree(1L);
	System.out.println(jsonStr);
    }
    
}
