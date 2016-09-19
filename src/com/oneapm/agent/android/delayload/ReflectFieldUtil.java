package com.oneapm.agent.android.delayload;

import java.lang.reflect.Field;

/**
 *
 * @author  :haoqqmeail@qq.com
 * reference from :https://github.com/jasonross/Nuwa
 */
public class ReflectFieldUtil {

    public static Object getField(Object obj, Class<?> cl, String field)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    public static void setField(Object obj, Class<?> cl, String field, Object value)
            throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        localField.set(obj, value);
    }


}
