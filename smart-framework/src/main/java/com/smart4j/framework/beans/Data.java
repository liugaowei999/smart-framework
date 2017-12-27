package com.smart4j.framework.beans;

/**
 * 返回数据对象
 * 
 * Action 的方法调用的返回值之二：数据对象， 例如一个Json串
 * 
 * 框架会将Data封装的数据写入HttpServletResponse对象中，浏览器可以直接读取显示
 * 
 * @author liugaowei
 *
 */
public class Data {
	/**
	 * 模型数据
	 */
	private Object model;

	/**
	 * 构造函数
	 * @param model
	 */
	public Data(Object model) {
		this.model = model;
	}

	public Object getModel() {
		return model;
	}

}
