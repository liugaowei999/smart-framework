package com.smart4j.framework.beans;

import java.util.Map;

import com.smart4j.framework.utils.CastUtil;

/**
 * 请求参数对象
 * 
 * @author liugaowei
 *
 */
public class Param {

	private Map<String, Object> paramMap;

	/**
	 * 构造函数
	 * 
	 * @param paramMap
	 */
	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 获取所有参数的列表
	 * 
	 * @return
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * 根据参数名获取long型的参数值
	 * 
	 * @param name
	 * @return
	 */
	public long getLong(String name) {
		return CastUtil.castLong(paramMap.get(name));
	}

}
