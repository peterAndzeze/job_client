phenix.Msg = {};//phenix消息函数集

phenix.Msg.msgBox = null;
phenix.Msg.mainMask = null;

phenix.Msg.waitMask = function(){
	if(!phenix.Msg.mainMask){
		phenix.Msg.mainMask = new Ext.LoadMask(Ext.getBody(), {msg:loadMsg});
	}
	phenix.Msg.mainMask.show();
	return phenix.Msg.mainMask;
}

/**
 * 显示遮罩
 */
phenix.Msg.showMask = function(maskMsg){
	if(!maskMsg) maskMsg = loadMsg;
//	if(!phenix.Msg.mainMask){
//		phenix.Msg.mainMask.hide();
//	}
	phenix.Msg.hideMask();
	phenix.Msg.mainMask = new Ext.LoadMask(Ext.getBody(), {msg:maskMsg});
	phenix.Msg.mainMask.show();
	return phenix.Msg.mainMask;
}
/**
 * 关闭遮罩
 */
phenix.Msg.hideMask = function(){
	if(phenix.Msg.mainMask){
		phenix.Msg.mainMask.hide();
	}
}
/**
 * 消息－信息
 * @param {Object} msg
 */
phenix.Msg.info = function(msg,callback,timeout){
	var patt=new RegExp("(.*<[^>]+>[\\n\\r\\t]*){5,}");
	if(patt.test(msg)){
		msg = "<div style='overflow:auto; height:100px;'>" + msg + "</div>";
	}
	var msgBox = Ext.Msg.show({
		title: '信息',
		msg: msg,
		icon: Ext.Msg.INFO,
		buttons: Ext.Msg.OK,
		animEl: Ext.getBody(),
		minWidth:250,
		fn:callback
	});
	phenix.Msg.msgBox = msgBox;
	if(timeout) window.setTimeout(function(){msgBox.hide();}, timeout);
}
/**
 * 消息－确认
 * @param {Object} msg
 */
phenix.Msg.confirm = function(title,msg,callback){
	var msgBox = Ext.Msg.confirm(title,msg,callback);
	phenix.Msg.msgBox = msgBox;
}
/**
 * 提示输入信息
 * @param {} msg
 * @param {} callback
 */
phenix.Msg.prompt = function(title,msg,callback){
	/*Ext.Msg.prompt('Name', 'Please enter your name:', function(btn, text){
    if (btn == 'ok'){
        // 处理文本值，然后关闭...
    }
    });*/
	var msgBox = Ext.Msg.prompt(title,msg,callback);
	phenix.Msg.msgBox = msgBox;
}
/**
 * 消息－警告
 * @param {Object} msg
 */
phenix.Msg.warn = function(msg,callback){
	var msgBox = Ext.Msg.show({
		title: '警告',
		msg: msg,
		icon: Ext.Msg.WARNING,
		buttons: Ext.Msg.OK,
		//animEl: Ext.getBody(),
		minWidth:250,
		fn:callback
	});
	phenix.Msg.msgBox = msgBox;
}
/**
 * 消息－错误
 * @param {Object} msg
 */
phenix.Msg.error = function(msg,callback){
	var msgBox = Ext.Msg.show({
		title: '错误',
		msg: msg,
		icon: Ext.Msg.ERROR,
		buttons: Ext.Msg.OK,
		//animEl: Ext.getBody(),
		minWidth:250,
		fn:callback
	});
	phenix.Msg.msgBox = msgBox;
}
/**
 * 无确认按钮的info
 * @param {} url
 * @param {} params
 * @param {} callback
 */
phenix.Msg.tip = function(msg,callback){
	var msgBox = Ext.Msg.show({
		title: '提示',
		msg: msg,
		icon: Ext.Msg.INFO,
		//animEl: Ext.getBody(),
		fn:callback
	});
	phenix.Msg.msgBox = msgBox;
}
/**
 * 无确认按钮且不可关闭的info
 * @param {} url
 * @param {} params
 * @param {} callback
 */
phenix.Msg.stip = function(msg,callback){
	var msgBox = Ext.Msg.show({
		title: '提示',
		msg: msg,
		icon: Ext.Msg.INFO,
		animEl: Ext.getBody(),
		closable:false,
		fn:callback
	});
	phenix.Msg.msgBox = msgBox;
}
/**
* 无确认按钮且不可关闭的等待提示
* @param {} url
* @param {} params
* @param {} callback
*/
phenix.Msg.wait = function(msg){
	var msgBox = Ext.Msg.wait(msg,'',{
		animate: true
	});
	phenix.Msg.msgBox = msgBox;
}
/**
 * 右下角通知框
 */
phenix.Msg.notice = function(title,msg,timeout,height){
	if(!timeout) timeout = 3000;
	if(!title) title='通知';
	var autoHeight = false;
	if(!height){
		autoHeight = true;
	}
	if(!msg) alert('(phenix.Msg.notice)未设置通知内容');
	new Ext.ux.Notification({
		autoHide:   true,
		hideDelay:  timeout,
		height: height,
		autoHeight:autoHeight
	}).showMessage(title,'<h1>'+msg+'</h1>');
}
/**
 * 右下角非模式提示框
 * @param {} msg
 */
phenix.Msg.ltip = function(msg,dialogId){
	/*
	var boxStr = ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');*/
	var winId = null;
	if(dialogId){
		winId = dialogId;
	}else{
		winId = Ext.id();
	}
	var width = 400;
	var height = 300;
	var left = document.body.clientWidth-width-10;
	var top = document.body.clientHeight-height-40;
	var dialog = new Ext.Window({
		id:winId,
		title:'消息提醒',
		//x:left,
		//y:top,
		width:width,
		height:height,
		resizable:true,
		maximizable:true,
		iconCls:'bmp3',
		shadow:true,
		//plain:true,
		bodyStyle:'background-color:#ffffff;overflow:auto;margin:0px;padding:5px;font-size:13px;',
		modal:false,
		items:[
			{xtype:'label',html:msg}
		]
	});
	//dialog.show(Ext.getBody());//改由调用者自己显示
	//dialog.getEl().moveTo(left,top-height-10, true);
	phenix.Msg.msgBox = dialog;
	return dialog;
}