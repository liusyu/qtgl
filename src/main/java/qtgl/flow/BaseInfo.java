package qtgl.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quantangle.infoplus.sdk.InfoPlusEvent;
import com.quantangle.infoplus.sdk.InfoPlusResponse;
import com.quantangle.infoplus.sdk.model.CodeItem;
import com.quantangle.infoplus.sdk.model.CodeList;
import com.quantangle.infoplus.sdk.model.CodeSuggestion;

public class BaseInfo {

	public final static Gson gson = new GsonBuilder()
    .registerTypeHierarchyAdapter(CodeItem.class,new CodeItemJson())
    .create();
	
	public  enum EventType{
		
		Rendering,Clicking,Doing;
	}


	/**
	 * 获得方法名称
	 * @param e 
	 * @param type 
	 * @return
	 */
	public static String GetMethod(InfoPlusEvent e,EventType type)
	{
		String action=type.name();
		String methodName = null;
		String fromStep = e.getStep().getStepCode();
		if (fromStep == null) {
			methodName = String.format("onInstance%s", action);
		} else {
			String actionCode = e.getActionCode();
			if (actionCode == null) {
				methodName = String.format("onStep%c%s%s", Character.toUpperCase(fromStep.charAt(0)), fromStep.substring(1), action);
			} else {
				methodName = String.format("onStep%c%sAction%c%s%s", Character.toUpperCase(fromStep.charAt(0)), fromStep.substring(1), Character.toUpperCase(actionCode.charAt(0)), actionCode.substring(1), action);
			}
		}
		
		return methodName;
	}
	/**
	 * 获取流程信息
	 * @param e
	 * @return
	 */
    public static FlowInfo Get_info(InfoPlusEvent e)
    {
    	FlowInfo fi=new FlowInfo();
        if (e != null)
        {
        	fi.setWorkflows_GUID(e.getStep().getInstance().getInstanceId());//流程GUID
        	fi.setWorkflows_ID(String.valueOf(e.getStep().getInstance().getEntryId()));//流水号
        	fi.setWorkflows_Code(e.getStep().getWorkflowCode());//工作流Code

        	 
        }
        
        return fi;
    }
    
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean StringIsNotEmpty(String str)
    {
    	
    	return StringUtils.isNotEmpty(str);
    }
    
    /**
     * 获取当前分支对象
     * @param e
     * @param list
     * @param clazz
     * @return
     */
    public static <T> T LocateSplitObject(InfoPlusEvent e, List<T> list)
    {
		if (list == null) {
			throw new NullPointerException();
		}
		String code = e.getStep().getSplitIdentifier();
		String[] path = code.split(",");
		int index = -1;
		if (path.length > 1) {
			path = path[2].split("/");
			if (path.length > 1) {
				index = Integer.valueOf(path[1]);
			} else
				index = -1;
		}
		if (index==-1) {
			return null;
		}

		return list.get(index);
    }
    
    /**
     * 对于code数据 查询与分页
     * @param e
     * @param code
     * @param item_list
     * @return
     */
    public static InfoPlusResponse getQeryCodeList(InfoPlusEvent e,String code,List<CodeItemImpl> item_list)
    {
		InfoPlusResponse res = new InfoPlusResponse();
		CodeList codeList = new CodeList();
		res.setCodes(Arrays.asList(codeList));
		List<CodeItemImpl> items = new ArrayList<>();
		codeList.setItems(items);
		
		CodeSuggestion suggestion = e.getSuggestion();
		//String code = suggestion.getCode();
		List<CodeItemImpl> list = item_list;
	
		if (list == null) {
			return res;
		}
		
		
		int pageSize = suggestion.getPageSize();
		int start = suggestion.getPageNo() * pageSize;
		int end = start + pageSize - 1;
		int count = 0;
		String prefix = suggestion.getPrefix().toUpperCase();
		String parent = suggestion.isIsTopLevel() ? null : suggestion.getParent();
		
//		System.out.println("prefix："+prefix);
//		System.out.println("parent："+parent);
		if (prefix.equals("")) {
			prefix="  ";
		}

		prefix="  ";
		parent="";
		
		final String[] tokens = prefix.split(",| ");
		final int c = tokens.length;
		final Pattern[] patterns = new Pattern[tokens.length];
		final StringBuffer query = new StringBuffer();
		for (int i = 0; i < c; i++) {
			query.setLength(0);
			query.append("\\|");
			for (int j = 0, cc = tokens[i].length(); j < cc; j++) {
				char ch = tokens[i].charAt(j);
				if (ch == '\\' || ch == '.' || ch == '?' || ch == '*' || ch == '(' || ch == ')' || ch == '[' || ch == ']' || ch == '^' || ch == '$' || ch == '{'  || ch == '}') {
					query.append('\\');
				}
				query.append(ch).append("\\|?");
			}
			patterns[i] = Pattern.compile(query.toString());
		}
		for (CodeItemImpl item : list) {
			if (item.matchParent(parent)) {
				boolean match = true;
				for (int i = 0; i < c; i++) {
					 if (!patterns[i].matcher(item.index).find()) {
						 match = false;
						 break;
					 }
				}
				if (match) {
					count++;
					if (count > start) {
						items.add(item);
					}
					if (count > end) {
						break;
					}
				}
			}
		}
		return res;
	}
    
}
