package com.smart4j.framework.beans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 封装请求消息
 * 
 * @author liugaowei
 *
 */
public class Request {

	/**
	 * 请求的方法:GET/POST/HEAD/PUT/DELETE....
	 */
	private String requestMethod;

	/**
	 * 请求的路径
	 */
	private String requestPath;

	// 构造函数
	public Request(String requestMethod, String requestPath) {
		this.requestMethod = requestMethod;
		this.requestPath = requestPath;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
