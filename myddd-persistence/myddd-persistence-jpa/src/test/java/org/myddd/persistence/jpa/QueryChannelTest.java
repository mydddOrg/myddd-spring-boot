package org.myddd.persistence.jpa;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.persistence.AbstractTest;
import org.myddd.persistence.mock.User;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.utils.Page;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Map;

@Transactional
class QueryChannelTest extends AbstractTest {

    @Inject
    private QueryChannelService queryChannelService;

    @BeforeEach
    void beforeEach(){
        for (int i = 0;i<= 10;i++){
           randomUser().createLocalUser();
        }
    }

    @Test
    void testQueryChannelNotNULL(){
        Assertions.assertNotNull(queryChannelService);
    }

    @Test
    void testJpqlPageQuery(){
        Page<User> userPage =  queryChannelService.createJpqlQuery("from User", User.class)
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());


        userPage =  queryChannelService.createJpqlQuery("from User where userId like :userId", User.class)
                .setParameters(Map.of("userId","%"))
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();
        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());


        userPage =  queryChannelService.createJpqlQuery("from User where userId like ?1", User.class)
                .setParameters("%")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();
        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

}
