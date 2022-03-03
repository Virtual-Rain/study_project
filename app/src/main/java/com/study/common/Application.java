package com.study.common;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lsxiao.apollo.core.Apollo;
import com.study.commonlibrary.httpsdk.NetWorkConfig;
import com.study.commonlibrary.httpsdk.cookie.CookieJarImpl;
import com.study.commonlibrary.httpsdk.cookie.PersistentCacheCookie;
import com.study.commonlibrary.httpsdk.https.HttpsUtils;
import com.study.commonlibrary.uitls.AppUtil;
import com.study.commonlibrary.uitls.ScreenUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Author:zx on 2019/9/2811:50
 */
public class Application extends android.app.Application {
    public static HashMap<String, String> DEV_ENVIRONMENT = new HashMap<String, String>() {{
//        put(LOGIN, "https://172.18.3.111:8005");
//        put(BUSINESS, "https://172.18.3.111:8000");
    }};
    public static HashMap<String, String> TEST_ENVIRONMENT = new HashMap<String, String>() {{
//        put(LOGIN, "https://172.18.3.113:8005");
//        put(BUSINESS, "https://172.18.3.113:8000");
    }};

    @Override
    public void onCreate() {
        super.onCreate();
        initLib();
        initThirdLib();
        initNewWork();
        ARouter.init(this);
    }

    /**
     * 自己封装的类库初始化
     */
    private void initLib() {
        AppUtil.init(getApplicationContext());
        ScreenUtils.init(getApplicationContext());
    }

    /**
     * 第三方的类库初始化
     */
    private void initThirdLib() {
        Apollo.init(AndroidSchedulers.mainThread(), this);
    }

    /**
     * 网络框架初始化
     */
    private void initNewWork() {
        NetWorkConfig netWorkConfig = new NetWorkConfig(this);
        netWorkConfig.defaultHost("https://172.18.3.111:8005")
                .timeout(500)
                //可以自定义自己的cookie处理
                .cookieJarImpl(new CookieJarImpl(new PersistentCacheCookie(this)))
                //设置可访问所有的https网站
                .sslParams(HttpsUtils.getSslSocketFactory(null, null, null));
        if (BuildConfig.DEBUG) {
            netWorkConfig.debugMode();
            netWorkConfig.getHosts().putAll(DEV_ENVIRONMENT);
        } else {
            netWorkConfig.getHosts().putAll(TEST_ENVIRONMENT);
        }
//        MkTicketMethodManager.initialStart(getApplicationContext(), netWorkConfig);
        /**数据库**/
        Realm.init(AppUtil.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(5)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
