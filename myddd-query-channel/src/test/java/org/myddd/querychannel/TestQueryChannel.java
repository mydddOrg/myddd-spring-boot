package org.myddd.querychannel;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.User;
import org.myddd.utils.Page;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
class TestQueryChannel extends AbstractTest{

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
    }
}
