package com.study.common.aop;

import android.view.View;

import com.study.common.utils.XClickUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Author:zx on 2019/10/3109:22
 */
@Aspect
public class SingleClickAspect {

    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 项目SingleClick全路径
     */
    @Pointcut("execution(@com.study.common.aop.SingleClick * *(..))")
    public void methodAnnotated() {
    }

    /**
     * 定义一个切面方法，包裹切点方法
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出方法的参数
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (null == view) {
            return;
        }
        //取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(SingleClick.class)) {
            return;
        }
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        //判断是否快速点击
        if (!XClickUtil.isFastDoubleClick(view, singleClick.value())) {
            //不是快速点击，执行原方法
            joinPoint.proceed();
        }
    }
}
