package cc.lingenliu.mhm.backend.document.domain.core;

import org.myddd.domain.BaseIDEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "document_space")

public class DocumentSpace extends BaseIDEntity {

    private static final String SHARED_SPACE = "SHARED_SPACE";

    @Column(name = "space_id", nullable = false, unique = true)
    private String spaceId;

    private String name;

    private long created;

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    private static DocumentRepository documentRepository;

    private static DocumentRepository getDocumentRepository(){
        if(Objects.isNull(documentRepository)){
            documentRepository = InstanceFactory.getInstance(DocumentRepository.class);
        }
        return documentRepository;
    }

    public static DocumentSpace createSharedSpace(){
        DocumentSpace documentSpace = new DocumentSpace();
        documentSpace.setSpaceId(SHARED_SPACE);
        documentSpace.setName(SHARED_SPACE);
        return getDocumentRepository().createDocumentSpace(documentSpace);
    }

}
