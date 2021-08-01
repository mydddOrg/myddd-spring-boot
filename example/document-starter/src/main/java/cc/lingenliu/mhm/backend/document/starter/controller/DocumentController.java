package cc.lingenliu.mhm.backend.document.starter.controller;

import cc.lingenliu.mhm.backend.document.api.DocumentApplication;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentDTO;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentHistoryDTO;
import cc.lingenliu.mhm.backend.document.starter.BaseResponse;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class DocumentController {

    @Inject
    private DocumentApplication documentApplication;

    @PostMapping(value = "/documents/dir")
    public ResponseEntity<BaseResponse<DocumentDTO>> createDir(@RequestBody DocumentDTO documentDTO){
        if(Objects.isNull(documentDTO.getParentId())){
            return ResponseEntity.ok(BaseResponse.ok(documentApplication.createRootDir(documentDTO.getName())));
        }else{
            return ResponseEntity.ok(BaseResponse.ok(documentApplication.createSubDir(documentDTO.getParentId(),documentDTO.getName())));
        }
    }

    @PostMapping(value = "/documents")
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody DocumentDTO documentDTO){
        return ResponseEntity.ok(documentApplication.createDocument(documentDTO));
    }

    @GetMapping(value = "/documents/{documentId}")
    public ResponseEntity<BaseResponse<DocumentDTO>> queryDocument(@PathVariable Long documentId){
        return ResponseEntity.ok(BaseResponse.ok(documentApplication.queryDocumentById(documentId)));
    }

    @PostMapping(value = "/documents/batch")
    public ResponseEntity<BaseResponse> createDocuments(@RequestBody List<DocumentDTO> documents){
        documentApplication.createDocuments(documents);
        return ResponseEntity.ok(BaseResponse.ok());
    }
    
    @PostMapping(value = "/documents/{documentId}/version")
    public ResponseEntity<BaseResponse<DocumentDTO>> updateNewVersion(@PathVariable(value = "documentId") Long documentId,@RequestBody DocumentDTO documentDTO){
        documentDTO.setId(documentId);
        return ResponseEntity.ok(BaseResponse.ok(documentApplication.updateNewVersion(documentDTO)));
    }

    @GetMapping(value = "/documents/{documentId}/histories")
    public ResponseEntity<BaseResponse<List<DocumentHistoryDTO>>> queryHistories(@PathVariable(value = "documentId") Long documentId){
        return ResponseEntity.ok(BaseResponse.ok(documentApplication.queryHistories(documentId)));
    }

    @GetMapping(value = "/documents")
    public ResponseEntity<BaseResponse<Page<DocumentDTO>>> pageQueryDocuments(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(value = "parentId",defaultValue = "") Long parentId){
        if(Objects.isNull(parentId)){
            return ResponseEntity.ok(BaseResponse.ok(documentApplication.pageQueryRootDocuments(page,pageSize)));
        }else{
            return ResponseEntity.ok(BaseResponse.ok(documentApplication.pageQuerySubDocuments(parentId,page,pageSize)));
        }
    }

    @GetMapping(value = "/documents/search")
    public ResponseEntity<BaseResponse<Page<DocumentDTO>>> pageQueryDocumentsWithSearch(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize,@RequestParam(value = "text",defaultValue = "") String search){
        Assert.notEmpty(search);
        return ResponseEntity.ok(BaseResponse.ok(documentApplication.pageSearchDocuments(search,page,pageSize)));
    }

    @DeleteMapping(value = "/documents/{documentId}")
    public ResponseEntity<BaseResponse> deleteDocument(@PathVariable(value = "documentId") Long documentId){
        documentApplication.deleteDocument(documentId);
        return ResponseEntity.ok(BaseResponse.ok());
    }

}
