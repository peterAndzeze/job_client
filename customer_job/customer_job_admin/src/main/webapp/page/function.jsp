<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
logout=function(){
    phenix.Msg.confirm('确认退出','您确定要退出系统吗？',
        function(btn){
            if("yes"==btn){
                doLogout();
            }
        }
    );
}
function doLogout(){
    window.document.body.onbeforeunload = Ext.emptyFn;// 关闭刷新提示，注：在FireFox下无效
    // msg.stop();//关闭短信系统
    IndexMask.show();
    window.location = "${ctxPath}/login.do";
    IndexMask.hide();
}
msg.start = function(){
    msg.refresh();// 刷新状态
}

msg.stop = function(){
    window.clearTimeout(msg.refreshTimer);
}
/**
 * 远程家在菜单
 * 
 * @param {}
 *            obj
 */
function loadMenus(obj){
    phenix.ajax.request("${ctxPath}/menu/getMenus.do",{parentId:null},function(result){
    	if(null!=result.data && true==result.success){
    		var arr=Ext.decode(result.data);
    		var bbar =[ {
                    text : '"${userName}" 您好！',
                    iconCls : 'buser'
                }// 显示用户姓名
               ]
    	   	var tbItem = obj.getBottomToolbar();  
            tbItem.removeAll();  
            tbItem.add(bbar);    
            getFirstItems(tbItem,arr);
            tbItem.add([ '->',{ text : '退出系统',iconCls : 'blogout',tooltip : '退出系统', handler : logout  }]);
            obj.doLayout();     
    	}
    });    
}

function getFirstItems(tbItem,list){
        var items = [];
        if(list == null){
            return items;
        }
        var index = 0;
        for(var i=0; i<list.length; i++){
            var obj = list[i];
            var menu = {
                id:obj.idd,
                text:'<font>'+obj.menuName+'</font>',
                currMyMenu:obj,//把菜单对象放入当前对象中
                iconCls:obj.iconCls?obj.iconCls:'icon-modify',
                listeners:{
                    mouseover:function(){
                        //鼠标悬浮显示菜单
                        this.showMenu();
                    }
                },handler:function(){
                    var curObj = this.currMyMenu;
                    if(curObj.leaf=="0" && null!=curObj.path){
                        var path="${ctxPath}"+curObj.path;
                        var node = {
                                  id : curObj.id,
                                  text : curObj.menuName,
                                  attributes : {
                                      openType : 'TAB',
                                      path : path
                                   }
                         };
                       phenix.util.menuHandle(node);
                    }
                }
            };
            
            //创建下级菜单
            getMenu(obj, menu);
            items[index*2] = menu;
            if(list.length > 1 && i != (list.length - 1)){
                items[index*2+1] = '-';
            }
            index++;
        }
        tbItem.add(items);
        //return items;
    }
    
    /**
     * 递归创建下级菜单
     */
    function getMenu(obj, parentMenu){
        
        var list = obj.childMenuModels;
        if(list == null || list.length == 0){
            return;
        }
        
        var items = [];
        var index = 0;
        for(var i=0; i<list.length; i++){
            
            var currObj = list[i];
            var menu = {
                id:currObj.id,
                text:currObj.menuName,
                currMyMenu:currObj,//把菜单对象放入当前对象中
                handler:function(){
                    var curObj = this.currMyMenu;
                     if(curObj.leaf=="0" && null!=curObj.path){
                        var path="${ctxPath}"+curObj.path;
                        var node = {
                                  id : curObj.id,
                                  text : curObj.menuName,
                                  attributes : {
                                      openType : 'TAB',
                                      path : path
                                   }
                         };
                       phenix.util.menuHandle(node);
                    }
                },
                iconCls:currObj.iconCls?currObj.iconCls:'icon-modify'
            };
            
            //递归创建下级菜单
            getMenu(currObj, menu);
            
            items[index*2] = menu;
            if(list.length > 1 && i != (list.length - 1)){
                items[index*2+1] = '-';
            }
            index++;
        }
        
        var menu = new Ext.menu.Menu({
            items:items
        });
        parentMenu.menu = menu;
    }

function serviceUrl(url){
     phenix.ajax.request(url,null,function(result){
     	
    });	
}
