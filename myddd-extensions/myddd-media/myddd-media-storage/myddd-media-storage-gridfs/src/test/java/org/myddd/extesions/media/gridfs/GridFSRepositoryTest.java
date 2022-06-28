package org.myddd.extesions.media.gridfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SpringBootTest(classes = ApplicationTest.class)
@Disabled("依赖GridFS服务")
public class GridFSRepositoryTest {

    @Inject
    private GridFSRepository gridFSRepository;

    @Test
    void testRepositoryExists(){
        Assertions.assertNotNull(gridFSRepository);
    }

    @Test
    void testUploadFile() throws FileNotFoundException {
        String sourcePath = Objects.requireNonNull(GridFSRepositoryTest.class.getClassLoader().getResource("my_avatar.png")).getPath();
        var mediaId = gridFSRepository.uploadToStorage("my_avatar.png",new FileInputStream(sourcePath));
        Assertions.assertNotNull(mediaId);
    }

    @Test
    void testDownloadFile() throws IOException {
        String sourcePath = Objects.requireNonNull(GridFSRepositoryTest.class.getClassLoader().getResource("my_avatar.png")).getPath();
        var mediaId = gridFSRepository.uploadToStorage("my_avatar.png",new FileInputStream(sourcePath));
        Assertions.assertNotNull(mediaId);

        InputStream inputStream = gridFSRepository.downloadFromStorage(mediaId);
        Assertions.assertNotNull(inputStream);
        inputStream.close();
    }
}
