package com.smart4j.framework.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * 
 * Action 的方法调用的返回值之一：视图对象， 例如一个JSP页面
 * 
 * @author liugaowei
 *
 */
public class View {

	/**
	 * 视图路径
	 */
	private String path;

	/**
	 * 模型数据
	 */
	private Map<String, Object> model;

	/**
	 * 构造函数
	 * 
	 * @param path
	 */
	public View(String path) {
		this.path = path;
		model = new HashMap<String, Object>();
	}

	public String getPath() {
		return path;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public View addModel(String key, Object value) {
		model.put(key, value);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n=================View print: ==============\n");
		stringBuilder.append("path=" + this.path + "\n");
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			stringBuilder.append("key=" + entry.getKey() + ", value=" + entry.getValue() + "\n");
		}
		stringBuilder.append("===========================================\n");
		return stringBuilder.toString();
	}

}
