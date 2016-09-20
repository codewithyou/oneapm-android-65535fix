package com.oneapm.test.dexapplication;

//import com.oneapm.agent.android.OneApmAgent;
import com.oneapm.agent.android.delayload.AgentUtil;
import android.app.Application;
import android.content.Context;
import org.w3c.dom.ProcessingInstruction;

/**
 *
 * @author  :haoqqmeail@qq.com
 *
 */
public class DexApplication extends Application {

	private final static String TOKEN = "-- YOU  TOKEN GENERATE FROM ONEAPM SITE -- ";
	private final static String HOST = "mobile.oneapm.com:80";

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		AgentUtil.init(this,"oneapm/oneapm-agent.jar");
	}


	@Override
	public void onCreate() {
		super.onCreate();
		//
		//start OneApm with token !
		//simple place a oneapm-android-agent.jar in libs directory ! the  oneapm-android-agent.jar must match /assets/oneapm/oneapm-agent.jar
		//for more information ,join QQ group :485379471   OR  email:haoqqemail@qq.com
		//
		//OneApmAgent.init(this.getApplicationContext()).setToken(TOKEN).start();

		//start OneApm with token and host
		//OneApmAgent.init(this.getApplicationContext()).setHost(HOST).setToken(TOKEN).start();

		//use if  oneapm-android-agent.jar is not exists in libs directory !
		AgentUtil.startOneAPM(this.getApplicationContext(),TOKEN,HOST,false);

	}













}
