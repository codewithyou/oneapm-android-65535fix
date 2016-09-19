# oneapm-android-65535fix

##这个项目主要解决什么问题的？
主要是解决用户在使用oneapm的过程中由于自身的project已经方法数很多了，方法数已经到了65535的临界值，一旦加入oneapm的
功能之后，就开始报 65535的错误！为此，我们参考了[NuWa](https://github.com/jasonross/Nuwa)的做法设计了一个解决方案。

向NuWa致敬！


##核心原理
* 方法数超过限制的核心原因就是编译过程中dx工具在处理和优化dex的过程中会把方法数缓存到一个short类型的变量中存储，一旦方法数超过short
的长度就会报错！所以，这里的核心思路就是避开编译期加载和编译，换成在APK启动的过程中加载探针和启动探针。
* 另外,为了让开发者在启动探针的时候向正常方式一样，我们提供了一个oneapm-android-agent.jar的fake的jar包放在libs目录，开发者可以很方便的调用
类似于如下的代码
```javascript
OneApmAgent.init(this.getApplicationContext()).setToken(TOKEN).start();
```


##使用方法

* 拷贝示例工程（OneAPM-Test-fix65535-Project）下面的assets/oneapm的onepam目录到您的工程的assets目录
* 拷贝示例工程（OneAPM-Test-fix65535-Project）下面的libs下面的onepam-android-agent.jar文件到您的libs目录，并添加到build path中
* 自定义继承自android.app.Application;的Application类重写attachBaseContext方法！并调用AgentUtil.init(this,"oneapm/oneapm-agent.jar");
* 在Application的onCreate方法中调用启动OneAPM的代码 OneApmAgent.init(this.getApplicationContext()).setToken(TOKEN).start(); （TOKEN请到[OneAPM](http://www.oneapm.com)申请）
* 安装oneapm打包插件 ，其他过程和onepam官方网站一致！

完成之后的自定义application代码如下（参考示例代码中的DexApplication）
```java
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
		OneApmAgent.init(this.getApplicationContext()).setToken(TOKEN).start();

		//start OneApm with token and host
		//OneApmAgent.init(this.getApplicationContext()).setHost(HOST).setToken(TOKEN).start();

		//use if  oneapm-android-agent.jar is not exists in libs directory !
		//AgentUtil.startOneAPM(this.getApplicationContext(),TOKEN,HOST,false);

	}
}

```

##注意事项
* 如果使用jar包的方式启动oneapm需要在libs下面放置oneapm-android-agent.jar文件，并且这个问题要和assets/oneapm下面的的jar包对应
* 如果不在libs下面放置jar包启动oneapm，可以调用AgentUtil.startOneAPM(this.getApplicationContext(),TOKEN,HOST,false);启动
* 这个工程只是解决65535问题，oneapm 官方的android打包插件等正常流程依旧，不受这个影响。


在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流。
* 邮件haoqqemail#qq.com, 把#换成@)
* QQ群: 485379471



##关于作者

```java
  class author  {
    String nickName = "美一天";
    String email = "haoqqemail@qq.com";
  }
```

