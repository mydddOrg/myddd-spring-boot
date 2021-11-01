package org.myddd.persistence.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.persistence.AbstractTest;
import org.myddd.querychannel.QueryRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Transactional
class TestQueryRepository extends AbstractTest {

    @Inject
    private QueryRepository queryRepository;

    @Test
    void testGetQueryStringOfNamedQuery(){
        String querySQL = queryRepository.getQueryStringOfNamedQuery("User.queryByUserId");
        Assertions.assertNotNull(querySQL);

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            queryRepository.getQueryStringOfNamedQuery("User.notExists");
        });
    }
}
