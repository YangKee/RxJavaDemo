package example.yangyee.com.rxjava;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;


public class EmptyUtil {

    public static boolean isStringEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isStringNotEmpty(String str) {
        return !isStringEmpty(str);
    }

    public static <T> boolean isMapEmpty(Map<T, T> map) {
        return null == map || map.isEmpty();
    }

    public static <T> boolean isMapNotEmpty(Map<T, T> map) {
        return !isMapEmpty(map);
    }


    public static <T> boolean isCollectionEmpty(Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }


    public static <T> boolean isCollectionNotEmpty(Collection<T> collection) {
        return !isCollectionEmpty(collection);
    }




}
