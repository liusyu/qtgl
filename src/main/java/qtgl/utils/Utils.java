package qtgl.utils;

import java.lang.reflect.Method;
import java.util.Collection;

public class Utils {

	
	public static Method getGetMehtodByField(Class clazz, String field) {
		Method method = null;

		String methodName = "get" + field.substring(0, 1).toUpperCase()
				+ field.substring(1);
		try {
			method = clazz.getMethod(methodName, new Class[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return method;
	}
	
	public static Object[] objectToArray(Object o) {
        // TODO : try Arrays.asList
        if (null == o) return null;
        Class type = o.getClass();
        // String is an exception
        if (type.equals(String.class) || type.isPrimitive()) return null;
        if (type.isArray()) {
            return (Object[]) o;
        }
        /*
        if (typeof(IEnumerable<Object>).IsAssignableFrom(type))
            return (o as IEnumerable<Object>).ToArray();
        else if (typeof(IEnumerable).IsAssignableFrom(type))
        return (o as IEnumerable).Cast<Object>().ToArray();
        */
        if (Collection.class.isAssignableFrom(type)) {
            return ((Collection) o).toArray();
        }
        return null;
    }
}
