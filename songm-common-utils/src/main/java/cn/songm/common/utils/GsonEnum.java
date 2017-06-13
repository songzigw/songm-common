package cn.songm.common.utils;

public interface GsonEnum<E> {

    String serialize();

    E deserialize(String jsonEnum);
}
