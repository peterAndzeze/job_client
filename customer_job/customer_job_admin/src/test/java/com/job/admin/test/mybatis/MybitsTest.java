package com.job.admin.test.mybatis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

import com.job.admin.common.PageModel;
import com.job.admin.test.util.BaseJunit;

public class MybitsTest extends BaseJunit {
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    @Test
    public void getSql(){
	PageModel pageModel=new PageModel();
	Map<String, Object> paramters=new HashMap<String, Object>();
	pageModel.setCountSql("JobGroupModelMapper.count");
	int count=sqlSessionTemplate.selectOne(pageModel.getCountSql(), paramters);
	System.out.println(count);
    }
}
