package com.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart4j.framework.annotation.Component;
import com.smart4j.framework.annotation.Controller;
import com.smart4j.framework.annotation.Service;
import com.smart4j.framework.utils.ClassUtil;

/**
 * 类操作助手类
 * 
 * 功能：获取指定注解的类列表
 * 
 * @author Administrator
 *
 */
public final class ClassHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);

    /**
     * 定义类集合， 存放所加载的类
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        LOGGER.debug("=================================================================");
        LOGGER.debug("开始扫描并加载以下package的所有类（包括jar包），并加载这些类建立Class对象:<" + basePackage + "> ...................");
        CLASS_SET = ClassUtil.getClassSet(basePackage);
        LOGGER.debug("扫描并加载以下package的所有类（包括jar包），并加载这些类建立Class对象:<" + basePackage + "> 完成！");
        LOGGER.debug("=================================================================\n");
    }

    /**
     * 获取应用包名下所有的类
     * 
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下所有@Service的类
     * 
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有@Controller的类
     * 
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有@Component的类
     * 
     * @return Set<Class<?>>
     */
    public static Set<Class<?>> getComponetClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Component.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有Bean类(包括@Service，@Controller )
     * 
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        classSet.addAll(getComponetClassSet());
        return classSet;
    }

    /**
     * 获取应用包名 下某父类（或接口）的所有子类（或实现类）
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下 的目标类 ------------- 带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

}
