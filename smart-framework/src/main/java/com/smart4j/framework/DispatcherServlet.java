package com.smart4j.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.smart4j.framework.beans.Data;
import com.smart4j.framework.beans.Handler;
import com.smart4j.framework.beans.Param;
import com.smart4j.framework.beans.View;
import com.smart4j.framework.helper.BeanHelper;
import com.smart4j.framework.helper.ConfigHelper;
import com.smart4j.framework.helper.ControllerHelper;
import com.smart4j.framework.utils.ArrayUtil;
import com.smart4j.framework.utils.CodecUtil;
import com.smart4j.framework.utils.JsonUtil;
import com.smart4j.framework.utils.ReflectionUtil;
import com.smart4j.framework.utils.StreamUtil;
import com.smart4j.framework.utils.StringUtil;

/**
 * 请求转发器 ： MVC框架中最核心的类
 * 
 * @author liugaowei
 *
 */
/**
 * 使用Servlet的@WebServlet注解
 * name         ：指定servlet的名称，如果没有指定，默认名称为类的全限定名
 * urlPatterns  ：指定URL的匹配模式
 * loadOnStartup：指定Servlet的加载顺序；设置大于0的值(默认值为-1)，表示启动应用程序后就要初始化Servlet
 * (而不是实例化几个Servlet)。数字代表了Servlet的初始顺序，容器必须保证有较小数字的Servlet先初始化
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//super.service(request, response);
		// 获取请求方法
		String requestMethod = request.getMethod().toLowerCase();
		// 获取请求路径
		String requestPath = request.getPathInfo();

		// 获取Action处理器
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

		// 请求需要调用的方法存在
		if (handler != null) {
			// 获取Controller类
			Class<?> controllerClass = handler.getControllerClass();
			// 获取Controller类对应的Bean实例
			Object controllerBean = BeanHelper.getBean(controllerClass);

			// 创建请求参数对象
			Map<String, Object> paramMap = new HashMap<String, Object>();

			// Post方式 - 从HttpRequest中获取请求参数
			Enumeration<String> paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
				String paramValue = request.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}

			// Get方式参数 - 从HttpRequest中获取请求URL, 并从URL获取查询参数
			String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));

			if (StringUtil.isNotEmpty(body)) {
				String[] params = body.split("&");
				if (ArrayUtil.isNotEmpty(params)) {
					for (String param : params) {
						String[] array = StringUtils.split(param, "=");// param.split("=");
						if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
							String paramName = array[0];
							String paramValue = array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}

			// 创建Param参数对象
			Param param = new Param(paramMap);

			// 调用Action方法
			Method actionMethod = handler.getActionMethod();
			Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			if (result instanceof View) {
				// 返回JSP页面
				View view = (View) result;

				String path = view.getPath();
				if (StringUtil.isNotEmpty(path)) {
					if (path.startsWith("/")) {
						response.sendRedirect(request.getContextPath() + path);
					} else {
						Map<String, Object> model = view.getModel();
						for (Map.Entry<String, Object> entry : model.entrySet()) {
							request.setAttribute(entry.getKey(), entry.getValue());
						}
						/**
						 * Forwards a request from a servlet to another resource (servlet, JSP file, or HTML file) on the server
						 */
						request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
					}
				}
			} else if (result instanceof Data) {
				// 返回Json数据
				Data data = (Data) result;
				Object model = data.getModel();
				if (model != null) {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					PrintWriter writer = response.getWriter();
					String json = JsonUtil.toJson(model);
					writer.write(json);
					writer.flush();
					writer.close();
				}
			}
		}
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		// 初始化相关的Helper类
		HelperLoader.init();

		// 获取Servlet上下文 ServletContext对象（用于注册Servlet到Servlet容器中）
		ServletContext servletContext = servletConfig.getServletContext();

		// 从Servlet容器中获取处理JSP的Servlet， 进行配置JSP页面环境路径
		/**
		 * 这个方法返回与指定servlet对应的ServletRegistration，或者如果不存在此名字对应的ServletRegistration就返回null。
		 * 如果没有servlets注册到ServletContext，将返回一个空的Map。返回的Map包括与所有申明的和注解的servlet对应的
		 * ServletRegistration对象，以及与所有通过addServlet方法添加到ServletContext的servlet对应的
		 * ServletRegistration对象。返回的Map的任何变化不得影响ServletContext。
		 */
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

		// 处理静态资源的默认Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");
	}

}
