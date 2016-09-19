package com.oneapm.agent.android.delayload;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

import java.lang.reflect.Array;

/**
 *
 * @author  :haoqqmeail@qq.com
 * reference from :https://github.com/jasonross/Nuwa
 */
public class LoadUtil {

    /**
     * 加载的时候一次性加载
     * @param filePath 目标jar 或者 dex所在路径
     * @param optPath 优化之后存放的路径
     */
    public static DexClassLoader  loadOnce(String filePath, String optPath) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        DexClassLoader dexClassLoader = new DexClassLoader(filePath, optPath, filePath, getPathClassLoader());
        Object originPathList = getPathList(getPathClassLoader());
        Object baseDexElements = getDexElements(originPathList);
        Object newDexElements = getDexElements(getPathList(dexClassLoader));
        Object allDexElements = combineArray(newDexElements, baseDexElements);
        ReflectFieldUtil.setField(originPathList, originPathList.getClass(), "dexElements", allDexElements);

        return dexClassLoader;
    }


    private static PathClassLoader getPathClassLoader() {
        PathClassLoader pathClassLoader = (PathClassLoader) LoadUtil.class.getClassLoader();
        return pathClassLoader;
    }

    private static Object getDexElements(Object paramObject)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        return ReflectFieldUtil.getField(paramObject, paramObject.getClass(), "dexElements");
    }

    private static Object getPathList(Object baseDexClassLoader)
            throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        return ReflectFieldUtil.getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    private static Object combineArray(Object firstArray, Object secondArray) {
        Class<?> localClass = firstArray.getClass().getComponentType();
        int firstArrayLength = Array.getLength(firstArray);
        int allLength = firstArrayLength + Array.getLength(secondArray);
        Object result = Array.newInstance(localClass, allLength);
        for (int k = 0; k < allLength; ++k) {
            if (k < firstArrayLength) {
                Array.set(result, k, Array.get(firstArray, k));
            } else {
                Array.set(result, k, Array.get(secondArray, k - firstArrayLength));
            }
        }
        return result;
    }



}
