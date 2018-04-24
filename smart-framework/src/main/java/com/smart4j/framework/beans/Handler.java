package com.smart4j.framework.beans;

import java.lang.reflect.Method;

/**
 * 封装Action信息【表示：某一个控制器类 的 某一个方法】
 * 
 * @author liugaowei
 *
 */
public class Handler {

    /**
     * Controller 类
     */
    private Class<?> controllerClass;

    /**
     * Action 方法
     */
    private Method actionMethod;

    /**
     * 构造函数
     */
    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
