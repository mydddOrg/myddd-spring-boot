package org.myddd.domain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TestBaseDistributedEntity {

    private InstanceProvider instanceProvider = mock(InstanceProvider.class);

    @Test
    void testCreateEntity(){
        InstanceFactory.setInstanceProvider(instanceProvider);

        Assertions.assertThrows(Exception.class, BaseDistributedEntity::new);
        when(instanceProvider.getInstance(any())).thenReturn(new SnowflakeDistributeId(0,0));
        BaseDistributedEntity entity = new BaseDistributedEntity();
        Assertions.assertNotNull(entity.getId());
    }

}
