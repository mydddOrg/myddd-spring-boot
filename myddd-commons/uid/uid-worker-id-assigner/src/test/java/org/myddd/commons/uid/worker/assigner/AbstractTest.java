package org.myddd.commons.uid.worker.assigner;


import org.myddd.commons.uid.worker.assigner.api.dto.WorkIdDto;
import org.myddd.commons.uid.worker.assigner.domain.WorkID;
import org.junit.jupiter.api.BeforeEach;
import org.myddd.domain.InstanceFactory;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Random;
import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
public abstract class AbstractTest extends TestApplication {

    @Autowired
    protected ApplicationContext applicationContext;

    private final Random random = new Random();

    @BeforeEach
    protected void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    public String randomId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public int randomInt(){
        return random.nextInt();
    }

    protected WorkID randomWorkID(){
        WorkID workID = new WorkID();
        workID.setName(randomId());
        workID.setHost(randomId());
        workID.setPort(randomInt());
        return workID;
    }

    protected WorkIdDto randomWorkIdDto(){
        WorkIdDto workIdDto = new WorkIdDto();
        workIdDto.setName(randomId());
        workIdDto.setHost(randomId());
        workIdDto.setPort(randomInt());
        return workIdDto;
    }
}
