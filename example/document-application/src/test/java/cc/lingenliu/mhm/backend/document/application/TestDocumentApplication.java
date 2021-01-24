package cc.lingenliu.mhm.backend.document.application;

import cc.lingenliu.mhm.backend.document.AbstractDocumentTest;
import cc.lingenliu.mhm.backend.document.api.DocumentApplication;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentDTO;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentHistoryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.utils.Page;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class TestDocumentApplication extends AbstractDocumentTest {

    @Inject
    private DocumentApplication documentApplication;

    @Test
    void testCreateRootDir(){
        DocumentDTO createdRootDir = documentApplication.createRootDir("MY_IDR");
        Assertions.assertTrue(createdRootDir.getId() > 0);
    }

    @Test
    void testCreateSubDir(){

        Assertions.assertThrows(RuntimeException.class,()->{
            documentApplication.createSubDir(Long.MAX_VALUE,"SUB_DIR");
        });
        DocumentDTO createdRootDir = documentApplication.createRootDir("MY_IDR");
        DocumentDTO createdSubDir = documentApplication.createSubDir(createdRootDir.getId(),"SUB_DIR");
        Assertions.assertTrue(createdRootDir.getId() > 0);
        Assertions.assertTrue(createdSubDir.getParentId().equals(createdRootDir.getId()));

        Assertions.assertThrows(RuntimeException.class,()->{
            documentApplication.createSubDir(null,"SUB_DIR");
        });
    }

    @Test
    void testDocumentUpdateNewVersion(){
        DocumentDTO createdDocument = documentApplication.createDocument(createDocument());
        Assertions.assertNotNull(createdDocument.getId() > 0);

        createdDocument.setMediaId(UUID.randomUUID().toString());
        createdDocument.setName("BBB.png");

        DocumentDTO updateDocument = documentApplication.updateNewVersion(createdDocument);
        Assertions.assertNotNull(updateDocument.getId());

        Assertions.assertEquals(createdDocument.getHistoryVersion() + 1,updateDocument.getHistoryVersion());
    }

    @Test
    void testDocumentQueryHistories(){
        List<DocumentHistoryDTO> documentHistoryDTOList = documentApplication.queryHistories(Long.MAX_VALUE);
        Assertions.assertTrue(documentHistoryDTOList.isEmpty());


        DocumentDTO createdDocument = documentApplication.createDocument(createDocument());
        Assertions.assertNotNull(createdDocument.getId() > 0);
        documentHistoryDTOList = documentApplication.queryHistories(createdDocument.getId());
        Assertions.assertTrue(documentHistoryDTOList.isEmpty());

        createdDocument.setMediaId(UUID.randomUUID().toString());
        createdDocument.setName("BBB.png");
        documentApplication.updateNewVersion(createdDocument);
        documentHistoryDTOList = documentApplication.queryHistories(createdDocument.getId());
        Assertions.assertFalse(documentHistoryDTOList.isEmpty());
    }

    @Test
    void testCreateDocument(){
        DocumentDTO createdDocument = documentApplication.createDocument(createDocument());
        Assertions.assertNotNull(createdDocument.getId() > 0);
    }

    @Test
    void testCreateDocuments(){
        List<DocumentDTO> documentDTOS = new ArrayList<>();
        for(int i = 0 ;i < 10 ;i++){
            documentDTOS.add(createDocument());
        }

        boolean success = documentApplication.createDocuments(documentDTOS);
        Assertions.assertTrue(success);

    }

    @Test
    void testDeleteDocument(){
        DocumentDTO createdDocument = documentApplication.createDocument(createDocument());
        documentApplication.deleteDocument(createdDocument.getId());

        Assertions.assertThrows(RuntimeException.class,() -> {
            documentApplication.deleteDocument(-1l);
        });
    }

    @Test
    void testPageSearchDocuments(){
        DocumentDTO createdDocument = documentApplication.createDocument(createDocument());
        Page<DocumentDTO> documentDTOPage = documentApplication.pageSearchDocuments("NAME",1,10);
        Assertions.assertTrue(documentDTOPage.getResultCount() > 0);

        documentDTOPage = documentApplication.pageSearchDocuments(UUID.randomUUID().toString(),1,10);
        Assertions.assertTrue(documentDTOPage.getResultCount() == 0);

        documentApplication.deleteDocument(createdDocument.getId());
        documentDTOPage = documentApplication.pageSearchDocuments(UUID.randomUUID().toString(),1,10);
        Assertions.assertTrue(documentDTOPage.getResultCount() == 0);
    }

    @Test
    void testPageQueryRootDocuments(){
        DocumentDTO createdDocument = documentApplication.createDocument(createDocument());
        Page<DocumentDTO> documentDTOPage = documentApplication.pageQueryRootDocuments(1,10);

        Assertions.assertTrue(documentDTOPage.getResultCount() > 0);
    }

    @Test
    void testPageQuerySubDocuments(){
        DocumentDTO createdRootDir = documentApplication.createRootDir("MY_IDR");
        DocumentDTO document = createDocument();
        document.setParentId(createdRootDir.getId());
        documentApplication.createDocument(document);

        Page<DocumentDTO> documentDTOPage = documentApplication.pageQuerySubDocuments(createdRootDir.getId(),1,10);
        Assertions.assertTrue(documentDTOPage.getResultCount() > 0);

        Assertions.assertThrows(RuntimeException.class,() -> {
            documentApplication.pageQuerySubDocuments(null,1,10);
        });
    }

    private DocumentDTO createDocument(){
        DocumentDTO document = new DocumentDTO();
        document.setMd5(UUID.randomUUID().toString());
        document.setMediaId(UUID.randomUUID().toString());
        document.setName("NAME.mp3");
        document.setSuffix("mp3");
        document.setType("FILE");
        return document;
    }


}
