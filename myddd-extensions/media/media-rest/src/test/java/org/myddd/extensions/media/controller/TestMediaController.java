package org.myddd.extensions.media.controller;


import org.myddd.extensions.media.AbstractControllerTest;
import org.myddd.extensions.media.controller.response.MediaResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.lang.ErrorResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class TestMediaController extends AbstractControllerTest {

    @Test
    void testQueryMediaByDigest() throws IOException {
        MediaResponse mediaResponse = createMedia();
        Assertions.assertNotNull(mediaResponse);

        String sourcePath = Objects.requireNonNull(TestMediaController.class.getClassLoader().getResource("my_avatar.png")).getPath();

        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));

        ResponseEntity<MediaResponse> queryResponse =  restTemplate.getForEntity(baseUrl() + "/v1/medias/digest/" + digest,MediaResponse.class);
        Assertions.assertEquals(200,queryResponse.getStatusCodeValue());

        queryResponse = restTemplate.getForEntity(baseUrl() + "/v1/medias/digest/" + UUID.randomUUID(),MediaResponse.class);
        Assertions.assertEquals(400,queryResponse.getStatusCodeValue());
    }

    @Test
    void testCreateMedia(){
        MediaResponse mediaResponse = createMedia();
        Assertions.assertNotNull(mediaResponse);
    }


    @Test
    void testQueryMedia(){
        MediaResponse mediaResponse = createMedia();
        Assertions.assertNotNull(mediaResponse);

        String fileUrl = baseUrl() + "/v1/medias/";

        HttpHeaders headers = restTemplate.headForHeaders(fileUrl);
        long contentLength = headers.getContentLength();
        System.out.println(contentLength);
        File file = restTemplate.execute(fileUrl, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = File.createTempFile("download", "tmp");
            System.out.println(ret.getAbsolutePath());
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });

        Assertions.assertNotNull(file);

    }

    @Test
    void testMediaNotFound(){
        ResponseEntity<ErrorResponse> response = restTemplate
                .getForEntity(baseUrl() + "/v1/medias/" + UUID.randomUUID(), ErrorResponse.class);

        Assertions.assertEquals(400,response.getStatusCodeValue());
        Assertions.assertEquals(MediaRestError.MEDIA_NOT_FOUND.toString(),response.getBody().getErrorCode());
    }

    private MediaResponse createMedia(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String sourcePath = Objects.requireNonNull(TestMediaController.class.getClassLoader().getResource("my_avatar.png")).getPath();

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        File uploadFile = new File(sourcePath);
        FileSystemResource fileSource = new FileSystemResource(uploadFile);

        body.add("file", fileSource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);


        ResponseEntity<MediaResponse> response = restTemplate
                .postForEntity(baseUrl() + "/v1/medias", requestEntity, MediaResponse.class);

        Assertions.assertEquals(200,response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody().getMediaId());

        return response.getBody();
    }

}
