package qtgl.flow.messengers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import qtgl.api.InfoplusApi;
import qtgl.api.model.FileVo;
import qtgl.flow.BaseInfo;
import qtgl.flow.BaseInfo.EventType;
import qtgl.flow.MyCodeItem;
import qtgl.flow.bo.PxDemoInfo;
import qtgl.flow.bo.PxDemoInfoGZJYList;

import com.quantangle.infoplus.sdk.AbstractMessenger;
import com.quantangle.infoplus.sdk.InfoPlusEvent;
import com.quantangle.infoplus.sdk.InfoPlusResponse;
import com.quantangle.infoplus.sdk.model.CodeItem;
import com.quantangle.infoplus.sdk.model.CodeList;
import com.quantangle.infoplus.sdk.model.Timer;

public class Demo_Messenger extends AbstractMessenger {

	 
	
	BaseInfo bi=new BaseInfo();
	

	/**
	 * 欢迎页  加载事件(流程级)
	 */
	public InfoPlusResponse onInstanceRendering(InfoPlusEvent e) {
	 
		 
		 
		//e.getStep().getTaskId()
		System.out.println("onInstanceRendering");
		
		return null;
	}
	
	
	
	/**
	 *导出 (流程级)
	 */
	public InfoPlusResponse onInstanceExporting(InfoPlusEvent e) {
		System.out.println();
		return null;
	}
	
	
	/**
	 * 启动流程初始化数据(流程级)  onInstanceStarting
	 */
	public InfoPlusResponse onInstanceStarting(InfoPlusEvent e) {
		PxDemoInfo entity = e.toBean(PxDemoInfo.class);
		boolean rest = true;
		String str = null;
		String account = e.getUser().getAccount();
	//	String token=e.getTokens().get("enterprise");//获取第三方资源 token 由平台获取给到我们
		
	//	String url="https://api.sjtu.edu.cn/v1/income/content/2017?access_token="+token;
		
	//	JSONObject jsonObject=HttpConnection.doGet(url, null);
		
		 
//		InfoPlusApplication application = InfoPlusApplication.defaultApplication();
//		application.getSharedCodeTables().invalidate("Jjc_ProjectCode");
//		
		
		e.getStep().getInstance().getInstanceName();
		e.getStep().getappName();
		
		System.out.println("11111111111111111111111111111111111111");
		
		
		this.invalidateCodeTable("Jjc_ProjectCode");
		
		if (rest) {
			double s = 6;
			entity.setBMDM("1");
			InfoPlusResponse res = new InfoPlusResponse();
			// Timer timer=new Timer();
			// timer.setInterval(-1);
			// res.setTimer(timer);
			
//			 Map map=e.getFormData(); 
//	        if(entity.getGRGJZ1().equals("11"))
//	        {
//				map.put("YC", true);
//			}else{
//				map.put("YC", false);
//			}

		
			res.toForm(e, entity);
			//res.toForm(e, entity,"");
			return res;

		} else {

			return new InfoPlusResponse(true, true, str);
		}
		
		
		
		//entity.setRQ(entity.getRQ());
		//response.toForm(e, entity,new String[]{"NL"});
		
		

	}
	

	/**
	 * 补偿事件
	 */
	public InfoPlusResponse onInstanceCompensation(InfoPlusEvent e) {
		PxDemoInfo entity = e.toBean(PxDemoInfo.class);
		
		System.out.println(e.getStep().getInstance().getToken());//每一个流水是一个token
		
		
		System.out.println("onInstanceCompensation");
		
		BaseInfo bi=new BaseInfo();

		InfoplusApi ia = new InfoplusApi();
		JSONObject jsonObject = null;
		JSONObject jsonData = new JSONObject();
		 
		jsonData.put("fieldBZ", "这个是后台赋值的");

		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;
	}
	
	/**
	 * 加载 项目
	 */
	public InfoPlusResponse onFieldChanging(InfoPlusEvent e) {
		PxDemoInfo entity = e.toBean(PxDemoInfo.class);
		 
		InfoPlusResponse response = new InfoPlusResponse();
		
		switch (e.getChangingField()) {
		case "fieldXB":
			String cgfs=entity.getBZ();//采购方式
			if (cgfs.equals("1")) {
				
				List<PxDemoInfoGZJYList> gZJYList=new ArrayList<>();
				for (int i = 0; i <3; i++) {
					PxDemoInfoGZJYList model=new PxDemoInfoGZJYList();
					gZJYList.add(model);
				}
				
				entity.setGZJYList(gZJYList);
				
				
			}else if (cgfs.equals("2")) {
				List<PxDemoInfoGZJYList> gZJYList=new ArrayList<>();
				for (int i = 0; i <5; i++) {
					PxDemoInfoGZJYList model=new PxDemoInfoGZJYList();
					gZJYList.add(model);
				}
				
				entity.setGZJYList(gZJYList);
			}
			entity.setAccount("11111");
			//entity.setRQ(entity.getRQ());
			//response.toForm(e, entity,new String[]{"NL"});
			response.toForm(e, entity);
			break;
		case "fieldInfoXB":
			PxDemoInfoGZJYList sss=e.getChangingObject(entity);
			 
			sss.setGZJYGSMC("44444");
		    response.toForm(e, sss);
		   
			break;
		default:
			break;
		}
		return response;
		 
	}
	/**
	 * 外部代码表 无缓存
	 */
	@Override
	public InfoPlusResponse onFieldSuggesting(InfoPlusEvent e) {

		String code = e.getSuggestion().getCode();
		String Parent = e.getSuggestion().getParent();
		
		String SFBX_CodeId = "";

		
		List<CodeItem> item_list = new ArrayList<CodeItem>();
		switch (code) {
		case "px_xkmcxx":// 选课 课程名称
			SFBX_CodeId="2";
			MyCodeItem ciCodeItem=new  MyCodeItem(null,null,null,null);
			break;
		
			
		default:
			return super.onFieldSuggesting(e);//使用全局code数据
		}

		CodeList codeList = new CodeList();
		codeList.setName(code);
		codeList.setItems(item_list);

		List<CodeList> list_code = new ArrayList<CodeList>();
		list_code.add(codeList);

		InfoPlusResponse res = new InfoPlusResponse();
		res.setCodes(list_code);

		return res;
	}
	
	/**
	 * 外部代码表  有缓存
	 */
	@Override
	public List<? extends CodeItem> getSuggestionData(String code) {
		List<CodeItem> item_list = new ArrayList<CodeItem>();
 
		switch(code)
		{
		case "courseno":
			MyCodeItem ciCodeItem=new  MyCodeItem(null,null,null,null);
			
			Map<String, String> map=new HashMap();
			map.put("ms", "1212");
			map.put("CCCC", "");
			ciCodeItem.setAttributes(map);
			
			 item_list.add(ciCodeItem);
			 
			//清理缓存   本流程
			this.invalidateCodeTable("CodeNmae");
			//全局  清理项目信息缓存
			this.application.getSharedCodeTables().invalidate("PX_PXMC");
			
			
			break;	
			
		case "ssss":
				super.getSuggestionData(code);
			break;
		 
		
		}
		return item_list;
	}
	
	
	
	/**
	 * 填写申请 
	 * Rendering刷新加载事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddRendering(InfoPlusEvent e) {
		
		return Rendering(e);
		
	}
	
	
	/**
	 * 填写申请 Add 提交 Submit
	 * Clicking点击事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddActionSubmitClicking(InfoPlusEvent e) {
		//System.out.println("onStepAddActionSubmitClicking");
		return Clicking(e);
	}
	
	/**
	 * 填写申请 Add 通过 Submit
	 * Doing事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddActionSubmitDoing(InfoPlusEvent e) {
		//System.out.println(e.getFormData());
		//System.out.println("onStepAddActionSubmitDoing");
		return Doing(e);
	}
	
	/**
	 * 节点保存事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddSaving(InfoPlusEvent e)
	{
		System.out.println("onStepAddSaving");
		
		return new InfoPlusResponse();
	}
	
	public InfoPlusResponse onStepAddSaved(InfoPlusEvent e)
	{
		System.out.println("onStepAddSaved");
		
		return new InfoPlusResponse();
	}
	
	/**
	 * 打印事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddPrinting(InfoPlusEvent e)
	{
		System.out.println("onStepAddPrinting");
		
		PxDemoInfo entity = (PxDemoInfo) e.toBean(PxDemoInfo.class);
		 entity.setAccount("1111111111");
		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;
	}
	
	
	/**
	 * 步骤级导出事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddExporting(InfoPlusEvent e)
	{
		System.out.println("onStepAddExporting");
		
		PxDemoInfo entity = (PxDemoInfo) e.toBean(PxDemoInfo.class);
		 entity.setAccount("修改之后的数据");
		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;
	}
	
	
	
	/**
	 * 撤回事件  使用提交的节点和动作 来拼接方法名称
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddActionSubmitWithdrawn(InfoPlusEvent e) {
		System.out.println("onStepAddActionSubmitWithdrawn");
		return new InfoPlusResponse(true, true, "111");
	}
	public InfoPlusResponse onStepAddActionSubmitWithdrawing(InfoPlusEvent e) {
		System.out.println("onStepAddActionSubmitWithdrawing");
		return new InfoPlusResponse(true, true, "222");
	}
	
	
	/**
	 * 填写申请 超时
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddExpiring(InfoPlusEvent e) {
		System.out.println("填写申请 超时");
		InfoPlusResponse info = new InfoPlusResponse();
		Timer timer = new Timer();
		timer.setAction("End");
		info.setTimer(timer);
		return info;
	}
	
	
	/**
	 * 处理所有Rendering 事件
	 * 
	 * @param e
	 * @return
	 */
	public InfoPlusResponse Rendering(InfoPlusEvent e) {
		PxDemoInfo entity =e.toBean(PxDemoInfo.class);
		String methodName = BaseInfo.GetMethod(e, EventType.Rendering);
 
		String token=e.getStep().getInstance().getToken();//每个实例一个token 用于拼接地址 进行显示
		System.out.println("token:"+token);
	 
 
		switch (methodName) {
		case "onStepAddRendering"://填写申请
			//transaction_test();
			//testRSC();
			break;
		default:
			break;
		}
		
		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;
	}
	
	
	/**
	 * 处理所有 Clicking 事件
	 * 
	 * @param e
	 * @return
	 */
	public InfoPlusResponse Clicking(InfoPlusEvent e) {
		PxDemoInfo entity = (PxDemoInfo) e.toBean(PxDemoInfo.class);
		String methodName = BaseInfo.GetMethod(e, EventType.Clicking);
		String str = "";
		switch (methodName) {

		case "onStepAddActionSubmitClicking": 
			//GRGJZ1
			
			//System.out.println(entity.getGRGJZ1());
			
			
			//rest=false;
			//str="测试";
			  
			 
			break;
		 
		default:
			break;
		}
		
		 
		
		
		//Add(e);
		
		if (bi.StringIsNotEmpty(str)) {
			return new InfoPlusResponse(true, true, str);
			
		} else {
			return new InfoPlusResponse();
		}

	}
	
	
	
	/**
	 * 处理所有Doing事件
	 * 
	 * @param e
	 * @return
	 */
	public InfoPlusResponse Doing(InfoPlusEvent e) {
		PxDemoInfo entity = (PxDemoInfo) e.toBean(PxDemoInfo.class);
		String methodName = BaseInfo.GetMethod(e, EventType.Doing);
		boolean rest_add = false;
		switch (methodName) {
		case "onStepAddActionSubmitDoing":
			rest_add = true;
			break;

		default:
			break;
		}
		
		 
		
		//上传文件到 file 服务器
		uploadFile1(e);
		
		if (rest_add) {
			//Add(e);

		}
		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;
	}
	
	/**
	 * 利用导出word模板  导出文件到本地 然后上传到file服务器
	 * @param e
	 */
	public void uploadFile(InfoPlusEvent e){

		InfoplusApi ia = new InfoplusApi();
		String EntryId=e.getStep().getInstance().getEntryId()+"";
		//利用导出word模板 调用API 生成 word或者pdf 保存到本地 获得文件地址
		String filePath=ia.export_word_or_pdf(EntryId, "", "11",InfoplusApi.FileType.pdf);

		 System.out.println(filePath);
//		//阿里云测试
//		 String file = "http://ws01.taskcenter.net/file";
//		 String access_token="436c169133e010e456531de9e2cc5171";
		 
		 
		 //交大测试
		 String file = "https://api.sjtu.edu.cn/v1/file";
		 String EngieUrl="https://jaccount.sjtu.edu.cn";//用于获取token
		 String access_token="";
		 try {
			access_token=ia.getAccessToken(EngieUrl,"VitpfbVtgOMkbOhbMqJW5o74","1A344A1F8054E99E94F26BF8C40D085229266EBA13D3911C","storage").getAccessToken();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		 System.out.println("access_token:"+access_token);
		 FileVo uploadFile = ia.uploadFile(file,access_token, filePath);
		 String uri = uploadFile.getUri();
		 System.out.println("uri:"+uri);
		 
		 
	}
	
	public void uploadFile1(InfoPlusEvent e){

		InfoplusApi ia = new InfoplusApi();
		String EntryId=e.getStep().getInstance().getEntryId()+"";
		 
		 //交大测试
		 String fileApi = "https://api.sjtu.edu.cn/v1/file";
		 String EngieUrl="https://jaccount.sjtu.edu.cn";//用于获取token
		 String access_token="";
		 try {
			access_token=ia.getAccessToken(EngieUrl,"VitpfbVtgOMkbOhbMqJW5o74","1A344A1F8054E99E94F26BF8C40D085229266EBA13D3911C","storage").getAccessToken();
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		 System.out.println("access_token:"+access_token);
		 FileVo uploadFile = ia.uploadFile(EntryId,"ceshi",InfoplusApi.FileType.pdf, fileApi, access_token);
		 String uri = uploadFile.getUri();
		 System.out.println("uri:"+uri);
		 
		 
	}
	
	
	  
	
	/**
	 * 数据入库
	 * 
	 * @param e
	 */
	public void Add(InfoPlusEvent e) {
//		PxDemoInfo entity = (PxDemoInfo)e.toBean(PxDemoInfo.class);
//		FlowInfo fl = BaseInfo.Get_info(e);
//		
//		jwc.Add_WorkFlowModel_All(entity, fl);
//		String sql="SELECT * FROM PxDemoInfo WHERE Workflows_ID='16059'";
//		
//		PxDemoInfo newEntity=jwc.getModel_All(PxDemoInfo.class, sql); 
//		
//		System.out.println(newEntity.getGZJYList().size());
		
	}
	
}
