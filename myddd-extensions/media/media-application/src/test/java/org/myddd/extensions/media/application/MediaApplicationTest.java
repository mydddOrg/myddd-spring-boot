package org.myddd.extensions.media.application;

import com.google.protobuf.ByteString;
import com.google.protobuf.StringValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.media.api.MediaApplication;
import org.myddd.extensions.media.api.MediaByte;
import org.myddd.extensions.media.api.MediaDTO;
import org.myddd.extesions.media.MediaNotFoundException;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
@SpringBootApplication
@ComponentScan(basePackages = {"org.myddd"})
@EntityScan(basePackages = {"org.myddd"})
class MediaApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    @Inject
    private MediaApplication mediaApplication;

    @Test
    void testInstanceExists(){
        Assertions.assertNotNull(mediaApplication);
    }

    @Test
    void testCreateMedia() {
        MediaDTO mediaDTO = createMedia();
        Assertions.assertNotNull(mediaDTO);
    }

    @Test
    void queryMedia() {
        MediaDTO mediaDTO = createMedia();
        MediaByte mediaByte = mediaApplication.queryMedia(StringValue.of(mediaDTO.getMediaId()));
        Assertions.assertNotNull(mediaByte);
    }


    @Test
    void mediaNotExists(){
        var randomMediaId = StringValue.of(UUID.randomUUID().toString());
        Assertions.assertThrows(MediaNotFoundException.class,()->mediaApplication.queryMedia(randomMediaId));
    }

    @Test
    void queryMediaByDigest(){
        var optionalMediaDTO = mediaApplication.queryMediaIdByDigest(StringValue.of(randomUUIDString()));
        Assertions.assertFalse(optionalMediaDTO.hasMedia());

        var digest = randomUUIDString();
        createMedia(digest);
        optionalMediaDTO = mediaApplication.queryMediaIdByDigest(StringValue.of(digest));
        Assertions.assertTrue(optionalMediaDTO.hasMedia());
    }

    private String randomUUIDString(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    private MediaDTO createMedia(){
        return createMedia(randomUUIDString());
    }

    private MediaDTO createMedia(String digest){
        try {
            String sourcePath = MediaApplicationTest.class.getClassLoader().getResource("my_avatar.png").getPath();
            MediaByte mediaByte = MediaByte.newBuilder().setContent(ByteString.readFrom(new FileInputStream(sourcePath)))
                    .setName("my_avatar.png")
                    .setDigest(digest)
                    .setSize(new File(sourcePath).length())
                    .build();

            return mediaApplication.createMedia(mediaByte);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}