package org.myddd.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 实例工厂类，充当IoC容器的门面，通过它可以获得部署在IoC容器中的Bean的实例。 InstanceFactory向客户代码隐藏了IoC
 *
 * @author lingenliu (<a href="mailto:lingenliu@gmail.com">lingenliu@gmail.com</a>)
 */
public class InstanceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceFactory.class);


    //实例提供者，代表真正的IoC容器
    private static InstanceProvider instanceProvider;

    private InstanceFactory() {

    }

    /**
     * 设置实例提供者。
     *
     * @param provider 一个实例提供者的实例。
     */
    public static void setInstanceProvider(InstanceProvider provider) {
        instanceProvider = provider;
    }

    /**
     * 根据类型获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。如果找不到该类型的实例则抛出异常。
     *
     * @param <T> 对象的类型
     * @param beanType 对象所属的类型
     * @return 类型为T的对象实例
     */
    public static <T> T getInstance(Class<T> beanType) {
        T result = instanceProvider.getInstance(beanType);
        if (result != null) {
            return result;
        }
        throw new IocInstanceNotFoundException("There's not bean of type '" + beanType + "' exists in IoC container!");
    }

    /**
     * 检测是否有实例
     */
    public static <T> T getInstanceWithDefault(Class<T> beanType,T defaultBean){
        T result = instanceProvider.getInstance(beanType);
        if (result != null) {
            return result;
        }
        return defaultBean;
    }

    /**
     * 根据类型和名称获取对象实例。返回的对象实例所属的类是T或它的实现类或子类。不同的IoC容器用不同的方式解释beanName。
     * 具体的解释方式请参见各种InstanceProvider实现类的Javadoc。 如果找不到该类型的实例则抛出异常。
     *
     * @param <T> 类型参数
     * @param beanName bean的名称
     * @param beanType 实例的类型
     * @return 指定类型的实例。
     */
    public static <T> T getInstance(Class<T> beanType, String beanName) {
        T result = instanceProvider.getInstance(beanType, beanName);
        if (result != null) {
            return result;
        }
        throw new IocInstanceNotFoundException("There's not bean of type '" + beanType + "' exists in IoC container!");
    }
}
