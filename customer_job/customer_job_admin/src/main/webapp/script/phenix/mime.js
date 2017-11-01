//多媒体格式
phenix.mime = {};
phenix.mime.img = {};

phenix.mime.img.width = 80;
phenix.mime.img.height = 60;

phenix.mime.img.types = [
	'gif','jpg','jpeg','png','bmp'
];
/**
 * 判断是否是图片格式
 * @param {} fileName
 * @return {Boolean}
 */
phenix.mime.isImg = function(fileName){
	if(!fileName) return false;
	var extName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
	for( var i=0; i<phenix.mime.img.types.length; i++){
		var type = phenix.mime.img.types[i];
		if(extName==type) return true;
	}
	return false;
}
/**
 * 判断是否为Flash
 * @param {} fileName
 * @return {}
 */
phenix.mime.isFlash = function(fileName){
	var extName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
	return extName=='swf';
}