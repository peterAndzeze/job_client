phenix.value = {}; //phenix value的函数集
//重新设置下拉列表
phenix.value.resetCombo = function(cob){ 
	if(null==cob || ''==cob){
		return;
	}
    if(cob.selectedIndex == -1){  
        cob.setValue(cob.getValue());  
    }  
}