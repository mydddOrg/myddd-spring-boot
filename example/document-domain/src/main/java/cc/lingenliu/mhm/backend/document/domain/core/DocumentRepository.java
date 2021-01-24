package cc.lingenliu.mhm.backend.document.domain.core;

import org.myddd.domain.AbstractRepository;

import java.util.List;

public interface DocumentRepository extends AbstractRepository {

    DocumentSpace createDocumentSpace(DocumentSpace documentSpace);

    Document createDocument(Document document);

    Document queryDocumentByID(Long documentId);

    List<DocumentHistory> queryHistories(Long documentId);


}

