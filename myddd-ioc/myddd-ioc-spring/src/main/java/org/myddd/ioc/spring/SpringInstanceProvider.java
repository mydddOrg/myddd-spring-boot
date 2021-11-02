package org.myddd.ioc.spring;

import org.myddd.domain.InstanceProvider;
import org.myddd.domain.IocInstanceNotUniqueException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.util.*;

public class SpringInstanceProvider implements InstanceProvider {

    private ApplicationContext applicationContext = null;

    public static SpringInstanceProvider createInstance(ApplicationContext applicationContext){
        SpringInstanceProvider instanceProvider = new SpringInstanceProvider();
        instanceProvider.applicationContext = applicationContext;
        return instanceProvider;
    }

    @Override
    public <T> T getInstance(Class<T> beanType) {
        try {
            return applicationContext.getBean(beanType);
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IocInstanceNotUniqueException(e);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    @Override
    public <T> T getInstance(Class<T> beanType, String beanName) {
        try {
            return applicationContext.getBean(beanName, beanType);
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IocInstanceNotUniqueException(e);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }


    @Override
    public <T> Set<T> getInstances(Class<T> beanType) {
        return new HashSet<>(applicationContext.getBeansOfType(beanType).values());
    }
}
