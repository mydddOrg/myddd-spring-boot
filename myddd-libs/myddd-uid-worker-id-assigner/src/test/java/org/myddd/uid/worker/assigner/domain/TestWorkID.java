package org.myddd.uid.worker.assigner.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.uid.worker.assigner.AbstractTest;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@Transactional
public class TestWorkID extends AbstractTest {

    @Test
    void testCreateWorkId(){
        var randomWorkId = randomWorkID();
        var created = randomWorkId.createWorkId();
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created.getId()).isGreaterThan(0);

        var notValidWorkId = new WorkID();
        notValidWorkId.setHost(created.getHost());
        notValidWorkId.setPort(created.getPort());
        Assertions.assertThatExceptionOfType(PersistenceException.class).isThrownBy(()-> notValidWorkId.createWorkId());
    }

    @Test
    void testQueryWorkId(){
        var notExistsWorkId = WorkID.queryWorkId(randomId(),8008);
        Assertions.assertThat(notExistsWorkId).isNull();

        var created = randomWorkID().createWorkId();
        Assertions.assertThat(WorkID.queryWorkId(created.getHost(),created.getPort())).isNotNull();
    }
}
