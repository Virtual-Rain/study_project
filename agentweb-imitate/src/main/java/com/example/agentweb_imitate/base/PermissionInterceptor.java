package com.example.agentweb_imitate.base;

/**
 * Author:zx on 2019/8/3110:31
 */
public interface PermissionInterceptor {
    boolean intercept(String url, String[] permissions, String action);
}
