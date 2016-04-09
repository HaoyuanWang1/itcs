package com.dc.itcs.core.base.support;


/**
 * 系统常量
 * @Class Name ITConstants
 * @Create In 2014年8月18日
 */
public class ItcsConstants {
	public static final String TENANT_DC = "DC";
	public static final String TENANT_ITS = "ITS";
	// 是否常量
	public static final int IS_YES = 1;			// 是		
	public static final int IS_NOT = 0;			// 否
	public static final String IS_YESS = "是";	// 是
	public static final String IS_NO = "否";		// 否
	
	//是否代提
	public static final String IS_REPLACE="isReplace";
	
	// 是否有效
	public static final int STATE_ON = 1;		// 是		
	public static final int STATE_OFF = 0;		// 否
	
	// 角色
	public static final String ROLE_ADMIN_USER = "adminUser";							// 管理员		
	public static final String ROLE_COMMON_USER = "commonUser";						// 用户	
	public static final String ROLE_CUSTOMER_MANAGER_USER = "customerManagerUser";	// 客户经理	
	public static final String ROLE_SERVICE_MANAGER_USER = "serviceManagerUser";		// 服务经理
	
	// 邮件审批模板名称
	public static final String MAIL_TEMPLATE_CHOOSEACTOR_AUDITMAIL = "chooseActorAuditMail";
	public static final String MAIL_TEMPLATE_EXPERT_AUDITMAIL = "expertAuditMail";
	public static final String MAIL_TEMPLATE_BACKMAIL = "backMail";
	public static final String MAIL_TEMPLATE_ENDMAIL = "endMail";
	public static final String MAIL_TEMPLATE_CANCELMAIL = "cancelMail";
	public static final String MAIL_TEMPLATE_NOTICEMAIL = "noticeMail";
	
	public static final int EVENT_SLA_NORMAL = 0;	//事件SLA状态：正常
	public static final int EVENT_SLA_WARNING = 1;	//事件SLA状态：预警
	public static final int EVENT_SLA_OVERDUE = 2;	//事件SLA状态：超期
	
	public static final String USER_PASSWORD = "password123";			//默认密码	
	
	public static final String SUBMIT_OF_INSTEAD = "yes";				//代提
	public static final String SUBMIT_OF_MINE = "no";					//自提
	public static final String SRVICE_MANAGER_NO_OK = "otherManager";	//服务经理--未解决
	public static final String SRVICE_MANAGER_OK = "slove";				//服务经理--解决
	public static final String SRVICE_MANAGER_PASS = "pass";				//服务经理--解决

	public static final String STR_JOIN_PREFIX = "【";  //字符串连接时前缀，为处理like定位不准问题    
    public static final String STR_JOIN_SUFFIX = "】";   //字符串连接时后缀，为处理like定位不准问题
	
    /** 事件环节 开始 */
    public static final String EVENT_NODE_START = "start";
    /** 事件环节 是否代提 */
    public static final String EVENT_NODE_ISREPLACE = "isReplace";
    /** 事件环节 客户经理审批 */
    public static final String EVENT_NODE_CLIENTMANAGER_CONFIRM = "tenantManagerConfirm";
    /** 事件环节 服务经理审批 */
    public static final String EVENT_NODE_SERVICEMANAGER_CONFIRM = "serviceManagerConfirm";
    /** 事件环节 服务经理操作 */
    public static final String EVENT_NODE_SERVICEMANAGER_OPT = "serviceManagerOpt";
    /** 事件环节 用户确认 */
    public static final String EVENT_NODE_USER_CONFIRM = "userConfirm";
    /** 事件环节 结束 */
    public static final String EVENT_NODE_END = "end";
}
