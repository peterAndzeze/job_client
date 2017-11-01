package com.job.admin.framework.springmvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.customer.json.JsonUtil;
import com.job.admin.common.PageModel;

/**
 * 
 * @Title: BaseController.java
 * @Package: com.job.admin.framework
 * @Description: 控制层基类
 * @author: sunwei
 * @date: 2017年5月20日 下午1:12:03
 * @version: V1.0
 */
public abstract class BaseController {
    /**
     * 
     * @Title: redirct
     * @Description:重定向跳转
     * @author:sunwei
     * @createTime:2017年5月20日下午1:13:25
     * @param url
     * @return
     */
    protected String redirct(String url){
	return "redirct:"+url;
    }
    /**
     * 
     * @Title: forward
     * @Description:服务跳转
     * @author:sunwei
     * @createTime:2017年5月20日下午1:14:27
     * @param url 跳转地址
     * @return 跳转
     */
    protected String forward(String url){
	return url;
    }
    /**
     * 
     * @Title: resopnseMsg
     * @Description: 响应消息
     * @author:sunwei
     * @createTime:2017年5月22日上午9:50:05
     * @param isSuccess 是否成功
     * @param msg 消息
     * @return json字符串
     */
    protected void resopnseMsg( HttpServletResponse response,boolean isSuccess,String msg){
	Map<String, Object> responseMap=new HashMap<String, Object>();
	responseMap.put("success",isSuccess);
	responseMap.put("msg", msg);
	try {
	    response.getWriter().print(JsonUtil.objectToJson(responseMap));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public void forward(HttpServletResponse response,String url,HttpServletRequest request){
	request.getRequestDispatcher(url);
    }
    /**
     * 
     * @Title: modelAndView
     * @Description:返回页面和消息
     * @author:sunwei
     * @createTime:2017年5月23日上午11:05:41
     * @param url
     * @param params
     * @return
     */
    protected String jsonStrAndState(Object data,boolean isSuccess,String msg){
	ReturnMsgModel returnMsgModel=new ReturnMsgModel(isSuccess, msg, data);
	String str=JsonUtil.objectToJsonExcludeProperty(returnMsgModel);
	return str;
    }
    /**
     * 
     * @Title: jsonStr
     * @Description:shuju 
     * @author:sunwei
     * @createTime:2017年6月1日上午11:12:24
     * @param data
     * @return
     */
    protected String jsonStrData(Object data){
	String str=JsonUtil.objectToJsonExcludeProperty(data);
	return str;
    }
    
    protected String jsonStrListData(Object data){
  	String str=JsonUtil.objectToJson(data);
  	return str;
      }
    /**
     * 
     * @Title: getPath
     * @Description:
     * @author:sunwei
     * @createTime:2017年5月27日上午9:19:24
     * @return
     */
    public abstract String getPath(HttpServletRequest request);
    /**
     * 
     * @Title: createPage
     * @Description:
     * @author:sunwei
     * @createTime:2017年6月1日上午11:09:43
     * @param total
     * @param data
     * @param currentPage
     * @param pageSize
     * @return
     */
    protected PageModel createPage(int total,List<?> data,int currentPage,int pageSize){
	PageModel pageModel=new PageModel();
	pageModel.setPageSize(0);
	pageModel.setRowCount(total);
	pageModel.setRecords(data);
	return pageModel;
    }
}
