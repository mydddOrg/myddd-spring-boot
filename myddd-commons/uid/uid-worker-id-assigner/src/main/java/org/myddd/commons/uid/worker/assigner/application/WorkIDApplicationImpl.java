package org.myddd.commons.uid.worker.assigner.application;

import org.myddd.commons.uid.worker.assigner.api.WorkIDApplication;
import org.myddd.commons.uid.worker.assigner.api.dto.WorkIdDto;
import org.myddd.commons.uid.worker.assigner.application.assembler.WorkIDAssembler;
import org.myddd.commons.uid.worker.assigner.domain.WorkID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
public class WorkIDApplicationImpl implements WorkIDApplication {

    @Inject
    private WorkIDAssembler workIDAssembler;

    @Override
    public WorkIdDto queryWorkId(String host, int port) {
        WorkID query = WorkID.queryWorkId(host,port);
        return workIDAssembler.toDto(query);
    }

    @Override
    @Transactional
    public WorkIdDto createWorkId(WorkIdDto workIdDto) {
        var created = workIDAssembler.toEntity(workIdDto).createWorkId();
        return workIDAssembler.toDto(created);
    }
}
