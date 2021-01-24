package cc.lingenliu.mhm.backend.document.application.assembler;

import cc.lingenliu.mhm.backend.document.api.dto.DocumentHistoryDTO;
import cc.lingenliu.mhm.backend.document.domain.core.DocumentHistory;

import javax.inject.Named;

@Named
public class DocumentHistoryAssembler {

    public DocumentHistoryDTO toDTO(DocumentHistory entity){
        DocumentHistoryDTO dto = new DocumentHistoryDTO();
        dto.setHistoryVersion(entity.getHistoryVersion());
        dto.setMd5(entity.getMd5());
        dto.setName(entity.getName());
        dto.setMediaId(entity.getMediaId());
        dto.setCreated(entity.getCreated());
        dto.setRemark(entity.getRemark());
        return dto;
    }
}
