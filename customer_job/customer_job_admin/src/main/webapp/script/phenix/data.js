phenix.data = {};//与数据相关的函数

/**
 * 根据关键字从ds中取得显示名称
 * @param {Object} id
 * @param {Object} ds
 */
phenix.data.transform = function(id, ds){
	var rtn = id;
	var key = rtn;
	try{
		key = rtn.toUpperCase();
	}catch(e){}
	
	var record = ds.getById(key);
	if(null!=record){ rtn = record.data.display; }
	return rtn;
}
/**
 * 生成动态更新的ds
 * @param ds
 * @param params
 * @returns
 */
phenix.data.dynaDsFun = function(ds,params){
	ds.baseParams = params;
	ds.reload();
	return ds;
}
/**
 * 更新combo的数据
 * @param comboId
 * @param params
 */
phenix.data.updateCombo = function(comboId, params){
	var store = Ext.getCmp(comboId).getStore();
	store.baseParams = params;
	store.load();
}
