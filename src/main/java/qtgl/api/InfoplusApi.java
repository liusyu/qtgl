package qtgl.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import qtgl.api.model.FileVo;
import qtgl.api.model.NotificationBody;
import qtgl.api.model.Users;
import qtgl.utils.PropertyConfigurer;
import qtgl.utils.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class InfoplusApi {

	//  *************注意*************
	//  如果调用接口  接口地址表述含有（/me） 那么token需要传入当前登录人的 token
	//  如果不直接使用（/me）那么需要改为/user/{userId} 传入获取的token
    //	注意：形如1.1(s)格式的Id表示此API可同时支持user和sys两种token，但事实上有两点不同：
    //	/me/profile仅支持user类型的token，相应的sys版的接口需要换成 /user/{userId}/profile
    //	sys版本的scope需要前缀sys_，本例中应该申请 sys_profile
	
	
	// 谷歌json工具
	public final static Gson gson = new GsonBuilder().create();
	private AppInfo app;

 
	public	InfoplusApi() {
		this.app = get_appinfo();
	}

	public AppInfo getApp() {
		return app;
	}

	public void setApp(AppInfo app) {
		this.app = app;
	}
	

	

	/**
	 * 获取App应用信息
	 * 
	 * @return
	 */
	private AppInfo get_appinfo() {
		
		String EngieUrl = "http://sandbox.qtgl.com.cn/infoplus";
		String ClientId = "1640e2e4-f213-11e3-815d-fa163e9215bb";
		String ClientSecret = "fa163e97";
		boolean DeBug = true;
		
//		EngieUrl="https://form.sjtu.edu.cn/infoplus";
//		ClientId="8ef0ced2-2fba-4f28-9c4f-fcedb410fc2e";
//		ClientSecret="ce3bddbc634d3ddbce99b3624d0441f9";
		
		//--------------------读取配置文件中的信息--------------------------
		try {
			
			EngieUrl = PropertyConfigurer.getProperty("engieUrl");
			ClientId = PropertyConfigurer.getProperty("app.ClientId");
			ClientSecret =PropertyConfigurer.getProperty("app.ClientSecret");
			String isdebug=PropertyConfigurer.getProperty("app.DeBug");
			DeBug = true;
			if (!isdebug.equals("true")) {
				DeBug=false;
			}
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//--------------------读取配置文件中的信息--------------------------
		
		
		AppInfo ai = new AppInfo();
		ai.setEngieUrl(EngieUrl);
		ai.setDebug(DeBug);
		ai.setClientId(ClientId);
		ai.setClientSecret(ClientSecret);
		return ai;

	}

	public static void main(String[] args) {

		InfoplusApi ia = new InfoplusApi();
		JSONObject jsonObject = null;

		// 获取 部门对应岗位 下人员信息
		// jsonObject=ia.get_UserList("*","KaiFaZhe");
		//
		//
		// JSONArray
		// json_array=JSONObject.fromObject(jsonObject).getJSONArray("entities");
		// for (int i = 0; i < json_array.size(); i++) {
		// JSONObject obj=json_array.getJSONObject(i);
		// System.out.println(obj.get("account"));
		// }

		// 发起流程
//		JSONObject jsonData = new JSONObject();
//		jsonData.put("id", "项目ID");
//		jsonData.put("Name", "121212");
//
//		jsonObject = ia.Star("Alice", "Alice", "JavaTestAPI",jsonData.toString());

		 //执行一步操作
		// jsonObject=ia.DoAction("16624", "submit", "Add", "Submit");

		// // 删除用户的一条：岗位 对应 部门 信息
//		 jsonObject=ia.delete_dept_post_user( "HW", "BDYWX_DDR", "10006");

		// String url
		// ="https://api.sjtu.edu.cn/v1/notification?access_token=cef0b49d9f20fb9a14029f42d799b07f";
		// //
		// //url="http://wsu.taskcenter.net:8080/notificationApi2/notification?access_token=436c169133e010e456531de9e2cc5171";
		// //
		// //url="http://ws01.taskcenter.net:8080/notificationApi2/notification?access_token=436c169133e010e456531de9e2cc5171";
		// NotificationBody body=new NotificationBody();
		// body.setName("1212");
		// body.setContent("内容测试Content");
		// // body.setBody("内容测试Body");
		// // body.setHtml("内容测试Html");
		//
		//
		// List<String> emails_list=new ArrayList<>();
		// emails_list.add("yyu@quantangle.com.cn");
		// body.setEmails(emails_list);
		//
		//
		// List<String> channels_list=new ArrayList<>();
		// channels_list.add("email");
		// body.setChannels(channels_list);
		//
		// //发送通知测试
		// jsonObject=ia.Sen_Notification(url,body);

		jsonObject=ia.get_triple("user", 0, 0, "余勇", "","");
		
		System.out.print(jsonObject);
		test();
	}

	public static void test()
	{
		String s="15120248";
		System.out.println(s.substring(2, 3));
		 
		
	}

	

	/**
	 * 调用webService方法
	 * @param endpoint  接口地址
	 * @param OperationName 接口地址名称
	 * @param parameterMap 参数信息
	 * @return
	 */
	 public  JSONObject get_Service(String endpoint,String OperationName,LinkedHashMap<String, String> parameterMap)
	 {
		String result = null;
		try {
			DynamicWebServiceImpl webService = new DynamicWebServiceImpl();
			webService.setWsdlLocation(endpoint + "?wsdl");
			Object[] paramValues = null;
			if (parameterMap!=null&&parameterMap.size() > 0) {
				paramValues = new Object[parameterMap.size()];
				int i = 0;
				for (Entry<String, String> entry : parameterMap.entrySet()) {
					paramValues[i] = entry.getValue();
					i++;
				}
			}
			//System.out.println(paramValues.length);

			result = (String) webService.invoke(OperationName, paramValues);

			//System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JSONObject.fromObject(result);
	 }
	 
	 /**
	  * 发生通知
	  * @param Title 标题
	  * @param Content 内容
	  * @param emails_list 邮箱列表
	  * @param phones_list 手机列表
	  * @return
	  */
	 public JSONObject sen_Info(String Title,String Content,List<String> emails_list,List<Users> users_list)
	 {
		 AppInfo ai=this.getApp();
		   // String url ="http://10.105.1.87:8080/notificationApi2/notification?access_token=436c169133e010e456531de9e2cc5171";
		    String url =ai.getEngieUrl().replace("/infoplus", "")+"/notificationApi2/notification?access_token=436c169133e010e456531de9e2cc5171";
			
			NotificationBody body=new NotificationBody();
			body.setName(Title);//标题
			body.setContent(Content);//内容
			
			List<String> channels_list=new ArrayList<>();//渠道
			
			if (emails_list!=null&&emails_list.size()>0) {
				body.setEmails(emails_list);//邮箱列表
				channels_list.add("email");
			}
			
			if (users_list!=null&&users_list.size()>0) {
				body.setUsers(users_list);
				channels_list.add("duanxin");
			}
			
			body.setChannels(channels_list);
			InfoplusApi ia=new InfoplusApi();
			JSONObject jsonObject=null;
			jsonObject=ia.Sen_Notification(url,body);
		 return jsonObject;
	 }
	 
 
	 /**
	  * 调用平台API 发送通知
	  * @param url
	  * @param body
	  * @return
	  */
	 public JSONObject Sen_Notification(String url,NotificationBody body)
	 {
		 try {
			   
				//String outputStr=JSONObject.fromObject(body).toString();
			    String outputStr=gson.toJson(body);
				
				url+= "&method=put";
				//数据传输要用json格式
//				System.out.println(url);
//				System.out.println(outputStr);
				
				JSONObject jsonObject = HttpConnection.doPost_json(url, outputStr);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		 
	 }
	 
	 
	
	/**
	 * 发起流程
	 * @param userId  用户ID
	 * @param assignTo 第一步可执行人
	 * @param code 工作流code
	 * @param data 初始化数据，为JSON序列化的hashmap
	 * @return
	 */
	public JSONObject Star(String userId, String assignTo, String code, String data) {
		try {
			// API-3.2s：发起流程，格式：PUT
			// /process?userId={string}&assignTo={string}&code={string}&data={string}
			
			AppInfo ai=this.getApp();
			ai.setScope("sys_start");
			
			String requestURL = ai.getEngieUrl() + "/apis/";
			if (ai.isDebug()) {
				requestURL += "v2d/";

			} else {
				requestURL += "v2/";
			}
			requestURL += "process";
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			map.put("userId", userId);
			map.put("assignTo", assignTo);
			map.put("code", code);
			map.put("data", data);

			JSONObject jsonObject = HttpConnection.doPut(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 办理一个任务 (使用用户账号办理)
	 * 
	 * @param GUID 任务ID(GUID)
	 * @param userId  该办理人账号(Account)
	 * @param actionCode 动作Code
	 * @param Remark  填写备注
	 * @return
	 */
	public JSONObject UserDoAction( String GUID, String userId,String actionCode, String Remark) {
		try {
			//3.1u 帮用户办理一步 /task/{id}
			AppInfo ai=this.getApp();
			ai.setScope("sys_submit");
			
			String requestURL = ai.getEngieUrl() + "/apis/";
			if (ai.isDebug()) {
				requestURL += "v2d/";

			} else {
				requestURL += "v2/";
			}
			requestURL += "task/"+GUID;
			
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			map.put("userId", userId);
			map.put("actionCode", actionCode);
			map.put("remark", Remark);
 
			
			JSONObject jsonObject = HttpConnection.doPost(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 办理一个任务 （系统用户办理）
	 * @param entryId 流水号
	 * @param action 操作:submit，kill，Suspend
	 * @param stepCode 节点Code
	 * @param actionCode 动作Code
	 * @return
	 */
	public JSONObject DoAction(String entryId, String action,String stepCode, String actionCode) {
		try {
			// 自动完成某个步骤，注：该步骤必须在工作流编辑器标记为可以被自动执行。 格式：POST /process/{entryId}
			
			AppInfo ai=this.getApp();
			ai.setScope("sys_process_edit");
			
			String requestURL = ai.getEngieUrl() + "/apis/";
			if (ai.isDebug()) {
				requestURL += "v2d/";

			} else {
				requestURL += "v2/";
			}
			requestURL += "process/"+entryId;
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			map.put("action", action);
			map.put("stepCode", stepCode);
			map.put("actionCode", actionCode);
 
			JSONObject jsonObject = HttpConnection.doPost(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 办理一个任务 （系统用户办理）   用于并行办理
	 * @param entryId 流水号
	 * @param action 操作:submit，kill，Suspend
	 * @param stepCode 节点Code
	 * @param actionCode 动作Code
	 * @param splitPath 分支splitPath 对于重复节嵌套情况，按第x节（0下标）中的第y节并行，应该出路径为："x/y"
	 * @return
	 */
	public JSONObject DoAction_splitPath(String entryId, String action,String stepCode, String actionCode,String splitPath) {
		try {
			// 自动完成某个步骤，注：该步骤必须在工作流编辑器标记为可以被自动执行。 格式：POST /process/{entryId}
			AppInfo ai=this.getApp();
			ai.setScope("sys_process_edit");
			
			String requestURL = ai.getEngieUrl() + "/apis/";
			if (ai.isDebug()) {
				requestURL += "v2d/";

			} else {
				requestURL += "v2/";
			}
			requestURL += "process/"+entryId;
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			map.put("action", action);
			map.put("stepCode", stepCode);
			map.put("actionCode", actionCode);
			map.put("splitPath", splitPath);

			JSONObject jsonObject = HttpConnection.doPost(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 终止一个流程
	 * @param entryId 流水号
	 * @param userId 用户账号
	 * @return
	 */
	public JSONObject Kill(String entryId, String userId)
	{
		
		try {
			 //  str = api.Kill("6433", "alice");
            //需要设置 流程 管理权限，该用户有管理权限
            //或者设置租户系统用户SYSTEM_USER_ID 	82e34616-4f3c-11e6-a235-00163e0226a1 
            //终止某个流程  格式：POST /process/{entryId}
			
			AppInfo ai=this.getApp();
			ai.setScope("sys_process_edit");
			String requestURL = ai.getEngieUrl() + "/apis/";
			
			if (ai.isDebug()) {
				requestURL += "v2d/";

			} else {
				requestURL += "v2/";
			}
			requestURL += "process/"+entryId;
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			map.put("action", "kill");
			map.put("userId", userId);
			JSONObject jsonObject = HttpConnection.doPost(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 补偿事件
	 * 
	 * @param entryId
	 *            流水号
	 * @return
	 */
	public JSONObject compensate(String entryId) {

		try {

			// 补偿某个流程，用于修复数据 格式：POST /process/{entryId}

			AppInfo ai = this.getApp();
			ai.setScope("sys_process_edit");
			String requestURL = ai.getEngieUrl() + "/apis/";

			if (ai.isDebug()) {
				requestURL += "v2d/";

			} else {
				requestURL += "v2/";
			}
			requestURL += "process/" + entryId;
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			map.put("action", "compensate");
			JSONObject jsonObject = HttpConnection.doPost(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改指定流程的数据
	 * @param entryId 流水号
	 * @param data  需要修改的数据，为JSON序列化的hashmap
	 * @return
	 */
	public JSONObject update_data(String entryId,String data)
	{
		
		try {
			// 修改指定流程的数据：POST /process/{id}/data
			AppInfo ai = this.getApp();
			ai.setScope("sys_process_edit");
			String requestURL = ai.getEngieUrl() + "/apis/";

			if (ai.isDebug()) {
				requestURL += "v2d/";

			} else {
				requestURL += "v2/";
			}
			requestURL += "process/"+entryId+"/data";
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			map.put("data", data);
			JSONObject jsonObject = HttpConnection.doPost(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取一个实例详情
	 * @param entryId 流水号
	 * @return
	 */
	 public JSONObject Get_WorkFlow_info(String entryId)
	 {
		 try {
			//2.8	GET	/process/{id}	process	u/s	获取指定流程的详情
			 AppInfo ai=this.getApp();
			 ai.setScope("sys_process");
			String requestURL = ai.getEngieUrl() + "/apis/";
			if (ai.isDebug()) {
				requestURL += "v2d/";
			} else {
				requestURL += "v2/";
			}
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			requestURL += "process/"+entryId;
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token",Token);
			JSONObject jsonObject = HttpConnection.doGet(requestURL, map);
			return jsonObject;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	 }
	
	 

		/**
		 * 注册用户
		 * @param code 用户Account
		 * @param name 姓名
		 * @param express 是否快捷用户(True：是 false：否)
		 * @param email 邮箱
		 * @return
		 */
	    public JSONObject insert_user(String code, String name, String express, String email)
		  {
			  try {
				//API-5.4: 创建用户，格式：PUT /enterprise/user?code={string}&name={string}&express={boolean}&email={string}
				  AppInfo ai=this.getApp();
				  ai.setScope("sys_triple_edit");
				  
				  String requestURL = ai.getEngieUrl() + "/apis/";
					if (ai.isDebug()) {
						requestURL += "v2d/";

					} else {
						requestURL += "v2/";
					}
					requestURL += "enterprise/user";
					String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("access_token", Token);
					map.put("code", code);
					map.put("name", name);
					map.put("express",express);
					map.put("email",email);
					
					JSONObject jsonObject = HttpConnection.doPut(requestURL, map);
					return jsonObject;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null; 
			  
		  }
		  
		/**
		 * 赋予用户岗位
		 * @param dept 部门代码
		 * @param post 岗位代码
		 * @param code 用户Account
		 * @return
		 */
		public JSONObject insert_post(String dept, String post, String code)
		{
			try {
				//API-5.8: 赋予岗位，格式：PUT /position/{dept}:{post}/user?code={string}
				  AppInfo ai=this.getApp();
				  ai.setScope("sys_triple_edit");
				  
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";

				} else {
					requestURL += "v2/";
				}
				requestURL += "position/"+dept+":"+post+"/user";
				
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token", Token);
				map.put("code", code);
				
				JSONObject jsonObject = HttpConnection.doPut(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		/**
		 * 删除用户
		 * @param code 用户编码（GUID）
		 * @return
		 */
		public JSONObject delete_user(String code)
		{
			try {
				//API 5.9: 删除用户，格式：DELETE /position/{dept}:{post}/user?code={string}
				  AppInfo ai=this.getApp();
				  ai.setScope("sys_triple_edit");
				  
				  
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "enterprise/user";
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token", Token);
				map.put("code", code);
				
				JSONObject jsonObject = HttpConnection.doDelete(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
			
		}
		
		/**
		 * 删除用户的一条：岗位 对应 部门 信息
		 * @param dept 部门code
		 * @param post 岗位code
		 * @param code 用户账号（account）
		 * @return
		 */
		public JSONObject delete_dept_post_user(String dept, String post, String code)
		{
			try {
				//API 5.9: 删除用户，格式：DELETE /position/{dept}:{post}/users?code={string}
				
				AppInfo ai=this.getApp();
				ai.setScope("sys_triple_edit");
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "position/"+dept+":"+post+"/users";
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token", Token);
				map.put("code", code);
				
				JSONObject jsonObject = HttpConnection.doDelete(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
			
		}
		  
		 /**
	     * 获取用户岗位信息
	     * @param UserId 用户账号
	     * @return
	     */
	    public JSONObject getUserPosts(String UserId)
	    {
	    	try {
				//1.6(6.1s)：获取用户岗位  格式：GET /user/{userid}/positions [1.6(6.1s)]
				AppInfo ai=this.getApp();
				ai.setScope("sys_triple");
				
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "user/"+UserId+"/positions";
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				JSONObject jsonObject = HttpConnection.doGet(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
	    	
	    }
	    
		/**
		 * 查询某院系、岗位下的用户列表
		 * @param ai 流程信息
		 * @param dept 部门代码
		 * @param post 岗位代码
		 * @return
		 */
		public JSONObject get_UserList(String dept, String post)
		{
			try {
				//API-5.7：查询某院系、岗位下的用户列表  格式：GET /position/{dept}:{post}/users [API 5.7]
				AppInfo ai=this.getApp();
				ai.setScope("sys_triple");
				
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "position/"+dept+":"+post+"/users";
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				JSONObject jsonObject = HttpConnection.doGet(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
 
		/**
		 * 查询列表（用户/岗位/院系）
		 * @param type dept|post|user
		 * @param start 分页开始下标，零下标
		 * @param limit 分页结束下标，开区间
		 * @param keyword 查询关键字，逗号或空格分割，同User控件使用方式
		 * @param positions 岗位、院系限定，同User控件的UserFilters参数 (40100:BuiltInFaculty,40110:BuiltInFaculty)
         * @param domain 如果使用的是 system下的APP授权 需要传入domain信息 反之可以为空
		 * @return
		 */
		public JSONObject get_triple(String type,int start,int limit,String keyword,String positions,String domain)
		{
			try {
				//5.5s	GET	/triple/{type}s	sys_triple	sys	查询列表（用户/岗位/院系）
				AppInfo ai=this.getApp();
				ai.setScope("sys_triple");
				
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "triple/"+type+"s";
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				if (domain!=null&&!"".equals("domain")) {
					map.put("domain", domain);
				}
				if (limit>0) {
					map.put("start",String.valueOf(start));
					map.put("limit",String.valueOf(limit));	
				}
				map.put("keyword",keyword);
				map.put("positions",positions);
				JSONObject jsonObject = HttpConnection.doGet(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
			
		}
		
		
		 /**
		  * 获取用户可办应用
		  * @param UserId  用户账号(如:Alice)
		  * @return
		  */
		public JSONObject get_APPs(String UserId)
		{
			try {
				//API-2.1：获取用户可办应用  格式：GET /user/{userid}/apps [API 2.1]
				AppInfo ai=this.getApp();
				ai.setScope("sys_app");
				
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "user/"+UserId+"/apps";
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				JSONObject jsonObject = HttpConnection.doGet(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
 
		/**
		 * 查询用户的委托人、被委托人列表
		 * @param id 当前登录人员账号(如:Alice)
		 * @param relationship  [entrusters|entrustees]，分别表示委托人、被委托人。
		 * @return
		 */
		public JSONObject get_Entrusts(String id,String relationship)
		{
			try {
				//API-10.1(s)：查询用户的委托人、被委托人列表 格式：GET /user/{id}/{relationship}
				AppInfo ai=this.getApp();
				ai.setScope("sys_entrust");
				
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "user/"+id+"/"+relationship;
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				JSONObject jsonObject = HttpConnection.doGet(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
	 
		/**
		 * 新增委托
		 * @param id(申请人账号:Alcie)
		 * @param relationship [entrusters|entrustees]，分别表示委托人、被委托人。新增使用:entrusters
		 * @param otherUserId  被委托人账号
		 * @param app APP信息，code或id
		 * @param start 开始时间
		 * @param finish 结束时间
		 * @return
		 */
		public JSONObject add_Entrusts(String id,String relationship,String otherUserId,String app,String start,String finish)
		{
			try {
				//API-10.2(s)：增加委托格式：GET /user/{id}/{relationship}/{other}
				AppInfo ai=this.getApp();
				ai.setScope("sys_entrust_edit");
				
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "user/"+id+"/"+relationship+"/"+otherUserId;
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				map.put("app",app);
				map.put("start",start);
				map.put("finish",finish);
				JSONObject jsonObject = HttpConnection.doPut(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		/**
		 * 删除委托信息
		 * @param id(申请人账号:Alcie)
		 * @param relationship [entrusters|entrustees]，分别表示委托人、被委托人。删除使用:entrustees
		 * @param otherUserId  被委托人账号
		 * @param app APP信息，code或id
		 * @return
		 */
		public JSONObject delete_Entrusts(String id,String relationship,String otherUserId,String app)
		{
			try {
				//API-10.3(s)：删除委托：GET /user/{id}/{relationship}/{other}
				AppInfo ai=this.getApp();
				ai.setScope("sys_entrust_edit");
				
				String requestURL = ai.getEngieUrl() + "/apis/";
				if (ai.isDebug()) {
					requestURL += "v2d/";
				} else {
					requestURL += "v2/";
				}
				requestURL += "user/"+id+"/"+relationship+"/"+otherUserId;
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				map.put("app",app);
				JSONObject jsonObject = HttpConnection.doDelete(requestURL, map);
				return jsonObject;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		public enum FileType {

			docx,//word文件
			pdf,//pdf文件
			png
		}
		
		/**
		 * 利用流程导出模板 生成 word或pdf文件保存到本地
		 * @param entryId 流水号
		 * @param savePath 保存地址  main\webapp\WEB-INF\classes\WordORPdf
		 * @param fileName 文件名称  
		 * @param fileType 文件类型 docx、pdf、png 
		 * @return 最终导出的文件位置
		 */
		public String export_word_or_pdf(String entryId, String savePath,String fileName, FileType fileType)
		{
			//保存文件位置
			if (savePath==null||savePath.equals("")) {
				String path = System.getProperty("user.dir");
				savePath= path+"\\src\\main\\webapp\\WEB-INF\\classes\\WordORPdf\\";
			}
			//文件名称
			if (fileName==null||fileName.equals("")) {
				fileName="demo";
			}
			String file_type=fileType.name();
			//docx、pdf、png 文件类型
			if (file_type==null||file_type.equals("")) {
				file_type="docx";
			}
			String saveUrl=savePath+fileName+"."+file_type;
			try {
				//2.10	GET	/process/{id}/data/{format}	 	u/s	导出指定流程的数据到Word/PDF
				AppInfo ai=this.getApp();
				ai.setScope("sys_process");
				String requestURL = ai.getEngieUrl() + "/apis/";
				requestURL += "v2/";
				requestURL += "process/"+entryId+"/data/"+fileType;
				String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token",Token);
				//调用导出API 保存文件到本地
				HttpConnection.httpDownloadFile(requestURL,map);
				return saveUrl;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		
			
		}

		/**
		 * 上传文件到 file服务器
		 * @param fileApi  file服务器地址
		 * @param access_token  token信息
		 * @param filePath  本地文件地址
		 * @return
		 */
	    public static FileVo uploadFile(String fileApi, String access_token,String filePath) {
		try {
			if(access_token!=null&&!access_token.equals(""))
			{
				fileApi+="?access_token="+access_token;
			}
			String json = HttpConnection.uploadFile(fileApi, filePath);
			//System.out.println(json);
			Response<FileVo> response = gson.fromJson(json,new TypeToken<Response<FileVo>>() {}.getType());
			List<FileVo> list = response.getEntities();
			FileVo f = null;
			if (list != null && list.size() > 0) {
				f = list.get(0);
				// 删除临时文件
				File oldFile = new File(filePath);
				oldFile.delete();

			      }
			
		      	return f;

		      } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		return null;
	  }
		

	    /**
	     * 上传文件到 file服务器
	     * @param entryId 流水号
	     * @param filename 文件名称（不要后缀）
	     * @param fileType 文件类型  docx、pdf、png 文件类型
	     * @param fileApi file服务器地址
	     * @param access_token file上传token
	     * @return
	     */
	    public  FileVo uploadFile(String entryId,String filename,FileType fileType,String fileApi, String access_token) 
	    {
			
		try {
			String file_type = fileType.name();
			// docx、pdf、png 文件类型
			if (file_type == null || file_type.equals("")) {
				file_type = "docx";
			}

			// 2.10 GET /process/{id}/data/{format} u/s 导出指定流程的数据到Word/PDF
			AppInfo ai = this.getApp();
			ai.setScope("sys_process");
			String requestURL = ai.getEngieUrl() + "/apis/";
			requestURL += "v2/";
			requestURL += "process/" + entryId + "/data/" + file_type;
			String Token = this.getAccessTokenByWorkflowCodeAndScope(ai);
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", Token);
			// 调用导出API 输出文件流
			ByteArrayOutputStream output = HttpConnection.httpDownloadFile(requestURL, map);
			//System.out.println("size:"+output.size());
			if (output != null) {
				filename=filename+"."+file_type;
				
				fileApi += "?access_token=" + access_token;
				String json = HttpConnection.uploadFile(fileApi, output,filename);
				//System.out.println(json);
				Response<FileVo> response = gson.fromJson(json,new TypeToken<Response<FileVo>>() {}.getType());
				List<FileVo> list = response.getEntities();
				if (list != null && list.size() > 0) {
					return list.get(0);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		return null;
	  }
		
	 
	 private Map<AppInfo, Map<OAuthAccessTokenResponse, Set<String>>> accessTokenCache = new ConcurrentHashMap<>();
	 
	 private String getAccessTokenByWorkflowCodeAndScope(AppInfo ai) throws OAuthSystemException, OAuthProblemException {
//	        String code = workflowCode;
//	        String domain = application.getDomain();
//	        int index = code.indexOf('@');
//	        if (index != -1) {
//	            domain = code.substring(index + 1);
//	            code = code.substring(0, index);
//	        }
	        if (ai == null) {
	            throw new RuntimeException(String.format("Missing messenger configuration for code %s@%s","", ai.getDomain()));
	        }
	        return this.getAccessToken(ai);
	    }
	
	 private String getAccessToken(AppInfo ai) throws OAuthProblemException, OAuthSystemException {
	        synchronized (ai) {
	            Map<OAuthAccessTokenResponse, Set<String>> accessTokens = accessTokenCache.get(ai);
	            if (accessTokens == null) {
	                accessTokens = new ConcurrentHashMap<>();
	                accessTokenCache.put(ai, accessTokens);
	            }
	            Set<String> scopes = scopeStringToSet(ai.getScope());
	            Entry<OAuthAccessTokenResponse, Set<String>> expiredEntry = null;
	            for (Entry<OAuthAccessTokenResponse, Set<String>> entry : accessTokens.entrySet()) {
	                if (entry.getValue().containsAll(scopes)) {
	                    OAuthAccessTokenResponse response = entry.getKey();
	                    if (System.currentTimeMillis() / 1000 > response.getExpiresIn() - 10) {
	                        //expired
	                        if (expiredEntry == null || expiredEntry.getValue().size() < entry.getValue().size()) {
	                            expiredEntry = entry;
	                        }
	                        continue;
	                    }
	                    return response.getAccessToken();
	                }
	            }
	            if (expiredEntry == null) {
	                OAuthAccessTokenResponse response = this.getAccessToken(ai.getEngieUrl(),ai.getClientId(), ai.getClientSecret(),ai.getScope());
	                accessTokens.put(response, scopeStringToSet(response.getScope()));
	                return response.getAccessToken();
	            }
	            OAuthAccessTokenResponse oldResponse = expiredEntry.getKey();
	            OAuthAccessTokenResponse response = this.getAccessToken(ai.getEngieUrl(),oldResponse.getRefreshToken());
	            accessTokens.remove(response);
	            accessTokens.put(response, scopeStringToSet(response.getScope()));
	            return response.getAccessToken();
	        }
	    }

	 private Set<String> scopeStringToSet(String scope) {
	        Set<String> result = new HashSet<>();
	        if (scope == null || scope.trim().length() == 0) {
	            result.add("*");
	            return result;
	        }
	        for (String s : scope.split(",")) {
	            s = s.trim().toLowerCase();
	            if (s.length() > 0) result.add(s);
	        }
	        return result;
	    }

	 public OAuthAccessTokenResponse getAccessToken(String EngieUrl,String clientId, String clientSecret, String scope) throws OAuthSystemException, OAuthProblemException {
	        OAuthClientRequest oAuthRequest = OAuthClientRequest
	                .tokenLocation(String.format("%s/oauth2/token", EngieUrl))
	                .setGrantType(GrantType.CLIENT_CREDENTIALS)
	                .setClientId(clientId)
	                .setScope(scope)
	                .setClientSecret(clientSecret)
	                .buildBodyMessage();
	       
	        OAuthClient oAuthClient =null;
	        if(EngieUrl.startsWith("https")) {
	        	oAuthClient=new OAuthClient(new SSLConnectionClient());
			}else {
				oAuthClient=new OAuthClient(new URLConnectionClient());
			}
	        
	        return oAuthClient.accessToken(oAuthRequest, OAuthJSONAccessTokenResponse.class);
	    }
	 public OAuthAccessTokenResponse getAccessToken_password(String EngieUrl,String clientId, String clientSecret,String username,String password) throws OAuthSystemException, OAuthProblemException {
	        OAuthClientRequest oAuthRequest = OAuthClientRequest
	                .tokenLocation(String.format("%s/oauth2/token", EngieUrl))
	                .setGrantType(GrantType.PASSWORD)
	                .setClientId(clientId)
	                .setClientSecret(clientSecret)
	                .setUsername(username)
	                .setPassword(password)
	                .buildBodyMessage();

	        OAuthClient oAuthClient =null;
	        if(EngieUrl.startsWith("https")) {
	        	oAuthClient=new OAuthClient(new SSLConnectionClient());
			}else {
				oAuthClient=new OAuthClient(new URLConnectionClient());
			}
	        
	        return oAuthClient.accessToken(oAuthRequest, OAuthJSONAccessTokenResponse.class);
	    }
	 

	 private OAuthAccessTokenResponse getAccessToken(String EngieUrl,String refreshToken) throws OAuthSystemException, OAuthProblemException {
	        OAuthClientRequest oAuthRequest = OAuthClientRequest
	                .tokenLocation(String.format("%s/oauth2/token", EngieUrl))
	                .setGrantType(GrantType.REFRESH_TOKEN)
	                .setRefreshToken(refreshToken)
	                .buildBodyMessage();
	        
	        
	        OAuthClient oAuthClient =null;
	        if(EngieUrl.startsWith("https")) {
	        	oAuthClient=new OAuthClient(new SSLConnectionClient());
			}else {
				oAuthClient=new OAuthClient(new URLConnectionClient());
			}
	       
	        return oAuthClient.accessToken(oAuthRequest, OAuthJSONAccessTokenResponse.class);
	    }
	    



}
