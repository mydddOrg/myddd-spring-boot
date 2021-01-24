package cc.lingenliu.mhm.backend.document.domain.core;

import org.myddd.domain.BaseIDEntity;
import org.myddd.domain.InstanceFactory;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "document_history",
        indexes = {@Index(name = "document_history_document_id", columnList = "document_id")})
public class DocumentHistory extends BaseIDEntity {

    @ManyToOne(cascade = {},fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(nullable = false)
    private String name;

    @Column(name = "media_id")
    private String mediaId;

    private String md5;

    private long created;

    private String remark;

    private int historyVersion;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getHistoryVersion() {
        return historyVersion;
    }

    public void setHistoryVersion(int historyVersion) {
        this.historyVersion = historyVersion;
    }

    private static DocumentRepository documentRepository;

    private static DocumentRepository getDocumentRepository(){
        if(Objects.isNull(DocumentHistory.documentRepository)){
            DocumentHistory.documentRepository = InstanceFactory.getInstance(DocumentRepository.class);
        }
        return DocumentHistory.documentRepository;
    }

    public static DocumentHistory createHistory(Document document){
        DocumentHistory documentHistory = new DocumentHistory();
        documentHistory.setDocument(document);
        documentHistory.setCreated(document.getCreated());
        documentHistory.setName(document.getName());
        documentHistory.setMediaId(document.getMediaId());
        documentHistory.setMd5(document.getMd5());
        documentHistory.setHistoryVersion(document.getHistoryVersion());
        return documentHistory;
    }


}
