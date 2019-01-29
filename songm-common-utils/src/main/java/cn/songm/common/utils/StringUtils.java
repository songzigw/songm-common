package cn.songm.common.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class StringUtils {

	public static String toString(Object obj) {
		try {
			return _toString(obj);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String _toString(Object obj) throws IllegalArgumentException, IllegalAccessException {
		if (obj == null) return null;
		
		Class<?> cla = obj.getClass();
		if (cla.isEnum() || cla == Integer.class || cla == Short.class || cla == Byte.class || cla == Long.class || cla == Double.class
			|| cla == Float.class || cla == Boolean.class || cla == String.class || cla == Character.class) {
		    return obj.toString();
		}

		StringBuilder str = new StringBuilder();	
		if (cla.isArray()) {
		    str.append("[");
		    for (int i = 0; i < Array.getLength(obj); i++) {
			if (i > 0) str.append(", ");
			Object val = Array.get(obj, i);
			
			if (val != null && !val.equals("")) {
			    str.append(toString(val));
			}
		    }
		    str.append("]");
		    return str.toString();
		}
		
		Field[] fields = cla.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		str.append("[");
		for (int i = 0; i < fields.length; i++) {
		    if (i > 0) str.append(", ");
		    Field f = fields[i];
		    str.append(f.getName()).append("=");
		    if (!f.getType().isPrimitive()) {
			str.append(toString(f.get(obj)));
		    } else {
			str.append(f.get(obj));
		    }
		}
		str.append("]");
		
		return str.toString();
	}
	
	public static String html(String content) {
        return content.replace("\\r\\n", "<br />");
    }

    public static Long parseLong(String param) {
    	if (isEmptyOrNull(param)) return null;
        return Long.valueOf(param.trim());
    }

    public static Integer parseInt(String param) {
    	if (isEmptyOrNull(param)) return null;
        return Integer.valueOf(param.trim());
    }

    public static Float parseFloat(String param) {
    	if (isEmptyOrNull(param)) return null;
        return Float.valueOf(param.trim());
    }

    public static Boolean parseBoolean(String param) {
    	if (isEmptyOrNull(param)) return null;
    	if (!param.trim().equals("true") && !param.trim().equals("false")) {
    		throw new IllegalArgumentException(String.format("param=%s, param('true','false')", param));
    	}
        return Boolean.valueOf(param.trim());
    }

    /**
     * 获得文件名的后缀名
     * 
     * @param fileName
     * @return
     */
    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取去掉横线的长度为32的UUID串
     * 
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取带横线的长度为36的UUID串
     * 
     * @return
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean isEmptyOrNull(Object obj) {
    	if (null == obj) return true;
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        if (obj.getClass().isArray()) {
        	return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection<?>) {
        	Collection<?> c = (Collection<?>) obj;
        	return c.isEmpty();
        }
        if (obj instanceof Map<?, ?>) {
        	Map<?, ?> c = (Map<?, ?>) obj;
        	return c.isEmpty();
        }
        throw new RuntimeException("does not support");
    }

}