package org.myddd.commons.uid.worker.assigner.domain;

import org.myddd.domain.AbstractRepository;

public interface WorkIDRepository extends AbstractRepository {

    WorkID queryWorkId(String host,int port);

}
