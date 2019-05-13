package qtgl.flow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.quantangle.infoplus.sdk.model.CodeItem;
import com.quantangle.infoplus.sdk.model.CodeItemEx;
import com.quantangle.infoplus.sdk.util.Pinyin;

public class CodeItemImpl implements CodeItemEx {

	private String codeId;
	private String codeName;
	private String codeDescription;
	private String codeParentId;
	private String[] codeIndexes;
	private Map<String, String>attributes;
	
	String index;
	
	public CodeItemImpl() {
	}
	
	public CodeItemImpl(String id, String name, String desc, String pid) {
		this(id, name, desc, pid, null);
	}

	public CodeItemImpl(String id, String name, String desc, String pid, Map<String, String>attributes) {
	    this.codeId = id;
	    this.codeName = name;
	    this.codeDescription = desc;
	    this.codeParentId = pid;
		if (attributes != null) {
			this.attributes = new HashMap<>();
			this.attributes.putAll(attributes);
		}
	}

	public CodeItemImpl(CodeItem item) {
		this.codeId = item.getCodeId();
		this.codeName = item.getCodeName();
		this.codeDescription = item.getCodeDescription();
		this.codeParentId = item.getCodeParentId();
		Map<String, String>codeExpandos = item.getAttributes();
		if (codeExpandos != null) {
			this.attributes = new HashMap<>();
			this.attributes.putAll(codeExpandos);
		}
		
		StringBuffer sb = new StringBuffer();
		if (item instanceof CodeItemEx) {
			this.codeIndexes = ((CodeItemEx)item).getCodeIndexes();
			if (this.codeIndexes != null && this.codeIndexes.length > 0) {
				for (String index : this.codeIndexes) {
					constructIndex(index, sb);
				}
			}
		}
		if (sb.length() == 0) {
			constructIndex(this.codeId, sb);
			constructIndex(this.codeName, sb);
			if (StringUtils.isNotEmpty(this.codeDescription)) {
				for (String token : this.codeDescription.split(",")) {
					constructIndex(token, sb);
				}
			}
		}
		this.index = sb.toString();
	}

	private static void constructIndex(String content, StringBuffer buffer) {
		if (content != null) {
			final char[] chars = content.toUpperCase().toCharArray();
			final int c = chars.length;
			boolean hasChineseChar = false;
			//原始内容
			for (int i = 0; i < c; i++) {
				char ch = chars[i];
				buffer.append('|');
				buffer.append(ch);
				hasChineseChar = hasChineseChar || Pinyin.isChineseChar(ch);
			}
			buffer.append('|');
			if (hasChineseChar) {
				//首字母
				for (int i = 0; i < c; i++) {
					char ch = chars[i];
					char pinyin = Pinyin.firstLetterOfPinyin(ch);
					buffer.append('|');
					buffer.append(pinyin == 0 ? ch : pinyin);
				}
				buffer.append('|');
				//全拼
				for (int i = 0; i < c; i++) {
					buffer.append('|');
					char ch = chars[i];
					String pinyin = Pinyin.pinyin(ch);
					if (pinyin == null) {
						buffer.append(ch);
					} else {
						buffer.append(pinyin);
					}
				}
				buffer.append('|');
			}
		}
	}
	
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setCodeDescription(String description) {
		this.codeDescription = description;
	}

	public String getCodeParentId() {
		return codeParentId;
	}

	public void setCodeParentId(String parentId) {
		this.codeParentId = parentId;
	}

	public String[] getCodeIndexes() {
		return codeIndexes;
	}

	public void setCodeIndexes(String[] codeIndexes) {
		this.codeIndexes = codeIndexes;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public boolean matchParent(String parent) {
		if (parent == null) {
			return true;
		}
		if (parent.length() == 0) {
			return StringUtils.isEmpty(codeParentId);
		}
		return parent.equals(codeParentId);
	}

}
