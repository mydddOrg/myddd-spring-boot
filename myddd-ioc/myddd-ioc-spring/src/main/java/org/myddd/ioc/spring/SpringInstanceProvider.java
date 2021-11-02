package org.myddd.ioc.spring;

import org.myddd.domain.InstanceProvider;
import org.myddd.domain.IocInstanceNotUniqueException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
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
        }    }

    @Override
    public <T> T getInstance(Class<T> beanType, Class<? extends Annotation> annotationType) {
        if (annotationType == null) {
            return getInstance(beanType);
        }
        Map<String, T> results = applicationContext.getBeansOfType(beanType);
        List<T> resultsWithAnnotation = new ArrayList<>();
        for (Map.Entry<String, T> entry : results.entrySet()) {
            if (applicationContext.findAnnotationOnBean(entry.getKey(), annotationType) != null) {
                resultsWithAnnotation.add(entry.getValue());
            }
        }
        if (resultsWithAnnotation.isEmpty()) {
            return null;
        }
        if (resultsWithAnnotation.size() == 1) {
            return resultsWithAnnotation.get(0);
        }
        throw new IocInstanceNotUniqueException();
    }

    @Override
    public <T> Set<T> getInstances(Class<T> beanType) {
        return new HashSet<>(applicationContext.getBeansOfType(beanType).values());
    }
}
