package com.example.agentweb_imitate.base;

import java.util.Map;

/**
 * Author:zx on 2019/9/609:38
 */
public interface JsInterfaceHolder {
    JsInterfaceHolder addJavaObjects(Map<String, Object> maps);

    JsInterfaceHolder addJavaObjects(String k, Object o);

    boolean checkObject(Object o);

}
