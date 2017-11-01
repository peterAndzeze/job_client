phenix.template = {};

/**
 * 由数据字典中的图标数据创建图标显示模板
 * @return {}
 */
phenix.template.iconClsDsTpl = function(){
	return new Ext.XTemplate(
        '<tpl for="."><div class="x-combo-list-item">',
            '<span class="{dictKey}" style="font-size:15px">　 {displayName}</span>',
        '</div></tpl>'
    );
}
/**
 * 显示图标
 * @param {} iconCls
 * @return {}
 */
phenix.template.iconDispTpl = function(iconCls){
	return '<span class="'+iconCls+'" style="font-size:15px">&nbsp;&nbsp;</span>';
}
/**
 * 显示图标，带链接
 * @param {} iconCls
 * @param {} url
 * @return {}
 */
phenix.template.iconDispLinkTpl = function(iconCls,url){
	return '<span class="'+iconCls+'" style="font-size:15px;cursor:pointer;" onclick="window.open(\''+url+'\')">&nbsp;&nbsp;</span>';
}