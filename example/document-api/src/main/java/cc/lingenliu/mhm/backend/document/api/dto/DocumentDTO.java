package cc.lingenliu.mhm.backend.document.api.dto;

public class DocumentDTO {

    private Long id;

    private int version;

    private DocumentSpaceDTO space;

    private String name;

    private String mediaId;

    private String md5;

    private Long parentId;

    private String type;

    private String suffix;

    private long created;

    private boolean delete;

    private int historyVersion;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentSpaceDTO getSpace() {
        return space;
    }

    public void setSpace(DocumentSpaceDTO space) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
}
