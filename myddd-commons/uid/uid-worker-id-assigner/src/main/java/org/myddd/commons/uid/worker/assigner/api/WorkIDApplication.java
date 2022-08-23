package org.myddd.commons.uid.worker.assigner.api;

import org.myddd.commons.uid.worker.assigner.api.dto.WorkIdDto;

public interface WorkIDApplication {

    WorkIdDto queryWorkId(String host,int port);

    WorkIdDto createWorkId(WorkIdDto workIdDto);
}
