package qtgl.api;

import qtgl.utils.PropertyConfigurer;

public class AppInfo {

	private String domain;//域名称 可以为空
	private String engieUrl;//平台地址如：http://cloud.qtgl.com.cn/infoplus
	private boolean debug;//是否为正式环境
	private String ClientId;//打开流程的属性（流程的ID）：Identity   或者 Code@police.taskcenter.net
	private String Scope;//流程的Scopes  权限 如：sys_start
	private String ClientSecret;//打开流程的属性：Secret
	
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getEngieUrl() {
		return engieUrl;
	}
	public void setEngieUrl(String engieUrl) {
		if(null==engieUrl||"".equals(engieUrl))
		{
			this.engieUrl=PropertyConfigurer.getProperty("url");
			
		}else {
			this.engieUrl = engieUrl;	
		}
	
		
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public String getClientId() {
		return ClientId;
	}
	public void setClientId(String clientId) {
		ClientId = clientId;
	}
	 
	public String getScope() {
		return Scope;
	}
	public void setScope(String scope) {
		Scope = scope;
	}
	public String getClientSecret() {
		return ClientSecret;
	}
	public void setClientSecret(String clientSecret) {
		ClientSecret = clientSecret;
	}
	
	
	
}
