package org.myddd.domain;

/**
 * 当试图获取在IoC容器中不存在的Bean实例时抛出此异常。
 */
public class IocInstanceNotFoundException extends IocException {

	private static final long serialVersionUID = -742775077430352894L;

	public IocInstanceNotFoundException(String message) {
		super(message);
	}

}
