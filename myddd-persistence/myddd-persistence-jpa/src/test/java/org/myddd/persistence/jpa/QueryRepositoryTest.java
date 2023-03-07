package org.myddd.persistence.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.persistence.AbstractTest;
import org.myddd.querychannel.QueryRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Transactional
class QueryRepositoryTest extends AbstractTest {

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
