package org.myddd.commons.uid.worker.assigner.application.assembler;

import org.myddd.commons.uid.worker.assigner.api.dto.WorkIdDto;
import org.myddd.commons.uid.worker.assigner.domain.WorkID;

import jakarta.inject.Named;
import java.util.Objects;

@Named
public class WorkIDAssembler {

    public WorkID toEntity(WorkIdDto dto){
        if(Objects.isNull(dto))return null;

        WorkID workID = new WorkID();
        workID.setId(dto.getId());
        workID.setName(dto.getName());
        workID.setHost(dto.getHost());
        workID.setPort(dto.getPort());
        return workID;
    }

   public WorkIdDto toDto(WorkID entity){
        if(Objects.isNull(entity))return null;

        WorkIdDto workIdDto = new WorkIdDto();
        workIdDto.setId(entity.getId());
        workIdDto.setName(entity.getName());
        workIdDto.setHost(entity.getHost());
        workIdDto.setPort(entity.getPort());
        return workIdDto;
    }
}
