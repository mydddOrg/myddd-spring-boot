package org.myddd.extesions.media.repository;

import org.myddd.extesions.media.domain.Media;
import org.myddd.extesions.media.domain.MediaRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

class TestMediaRepository extends AbstractIntegrationTest{

    @Inject
    private MediaRepository mediaRepository;

    @Test
    void assertMediaRepositoryExists(){
        Assertions.assertNotNull(mediaRepository);
    }

    @Test
    @Transactional
    void testCreateMedia() throws IOException {
        String sourcePath = Objects.requireNonNull(TestMediaRepository.class.getClassLoader().getResource("my_avatar.png")).getPath();
        FileInputStream fileInputStream = new FileInputStream(sourcePath);
        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));

        Media created = Media.createMediaFromInput(fileInputStream,new File(sourcePath).length(),"my_avatar.png",digest);
        Assertions.assertNotNull(created);

        Media query = mediaRepository.queryMediaByMediaId(created.getMediaId());
        Assertions.assertNotNull(query);

        Media notExists = mediaRepository.queryMediaByMediaId(UUID.randomUUID().toString());
        Assertions.assertNull(notExists);
    }

    @Test
    @Transactional
    void testQueryMediaByDigest() throws IOException {
        String sourcePath = Objects.requireNonNull(TestMediaRepository.class.getClassLoader().getResource("my_avatar.png")).getPath();
        FileInputStream fileInputStream = new FileInputStream(sourcePath);
        String digest = DigestUtils.md5Hex(new FileInputStream(sourcePath));

        Media created = Media.createMediaFromInput(fileInputStream,new File(sourcePath).length(),"my_avatar.png",digest);
        Assertions.assertNotNull(created);

        Media query = mediaRepository.queryMediaByDigest(created.getDigest());
        Assertions.assertNotNull(query);

        Media notExists = mediaRepository.queryMediaByDigest(UUID.randomUUID().toString());
        Assertions.assertNull(notExists);
    }

}
