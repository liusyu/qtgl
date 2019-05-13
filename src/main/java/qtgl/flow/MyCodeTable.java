package qtgl.flow;

import java.util.ArrayList;
import java.util.List;

import com.quantangle.infoplus.sdk.model.CodeItem;
import com.quantangle.infoplus.sdk.util.cache.CachableDataBuilder;



public class MyCodeTable implements
		CachableDataBuilder<String, List<? extends CodeItem>> {

	private long timeout = 3600000;
	private String key;

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public long getTimeoutMillis() {
		return timeout;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public List<? extends CodeItem> buildCachableData() {
		List<CodeItem> result = new ArrayList<CodeItem>();
		switch (key) {
		case "111":
			//		//全局   在messenger代码中清理代码表数据  清除缓存用的
			//this.application.getSharedCodeTables().invalidate("PX_PXMC");
			result.add(new MyCodeItem("1","11","22","11"));
			break;

		default:
			break;
		}
		return result;
	}

}
