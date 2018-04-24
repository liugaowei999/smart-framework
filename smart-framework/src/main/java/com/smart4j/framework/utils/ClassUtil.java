package com.smart4j.framework.utils;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 框架类加载器， 通知JVM加载对应的包名下的类对象到ClassLoader容器中。 只加载类，不做类的实例化对象
 * 
 * 类操作工具类
 * 
 * @author liugaowei
 *
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader cl = null;
        try {
            // 返回当前线程的类加载器
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtil.class.getClassLoader();
        }
        return cl;
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitilized) {
        Class<?> cls;
        try {
            /**
             * 1. Class.forName :
             * 通知JVM动态加载一个类，返回这个类的Class对象。【注意返回的不是实例化的Object对象】
             * 第二个参数可以控制是否执行类的静态代码块 2. Class对象调用.newInstance() 返回的是实例化的Object对象，
             * 调用的无参数的构造函数。
             */
            cls = Class.forName(className, isInitilized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                LOGGER.debug(url.toString());
                if (url != null) {
                    String protocol = url.getProtocol();
                    LOGGER.debug("protocol=" + protocol);
                    if (protocol.equals("file")) {
                        // 将空格的转义符%20 替换为实际的空格
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            // 示例：jarFile=D:\app\eclipse-jee-oxygen\eclipse\plugins\org.junit_4.12.0.v201504281640\junit.jar  
                            LOGGER.debug("jarFile=" + jarFile.getName());
                            if (jarFile != null) {
                                // 获取jar文件内所有的子文件信息列表
                                Enumeration<JarEntry> jarEntries = jarFile.entries();

                                // 遍历jar内所有元素， 加载.class结尾的类文件
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    LOGGER.debug("jarEntryName=" + jarEntryName);
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
                                                .replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get class SET failure", e);
            throw new RuntimeException(e);
        }

        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {

            @Override
            public boolean accept(File filepath) {
                // 只取目录下的子目录 或者 是以 .class 结尾的class文件
                return (filepath.isFile() && filepath.getName().endsWith(".class")) || filepath.isDirectory();
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            LOGGER.debug("fileName=" + fileName);

            // 如果是文件，则直接加在类文件； 如果是目录则继续递归调用，加载子目录下的类文件
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                LOGGER.debug("className=" + className);
                doAddClass(classSet, className);
            } else {
                // 获取子目录的完整路径
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                // 获取子包的完整包路径
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }

                // 递归调用，继续加载子目录下的类文件
                //addClass(classSet,subPackagePath,subPackagePath.replaceAll("/", "."));
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * 功能描述： 根据类的字符串名， 加载一个类
     * 
     * @param classSet
     * @param className
     * @return void
     */
    private static void doAddClass(Set<Class<?>> classSet, String className) {
        // 默认类加载时，不执行类的静态代码块初始化
        Class<?> class1 = loadClass(className, false);
        classSet.add(class1);
    }
}
