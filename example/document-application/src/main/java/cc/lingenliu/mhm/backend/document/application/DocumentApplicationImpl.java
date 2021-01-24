package cc.lingenliu.mhm.backend.document.application;

import cc.lingenliu.mhm.backend.document.api.DocumentApplication;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentDTO;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentHistoryDTO;
import cc.lingenliu.mhm.backend.document.application.assembler.DocumentAssembler;
import cc.lingenliu.mhm.backend.document.application.assembler.DocumentHistoryAssembler;
import cc.lingenliu.mhm.backend.document.domain.core.Document;
import cc.lingenliu.mhm.backend.document.domain.core.DocumentHistory;
import cc.lingenliu.mhm.backend.document.domain.core.DocumentType;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class DocumentApplicationImpl implements DocumentApplication {


    @Inject
    private QueryChannelService queryChannelService;

    @Inject
    private DocumentAssembler documentAssembler;

    @Inject
    private DocumentHistoryAssembler documentHistoryAssembler;


    @Override
    @Transactional
    public DocumentDTO createRootDir(String name) {
        Assert.notEmpty(name,"文件名不能为空");
        Document createdDIRDocument = Document.createRootDir(name);
        return documentAssembler.toDTO(createdDIRDocument);
    }

    @Override
    @Transactional
    public DocumentDTO createSubDir(Long parentId, String name) {
        Assert.notEmpty(name,"文件名不能为空");
        Assert.notNull(parentId,"指定的父文件夹为空");
        Document createdDIRDocument = Document.createSubDir(parentId,name);
        return documentAssembler.toDTO(createdDIRDocument);
    }

    @Override
    @Transactional
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document created =  documentAssembler.toEntity(documentDTO).createDocument();
        return documentAssembler.toDTO(created);
    }

    @Override
    @Transactional
    public boolean createDocuments(List<DocumentDTO> documentDTOList) {
        for (DocumentDTO documentDTO : documentDTOList){
            createDocument(documentDTO);
        }
        return true;
    }

    @Transactional
    public DocumentDTO updateNewVersion(DocumentDTO documentDTO) {
        Document document = documentAssembler.toEntity(documentDTO);
        Document updatedDocument = document.updateNewVersion();
        return documentAssembler.toDTO(updatedDocument);
    }

    @Override
    @Transactional
    public void deleteDocument(Long documentId) {
        Assert.notNull(documentId,"要删除的文档ID不能为空");
        Document.deleteDocument(documentId);
    }

    @Override
    public Page<DocumentDTO> pageSearchDocuments(String search, int page, int pageSize) {
        String sql = "from Document where name like ?1 and type = ?2 and deleted = false order by created desc";
        Page<Document> documentPage = queryChannelService
                .createJpqlQuery(sql)
                .setParameters("%"+search+"%", DocumentType.FILE)
                .setPage(page,pageSize)
                .pagedList();
        return Page.builder()
                .data(documentPage.getData().stream().map(it -> documentAssembler.toDTO(it)).collect(Collectors.toList()))
                .pageSize(pageSize)
                .totalSize(documentPage.getResultCount())
                .stat(documentPage.getStart());
    }

    @Override
    public Page<DocumentDTO> pageQueryRootDocuments(int page, int pageSize) {
        String sql = "from Document where deleted = false and parentId is null order by created desc";
        Page<Document> documentPage = queryChannelService
                .createJpqlQuery(sql)
                .setPage(page,pageSize)
                .pagedList();
        return Page.builder()
                .data(documentPage.getData().stream().map(it -> documentAssembler.toDTO(it)).collect(Collectors.toList()))
                .pageSize(pageSize)
                .totalSize(documentPage.getResultCount())
                .stat(documentPage.getStart());
    }

    @Override
    public Page<DocumentDTO> pageQuerySubDocuments(Long parentId, int page, int pageSize) {
        Assert.notNull(parentId,"父ID不能为空");
        String sql = "from Document where deleted = false and parentId = ?1 order by created desc";
        Page<Document> documentPage = queryChannelService
                .createJpqlQuery(sql)
                .setParameters(parentId)
                .setPage(page,pageSize)
                .pagedList();
        return Page.builder()
                .data(documentPage.getData().stream().map(it -> documentAssembler.toDTO(it)).collect(Collectors.toList()))
                .pageSize(pageSize)
                .totalSize(documentPage.getResultCount())
                .stat(documentPage.getStart());
    }

    @Override
    public List<DocumentHistoryDTO> queryHistories(Long documentId) {
        List<DocumentHistory> documentHistories = Document.queryHistories(documentId);
        return documentHistories.stream().map(it -> documentHistoryAssembler.toDTO(it)).collect(Collectors.toList());
    }
}
