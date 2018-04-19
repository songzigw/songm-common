package cn.songm.common.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String html(String content) {
        return content.replace("\\r\\n", "<br />");
    }

    public static boolean find(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean matches(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String replace(String str, String regex, String newStr) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll(newStr);
    }

    public static String[] split(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        return p.split(str);
    }

    public static String[] split(String str, String regex, int count) {
        Pattern p = Pattern.compile(regex);
        return p.split(str, count);
    }

    public static boolean isEmptyOrNull(String param) {
        return (param == null) || (param.trim().equals(""));
    }
    
    public static boolean isBlank(String param) {
        return isEmptyOrNull(param);
    }
    
    public static boolean isNotBlank(String param) {
        return !isEmptyOrNull(param);
    }

    public static Long parseLong(String param) {
        if ((param == null) || (param.trim().equals(""))) {
            return null;
        }
        return Long.valueOf(param.trim());
    }

    public static Integer parseInt(String param) {
        if ((param == null) || (param.trim().equals(""))) {
            return null;
        }
        return Integer.valueOf(param.trim());
    }

    public static Float parseFloat(String param) {
        if ((param == null) || (param.trim().equals(""))) {
            return null;
        }
        return Float.valueOf(param.trim());
    }

    public static Boolean parseBoolean(String param) {
        if ((param == null) || (param.trim().equals(""))) {
            return null;
        }
        return Boolean.valueOf(param.trim());
    }

    public static String convertLongToStr(Long value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static String convertDateToStr(Date value) {
        if (value == null) {
            return null;
        }
        return value.toString();
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

    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 函数功能说明 ： 判断对象数组是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(Object[] obj) {
        return null == obj || 0 == obj.length;
    }

    /**
     * 函数功能说明 ： 判断对象是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        return !(obj instanceof Number) ? false : false;
    }

    /**
     * 函数功能说明 ： 判断集合是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(List<?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 函数功能说明 ： 判断Map集合是否为空. 修改者名字： 修改日期： 修改内容：
     * 
     * @参数： @param obj
     * @参数： @return
     * @return boolean
     * @throws
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return null == obj || obj.isEmpty();
    }
    
    @SuppressWarnings("rawtypes")
	public static String toString(Object obj) {
    	if (null == obj) {
            return null;
        }
    	
        // getClass返回一个Class类型的实例,这个实例里面保存着这个对象运行时的类型信息
        Class cl = obj.getClass();
        // 如果这个对象是String类型的,则可以直接返回String
        if (cl == String.class) {
            return (String) obj;
        }
        // 数组类型,需要特殊处理
        if (cl.isArray()) {
            // getComponentType用于获取数组内部数据类型
            String r = cl.getComponentType() + "[]{";
            // 通过反射获得数组长度,然后遍历每个元素
            for (int i = 0; i < Array.getLength(obj); i++) {
                // 数组内元素用逗号分隔
                if (i > 0) {
                    r += ",";
                }
                // 通过反射获得数组内第i个元素
                Object val = Array.get(obj, i);
                // isPrimitive用来判断数组内元素是否是基本数据类型
                if (cl.getComponentType().isPrimitive()) {
                    // 是基本数据类型,则直接加上去
                    r += val;
                } else {
                    // 否则的话,递归调用toString
                    r += toString(val);
                }
            }
            return r + "}";
        }
        String r = cl.getName();
        // 开始遍历这个对象的所有域
        do {
            r += "[";
            // 获得这个类的全部域
            Field[] fields = cl.getDeclaredFields();
            // 将所有域设为可访问
            AccessibleObject.setAccessible(fields, true);
            // 遍历所有域,获得域的名字和值
            for (Field f : fields) {
                // 不是静态域
                if (!Modifier.isStatic(f.getModifiers())) {
                    if (!r.endsWith("[")) {
                        r += ",";
                    }
                    // 获得域名
                    r += f.getName() + "=";
                    try {
                        // 获得域的类型
                        Class t = f.getType();
                        // 获得域的值
                        Object val = f.get(obj);
                        // 如果是基本类型,则直接加
                        if (t.isPrimitive()) {
                            r += val;
                        } else {
                            // 否则递归调用
                            r += toString(val);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            r += "]";
            // 处理超类
            cl = cl.getSuperclass();
        } while (cl != null);
        return r;
    }
}