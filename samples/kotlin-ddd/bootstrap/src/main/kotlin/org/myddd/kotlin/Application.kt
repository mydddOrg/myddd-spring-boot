package org.myddd.kotlin

import org.myddd.domain.InstanceFactory
import org.myddd.ioc.spring.SpringInstanceProvider
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["org.myddd"])
@EntityScan(basePackages = ["org.myddd"])
open class Application

fun main(args:Array<String>){
    val applicationContext = SpringApplication.run(Application::class.java, *args)
    InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext))
}