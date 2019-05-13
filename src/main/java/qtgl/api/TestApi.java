package qtgl.api;

import qtgl.api.model.FileVo;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class TestApi {

	public static void main(String[] args) {
		TestApi testApi=new TestApi();
		testApi.LambdaAgo();
		 
	}

	public  void  LambdaAgo()
	{
		String[] players = {"Rafael Nadal", "Novak Djokovic",
				"Stanislas Wawrinka", "David Ferrer",
				"Roger Federer", "Andy Murray",
				"Tomas Berdych", "Juan Martin Del Potro",
				"Richard Gasquet", "John Isner"};
		Arrays.sort(players);//排序

		List<String> list=Arrays.asList(players);

		list.forEach(a->System.out.println(a));




	}


	public void uploadFile(){

		InfoplusApi ia = new InfoplusApi();
		String EntryId="44195";
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
	
	public void uploadFile1(){

		InfoplusApi ia = new InfoplusApi();
		String EntryId="44195";
		 
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
		 FileVo uploadFile = ia.uploadFile(EntryId,"niandujihua", InfoplusApi.FileType.pdf, fileApi, access_token);
		 String uri = uploadFile.getUri();
		 System.out.println("uri:"+uri);
		 
		 
	}
	
	
	
}
