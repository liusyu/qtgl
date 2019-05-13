package qtgl.flow;

import java.lang.reflect.Type;


import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.quantangle.infoplus.sdk.model.CodeItem;

public class CodeItemJson implements JsonSerializer<CodeItem>,JsonDeserializer<CodeItem>{

	Gson gson = new Gson();

	/**
	 * 序列化时调用
	 */
    @Override
    public JsonElement serialize(CodeItem item, Type type, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("codeId", item.getCodeId());
        wrapper.addProperty("codeName", item.getCodeName());
        wrapper.addProperty("description", item.getCodeDescription());
        wrapper.addProperty("parentId", item.getCodeParentId());
        wrapper.add("attributes", context.serialize(item.getAttributes()));
        return wrapper;
    }

    /** 
     * 反序列化时调用 
     */ 
	@Override
	public CodeItem deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context) throws JsonParseException {
		// TODO Auto-generated method stub
		// System.out.println(json);
		
		MyCodeItem ci = gson.fromJson(json, MyCodeItem.class);
		return ci;
	}
    
 
}