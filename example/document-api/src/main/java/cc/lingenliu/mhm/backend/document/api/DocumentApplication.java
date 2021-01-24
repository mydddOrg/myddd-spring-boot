package cc.lingenliu.mhm.backend.document.api;

import cc.lingenliu.mhm.backend.document.api.dto.DocumentDTO;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentHistoryDTO;
import org.myddd.utils.Page;

import java.util.List;

public interface DocumentApplication {

    DocumentDTO createRootDir(String name);

    DocumentDTO createSubDir(Long parentId,String name);

    DocumentDTO createDocument(DocumentDTO documentDTO);

    boolean createDocuments(List<DocumentDTO> documentDTOList);
    
    DocumentDTO updateNewVersion(DocumentDTO documentDTO);

    void deleteDocument(Long documentId);

    Page<DocumentDTO> pageSearchDocuments(String search,int page,int pageSize);

    Page<DocumentDTO> pageQueryRootDocuments(int page,int pageSize);

    Page<DocumentDTO> pageQuerySubDocuments(Long parentId,int page,int pageSize);

    List<DocumentHistoryDTO> queryHistories(Long documentId);

}
