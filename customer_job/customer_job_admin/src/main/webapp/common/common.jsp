<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Ext3基础库 -->
<link href="${ctxPath}/script/extjs/resources/css/ext-all.css" rel="stylesheet" type="text/css" ></link>
<link href="${ctxPath}/style/default.resource.css" rel="stylesheet" type="text/css" ></link>
<link href="${ctxPath}/style/ext-font.css" rel="stylesheet" type="text/css" ></link>
<script type="text/javascript" src="${ctxPath}/script/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/locale/ext-lang-zh_CN.js"></script>

<!-- 自定义库 -->
<script type="text/javascript" src="${ctxPath}/script/phenix.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/ajax.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/data.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/dom.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/form.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/grid.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/menu.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/mime.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/msg.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/template.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/text.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/panel.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/tree.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/util.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/value.js"></script>

<!-- Ext3官方扩展 -->
<link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/css/ux-all.css' />
<link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/fileuploadfield/css/fileuploadfield.css' />
<link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/css/MultiSelect.css' />
<link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/css/Portal.css' />
<link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/statusbar/css/statusbar.css' />
<!-- <link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/StaticTextField.css' /> -->
<!-- <link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/ColumnTree.css' /> -->
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/fileuploadfield/FileUploadField.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/MultiSelect.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/TabCloseMenu.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/Portal.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/PortalColumn.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/Portlet.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/statusbar/StatusBar.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/statusbar/ValidationStatus.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/GroupSummary.js"></script>
<!-- <script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/DisplayField.js"></script> -->
<!-- <script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/StaticTextField.js"></script> -->
<!-- <script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/ColumnTree.js"></script> -->
<!-- <script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/GridTip.js"></script> -->

<!-- lovcombo -->
<link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/ux/component/Ext.ux.form.LovCombo.css' />
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/Ext.ux.util.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/Ext.ux.form.ThemeCombo.js"></script>
<script type="text/javascript" src="${ctxPath}/script/extjs/ux/component/Ext.ux.form.LovCombo.js"></script>

<!-- 日志记录库 -->
<script src="${ctxPath}/script/blackbirdjs/blackbird.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxPath}/script/blackbirdjs/blackbird.css"/>

<!-- 数字格式化 -->
<script type="text/javascript" src="${ctxPath}/script/lib/formator/accounting.min.js"></script>

<!-- 引入全局配置参数 -->
<%@ include file="/common/globalConfigs.jsp"%>

<style>
<!--

-->
</style>

<script language="JavaScript">
//使grid内容可选择
if (!Ext.grid.GridView.prototype.templates) {
   Ext.grid.GridView.prototype.templates = {};
}
Ext.grid.GridView.prototype.templates.cell = new Ext.Template(
   '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>',
   '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
   '</td>'
);


	//Ext.BLANK_IMAGE_URL = '${ctxPath}/pub/lib/Ext2/resources/images/default/s.gif';
	Ext.useShims = true;
	//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.QuickTips.init();
	//Ext.form.Field.prototype.msgTarget = 'side';
	//var userTheme = '${sessionScope.USER_CONFIG_THEME}';
	//lovcombo
	Ext.override(Ext.ux.form.LovCombo, {
		beforeBlur: Ext.emptyFn
	});
	var globalBasePath = '${basePath}';
	var pageSize=20;
</script>
