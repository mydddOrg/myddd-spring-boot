package org.myddd.extesions.media.gridfs;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SpringBootTest(classes = GridFSMediaStorageTest.class)

@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})

@Disabled("依赖GridFS服务")
public class GridFSMediaStorageTest {

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
        String sourcePath = Objects.requireNonNull(GridFSRepositoryTest.class.getClassLoader().getResource("my_avatar.png")).getPath();
        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));
        MediaExtra mediaExtra = mediaStorage.uploadToStorage("my_avatar.png",new FileInputStream(sourcePath),digest);
        Assertions.assertNotNull(mediaExtra);
    }

    @Test
    void testDownloadFile() throws IOException {
        String sourcePath = Objects.requireNonNull(GridFSRepositoryTest.class.getClassLoader().getResource("my_avatar.png")).getPath();
        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));
        MediaExtra mediaExtra = mediaStorage.uploadToStorage("my_avatar.png",new FileInputStream(sourcePath),digest);
        Assertions.assertNotNull(mediaExtra);

        InputStream inputStream = mediaStorage.downloadFromStorage(mediaExtra);
        Assertions.assertNotNull(inputStream);
        inputStream.close();
    }
}
