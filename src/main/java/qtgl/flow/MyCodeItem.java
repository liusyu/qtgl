package qtgl.flow;
import java.util.Map;

import com.quantangle.infoplus.sdk.model.CodeItem;

public class MyCodeItem implements CodeItem{

	private String codeId;
	private String codeName;
	private String description;
	private String parentId;
	private Map<String, String> attributes;
	

	public MyCodeItem (String CodeId,String CodeName,String Desc,String Pid){
		codeId=CodeId;
		codeName=CodeName;
		description=Desc;
		parentId=Pid;
	}
	
	@Override
	public String getCodeId() {
		// TODO Auto-generated method stub
		return this.codeId;
	}

	@Override
	public String getCodeName() {
		// TODO Auto-generated method stub
		return this.codeName;
	}

	@Override
	public String getCodeDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}

	@Override
	public String getCodeParentId() {
		// TODO Auto-generated method stub
		return this.parentId;
	}

	@Override
	public Map<String, String> getAttributes() {
		// TODO Auto-generated method stub
		return this.attributes;
	}
	
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

}
