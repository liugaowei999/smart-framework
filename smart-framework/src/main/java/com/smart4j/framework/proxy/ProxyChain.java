package com.smart4j.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 使用CGLIB 的方法代理
 * 
 * @author liugaowei
 *
 */
public class ProxyChain {
    // 目标类
    private final Class<?> targetClass;
    // 目标对象
    private final Object targetObject;
    // 目标方法
    private final Method targetMethod;
    // 方法代理（CGLIB提供的方法代理对象）
    private final MethodProxy methodProxy;
    // 方法参数
    private final Object[] methodParams;

    // 代理列表
    private List<Proxy> proxyList = new ArrayList<Proxy>();
    // 代理索引（代理对象的计数器）
    private int proxyIndex = 0;

    /**
     * 构造函数
     * 
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodProxy
     * @param methodParams
     * @param proxyList
     */
    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy,
            Object[] methodParams, List<Proxy> proxyList) {
        super();
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            /**
             * 如果尚未到达proxyList代理对象的上线， 则调用List中元素：Proxy对象的doProxy方法，
             * doProxy会回调this的doProxyChain方法
             */
            System.out.println("doProxyChain proxyIndex < proxyList.size() : proxyIndex=" + proxyIndex
                    + ", proxyList.size()=" + proxyList.size());
            methodResult = proxyList.get(proxyIndex++).doProxy(this); // proxyIndex++ ---> 会改变proxyIndex的值 
        } else {
            System.out.println(
                    "proxyIndex=" + proxyIndex + ",methodResult = methodProxy.invokeSuper(targetObject, methodParams)");
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }

}
