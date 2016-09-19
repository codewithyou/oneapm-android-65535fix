package com.oneapm.agent.android.delayload;

import android.content.Context;
import android.util.Log;

import com.oneapm.agent.android.OneApmAgent;
import dalvik.system.DexClassLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author  :haoqqmeail@qq.com
 *
 */
public class AgentUtil {
    public final static String VERSION = "1.0.0";
    public static DexClassLoader classLoader;
    /**
     *
     * @param context Context
     * @param assetsFilePath  path ,eg :oneapm/onepam-agent.jar
     *
     */
    public static void init(Context context,String assetsFilePath) {
        try {
            File oneapmCacheDir = new File(context.getCacheDir(),"oneapm");
            if(!oneapmCacheDir.exists()){
                oneapmCacheDir.mkdirs();
            }
            String destPath = oneapmCacheDir.getAbsolutePath()+"/oneapm-agent.jar";
            FileUtil.copyFile(context.getAssets().open(assetsFilePath),destPath ,false);//复制asset下面的jar到缓存1区域
            classLoader = LoadUtil.loadOnce(destPath,oneapmCacheDir.getAbsolutePath());//把oneapm-agent.jar添加到dexPathList中！

            File f = new File(destPath);
            if(f.exists()){
                Log.i("onepam","oneapm agent jar exists!");
            } else  {
                Log.e("onepam","oneapm agent jar not  exists");
            }
            //Log.i("oneapm","VERSION:"+VERSION);
            //start agent .
            // startOneAPM(classLoader,context);
            //OneApmAgent.init(context).setToken("token").start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *  optional method ,to start oneapm agent !
     *  use if oneapm-android-agent.jar not exists in libs directory !
     * @param loader
     * @param context
     */
    public static void startOneAPM(Context context,String token,String host ,boolean useSsl) {
        try {
            if(classLoader == null){
                Log.e("onepam","oneapm agent jar not  exists,position:method startOneAPM.");
                return;
            }
            Class<?> oneapmAgent = classLoader.loadClass("com.oneapm.agent.android.OneApmAgent");
            if( oneapmAgent!=null ) {
        		// 调用带参数的静态方法
                Method init = oneapmAgent.getMethod("init", new Class[] { Context.class });
                Object initResult =   init.invoke(null, new Object[] {context.getApplicationContext()});
                //实例方法
                Method setToken = oneapmAgent.getMethod("setToken", new Class[] { String.class });
                Object setTokenResult =   setToken.invoke(initResult, new String[] {token});

                Method setHost = oneapmAgent.getMethod("setHost", new Class[] { String.class });
                Object setHostResult =   setHost.invoke(setTokenResult, new String[] {host});

                Method setUsessl = oneapmAgent.getMethod("setUseSsl", new Class[] { boolean.class });
                Object setUsesslResult =   setUsessl.invoke(setHostResult, new Boolean[] {useSsl});

                Method start = oneapmAgent.getMethod("start", null);
                start.invoke(setUsesslResult, null);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
