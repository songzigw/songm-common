package cn.songm.common.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtils {

    private static Gson gson = new Gson();

    public static <T> String toJson(Object obj, Type type) {
        return gson.toJson(obj, type);
    }

    public static <T> String toJson(Object obj, Class<T> clazz) {
        return gson.toJson(obj, clazz);
    }

    public static <T> byte[] toJsonBytes(Object obj, Class<T> clazz) {
        return toJson(obj, clazz).getBytes();
    }

    public static <T> T fromJson(String str, Class<T> clazz) {
        return gson.fromJson(str, clazz);
    }

    public static <T> T fromJson(byte[] json, Class<T> clazz) {
        return fromJson(new String(json), clazz);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
    
    public static <T> T fromJson(byte[] json, Type typeOfT) {
        return fromJson(new String(json), typeOfT);
    }
}
