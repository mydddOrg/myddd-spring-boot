package org.myddd.extesions.media.domain;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.domain.InstanceFactory;
import org.myddd.extesions.media.ApplicationTest;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = ApplicationTest.class)
class MediaTest {

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    @Test
    @Transactional
    void testCreateMedia() {
        Media created = createMedia();
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getMediaId());
        Assertions.assertNotNull(created.getDigest());
        Assertions.assertNotNull(created.getName());
        Assertions.assertTrue(created.getSize() > 0);
        Assertions.assertNotNull(created.getExtra());
        Assertions.assertNotNull(created.getMediaInputStream());

        var createdAgain  = createMedia();
        Assertions.assertNotNull(createdAgain);
        Assertions.assertEquals(created,createdAgain);
    }

    @Test
    @Transactional
    void testQueryMediaByDigest(){
        Media created = createMedia();

        Media query = Media.queryMediaByDigest(created.getDigest());
        Assertions.assertNotNull(query);

        Media notExists = Media.queryMediaByDigest(UUID.randomUUID().toString());
        Assertions.assertNull(notExists);
    }

    @Test
    @Transactional
    void testQueryByMediaId(){
        var notExistMedia = Media.queryMediaByMediaId(UUID.randomUUID().toString());
        Assertions.assertNull(notExistMedia);

        var created = createMedia();
        var queryMedia = Media.queryMediaByMediaId(created.getMediaId());
        Assertions.assertNotNull(queryMedia);
    }

    private Media createMedia(){
        try{
            String sourcePath = Objects.requireNonNull(MediaTest.class.getClassLoader().getResource("my_avatar.png")).getPath();
            FileInputStream fileInputStream = new FileInputStream(sourcePath);
            String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));

            Media created = Media.createMediaFromInput(fileInputStream,new File(sourcePath).length(),"my_avatar.png",digest);
            Assertions.assertNotNull(created);
            return created;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
