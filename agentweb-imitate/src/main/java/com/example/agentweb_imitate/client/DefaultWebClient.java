package com.example.agentweb_imitate.client;

import com.example.agentweb_imitate.middleware.MiddlewareWebClientBase;

/**
 * Author:zx on 2019/9/521:01
 */
public class DefaultWebClient extends MiddlewareWebClientBase {

    /**
     * 直接打开其他网页
     */
    public static final int DERECT_OPEN_OTHER_PAGE = 1001;
    /**
     * 弹窗咨询用户是否前往其他页面
     */
    public static final int ASK_USER_OPEN_OTHER_PAGE = DERECT_OPEN_OTHER_PAGE >> 2;

    /**
     * 不允许打开其他页面
     */
    public static final int DISALLOW_OPEN_OTHER_APP = DERECT_OPEN_OTHER_PAGE >> 4;

    public static enum OpenOtherPageWays {
        /**
         * 直接打开跳转页
         */
        DERECT(DefaultWebClient.DERECT_OPEN_OTHER_PAGE),
        /**
         * 咨询用户是否打开
         */
        ASK(DefaultWebClient.ASK_USER_OPEN_OTHER_PAGE),
        /**
         * 禁止打开其他页面
         */
        DISALLOW(DefaultWebClient.DISALLOW_OPEN_OTHER_APP);
        int code;

        OpenOtherPageWays(int code) {
            this.code = code;
        }

    }

}
