package org.myddd.extesions.media.gridfs;

import org.junit.jupiter.api.Disabled;
import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SpringBootTest(classes = TestApplication.class)
@Disabled("依赖GridFS服务")
public class TestGridFSMediaStorage {

    @Inject
    private MediaStorage mediaStorage;

    @Inject
    private GridFsTemplate gridFsTemplate;

    @Inject
    private GridFsOperations gridFsOperations;

    @Test
    void test(){
        Assertions.assertNotNull(gridFsTemplate);
        Assertions.assertNotNull(gridFsOperations);
    }

    @Test
    void mediaStorageExists(){
        Assertions.assertNotNull(mediaStorage);
    }

    @Test
    void testUploadFile() throws IOException {
        String sourcePath = Objects.requireNonNull(TestGridFSRepository.class.getClassLoader().getResource("my_avatar.png")).getPath();
        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));
        MediaExtra mediaExtra = mediaStorage.uploadToStorage("my_avatar.png",new FileInputStream(sourcePath),digest);
        Assertions.assertNotNull(mediaExtra);
    }

    @Test
    void testDownloadFile() throws IOException {
        String sourcePath = Objects.requireNonNull(TestGridFSRepository.class.getClassLoader().getResource("my_avatar.png")).getPath();
        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));
        MediaExtra mediaExtra = mediaStorage.uploadToStorage("my_avatar.png",new FileInputStream(sourcePath),digest);
        Assertions.assertNotNull(mediaExtra);

        InputStream inputStream = mediaStorage.downloadFromStorage(mediaExtra);
        Assertions.assertNotNull(inputStream);
        inputStream.close();
    }
}
