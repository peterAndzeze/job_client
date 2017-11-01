phenix.ajax = {};//phenix的ajax函数集

//phenix_ajax_isStoped = false;//标识服务器是否已停止
phenix.ajax.retryTimeout = null;//重试定时器引用

phenix.ajax.ERROR_INTERNET_TIMEOUT = 12002;
phenix.ajax.ERROR_INTERNET_NAME_NOT_RESOLVED = 12007;
phenix.ajax.ERROR_INTERNET_CANNOT_CONNECT = 12029;
phenix.ajax.ERROR_INTERNET_CONNECTION_ABORTED = 12030;
phenix.ajax.ERROR_INTERNET_CONNECTION_RESET = 12031;
phenix.ajax.ERROR_HTTP_INVALID_SERVER_RESPONSE = 12152;

/**
 * 以post形式向url发送params，成功后调用callback
 * @param {Object} url
 * @param {Object} params
 * @param {Object} callback
 */
phenix.ajax.request = function(url, params, callback, isMask, failCallback){
	if(!isMask) isMask = false;
	if(isMask) phenix.Msg.showMask();
	
	Ext.Ajax.request({
		url: url,
		method: 'post',
		params: params,
		waitTitle: loadTitle,
		waitMsg: loadMsg,
		timeout:AJAX_TIMEOUT,
		success: function(response, form){
			try{
				//处理session失效
				if(response.responseText.indexOf(logoutUrl)>=0){
					if(!phenix_ajax_isStoped){//防止多次提示session失效信息
						phenix_ajax_isStoped = true;
						msg.stop();
						var logout = confirm(sessionInvalidMsg);
						if(logout) doLogout();
					}
					return;
				}
				
				//常规处理
				//alert('status='+response.status);
				eval('var result = '+response.responseText);//在IE下Ext.decode方法对function无效
					if (null != result.success && !result.success) {
						if(failCallback){
							failCallback(result);
						}else{
							phenix.Msg.warn(result.msg);
							if(phenix.Msg.mainMask) phenix.Msg.mainMask.hide();
							//callback(false);
						}
					}else {
						callback(result);
					}
			}catch (e){
				log.error('ajax error: '+e.message+'\nresponse='+response.responseText);
			}
			if(isMask) phenix.Msg.hideMask();
		},
		failure: function(response,form){
			//alert('response.status='+response.status);
			if(isMask) phenix.Msg.hideMask();
			//处理服务器重启造成session失效的问题
			if(0==response.status || 500==response.status || 503==response.status
					|| phenix.ajax.ERROR_INTERNET_CANNOT_CONNECT==response.status){
				
				phenix.Msg.info("与服务器的通讯中断, 请刷新列表或联系管理员 !");
				window.document.body.onbeforeunload = Ext.emptyFn;
				window.location = ctxPath+'/jsp/info.jsp';
				return;
				
				if(!phenix_ajax_isStoped){
					phenix.ajax.stopClient();
				}
			}else{
				log.error('错误信息如下：');
				for(var key in response){
					log.debug(key+'='+response[key]);
				}
				try{
					if(response.responseText) {
						log.error('错误信息：\n'+response.responseText);
					}
				} catch(e){}
			}
		}
	});
};
/**
 * 停止客户端（应对服务器停止响应）
 */
phenix.ajax.stopClient = function(){
	phenix_ajax_isStoped = true;
	//msg.stop();
	if(null!=phenix.Msg.msgBox) phenix.Msg.msgBox.hide();
	phenix.Msg.wait(serverOffMsg);
	//phenix.ajax.retryTimeout = window.setTimeout(phenix.ajax.detectServer,msg.refreshTime);
	phenix.ajax.detectServer();
};
/**
 * 探测服务器状态
 */
phenix.ajax.detectServer = function(){
	var localUrl = ctxPath+'/jsp/info.jsp';
	phenix.ajax.fragment(localUrl, {}, function(text,response){
		if(200==response.status){
			var url = casServerUrl+'/login?service='+encodeURIComponent(serverUrl)+'/j_spring_cas_security_check';
			phenix.util.openIframeDialog(phenix_ajax_loginDialogId,url,'buser',1024,550,'请登录',true,true,false);
		}
	},false,function(response){
		phenix.ajax.retryTimeout = window.setTimeout(phenix.ajax.detectServer,5*1000);
	});
};
/**
 * 启动客户端（服务器开始响应后启动）
 */
phenix.ajax.startClient = function(winId){
	//phenix.Msg.hideMask();
	window.clearTimeout(phenix.ajax.retryTimeout);
	//msg.start();
	Ext.getCmp(winId).close();
	//phenix.Msg.info(serverOnMsg,null,10000);
	if(null!=phenix.Msg.msgBox) phenix.Msg.msgBox.hide();
	phenix.Msg.notice(null,serverOnMsg,5000,300);
	phenix_ajax_isStoped = false;
};
/**
 * 以ajax形式向url提交id为formId的表单，成功后调用callback
 * @param {Object} url
 * @param {Object} formId
 * @param {Object} callback
 */
phenix.ajax.submit = function(url, formId, callback, failedCallback){
	if(!failedCallback) failedCallback = Ext.emptyFn();
	Ext.getCmp(formId).form.doAction('submit', {
		url: url,
		method: 'post',
		waitTitle: loadTitle,
		waitMsg: loadMsg,
		reset: false,//是否清空表单
		timeout:AJAX_TIMEOUT,
		clientValidation:true,
		success: function(form, action){
			if(callback) callback(action.result);
		},
		failure: function(form, action){
			if(failedCallback){
				failedCallback(action.result);
			}else{
				if(action.failureType!='client'){
					phenix.Msg.error(action.result.msg);
				}
			}
		}
	});
};
/**
 * 以post形式向url发送params，成功后调用callback
 * @param {Object} url
 * @param {Object} params
 * @param {Object} callback
 */
phenix.ajax.fragment = function(url, params, callback, isMask, failCallback){
	if(!isMask) isMask = false;
	var mask = null;
	if(isMask){
		var mask = new Ext.LoadMask(Ext.getBody(), {msg:loadMsg});
		mask.show();
	}
	
	Ext.Ajax.request({
		url: url,
		method: 'post',
		params: params,
		waitTitle: loadTitle,
		waitMsg: loadMsg,
		timeout:AJAX_TIMEOUT,
		success: function(response, form){
			if(callback) callback(response.responseText, response);
			if(isMask){mask.hide();}
		},
		failure: function(response){
			if(failCallback) failCallback(response);
			if(isMask){mask.hide();}
		}
	});
};