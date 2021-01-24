package cc.lingenliu.mhm.backend.document.infra;

import cc.lingenliu.mhm.backend.document.AbstractDocumentTest;
import cc.lingenliu.mhm.backend.document.domain.core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

class TestDocumentRepository extends AbstractDocumentTest {

    @Inject
    private DocumentRepository documentRepository;

    @Test
    @Transactional
    void testCreateDocumentSpace(){
        DocumentSpace created = documentRepository.createDocumentSpace(createDocumentSpace());
        Assertions.assertTrue(created.getId() > 0);
    }

    @Test
    @Transactional
    void testCreateDocument(){
        DocumentSpace created = documentRepository.createDocumentSpace(createDocumentSpace());
        Document createDocument = documentRepository.createDocument(createDocument(created));
        Assertions.assertTrue(createDocument.getId() > 0);
    }

    @Test
    @Transactional
    void testQueryDocument(){
        DocumentSpace created = documentRepository.createDocumentSpace(createDocumentSpace());
        Document createDocument = documentRepository.createDocument(createDocument(created));

        Document queryDocument = documentRepository.queryDocumentByID(createDocument.getId());
        Assertions.assertNotNull(queryDocument);

        queryDocument = documentRepository.queryDocumentByID(Long.MAX_VALUE);
        Assertions.assertNull(queryDocument);
    }

    @Test
    @Transactional
    void testQueryDocumentHistories(){
        List<DocumentHistory> histories = documentRepository.queryHistories(Long.MAX_VALUE);
        Assertions.assertTrue(histories.isEmpty());


        DocumentSpace created = documentRepository.createDocumentSpace(createDocumentSpace());
        Document createDocument = documentRepository.createDocument(createDocument(created));

        histories = documentRepository.queryHistories(createDocument.getId());
        Assertions.assertTrue(histories.isEmpty());

        DocumentHistory documentHistory = DocumentHistory.createHistory(createDocument);
        documentRepository.save(documentHistory);

        histories = documentRepository.queryHistories(createDocument.getId());
        Assertions.assertFalse(histories.isEmpty());

    }

    private DocumentSpace createDocumentSpace(){
        DocumentSpace documentSpace = new DocumentSpace();
        documentSpace.setName("CREATED DOCUMENT SPACE");
        documentSpace.setSpaceId("SHARED");
        return documentSpace;
    }

    private Document createDocument(DocumentSpace documentSpace){
        Document document = new Document();
        document.setSpace(documentSpace);
        document.setMd5(UUID.randomUUID().toString());
        document.setMediaId(UUID.randomUUID().toString());
        document.setName("NAME.mp3");
        document.setSuffix("mp3");
        document.setType(DocumentType.FILE);
        return document;
    }

}
