package com.job.admin.menu.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 存放系统与核心常量
 * @author zhaoPC
 */
public final class Constants {
	
	
	public static final Long INVEST_MENU_TYPE = new Long(1);
	
	public static final String NEXT_PATH = "CE_NEXT_PATH";//用户将要访问的页面
	
	public static final String NULL_KEY = "";//代表空值
	public static final String BOOL_TRUE = "true";//代表真
	public static final String BOOL_FALSE = "false";//代表假
	
	public static final Long COMMON_ROOT = 10000L;//通用树形结构的根节点ID
	public static final String COMMON_CHILDREN = "children";//通用树形结构的根节点ID
	public static final Integer TREE_NORMAL_LEV = 2;//一次取树的常规级数
	public static final Integer TREE_MAX_LEV = 8;//一次取树的最大级数
	public static final Integer PAGE_SIZE = 20;//CONFIG.getInteger("pager.limit", 20);//分页大小
	
	public static final String BITMAP_0 = "0";//位图0标识
	public static final String BITMAP_1 = "1";//位图1标识
	
	public static final String ENCODING_GBK = "GBK";
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String DEFAULT_FONT = "宋体";
	
    public static final String FILE_SEPARATOR = File.separator;
    public static final int BIN_KILO = 1024;//2进制千
    public static final int IO_BUFFER_SIZE = 8*BIN_KILO;//读写操作时的默认缓冲大小
	public static final String FILE_ENCODING_UTF8 = "UTF-8";
	public static final String FILE_ENCODING_GBK = "GBK";
	public static final int TEXT_LINE_LENGTH = 100;//一行文本的最大长度
	
	public static final int MAX_RECORD_NUM = 1000;//一次取出的最大记录数（保证权限过滤后仍有至少一页的大小）
	public static final int HTTP_RETRY_TIME = 3;//http重试次数
    public static final int CONVERT_NUMBER = 1024;//转换基数

    public static final int CONVERT_M_NUMBER = 1024*1024;//转换基数
	
	public static final int FLUSH_SIZE = 100;//数据库缓存刷新上限
	
	public static final String AJAX_TYPE_TEXT = "ajax";//ajax类型关键字
	public static final String AJAX_TYPE_XML = "ajaxXml";
	public static final String AJAX_TYPE_XML_GBK = "ajaxXmlGBK";
	public static final String EDIT_OFFICE = "editOffice";
	public static final String EXPIRE = "expire";
	
	public static final String XML_ROOT_NAME = "data";//xml数据默认根元素名称
	
	public static final String ORG_ROOT = "ORG_ROOT";//部门树形数据的根
	public static final String ORG_ROOT_TEXT = "组织结构";//部门树形数据的根名称
	public static final String SYS_ROLE = "SYS_ROLE";//角色类型：系统角色
	public static final String APP_ROLE = "APP_ROLE";//角色类型：应用角色
	
	public static final String ROLE_SUPER_ADMIN = "ROLE_SYSADMIN";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	
	//系统标记
	public static final int PHENIX_SYSTEM_SIGN = 18;
	public static final String CURRENT_USER = "CURRENT_USER";//当前用户对象
	public static final String USER_CONFIG_ACLS = "USER_CONFIG_ACLS";//用户的访问控制列表
	public static final String USER_CONFIG_USERNAME = "USER_CONFIG_USERNAME";//用户名
	public static final String USER_CONFIG_USERID = "USER_CONFIG_USERID";//用户ID
	public static final String USER_CONFIG_ORGNAME = "USER_CONFIG_ORGNAME";//部门名
	public static final String USER_CONFIG_ORGID = "USER_CONFIG_ORGID";//部门ID
	public static final String USER_CONFIG_PERSONNAME = "USER_CONFIG_PERSONNAME";//人名
	public static final String USER_CONFIG_THEME = "USER_CONFIG_THEME";//主题
	public static final String USER_CONFIG_MENU = "USER_CONFIG_MENU";//菜单
	public static final String USER_CONFIG_FILES_PATH = "USER_CONFIG_FILES_PATH";//用户文件基路径
	
	public static final String STATUS_NORMAL = "0";//系统状态：正常
	public static final String STATUS_INVALID = "1";//系统状态：无效
	public static final String STATUS_DELETE = "2";//系统状态：软删除
	
	public static final String STATE_BUSINESS_DEFAULT = "0"; //默认业务状态
	
	public static final String COPY_PREFFIX = "Copy of ";//复制对象的命名前缀
	
	public static final Integer INTEGER_TRUE = 1;
	public static final Integer INTEGER_FALSE = 0;
	
	public static final String CACHE_TEMPORARY = "TemporaryCache";//临时性缓存
	public static final String CACHE_PERMANENT = "PermanentCache";//永久性缓存
	
	public static final String CMD_ENCODING = System.getProperty("sun.jnu.encoding");//命令行字符集
	
	/**------------------------------------ 系统组件相关常量  ------------------------------------**/
	
	/**------------------------ 系统日志相关  ------------------------**/
	//日志级别
	public static final String LOG_LEVEL_INFO = "INFO"; //信息
	public static final String LOG_LEVEL_WARN = "WARN"; //警告
	public static final String LOG_LEVEL_MANUAL = "MANUAL"; //警告
	
	/**------------------------ 数据字典相关  ------------------------**/
	//字典缓存
	public static final String DICT_CACHE = "dictCache";
	
	/**------------------------ 系统日历相关  --------------------------**/
	public static final String CALENDAR_CACHE = "calendarCache";
	
	//SMP
	public static final String CASSERVER_URL;  //UC服务器地址
	public static final String SERVER_URL;     //UC登录页面跳转向当前服务器的地址
	
	/**-------------------------- 短信相关   --------------------------**/
	// 短信模板关键字key，value分隔符   如：custName|kevin
	public static final String SMS_SEPARATOR = "|";
	//是否为多模板关键字
	public static final boolean MUTLIKEYWORDS_TEMPLATE = true;
	
	//用于hibernate执行时创建Query对象使用
	public static final int SQL_QUERY = 0;
	public static final int HQL_QUERY = 1;

	static {
		// 初始化配置信息
		InputStream is = null;
		Properties prop = new Properties();
		try {
			is = Constants.class.getResourceAsStream("/config.properties");
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		CASSERVER_URL = prop.getProperty("casserver.url");
		SERVER_URL = prop.getProperty("phenix.url");
	}
}