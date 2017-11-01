package com.job.admin.dictionary.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.customer.uuid.CoreUtil;
import com.job.admin.common.PageModel;
import com.job.admin.dictionary.model.DictionaryInfoModel;
import com.job.admin.dictionary.service.DictionaryService;
import com.job.admin.framework.springmvc.BaseController;
@Controller
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController {
    @Autowired
    private DictionaryService dictionaryService;
    @RequestMapping("/main")
    @Override
    public String getPath(HttpServletRequest request) {
	request.getServletContext().setAttribute("uuid",
		CoreUtil.genUUIDString());
	request.getServletContext().setAttribute("timeStr",
		System.currentTimeMillis());
	return "/page/dictionary/main";
    }
    /**
     * 
     * @Title: queryDics
     * @Description:获取
     * @author:sunwei
     * @createTime:2017年7月31日下午8:39:38
     * @param dictionaryInfoModel
     * @return
     */
    @RequestMapping(value="/queryDics",method=RequestMethod.POST)
    @ResponseBody
    public String queryDics(@RequestParam(value="id",defaultValue="0",required=false) Long parentId,PageModel pageModel){
	PageModel model= dictionaryService.getDictionarysByParentId(parentId,pageModel);
	return jsonStrData(model);
    }
    /**
     * 
     * @Title: dictionaryTree
     * @Description:
     * @author:sunwei
     * @createTime:2017年8月1日上午10:24:56
     * @param parentId
     * @return
     */
    @RequestMapping("/dictionaryTree")
    @ResponseBody
    public String dictionaryTree(@RequestParam(value="id",defaultValue="0",required=false) Long parentId){
	 String jsonStr=dictionaryService.dictionaryTree(parentId);
	 return jsonStr;
    }
    
    @RequestMapping("/updateDictionary")
    @ResponseBody
    public String updateDictionary(DictionaryInfoModel dictionaryInfoModel){
	try {
	    if(null==dictionaryInfoModel.getId()){//新增
		dictionaryService.addDictionary(dictionaryInfoModel);
	    }else{//修改
		dictionaryService.updateDictionary(dictionaryInfoModel);
	    }
	    return jsonStrAndState("", true, "操作成功");
	} catch (Exception e) {
	    return jsonStrAndState("", false, e.getMessage());
	}
    }
    
    
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteDictionary(DictionaryInfoModel infoModel){
	try {
	    dictionaryService.deleteInfoModel(infoModel);
	    return jsonStrAndState("", true, "操作成功");
	} catch (Exception e) {
	    e.printStackTrace();
	    return jsonStrAndState("", false, e.getMessage());
	}
    }
    
    
    @RequestMapping("/info")
    @ResponseBody
    public String dictionaryInfo(@RequestParam(value="id",required=false) Long id){
	DictionaryInfoModel dictionaryInfoModel=dictionaryService.queryById(id);
	return jsonStrData(dictionaryInfoModel);
    }
    
    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestParam(value="parentId",required=false) Long parentId){
	List<DictionaryInfoModel> dictionaryInfoModels=dictionaryService.queryByParenId(parentId);
	List<DictionaryInfoModel> dictionarys=new ArrayList<DictionaryInfoModel>();
	DictionaryInfoModel dictionary =null;
	for (DictionaryInfoModel dictionaryInfoModel : dictionaryInfoModels) {
	    dictionary=new DictionaryInfoModel();
	    dictionary.setValue(dictionaryInfoModel.getValue());
	    dictionary.setDisplay(dictionaryInfoModel.getDisplay());
	    dictionarys.add(dictionary);
	}
	return jsonStrListData(dictionarys);
    }
}
