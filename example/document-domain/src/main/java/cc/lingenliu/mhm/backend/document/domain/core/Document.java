package cc.lingenliu.mhm.backend.document.domain.core;

import org.myddd.domain.BaseIDEntity;
import org.myddd.domain.InstanceFactory;
import org.myddd.utils.Assert;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "document")
public class Document extends BaseIDEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="space_id")
    private DocumentSpace space;

    @Column(nullable = false)
    private String name;

    @Column(name = "media_id")
    private String mediaId;

    private String md5;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(nullable = false)
    private DocumentType type = DocumentType.FILE;

    private String suffix;

    private long created;

    private long updated;

    private boolean deleted;

    private String remark;

    @Column(name = "history_version")
    private int historyVersion;

    public Document(){
        this.historyVersion = 1;
        this.created = System.currentTimeMillis();
    }

    public DocumentSpace getSpace() {
        return space;
    }

    public void setSpace(DocumentSpace space) {
        this.space = space;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    private void newVersion(){
        this.historyVersion += 1;
    }

    private static DocumentRepository repository;

    private static DocumentRepository getDocumentRepository(){
        if(Objects.isNull(repository)){
            Document.repository = InstanceFactory.getInstance(DocumentRepository.class);
        }
        return Document.repository;
    }

    public Document createDocument(){
        Assert.isNull(this.getId());
        Assert.notNull(this.mediaId,"媒体ID不能为空");
        Assert.notNull(this.name,"文件名不能为空");
        this.type = DocumentType.FILE;

        if(Objects.nonNull(this.getParentId())){
            Document parentDir = Document.getDocumentRepository().get(Document.class,this.getParentId());
            Assert.notNull(parentDir,"指定的父目录不存在");
            Assert.isTrue(parentDir.getType() == DocumentType.DIR,"指定的父目录不能为文件");
        }
        return getDocumentRepository().createDocument(this);
    }

    public static Document createRootDir(String name){
        Document document = new Document();
        document.type = DocumentType.DIR;
        document.name = name;
        document.created = System.currentTimeMillis();
        return getDocumentRepository().save(document);
    }


    public static Document createSubDir(Long parentId, String name){
        Assert.isTrue(getDocumentRepository().exists(Document.class,parentId),"父目录不存在，请检查");

        Document document = new Document();
        document.type = DocumentType.DIR;
        document.name = name;
        document.parentId = parentId;
        document.created = System.currentTimeMillis();
        return getDocumentRepository().save(document);
    }

    public static void deleteDocument(Long documentId){
        Assert.notNull(documentId,"文档ID不能为空");

        Document document = getDocumentRepository().queryDocumentByID(documentId);
        if(Objects.nonNull(document)){
            document.setDeleted(true);
        }

        getDocumentRepository().save(document);
    }

    public Document updateNewVersion(){
        Document originDocument = Document.getDocumentRepository().queryDocumentByID(this.getId());
        Assert.notNull(originDocument,"找不到原文档");

        DocumentHistory documentHistory = DocumentHistory.createHistory(originDocument);
        DocumentHistory history =  getDocumentRepository().save(documentHistory);
        Assert.notNull(history);

        originDocument.setMediaId(mediaId);
        originDocument.setMd5(md5);
        originDocument.setName(name);
        originDocument.setRemark(remark);
        originDocument.setUpdated(System.currentTimeMillis());
        originDocument.newVersion();

        return getDocumentRepository().save(originDocument);
    }

    public static List<DocumentHistory> queryHistories(Long documentId){
        return getDocumentRepository().queryHistories(documentId);
    }

}
