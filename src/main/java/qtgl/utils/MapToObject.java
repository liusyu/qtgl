package qtgl.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import qtgl.flow.MyCodeItem;

import com.quantangle.infoplus.sdk.model.InfoPlusUser;

public class MapToObject {
	/**
	 * 返回由对象的属性为key,值为map的value的Map集合
	 * 
	 * @param obj
	 *            Object
	 * @return mapValue Map<String,String>
	 * @throws Exception
	 */
	public static Map<String, String> getFieldVlaue(Object obj)
			throws Exception {
		Map<String, String> mapValue = new HashMap<String, String>();
		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			String strGet = "get" + name.substring(0, 1).toUpperCase()
					+ name.substring(1, name.length());
			Method methodGet = cls.getDeclaredMethod(strGet);
			Object object = methodGet.invoke(obj);
			String value = object != null ? object.toString() : "";
			mapValue.put(name, value);
		}
		return mapValue;
	}

	/**
	 * 返回由Map的key对属性，value对应值组成的对应
	 * 
	 * @param map
	 *            Map<String,String>
	 * @param cls
	 *            Class
	 * @return obj Object
	 * @throws Exception
	 */
	public static <T> T setFieldValue(Map<String, String> map, Class<T> cls)
			throws Exception {

		Field[] fields = cls.getDeclaredFields();
		T obj = cls.newInstance();
		for (Field field : fields) {
			Class<?> clsType = field.getType();
			String name = field.getName();
			String strSet = "set" + name.substring(0, 1).toUpperCase()
					+ name.substring(1, name.length());
			Method methodSet = cls.getDeclaredMethod(strSet, clsType);
			if (map.containsKey(name)) {
				Object objValue = typeConversion(clsType, map.get(name));
				methodSet.invoke(obj, objValue);
			}
		}
		return obj;
	}

	/**
	 * 返回由Map的key对属性，value对应值组成的对应
	 * 
	 * @param map
	 *            Map<String,String>
	 * @param cls
	 *            Class
	 * @return obj Object
	 * @throws Exception
	 */
	public static <T> T setFieldValue_flow(Map<String, String> map, Class<T> cls)
			throws Exception {

		Field[] fields = cls.getDeclaredFields();
		T obj = cls.newInstance();
		String zd_name = null;

		for (Field field : fields) {
			Class<?> clsType = field.getType();// 类型
			if (!clsType.toString().endsWith(".List")) {
				String name = field.getName();
				String strSet = "set" + name.substring(0, 1).toUpperCase()
						+ name.substring(1, name.length());
				Method methodSet = cls.getDeclaredMethod(strSet, clsType);
				Boolean b = false;
				zd_name = name;
				// 判断是否存在信息
				if (map.containsKey(zd_name)) {
					b = true;
				} else {
					zd_name = name.toUpperCase();
					if (map.containsKey(zd_name)) {
						b = true;
					} else {
						zd_name = name.substring(0, 1).toUpperCase()
								+ name.substring(1);
						if (map.containsKey(zd_name)) {
							b = true;
						}
					}
				}

				if (b) {
					Object objValue = typeConversion_flow(clsType, map, zd_name);
					methodSet.invoke(obj, objValue);
				}

			}

		}
		return obj;
	}

	/**
	 * 获得数据
	 * 
	 * @param cls
	 * @param str
	 * @return
	 */
	public static Object typeConversion_flow(Class<?> cls,
			Map<String, String> map, String zd_name) {
		Object obj = null;
		String zd_Type = cls.getSimpleName();
		String name = map.get(zd_name);
		String code_id = zd_name + "_CodeId";
		String code_id_value = null;
		if (name != null) {
			if ("Integer".equals(zd_Type)) {
				obj = Integer.valueOf(name);
			}
			if ("String".equals(zd_Type)) {
				obj = name;
			}
			if ("Float".equals(zd_Type)) {
				obj = Float.valueOf(name);
			}
			if ("Double".equals(zd_Type)) {
				if (name == null) {
					obj = Double.valueOf("0");
				} else {
					obj = Double.valueOf(name);
				}

			}

			if ("Boolean".equals(zd_Type)) {
				obj = Boolean.valueOf(name);
			}
			if ("Long".equals(zd_Type)) {
				obj = Long.valueOf(name);
			}

			if ("Short".equals(zd_Type)) {
				obj = Short.valueOf(name);
			}

			if ("Character".equals(zd_Type)) {
				obj = name.charAt(1);
			}
			if ("Date".equals(zd_Type)) {
				if (name == null) {
					obj = null;

				} else {
					obj = DateUtil.getStandardDate(name);
				}
			}

		}

		if ("CodeItem".equals(zd_Type))// CodeItem
		{

			if (map.containsKey(code_id)) {
				code_id_value = map.get(code_id);
			} else {
				if (map.containsKey(code_id.toUpperCase())) {
					code_id_value = map.get(code_id.toUpperCase());
				}
			}
			MyCodeItem ci = new MyCodeItem(code_id_value, name, null, null);
			obj = ci;
		}

		if ("InfoPlusUser".equals(zd_Type))// InfoPlusUser
		{
			if (map.containsKey(code_id)) {
				code_id_value = map.get(code_id);
			} else {
				if (map.containsKey(code_id.toUpperCase())) {
					code_id_value = map.get(code_id.toUpperCase());
				}
			}
			final String account = code_id_value;
			InfoPlusUser iu = new InfoPlusUser();
			iu.setAccount(account);
			iu.setTrueName(name);
			obj = iu;

		}

		return obj;
	}

	/**
	 * 将Map里面的部分值通过反射设置到已有对象里去
	 * 
	 * @param obj
	 *            Object
	 * @param data
	 *            Map<String,String>
	 * @return obj Object
	 * @throws Exception
	 */
	public static Object setObjectFileValue(Object obj, Map<String, String> data)
			throws Exception {
		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			Class<?> clsType = field.getType();
			String name = field.getName();
			String strSet = "set" + name.substring(0, 1).toUpperCase()
					+ name.substring(1, name.length());
			Method methodSet = cls.getDeclaredMethod(strSet, clsType);
			if (data.containsKey(name)) {
				Object objValue = typeConversion(clsType, data.get(name));
				methodSet.invoke(obj, objValue);
			}
		}
		return obj;
	}

	/**
	 * 把对象的值用Map对应装起来
	 * 
	 * @param map
	 *            Map<String,String>
	 * @param obj
	 *            Object
	 * @return 与对象属性对应的Map Map<String,String>
	 */
	public static Map<String, String> compareMap(Map<String, String> map,
			Object obj) {
		Map<String, String> mapValue = new HashMap<String, String>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if (map.containsKey(name)) {
				mapValue.put(name, map.get(name));
			}
		}
		return mapValue;
	}

	/**
	 * 把临时对象的值复制到持久化对象上
	 * 
	 * @param oldObject
	 *            Object 持久化对象
	 * @param newObject
	 *            Object 临时对象
	 * @return 持久化对象
	 * @throws Exception
	 */
	public static Object mergedObject(Object oldObject, Object newObject)
			throws Exception {
		Class<?> cls = newObject.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			Class<?> clsType = field.getType();
			String name = field.getName();
			String method = name.substring(0, 1).toUpperCase()
					+ name.substring(1, name.length());
			String strGet = "get" + method;
			Method methodGet = cls.getDeclaredMethod(strGet);
			Object object = methodGet.invoke(newObject);
			if (object != null) {
				String strSet = "set" + method;
				Method methodSet = cls.getDeclaredMethod(strSet, clsType);
				Object objValue = typeConversion(clsType, object.toString());
				methodSet.invoke(oldObject, objValue);
			}
		}
		return oldObject;
	}

	public static Object typeConversion(Class<?> cls, String str) {
		Object obj = null;
		String nameType = cls.getSimpleName();
		if (str != null) {
			if ("Integer".equals(nameType)) {
				obj = Integer.valueOf(str);
			}
			if ("String".equals(nameType)) {
				obj = str;
			}
			if ("Float".equals(nameType)) {
				obj = Float.valueOf(str);
			}
			if ("Double".equals(nameType)) {
				obj = Double.valueOf(str);
			}

			if ("Boolean".equals(nameType)) {
				obj = Boolean.valueOf(str);
			}
			if ("Long".equals(nameType)) {
				obj = Long.valueOf(str);
			}

			if ("Short".equals(nameType)) {
				obj = Short.valueOf(str);
			}

			if ("Character".equals(nameType)) {
				obj = str.charAt(1);
			}

		}

		return obj;
	}
}
