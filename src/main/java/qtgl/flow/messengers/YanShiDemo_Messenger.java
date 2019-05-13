package qtgl.flow.messengers;
import java.util.Map;

import qtgl.flow.BaseInfo;
import qtgl.flow.BaseInfo.EventType;
import qtgl.flow.bo.PxDemoInfo;
import qtgl.service.jwcService.UserService;
import qtgl.service.rscService.ToKenService;

import com.quantangle.infoplus.sdk.AbstractMessenger;
import com.quantangle.infoplus.sdk.InfoPlusEvent;
import com.quantangle.infoplus.sdk.InfoPlusResponse;

import javax.annotation.Resource;

public class YanShiDemo_Messenger extends AbstractMessenger {

	BaseInfo bi=new BaseInfo();

	@Resource
	private UserService userService;

	@Resource
	private ToKenService toKenService;
	
	/**
	 * 启动流程初始化数据(流程级)  onInstanceStarting
	 */
	public InfoPlusResponse onInstanceStarting(InfoPlusEvent e) {
		PxDemoInfo entity = e.toBean(PxDemoInfo.class);
		String Account=e.getUser().getAccount();
	  entity.setBZ("这个是从后台：onInstanceStarting事件赋值的66666");


		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;

	}
	public InfoPlusResponse onStepAddRendering(InfoPlusEvent e) {
		System.out.println("onStepAddRendering");
		return Rendering(e);
	}
	
	/**
	 * 填写申请 Add 通过 Submit
	 * Doing事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepAddActionSubmitDoing(InfoPlusEvent e) {
		System.out.println("onStepAddActionSubmitDoing");
		return Doing(e);
	}
	/**
	 * 后勤人员审核 HQRY  通过 Submit
	 * Doing事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepHQRYActionSubmitDoing(InfoPlusEvent e) {
		System.out.println("onStepHQRYActionSubmitDoing");
		return Doing(e);
	}
	
	/**
	 * 宿管人员审核 SGRY 通过 Submit
	 * Doing事件
	 * @param e
	 * @return
	 */
	public InfoPlusResponse onStepSGRYActionSubmitDoing(InfoPlusEvent e) {
		System.out.println("onStepSGRYActionSubmitDoing");
		return Doing(e);
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

		System.out.println("调用了：onInstanceStarting");

		System.out.println("教务处:"+userService.findAll().size());

		System.out.println("人事处:"+toKenService.findAll().size());


		System.out.println("多数据源测试");

		System.out.println("多数据元测算111111");
		System.out.println("测试");


		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;
	}
	
	
	/**
	 * 处理所有Doing事件
	 * 
	 * @param e
	 * @return
	 */
	public InfoPlusResponse Doing(InfoPlusEvent e) {
		PxDemoInfo entity =e.toBean(PxDemoInfo.class);
		String methodName = BaseInfo.GetMethod(e, EventType.Doing);
		boolean rest_add = false;
		switch (methodName) {
		case "onStepAddActionSubmitDoing"://填写申请
		case "onStepSGRYActionSubmitDoing"://后勤管理人员
			rest_add = true;
			break;
		case "onStepHQRYActionSubmitDoing"://宿管人员
		 
			break;

		default:
			break;
		}
		
		 
		InfoPlusResponse res = new InfoPlusResponse();
		res.toForm(e, entity);
		return res;
	}
	
	 
	
	
}
