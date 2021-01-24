package cc.lingenliu.mhm.backend.document.infra.repository;

import cc.lingenliu.mhm.backend.document.domain.core.Document;
import cc.lingenliu.mhm.backend.document.domain.core.DocumentHistory;
import cc.lingenliu.mhm.backend.document.domain.core.DocumentRepository;
import cc.lingenliu.mhm.backend.document.domain.core.DocumentSpace;
import org.myddd.domain.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.List;

@Named
public class DocumentRepositoryJPA extends AbstractRepositoryJPA implements DocumentRepository {
    @Override
    public DocumentSpace createDocumentSpace(DocumentSpace documentSpace) {
        documentSpace.setCreated(System.currentTimeMillis());
        return getEntityRepository().save(documentSpace);
    }

    @Override
    public Document createDocument(Document document) {
        document.setCreated(System.currentTimeMillis());
        return getEntityRepository().save(document);
    }

    @Override
    public Document queryDocumentByID(Long documentId) {
        return getEntityRepository().get(Document.class,documentId);
    }

    @Override
    public List<DocumentHistory> queryHistories(Long documentId) {
        return getEntityRepository().createCriteriaQuery(DocumentHistory.class).eq("document.id",documentId).list();
    }
}
