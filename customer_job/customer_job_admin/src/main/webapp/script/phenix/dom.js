phenix.dom = {};

/**
 * 增加一个容器（div）
 * @param {Object} selfId
 * @param {Object} parentId
 */
phenix.dom.addDiv = function(selfId,parentId){
	Ext.DomHelper.append(parentId, [{
		tag: 'div',
		id: selfId,
		html: '',
		style:'width:100%;height:100%'
	}]);
}

phenix.dom.addLi = function(content,parentId){
	Ext.DomHelper.append(parentId, [{
		tag: 'li',
		html: content
	}]);
}