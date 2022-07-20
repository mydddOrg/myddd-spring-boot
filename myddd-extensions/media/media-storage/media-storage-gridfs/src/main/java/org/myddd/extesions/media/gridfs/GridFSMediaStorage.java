package org.myddd.extesions.media.gridfs;

import com.google.gson.Gson;
import org.myddd.extesions.media.MediaNotFoundException;
import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Named
public class GridFSMediaStorage extends MediaStorage {

    @Inject
    private GridFSRepository gridFSRepository;

    @Override
    public MediaExtra uploadToStorage(String fileName, InputStream inputStream, String digest) {
        var mediaId= gridFSRepository.uploadToStorage(fileName,inputStream);
        return new GridFSMediaExtra(mediaId,digest);
    }

    @Override
    public InputStream downloadFromStorage(MediaExtra extra) {
        GridFSMediaExtra gridFSMediaExtra = (GridFSMediaExtra)extra;
        Path destPath = Path.of(this.storagePath + File.separator + gridFSMediaExtra.getFileId());
        try {
            if(!Files.exists(destPath)){
                try(InputStream inputStream = gridFSRepository.downloadFromStorage(gridFSMediaExtra.getFileId())){
                    if(!Files.exists(destPath.getParent())){
                        Files.createDirectories(destPath.getParent());
                    }
                    Files.copy(inputStream,destPath);
                }
            }

            return Files.newInputStream(destPath);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(),e);
            throw new MediaNotFoundException();
        }
    }

    @Override
    public MediaExtra loadMediaExtra(String data) {
        return new Gson().fromJson(data,GridFSMediaExtra.class);
    }

    @Override
    public String mediaExtraToString(MediaExtra extra) {
        return new Gson().toJson(extra);
    }
}
