package org.myddd.domain;


import java.util.Set;

/**
 * 实例提供者接口，其实现类以适配器的方式将Bean查找的任务委托给真正的IoC容器，如SpringIoC或Google Guice。
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 *
 */
public interface InstanceProvider {

	/**
     * 根据类型获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。如果找不到该类型的实例则抛出异常。
	 * @param <T> 类型参数
	 * @param beanType 实例的类型
	 * @return 指定类型的实例。
	 */
	<T> T getInstance(Class<T> beanType);

	/**
     * 根据类型和名称获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。不同的IoC容器用不同的方式解释beanName。
     * 具体的解释方式请参见各种InstanceProvider实现类的Javadoc。
     * 如果找不到该类型的实例则抛出异常。
	 * @param <T> 类型参数
	 * @param beanName 实现类在容器中配置的名字
	 * @param beanType 实例的类型
	 * @return 指定类型的实例。
	 */
	<T> T getInstance(Class<T> beanType, String beanName);

	/**
	 * 获取指定类型的实例的集合
	 * @param beanType 实例的类型
	 * @param <T> 类型参数
	 * @return 指定类型的实例的集合
	 */
	<T> Set<T> getInstances(Class<T> beanType);

}
