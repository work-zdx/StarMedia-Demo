<center><font size=6><b>网际星辰广告SDK接入文档</b></font></center>



### 说明

本文档旨在帮助 Android 应用开发者在项目中快速植入网星聚合广告平台提供的广告。作为应用开发者，您只需要进行简单配置，就可以在您的应用中显示定制的广告。关于 SDK 的具体使用方法，请仔细阅读下面的文档。



## **背景**

### 术语介绍

- AppId：媒体 ID，是您在 [星辰+](https://channel.xingchenjia.com/#/login) 创建媒体时获得的 ID，这个 ID 是我们在广告服务中识别您应用的唯一 ID。
- slotId：广告位 ID，是您在 [星辰+](https://channel.xingchenjia.com/#/login) 为您的媒体所创建某种类型（Banner、开屏、插屏、模板）广告时获得的 ID。



### 版本概览

网星聚合广告SDK目前已经聚合了百度（百青藤）、腾讯（优量汇）、今日头条（穿山甲）以及Sigmob。网星聚合广告SDK各个版本对应第三方广告平台版本如下：

| 网星聚合SDK版本 | 百度（百青藤）SDK版本 | 腾讯（优量汇）SDK版本 | 今日头条（穿山甲）SDK版本 | Sigmob SDK版本 |
| :-------------: | :-------------------: | :-------------------: | :-----------------------: | :------------: |
|      1.2.2     |         5.85          |      4.211.1081       |          2.8.0.3          |     2.17.1     |



## **版本变更记录**
**1.2.1 → 1.2.2**

- 支持 内容联盟 和 填充率优化(热词卡广告)

**1.2.0 → 1.2.1**

- 支持 临渊搜索任务

**1.1.9 → 1.2.0**

- 支持 网星 和 腾讯（优量汇）广告物料接口(自渲染2.0)

**1.1.8 → 1.1.9**

- 升级百度（百青藤）、腾讯（优量汇）、今日头条（穿山甲）以及Sigmob广告SDK版本



**1.1.2 → 1.1.8**

- 加入一些统计信息
- 升级广点通和sigmob
- 增加广点通和头条广告的内置缓存



**1.0.10 → 1.1.2**

- 优化接口调用风格, 由静态方法改为创建对应然后调用load方法的形式
- 优化内部架构, 支持自主接入第三方广告平台
- 优化多进程下的数据存储和内部数据格式
- 激励视频增加服务端通知逻辑
- 信息流视频适配ViewPager组件
- 优化多进程下初始化逻辑
- 优化广告位大小适配



### 支持广告类型

网星聚合广告SDK目前支持以下几种广告类型。各个广告类型对应第三方广告平台类型如下，您可以根据开发需要选择合适的广告。

| 广告类型 |     百度（百青藤）广告类型     |    腾讯（优量汇）广告类型    |      今日头条（穿山甲）广告类型       | Sigmob 广告类型 |
| :------: | :----------------------------: | :--------------------------: | :-----------------------------------: | :-------------: |
|   160    | 信息流-智能优选-大图(上图下文) |      原生-模板-上图下文      |                  无                   |       无        |
|   170    | 信息流-智能优选-大图(上文下图) |      原生-模板-上文下图      |     信息流-模板渲染-上文下图模板      |       无        |
|   180    | 信息流-智能优选-单图(左图右文) |      原生-模板-左图右文      |     信息流-模板渲染-左图右文模板      |       无        |
|   190    | 信息流-智能优选-单图(右图左文) |      原生-模板-左文右图      |     信息流-模板渲染-左文右图模板      |       无        |
|   200    |   信息流-智能优选-大图+logo    |      原生-模板-双图双文      | 信息流-模板渲染-上文下图-附加创意模板 |       无        |
|   210    |               无               |     原生-模板-纯图片(横)     |                  无                   |       无        |
|   220    |    信息流-智能优选-三图图文    |       原生-模板-三小图       |       信息流-模板渲染-三图模板        |       无        |
|   230    |               无               | 原生-模板-文字浮层(上文下图) |    信息流-模板渲染-上文下浮层模板     |       无        |
|   240    |               无               | 原生-模板-文字浮层(上图下文) |                  无                   |       无        |
|   250    |  信息流-选取模板-大图上部文字  |   原生-模板-文字浮层(单图)   |     信息流-模板渲染-文字浮层模板      |       无        |
|   260    |               无               |     原生-模板-纯图片(竖)     |                  无                   |       无        |
|   270    |              横幅              |            Banner            |              Banner广告               |       无        |
|   280    |               无               |              无              |                  无                   |       无        |
|   290    |              开屏              |             开屏             |                 开屏                  |      开屏       |
|   300    |          插屏（全屏）          |         插屏（图文）         |                 插屏                  |       无        |
|   301    |               无               |         插屏（横图）         |                  无                   |       无        |
|   302    |               无               |         插屏（竖图）         |                  无                   |       无        |
|   310    |            激励视频            |           激励视频           |             激励视频广告              |    激励视频     |
|   320    |   信息流-返回元素-组合5-全屏   |              无              |            Draw信息流广告             |       无        |



## **全局配置**

### 网星聚合广告SDK支持最低系统版本：Android SDK Level 17+

网星聚合广告SDK目前已经聚合了百度（百青藤）、腾讯（优量汇）、今日头条（穿山甲）以及Sigmob。其中百度、腾讯、今日头条的广告SDK支持的最低系统版本为14+，Sigmob的广告SDK支持的最低系统版本18+。在不接入Sigmob广告平台的情况下，网星聚合广告SDK要求最低系统版本为17+。如果要接入Sigmob广告平台，网星聚合SDK要求最低系统版本为18+。

```groovy
minSdkVersion 17  //网星聚合广告SDK如果要接入Sigmob广告平台，Android最低系统版本必须为18+
```



### 工程配置

网星聚合广告SDK对外提供 Maven 集成的方式。获取网星聚合广告SDK的代码，需要在项目工程根目录下的build.gradle文件中添加如下代码：

```groovy
allprojects {
repositories {
    maven {
        url "http://inappregistry.iscrv.com/repository/maven-public/"
    }
    ...
}
}
```



### 网星聚合广告SDK接入

网星聚合广告SDK聚合了百度（百青藤）、腾讯（优量汇）、今日头条（穿山甲）以及Sigmob四家广告平台。接入网星聚合广告SDK必须接入网星主体SDK。其他第三方广告SDK根据需求进行接入。请在项目主工程下的build.gradle文件中添加如下代码：

```groovy
// 网星广告SDK（必须接入）
implementation 'com.starmedia:star_aar:1.2.2'

// 百度（百青藤）
implementation 'com.starmedia:star_bd:1.2.2'

// 今日头条（穿山甲）
implementation 'com.starmedia:star_tt:1.2.2'

// 腾讯（优量汇）
implementation 'com.starmedia:star_gdt:1.2.2'

// Sigmob
implementation 'com.starmedia:star_sigmob:1.2.2'
```


### 权限说明

建议您在AndroidManifest.xml添加以下权限声明，并建议在您的隐私协议中向开发者声明网星聚合广告SDK会获取下述权限并应用于广告投放。若您的targetSDKVersion >= 23您还需要在运行时进行动态权限申请。

**必要权限集合**

```xml
<!-- 网星广告SDK接入需要以下必要权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- 百青藤广告SDK接入需要以下必要权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- 广点通广告SDK接入需要以下必要权限 -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- 今日头条广告SDK接入需要以下必要权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Sigmob广告SDK接入需要以下必要权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```



## **集成实例**

### 初始化SDK,强烈建议开发者在应用的Application 中进行, 初始化一次即可

```kotlin
StarMedia.initialMedia(application, "appid", callback: ((Boolean) -> Unit)? = null)
```

- 注：首次启动因为没有获得权限, 很有可能是失败,  开发者可以不需要关心, SDK会在请求广告时, 判断当前是否需要进行初始操作

### 检查权限申请

- 获得所有SDK需要的权限
```
StarMedia.loadAllPermissions(@NonNull callback: (Set<String>) -> Unit)
```

- 检擦权限是否全部申请通过
```
StarMedia.checkMediaPermission(context: Context, callback: ((Boolean) -> Unit))
```
<font color='red'>网星聚合广告SDK初始化过程中会检查必要权限申请情况。如果有必要权限未申请，会导致网星广告SDK初始化失败。所以强烈建议开发者在应用的第一个Activity (Splash) 的 resume 中调用此方法来检测当前是否申请到了所有必要的权限</font>

### **公共接口**

网星聚合广告SDK中对外暴露的广告类都包含以下接口，开发者可根据需求进行接口实现。

**广告请求监听**

```kotlin
/**
    * 请求成功的监听
    */
var requestSuccessListener: (() -> Unit)? = null

/**
    * 请求失败的监听
    */
var requestErrorListener: ((message: String) -> Unit)? = null
```

**广告行为监听**

```kotlin
/**
    * 广告展示的监听
    */
var viewShowListener: (() -> Unit)? = null

/**
    * 广告点击的监听
    */
var viewClickListener: (() -> Unit)? = null

/**
    * 点击关闭的监听
    */
var viewCloseListener : (() -> Unit)? = null
```



### **模板广告**

- **广告样式类型：160, 170, 180, 190, 200, 210, 220, 230, 240, 250, 260**


- **广告请求**

```kotlin
val starNativeView = StarNativeView(this, slotId, null).apply {
    requestSuccessListener = {
        Logger.e("NativeActivity", "请求模板广告成功！")
        ...
    }

    requestErrorListener = {
        Logger.e("NativeActivity", "请求模板广告失败：$it")
        ...
    }

    viewShowListener = {
        Logger.e("NativeActivity", "模板广告展示！")
        ...
    }

    viewClickListener = {
        Logger.e("NativeActivity", "模板广告点击！")
        ...
    }

    viewCloseListener = {
        Logger.e("NativeActivity", "模板广告关闭！")
        ...
    }
}

starNativeView.load()
```

注：slotId为请求广告的广告位Id。创建StarNativeView对象时可以使用Context对象。AdParam为可选参数，在AdParam中你可以配置一些广告位的属性，相同的属性不一定适用不同的广告平台。



### **Banner广告**

- **广告样式类型：270**
网星聚合广告SDK 为接入方提供了 Banner 广告，开发者不用自行对广告样式进行编辑和渲染，可直接调用相关接口获取广告 view。



- **广告请求**

```kotlin
val starBannerView = StarBannerView(this, slotId, null).apply {
    requestSuccessListener = {
        Logger.e("BannerActivity", "请求Banner广告成功！")
        ...
    }

    requestErrorListener = {
        Logger.e("BannerActivity", "请求Banner广告失败：$it")
        ...
    }

    viewShowListener = {
        Logger.e("BannerActivity", "Banner广告展示！")
        ...
    }

    viewClickListener = {
        Logger.e("BannerActivity", "Banner广告点击！")
        ...
    }

    viewCloseListener = {
        Logger.e("BannerActivity", "Banner广告关闭！")
        ...
    }
}

starBannerView.load()
```

注：slotId为请求广告的广告位Id。<font color='red'>创建StarBannerView对象时必须使用Activity对象。</font>AdParam为可选参数，在AdParam中你可以配置一些广告位的属性，相同的属性不一定适用不同的广告平台。



### **开屏广告**

- **广告样式类型：280，290**
网星聚合广告SDK 为接入方提供了开屏广告，建议在用户进入 App 时展示的全屏广告。开屏广告为一个 View，宽高默认为 match_parent。注意开屏广告 view：width >= 70% 屏幕宽；height >= 70% 屏幕高 ，否则会影响计费。



- **广告请求**

```kotlin
val starSplashView = StarSplashView(this, slotId, viewGroup, null).apply {
    requestSuccessListener = {
        Logger.e("SplashActivity", "请求开屏广告成功！")
        ...
    }

    requestErrorListener = {
        Logger.e("SplashActivity", "请求开屏广告失败：$it")
        ...
    }

    viewShowListener = {
        Logger.e("SplashActivity", "开屏广告展示！")
        ...
    }

    viewClickListener = {
        Logger.e("SplashActivity", "开屏广告点击！")
        ...
    }

    viewCloseListener = {
        Logger.e("SplashActivity", "开屏广告关闭！")
        ...
    }
}

starSplashView.load()
```

注：slotId为请求广告的广告位Id，viewGroup为展示开屏广告的View。<font color='red'>创建StarSplashView对象时必须使用Activity对象。</font>AdParam为可选参数，在AdParam中你可以配置一些广告位的属性，相同的属性不一定适用不同的广告平台。



### **插屏广告**

- **广告样式类型：300, 301, 302**
网星聚合广告SDK 为接入方提供了个性化模板插屏广告。开发者不用自行对广告样式进行编辑和渲染，可直接调用相关接口获取广告 view。



- **广告请求**

```kotlin
val starInterstitial = StarInterstitial(this, slotId, null).apply {
    requestSuccessListener = {
        Logger.e("InterstitialActivity", "请求插屏广告成功！")
        starInterstitial.show()
        ...
    }

    requestErrorListener = {
        Logger.e("InterstitialActivity", "请求插屏广告失败：$it")
        ...
    }

    viewShowListener = {
        Logger.e("InterstitialActivity", "插屏广告展示！")
        ...
    }

    viewClickListener = {
        Logger.e("InterstitialActivity", "插屏广告点击！")
        ...
    }

    viewCloseListener = {
        Logger.e("InterstitialActivity", "插屏广告关闭！")
        ...
    }
}

starInterstitial.load()
```

注：slotId为请求广告的广告位Id。<font color='red'>创建StarInterstitial对象时必须使用Activity对象。</font>AdParam为可选参数，在AdParam中你可以配置一些广告位的属性，相同的属性不一定适用不同的广告平台。



### **激励视频**

- **广告样式类型：310**
网星聚合广告SDK 为接入方提供激励视频广告，该广告的效果为观看完毕视频广告，发放奖励给用户。支持的广告尺寸： 全屏横屏播放和竖屏



- **奖励发放接口**

```kotlin
/**
* 奖励发放的监听
*/
var rewardedResultListener: ((result: Boolean) -> Unit)? = null
```



- **广告请求**

```kotlin
val starRewardVideo = StarRewardVideo(this, slotId, null).apply {
    requestSuccessListener = {
        Logger.e("RewardedVideoActivity", "请求激励视频广告成功！")
    starRewardVideo.show()
    ...
    }

    requestErrorListener = {
        Logger.e("RewardedVideoActivity", "请求激励视频广告失败：$it")
    ...
    }

    viewShowListener = {
        Logger.e("RewardedVideoActivity", "激励视频广告展示！")
    ...
    }

    viewClickListener = {
        Logger.e("RewardedVideoActivity", "激励视频广告点击！")
    ...
    }

    viewCloseListener = {
        Logger.e("RewardedVideoActivity", "激励视频广告关闭！")
    ...
    }

    rewardedResultListener = {
        Logger.e("RewardedVideoActivity", "激励视频是否下发奖励: $it")
    ...
    }
}

starRewardVideo.load()
```

注：slotId为请求广告的广告位Id。<font color='red'>创建StarRewardVideo对象时必须使用Activity对象。</font>AdParam为可选参数，在AdParam中你可以配置一些广告位的属性，相同的属性不一定适用不同的广告平台。



### 视频流广告

- **广告样式类型：320**
网星聚合广告SDK 为接入方提供竖版模板视频信息流广告，该广告适合在竖版全屏视频流中使用，接入方不可干预广告的播放。支持的广告尺寸：全屏竖屏



- **广告请求**

```kotlin
val starListVideoView = StarListVideoView(this, slotId, null).apply {
    requestErrorListener = {
        Logger.e("ListVideoActivity", "请求列表视频广告失败：$it")
        ...
    }

    requestSuccessListener = {
        Logger.e("ListVideoActivity", "请求列表视频广告成功！")
        ...
    }

    viewShowListener = {
        Logger.e("ListVideoActivity", "列表视频广告展示！")
        ...
    }

    viewClickListener = {
        Logger.e("ListVideoActivity", "列表视频广告点击！")
        ...
    }
}

starListVideoView.load()
```

注：slotId为请求广告的广告位Id。<font color='red'>创建StarListVideoView对象时必须使用Activity对象。</font>AdParam为可选参数，在AdParam中你可以配置一些广告位的属性，相同的属性不一定适用不同的广告平台。



### JS广告

- **广告样式类型**
网星聚合广告SDK 为接入方提供了JS广告，该广告适合在客户端Webview中使用，接入方需要在打开Webview页面之前调用相应接口，获取到src和smua并拼接成script在Webview的onPageFinished监听中调用执行js。



- **回调接口：**

```kotlin
/**
* js广告请求成功的回调
*/
fun onSuccess(result: JSAD) {

}

/**
* js广告请求失败的回调
*/
fun onError(message: String) {

}
```



- **广告请求**

```kotlin
StarMedia.loadJSAD(slotId, object : ReqRet<JSAD> {
    override fun onSuccess(result: JSAD) {
    Logger.e("JSActivity", "请求JS广告成功！")

        script = "" +
                "(function () {\n" +
                "    var adElement = document.createElement(\"script\");\n" +
                "    adElement.setAttribute('src', '${result.src}');\n" +
                "    adElement.setAttribute('smua', '${result.smua}');\n" +
                "    adElement.setAttribute('type', 'text/javascript');\n" +
                "    document.body.appendChild(adElement);\n" +
                "})()"

        web_view.loadUrl(url)
    }

    override fun onError(message: String) {
    Logger.e("JSActivity", "请求JS广告失败：$message")
        ...
    }
})
```

```kotlin
webview.webViewClient = object : WebViewClient() {
    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.evaluateJavascript(script, null)
        } else {
            view.loadUrl("javascript:$script")
        }
    }
}
```




### **获取广告物料(自渲染2.0)**

- **广告样式类型：160, 170, 180, 190, 200, 210, 220, 230, 240, 250, 260**

- **广告请求**

```kotlin
StarMaterial(this, SLOT_ID, null).apply {
            requestSuccessListener = {
                adMaterial = this
                showMaterialAd()
            }

            requestErrorListener = { msg ->
                Toast.makeText(this@MaterialActivity, "Error $msg", Toast.LENGTH_LONG).show()
            }
        }.load()
```

注：slotId为请求广告的广告位Id。创建StarMaterial对象时可以使用Context对象。AdParam为可选参数，在AdParam中你可以配置一些广告位的属性，相同的属性不一定适用不同的广告平台。

- 物料说明

```kotlin
interface AdMaterial {

    /**
    * 获得广告样式
    */
    fun getType(): StarAdType

    /**
    * 广告动作类型, 下载, 落地页详情, 深度链接打开
    */
    fun getActionType(): StarAdActionType

    fun getTitle(): String

    fun getDesc(): String

    fun getIcon(): String?

    fun getImages(): List<String>

    fun getPlatform(): String

    fun getAdContainer(): ViewGroup

    /**
    * 原始平台物料数据, 可以根据 platform 做判断, 用于自定义处理
    */
    fun getOriginMaterial(): Any

    interface AdInteractionListener {
        fun onAdClicked()
        fun onAdCreativeClick()
        fun onAdShow()
    }

    fun registerViewForInteraction(
        clickViews: List<View>,
        creativeViews: List<View>,
        listener: AdInteractionListener
    )

}

enum class StarAdType {
    TYPE_160,
    TYPE_170,
    TYPE_180,
    TYPE_190,
    TYPE_200,
    TYPE_210,
    TYPE_220,
    TYPE_230,
    TYPE_240,
    TYPE_250,
    TYPE_260
}
```

- 接入样例

```kotlin
private fun showMaterialAd() {
    ad_container.removeAllViews()
    ad_container.addView(
        adMaterial!!.getAdContainer(),
        ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    )

    when (adMaterial?.getType()) {
        StarAdType.TYPE_1IMAGE_2TEXT -> {
            println("type: TYPE_1IMAGE_2TEXT")
            val view =
                layoutInflater.inflate(R.layout.ad_material_1, adMaterial!!.getAdContainer())

            bindView(view)
        }
        StarAdType.TYPE_2IMAGE_2TEXT -> {
            println("type: TYPE_2IMAGE_2TEXT")

            val view =
                layoutInflater.inflate(R.layout.ad_material_2, adMaterial!!.getAdContainer())

            bindView2(view)
        }
        StarAdType.TYPE_3IMAGE -> {
            println("type: TYPE_3IMAGE")

            val view =
                layoutInflater.inflate(R.layout.ad_material_3, adMaterial!!.getAdContainer())

            bindView3(view)
        }
        StarAdType.TYPE_BIG_IMAGE -> {
            println("type: TYPE_BIG_IMAGE")

            val view =
                layoutInflater.inflate(R.layout.ad_material_4, adMaterial!!.getAdContainer())

            bindViewBig(view)
        }
    }
}

private fun bindView(view: View) {
    if (adMaterial?.getIcon() != null && adMaterial?.getIcon()?.isNotEmpty() == true) {
        Glide.with(this).load(adMaterial?.getIcon()).into(view.iv_image)
    }

    view.tv_title.text = adMaterial?.getTitle() ?: "这里是标题"

    adMaterial?.registerViewForInteraction(
        listOf(view.iv_image),
        listOf(view.tv_title),
        object : AdMaterial.AdInteractionListener {
            override fun onAdClicked() {
                Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
            }

            override fun onAdCreativeClick() {
                Toast.makeText(this@MaterialActivity, "创意点击", Toast.LENGTH_LONG).show()
            }

            override fun onAdShow() {
                Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
            }

        })

    view.tv_platform.text = if (adMaterial?.getPlatform() == "TT") "穿山甲" else "百度"
}

private fun bindView2(view: View) {

    if (adMaterial?.getIcon() != null && adMaterial?.getIcon()?.isNotEmpty() == true) {
        Glide.with(this).load(adMaterial?.getIcon()).into(view.iv_2_icon)
    }

    view.tv_2_title.text = adMaterial?.getTitle() ?: "这里是标题"

    val urls = adMaterial?.getImages()

    if (urls != null && urls.isNotEmpty()) {
        val url = urls[0]

        if (url.isNotEmpty()) {
            Glide.with(this).load(url).into(view.iv_2_image)
        }
        adMaterial?.registerViewForInteraction(
            listOf(view.iv_2_image),
            listOf(view.tv_2_title),
            object : AdMaterial.AdInteractionListener {
                override fun onAdClicked() {
                    Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
                }

                override fun onAdCreativeClick() {
                    Toast.makeText(this@MaterialActivity, "创意", Toast.LENGTH_LONG).show()
                }

                override fun onAdShow() {
                    Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
                }

            })
    }

    view.tv_2_desc.text = adMaterial?.getDesc() ?: "这里是描述"
}

private fun bindView3(view: View) {
    view.tv_3_title.text = adMaterial?.getTitle() ?: "这里是标题"

    val urls = adMaterial?.getImages()

    if (urls != null && urls.size >= 3) {
        val first = urls[0]

        if (first.isNotEmpty()) {
            Glide.with(this).load(first).into(view.iv_3_first)
        }

        val second = urls[1]

        if (second.isNotEmpty()) {
            Glide.with(this).load(second).into(view.iv_3_second)
        }

        val third = urls[1]

        if (third.isNotEmpty()) {
            Glide.with(this).load(third).into(view.iv_3_third)
        }

        adMaterial?.registerViewForInteraction(
            listOf(view.iv_3_first, view.iv_3_second),
            listOf(view.iv_3_third),
            object : AdMaterial.AdInteractionListener {
                override fun onAdClicked() {
                    Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
                }

                override fun onAdCreativeClick() {
                    Toast.makeText(this@MaterialActivity, "创意点击", Toast.LENGTH_LONG).show()
                }

                override fun onAdShow() {
                    Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
                }

            })

    } else {
        view.cl_material_3image.visibility = View.GONE
        Toast.makeText(this@MaterialActivity, "3图广告返回的图片数量不足！", Toast.LENGTH_LONG).show()
    }

    view.tv_3_platform.text = if (adMaterial?.getPlatform() == "TT") "穿山甲" else "百度"
}

private fun bindViewBig(view: View) {
    view.tv_big_title.text = adMaterial?.getTitle() ?: "这里是标题"

    val urls = adMaterial?.getImages()

    if (urls != null && urls.isNotEmpty()) {
        val url = urls[0]

        if (url.isNotEmpty()) {
            Glide.with(this).load(url).into(view.iv_big_image)
        }
    }

    view.tv_big_desc.text = adMaterial?.getDesc() ?: "这里是描述"

    adMaterial?.registerViewForInteraction(
        listOf(view.cl_material_big_image),
        listOf(view.iv_big_image),
        object : AdMaterial.AdInteractionListener {
            override fun onAdClicked() {
                Toast.makeText(this@MaterialActivity, "点击", Toast.LENGTH_LONG).show()
            }

            override fun onAdCreativeClick() {
                Toast.makeText(this@MaterialActivity, "创意点击", Toast.LENGTH_LONG).show()
            }

            override fun onAdShow() {
                Toast.makeText(this@MaterialActivity, "广告展示", Toast.LENGTH_LONG).show()
            }

        })
}
```



### 内容联盟

- **百度内容联盟数据**
SDK提供原生内容联盟信息流能力, 使用View形式提供, 并提供默认的条目样式, 如需要更改样式, 覆盖默认的样式布局文件即可, **覆盖样式文件请保持View 的ID一致**, 否则无法正确填充图片和文案。

- **默认的布局文件名如下**

```kotlin
star_content_ad_3pic.xml            // 三图 自渲染广告
star_content_ad_leftright.xml        // 左图右文 自渲染广告
star_content_ad_updown.xml            // 上文下图 自渲染广告
star_content_image.xml                // 信息流 图集
star_content_left_picture.xml        // 信息流 左图右文
star_content_text.xml                // 信息流 纯文本
star_content_third_picture.xml        // 信息流 三图
star_content_video.xml                // 信息流 视频
```

如果默认的自渲染广告样式布局不满足自定义需求, 可以使用如下接口, 定制返回的自渲染广告View

```kotlin
fun setMaterialAdCreater(creater: MaterialAdCreater);

interface MaterialAdCreater {
    fun createAdView(material: AdMaterial): ViewGroup
}
```

- **加载内容**

```kotlin
val unionContentView = StarContentView()
//key 的取值有: 0:新闻、1:图片、2:视频
//value list 的item 的取值见类目编码
val params = HashMap<Long, List<Int>>()
params[0] = arrayListOf(1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009)
params[1] = arrayListOf(1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019)
params[2] = arrayListOf(1033, 1034, 1036, 1037, 1039, 1040, 1041, 1042)
unionContentView.request(params){
    if (!it) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }
}
```

**类目编码**

| 类目ID |   类目   | 资讯 | 图集 | 视频 |
| ------------ | ------------ | ------------ | ------------ | ------------ |
|  1001  |   娱乐   |      |      |      |
|  1002  |   体育   |      |      |      |
|  1003  |   图片   |  无  |  无  |      |
|  1004  |    IT    |  无  |  无  |      |
|  1005  |   手机   |  无  |  无  |      |
|  1006  |   财经   |      |      |      |
|  1007  |   汽车   |      |      |      |
|  1008  |   房产   |  无  |      |      |
|  1009  |   时尚   |      |      |      |
|  1011  |   文化   |      |      |      |
|  1012  |   军事   |      |      |      |
|  1013  |   科技   |      |      |      |
|  1014  |   健康   |      |      |      |
|  1015  |   母婴   |      |      |      |
|  1016  |   社会   |      |      |      |
|  1017  |   美食   |      |      |      |
|  1018  |   家居   |  无  |      |      |
|  1019  |   游戏   |      |      |      |
|  1020  |   历史   |      |      |      |
|  1021  |   时政   |  无  |      |      |
|  1024  |   美女   |  无  |      |      |
|  1025  |   搞笑   |      |      |      |
|  1026  |   猎奇   |  无  |  无  |      |
|  1027  |   旅游   |      |      |      |
|  1028  |   动物   |  无  |  无  |      |
|  1030  |   摄影   |  无  |      |      |
|  1031  |   动漫   |      |      |      |
|  1032  |   女人   |  无  |      |      |
|  1033  |   生活   |      |      |      |
|  1034  |   表演   |  无  |  无  |      |
|  1036  |   音乐   |  无  |  无  |      |
|  1037  | 影视周边 |  无  |  无  |      |
|  1039  | 相声小品 |  无  |  无  |      |
|  1040  |   舞蹈   |  无  |  无  |      |
|  1041  | 安全出行 |  无  |  无  |      |
|  1042  |  大自然  |  无  |  无  |      |
|  9999  |   其他   |      |      |      |

### 注意事项

1. 广告需要在不再使用时调用destroy方法释放资源
2. 开屏广告 view：width >= 70% 屏幕宽；height >= 70% 屏幕高 ，否则会影响计费。
3. 广告可以提前缓存, 但有效期在30分钟, 超过这个时间后使用有可能不算做有效展现
4. 视频信息流广告建议使用RecyclerView作为滑动容器, 当然这只是建议
5. 接入后请测试各平台的下载类广告时候可以正常安装, 如出现无法安装, 请检查检查是否时res/xml下文件冲突, 是否正确含有如下五个文件
gdt_file_path.xml
tt_file_paths.xml
bd_file_paths.xml
sigmob_provider_paths.xml
star_provider.xml

### 关于测试

- 开启日志 
```kotlin
StarMedia.debugMode(flag: Boolean)
```

- 设置只使用哪些平台广告, 设置之后只在进程存活期间有效

**注意, 上线前一定要停用此方法, 否则上线后无法使用多个平台的广告**
```kotlin
StarMedia.setUsePlatforms(vararg plats: String)
```
