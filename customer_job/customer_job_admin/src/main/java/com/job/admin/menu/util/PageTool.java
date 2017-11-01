package com.job.admin.menu.util;

import java.util.List;

/**
 * 分页工具
 * @author Kobe
 *
 */
public class PageTool {
	private List<?> records;
	private int pageSize = Constants.PAGE_SIZE;//每页显示行数
	private int pageNo=1;//当前页，默认为第1页
	private int rowCount;//总行数
	private int pageCount;//总页数
	
	/**
	 * 取得总行数
	 * @return 总行数
	 */
	public int getRowCount() {
		return rowCount;
	}
	/**
	 * 取得总页数
	 * @return 总页数
	 */
	public int getPageCount(){
		return pageCount;
	}
	/**
	 * 设定总行数
	 * @param rowCount 总行数
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		pageCount = (rowCount % pageSize == 0) ? (rowCount/pageSize) : (rowCount/pageSize +1);
		if(pageNo>pageCount){
			pageNo = pageCount;
		}
	}
	/**
	 * 取得首页的偏移量
	 * @return 首页的偏移量
	 */
	public int getFirstPage(){
		return 1;
	}
	/**
	 * 取得尾页的偏移量
	 * @return 尾页的偏移量
	 */
	public int getLastPage(){
		return pageCount;
	}
	/**
	 * 取得下一页的偏移量
	 * @return 下一页的偏移量
	 */
	public int getNextPage(){
		if(isLastPage()){
			return pageNo;
		}
		return pageNo+1;
	}
	/**
	 * 取得上一页的偏移量
	 * @return 上一页的偏移量
	 */
	public int getPrevPage(){
		if(isFirstPage()){
			return 1;
		}
		return pageNo-1;
	}
	/**
	 * 取得本页第一条记录的偏移量
	 * @return 本页第一条记录的偏移量
	 */
	public int getStart() {
		int start = (pageNo-1)*pageSize;
		if(start>0){
			return start;
		}
		return 0;
	}
	/**
	 * 取得本页最后一条记录的偏移量
	 * @return 本页最后一条记录的偏移量
	 */
	public int getEnd() {
		int end = getStart()+pageSize;
		if(end>rowCount){
			end = rowCount;
		}
		return end;
	}
	/**
	 * 取得当前页的页码
	 * @return 当前页的页码
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * 设置要前往的页码
	 * @param pageNo 要前往的页码
	 */
	public void setPageNo(int pageNo) {
		if(pageNo<=0){
			this.pageNo = 1;
		}else{
			this.pageNo = pageNo;
		}
	}
	/**
	 * 判断是否为首页
	 * @return 是否为首页
	 */
	private boolean isFirstPage(){
		return 1 == pageNo;
	}
	/**
	 *判断是否为末页
	 * @return 是否为末页
	 */
	private boolean isLastPage(){
		return pageCount == pageNo;
	}
	
	/*==================== helpers ======================*/
	public List<?> getRecords() {
		return records;
	}
	
	public void setRecords(List<?> records) {
		this.records = records;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 
	 * @param pageSize 每页显示行数
	 */
	public void setPageSize(int pageSize) {
		if(0!=pageSize) {
			this.pageSize = pageSize;
		}
	}
}
