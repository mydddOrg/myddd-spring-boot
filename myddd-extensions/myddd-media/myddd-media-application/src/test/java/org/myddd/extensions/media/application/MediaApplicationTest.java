package org.myddd.extensions.media.application;

import com.google.protobuf.ByteString;
import com.google.protobuf.StringValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.media.TestApplication;
import org.myddd.extensions.media.api.MediaApplication;
import org.myddd.extensions.media.api.MediaByte;
import org.myddd.extensions.media.api.MediaDTO;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@SpringBootTest(classes = TestApplication.class)
public class MediaApplicationTest {

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
    void testCreateMedia() throws IOException {
        MediaDTO mediaDTO = createMedia();
        Assertions.assertNotNull(mediaDTO);
    }

    @Test
    void testQueryMedia() throws IOException{
        MediaDTO mediaDTO = createMedia();
        MediaByte mediaByte = mediaApplication.queryMedia(StringValue.of(mediaDTO.getMediaId()));
        Assertions.assertNotNull(mediaByte);
    }

    private MediaDTO createMedia() throws IOException{
        String sourcePath = MediaApplicationTest.class.getClassLoader().getResource("my_avatar.png").getPath();
        MediaByte mediaByte = MediaByte.newBuilder().setContent(ByteString.readFrom(new FileInputStream(sourcePath)))
                .setName("my_avatar.png")
                .setSize(new File(sourcePath).length())
                .build();

        return mediaApplication.createMedia(mediaByte);
    }

    @Test
    void testMediaNotExists(){
        try{
            mediaApplication.queryMedia(StringValue.of(UUID.randomUUID().toString()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}