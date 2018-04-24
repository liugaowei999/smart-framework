package com.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart4j.framework.annotation.Aspect;
import com.smart4j.framework.proxy.AspectProxy;
import com.smart4j.framework.proxy.Proxy;
import com.smart4j.framework.proxy.ProxyManager;

public final class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    /**
     * 通过静态代码块来初始化整个AOP框架
     */
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                System.out.println(targetClass.getName());
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("AOP failure", e);
        }
    }

    /**
     * 获取Aspect注解中 设置的注解类
     * 
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();

        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**********************************************************************************************
     * 获取代理类（切面类）与目标类集合之间的映射关系 一个切面类可以对应多个目标类 切面类需要扩展AspectProxy抽象类，
     * 还需要带有Aspect注解。只有满足这两个条件，才能根据Aspect注解中所定义 的注解属性去获取该注解对应的目标类集合，然后才能建立
     * 代理类与目标类集合之间的映射关系。
     **********************************************************************************************/
    public static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        System.out.println("Start to createProxyMap() .......");
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();

        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            System.out.println("proxyClass.getName()=" + proxyClass.getName());
            // 如果有 @Aspect 的注解
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    //	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
    //		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
    //		Class<? extends Annotation> annotation = aspect.value();
    //		if (annotation != null && !annotation.equals(Aspect.class)) {
    //			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
    //		}
    //		return targetClassSet;
    //	}

    /**
     * 根据切面类 与 目标类之间的映射关系， 获取目标类 与 代理对象列表之间的映射关系
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();

        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
