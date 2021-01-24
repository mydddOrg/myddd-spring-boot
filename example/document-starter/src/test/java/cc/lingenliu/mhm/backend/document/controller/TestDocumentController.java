package cc.lingenliu.mhm.backend.document.controller;

import cc.lingenliu.mhm.backend.document.AbstractDocumentControllerTest;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentDTO;
import cc.lingenliu.mhm.backend.document.api.dto.DocumentHistoryDTO;
import cc.lingenliu.mhm.backend.document.starter.BaseResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.utils.Page;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

class TestDocumentController extends AbstractDocumentControllerTest {

    @Test
    void testCreateDir(){
        String json = "{\"name\":\"AAA\"}";
        ResponseEntity<BaseResponse<DocumentDTO>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/dir", HttpMethod.POST, createHttpEntityFromString(json), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testCreateSubDir(){
        String json = "{\"name\":\"AAA\"}";
        ResponseEntity<BaseResponse<DocumentDTO>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/dir", HttpMethod.POST, createHttpEntityFromString(json), new ParameterizedTypeReference<>() {});

        json = "{\"name\":\"AAA\",\"parentId\":"+ Objects.requireNonNull(responseEntity.getBody()).getResult().getId()+"}";
        responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/dir", HttpMethod.POST, createHttpEntityFromString(json), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        json = "{\"name\":\"AAA\",\"parentId\":-1}";
        responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/dir", HttpMethod.POST, createHttpEntityFromString(json), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).isResultSuccess());
    }

    @Test
    void testCreateDocuments(){
        String json = "[{\"name\":\"AAA.mp3\",\"mediaId\":\"AAA\"},{\"name\":\"BBB.mp3\",\"mediaId\":\"BBB\"},{\"name\":\"CCC.mp3\",\"mediaId\":\"CCC\"}]";
        ResponseEntity<BaseResponse<DocumentDTO>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/batch",HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).isResultSuccess());
    }

    @Test
    void testCreateDocument(){
        String json = "{\"name\":\"AAA.mp3\",\"mediaId\":\"AAA\"}";
        ResponseEntity<BaseResponse<DocumentDTO>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents",HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getResult().getId() > 0);

        json = "{\"name\":\"AAA.mp3\",\"mediaId\":\"AAA\",\"parentId\":-1}";
        responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents",HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(Objects.requireNonNull(responseEntity.getBody()).isResultSuccess());
    }

    @Test
    @Transactional
    void testCreateDocumentHistories(){
        String json = "{\"name\":\"AAA.mp3\",\"mediaId\":\"AAA\"}";
        ResponseEntity<BaseResponse<DocumentDTO>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents",HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getResult().getId() > 0);

        ResponseEntity<BaseResponse<List<DocumentHistoryDTO>>> listDocumentHistories = restTemplate.exchange(baseUrl() + "/v1/documents/" + responseEntity.getBody().getResult().getId() + "/histories", HttpMethod.GET, createEmptyHttpEntity(),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(listDocumentHistories.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(listDocumentHistories.getBody().getResult().isEmpty());

        String updateJson = "{\"name\":\"BBB.mp3\",\"mediaId\":\"BBB\"}";
        responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/" + responseEntity.getBody().getResult().getId() + "/version" ,HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getResult().getId() > 0);

        listDocumentHistories = restTemplate.exchange(baseUrl() + "/v1/documents/" + responseEntity.getBody().getResult().getId() + "/histories", HttpMethod.GET, createEmptyHttpEntity(),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(listDocumentHistories.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(listDocumentHistories.getBody().getResult().isEmpty());

    }

    @Test
    void testPageQueryDocuments(){
        ResponseEntity<BaseResponse<Page<DocumentDTO>>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents", HttpMethod.GET, createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        String json = "{\"name\":\"AAA.mp3\",\"mediaId\":\"AAA\"}";
        ResponseEntity<BaseResponse<DocumentDTO>> createResponseEntity = restTemplate.exchange(baseUrl() + "/v1/documents",HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(createResponseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(createResponseEntity.getBody()).getResult().getId() > 0);

        responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents", HttpMethod.GET, createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getResult().getResultCount() > 0);
    }

    @Test
    void testDisableDocument(){
        String json = "{\"name\":\"AAA.mp3\",\"mediaId\":\"AAA\"}";
        ResponseEntity<BaseResponse<DocumentDTO>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents",HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getResult().getId() > 0);


        ResponseEntity<BaseResponse> deleteResponseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/" + responseEntity.getBody().getResult().getId(), HttpMethod.DELETE, createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(deleteResponseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(deleteResponseEntity.getBody()).isResultSuccess());
    }

    @Test
    void testPageSearchDocuments(){
        ResponseEntity<BaseResponse<Page<DocumentDTO>>> responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents", HttpMethod.GET, createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        String json = "{\"name\":\"AAA.mp3\",\"mediaId\":\"AAA\"}";
        ResponseEntity<BaseResponse<DocumentDTO>> createResponseEntity = restTemplate.exchange(baseUrl() + "/v1/documents",HttpMethod.POST,createHttpEntityFromString(json),new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(createResponseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(createResponseEntity.getBody()).getResult().getId() > 0);

        responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/search?text=AAA", HttpMethod.GET, createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getResult().getResultCount() > 0);

        responseEntity = restTemplate.exchange(baseUrl() + "/v1/documents/search?text="+ UUID.randomUUID().toString(), HttpMethod.GET, createEmptyHttpEntity(), new ParameterizedTypeReference<>() {});
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).getResult().getResultCount() == 0);
    }
}
