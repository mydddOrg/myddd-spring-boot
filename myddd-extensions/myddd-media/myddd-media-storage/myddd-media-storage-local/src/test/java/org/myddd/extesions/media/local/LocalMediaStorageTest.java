package org.myddd.extesions.media.local;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.domain.InstanceFactory;
import org.myddd.extesions.media.MediaNotFoundException;
import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.myddd.test.TestApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
class LocalMediaStorageTest {

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    private static MediaStorage mediaStorage;

    private static MediaStorage getMediaStorage(){
        if(Objects.isNull(mediaStorage)){
            mediaStorage = InstanceFactory.getInstance(MediaStorage.class);
        }
        return mediaStorage;
    }

    @Test
    void testMediaStorage(){
        Assertions.assertNotNull(getMediaStorage());
    }

    @Test
    void testMediaExtraToString(){
        MediaExtra mediaExtra = new LocalMediaExtra(UUID.randomUUID().toString(),UUID.randomUUID().toString());
        String extraString = getMediaStorage().mediaExtraToString(mediaExtra);
        Assertions.assertNotNull(extraString);
    }


    @Test
    void testStringToMediaExtra(){
        String extra = "{path:\"aA\"}";
        MediaExtra mediaExtra = getMediaStorage().loadMediaExtra(extra);
        Assertions.assertNotNull(mediaExtra);
    }

    @Test
    void testUploadToStorage() throws IOException {
        String sourcePath = Objects.requireNonNull(LocalMediaStorageTest.class.getClassLoader().getResource("my_avatar.png")).getPath();

        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));
        MediaExtra mediaExtra = getMediaStorage().uploadToStorage("my_avatar.png",new FileInputStream(sourcePath),digest);
        Assertions.assertNotNull(mediaExtra);
    }

    @Test
    void testDownloadFileFromStorage() throws IOException {
        String sourcePath = Objects.requireNonNull(LocalMediaStorageTest.class.getClassLoader().getResource("my_avatar.png")).getPath();
        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));
        MediaExtra mediaExtra = getMediaStorage().uploadToStorage("my_avatar.png",new FileInputStream(sourcePath),digest);
        Assertions.assertNotNull(mediaExtra);

        InputStream downloadFile = getMediaStorage().downloadFromStorage(mediaExtra);
        Assertions.assertNotNull(downloadFile);


        Assertions.assertThrows(MediaNotFoundException.class,() -> {
           getMediaStorage().downloadFromStorage(new LocalMediaExtra(UUID.randomUUID().toString(),UUID.randomUUID().toString()));
        });
    }

}
