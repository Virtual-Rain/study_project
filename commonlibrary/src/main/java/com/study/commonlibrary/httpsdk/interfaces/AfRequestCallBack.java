package com.study.commonlibrary.httpsdk.interfaces;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 项目基础请求回调类
 *
 * @param <T>
 */
public class AfRequestCallBack<T> implements IRequestCallBack<T> {
    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onError(String exception) {

    }

    @Override
    public void onProcess(float process, long totalSize) {

    }

    @Override
    public void onReLoginForTokenInvalid() {

    }

    /**
     * 根据类型得到泛型类
     *
     * @param type
     * @return
     */
    public Class<T> getClassT(Type type) {
        Class<T> entityClass = null;
        if (type instanceof ParameterizedType) {
            /*反射获取T的真实类型*/
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (null != actualTypeArguments && actualTypeArguments.length == 1) {
                if (actualTypeArguments[0] instanceof Class) {
                    entityClass = (Class<T>) actualTypeArguments[0];
                } else {
                    /*递归获取到真实T类*/
                    getClassT(actualTypeArguments[0]);
                }
            } else {
                entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            }
        }
        return entityClass;
    }

    /**
     * 将网络返回的字符串数据解析为数据类
     *
     * @throws Exception
     */
    public T parse(String result) throws Exception {
        // 获取当前new的对象的 泛型的父类 类型
        Class<T> entityClass = getClassT(this.getClass().getGenericSuperclass());
        /*字符串类直接返回*/
        if (entityClass == String.class) {
            return (T) result;
        }
        /*Gson解析字符串为对象*/
        T returnResult = new Gson().fromJson(result, entityClass);
        return returnResult;
    }
}
