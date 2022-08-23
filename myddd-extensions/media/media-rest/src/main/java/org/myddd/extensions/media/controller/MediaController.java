package org.myddd.extensions.media.controller;

import org.myddd.extensions.media.api.MediaApplication;
import org.myddd.extensions.media.api.MediaByte;
import org.myddd.extensions.media.api.MediaDTO;
import org.myddd.extensions.media.controller.response.MediaResponse;
import com.google.protobuf.ByteString;
import com.google.protobuf.StringValue;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class MediaController {

    public static final String OCTET_STREAM = "application/octet-stream";

    @Inject
    private MediaApplication mediaApplication;

    private Map<String,String> contentTypeMap;

    public MediaController(){
        initContentTypeMap();
    }

    private void initContentTypeMap(){
        contentTypeMap = new HashMap<>();
        contentTypeMap.put(".PDF","application/pdf");
        contentTypeMap.put(".JPEG","image/jpeg");
        contentTypeMap.put(".JPG","image/jpeg");
        contentTypeMap.put(".DOC","application/msword");
        contentTypeMap.put(".DOCX","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        contentTypeMap.put(".XLS","application/vnd.ms-excel");
        contentTypeMap.put(".XLSX","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        contentTypeMap.put(".PNG","image/png");
    }

    @PostMapping("/medias")
    ResponseEntity uploadMedia(@RequestParam(name = "file") MultipartFile file) throws IOException {
        try(InputStream inputStream = file.getInputStream()){
            ByteString byteString = ByteString.readFrom(inputStream);

            String digest = DigestUtils.md5Hex(byteString.newInput());

            var optionalMediaDTO = mediaApplication.queryMediaIdByDigest(StringValue.of(digest));
            MediaDTO mediaDTO = optionalMediaDTO.getMedia();
            if(!optionalMediaDTO.hasMedia()){
                MediaByte mediaByte = MediaByte.newBuilder().setName(file.getOriginalFilename()).setDigest(digest).setSize(file.getSize()).setContent(byteString).build();
                mediaDTO = mediaApplication.createMedia(mediaByte);
            }

            if (Objects.nonNull(mediaDTO)) {
                return ResponseEntity.ok(MediaResponse.of(mediaDTO,file.getOriginalFilename(),file.getSize()));
            }
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/medias/digest/{digest}")
    public ResponseEntity<MediaResponse> queryMediaByDigest(@PathVariable String digest) {
        var optionalMediaDTO = mediaApplication.queryMediaIdByDigest(StringValue.of(digest));
        if(!optionalMediaDTO.hasMedia()){
            throw new MediaNotFoundException();
        }
        return ResponseEntity.ok(MediaResponse.of(optionalMediaDTO.getMedia()));
    }

    @GetMapping("/medias/{mediaId}")
    @RequestMapping(value="/medias/{mediaId}",method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Object> downloadMedia(@PathVariable("mediaId") String mediaId) {
        MediaByte mediaFile = mediaApplication.queryMedia(StringValue.of(mediaId));
        String contentDisposition = "attachment";

        if (Objects.nonNull(mediaFile)){
            HttpHeaders headers = new HttpHeaders();
            String fileName = new String(mediaFile.getName().getBytes(), StandardCharsets.ISO_8859_1);
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            headers.add("Pragma", "no-cache");
            headers.add("Content-Length", String.valueOf(mediaFile.getSize()));


            headers.add("Expires", "0");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(getContentTypeFromFileName(fileName)))
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition + "; filename=\"" + fileName + "\"")
                    .body(mediaFile.getContent().toByteArray());
        }

        return ResponseEntity.badRequest().build();
    }

    private String getContentTypeFromFileName(String fileName){
        String fileNameUpper = fileName.toUpperCase();
        if(fileNameUpper.lastIndexOf(".") > -1){
            String suffix = fileNameUpper.substring(fileNameUpper.lastIndexOf("."));
            if(contentTypeMap.containsKey(suffix)){
                return contentTypeMap.get(suffix);
            }
            return OCTET_STREAM;
        }
        return OCTET_STREAM;
    }
}
