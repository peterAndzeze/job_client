package com.job.core.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
/**
 * 
 * @Title: DBUtil.java
 * @Package: com.job.core.util
 * @Description: 数据库访问
 * @author: sunwei
 * @date: 2017年5月9日 下午1:30:17
 * @version: V1.0
 */
public class DBUtil<T> {
    /**
     * 
     * @Title: getConnection
     * @Description:获取数据库链接
     * @author:sunwei
     * @createTime:2017年5月9日下午1:55:50
     * @param dataSource 数据源
     * @return 数据库链接
     */
    private static Connection getConnection(DataSource dataSource){
	Connection connection =null;
	try {
//	    connection =dataSource.getConnection();
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/job","root","admin");
	} catch (Exception e) {
	    System.out.println("获取数据库链接异常");
	    e.printStackTrace();
	}
	return connection;
    }
    /**
     * 
     * @Title: query
     * @Description:执行查询
     * @author:sunwei
     * @createTime:2017年5月9日下午2:00:52
     * @param dataSource 数据源
     * @param sql
     * @param params
     */
    public static List<Map<String, Object>> query(DataSource dataSource,String sql,Object [] params){
	Connection connection=getConnection(dataSource);
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	try {
	    preparedStatement=connection.prepareStatement(sql);
	    if(null!=params){
	        for (int i=0;i<params.length;i++) {
	    	preparedStatement.setObject(i+1, params[i]);
	        }
	    }
	    resultSet=preparedStatement.executeQuery();
	    return controlResult(resultSet);
	} catch (SQLException e) {
	   System.out.println("查询异常！！！！");
	    e.printStackTrace();
	}finally{
	   closeDB(connection, preparedStatement, resultSet);
	}
	return null;
    }
    /**
     * 
     * @Title: update
     * @Description: 更新数据库
     * @author:sunwei
     * @createTime:2017年5月10日下午3:02:52
     * @param dataSource
     * @param sql
     * @param params
     * @return
     */
    public static int update(DataSource dataSource,String sql,Object [] params){
	Connection connection=getConnection(dataSource);
	PreparedStatement preparedStatement=null;
	int ret=0;
	try {
	    preparedStatement=connection.prepareStatement(sql);
	    if(null!=params){
		for (int i = 0; i < params.length; i++) {
		    preparedStatement.setObject(i+1, params[i]);
		}
	    }
	   ret= preparedStatement.executeUpdate();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}finally{
	    closeDB(connection, preparedStatement, null);
	}
	return ret;
    }
    
    
    
    private static List<Map<String, Object>> controlResult(ResultSet resultSet) throws SQLException{
	if(null==resultSet){
	    return new ArrayList<Map<String,Object>>();
	}
	List<Map<String, Object>> resultData=new ArrayList<Map<String,Object>>();
	ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
	int resultCount=resultSetMetaData.getColumnCount();
	while(resultSet.next()){
	    Map<String, Object> dataMap=new HashMap<String, Object>();
	    for (int i=1;i<=resultCount;i++){
		dataMap.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
	    }
	    resultData.add(dataMap);
	}
	return resultData;
    }
    /**
     * 
     * @Title: closeDB
     * @Description:关闭数据库链接
     * @author:sunwei
     * @createTime:2017年5月10日上午9:18:56
     * @param connection
     * @param preparedStatement
     * @param resultSet
     */
    public static void closeDB(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
	try {//单个关闭，避免上一步关闭异常，下面的资源关闭不了
	    if(null!=connection){
	        connection.close();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	try {
	    if(null!=preparedStatement){
	        preparedStatement.close();
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	try {
	    if(null!=resultSet){
	        resultSet.close();
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    public static void main(String[] args) {
	   List<Map<String, Object>> dateList= DBUtil.query(null, "select * from user_info", null);
	   for (Map<String, Object> map : dateList) {
	      for(Map.Entry<String, Object> entity:map.entrySet()){
		  System.out.println(entity.getValue()+"key:"+entity.getKey());
	      }
	   }
 }
	
}

class UserInfo{
    private int id;
    private String userName;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}

