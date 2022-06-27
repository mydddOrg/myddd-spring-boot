package org.myddd.extesions.media.local;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.myddd.extesions.media.MediaNotFoundException;
import org.myddd.extesions.media.MediaUploadFailedException;
import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;

import javax.inject.Named;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Named
public class LocalMediaStorage extends MediaStorage {

    private final String storagePath = System.getProperty("java.io.tmpdir") + File.separator + "STORAGE";

    public LocalMediaStorage(){
        createDirs(storagePath);
    }

    @Override
    public MediaExtra uploadToStorage(String fileName, InputStream inputStream, String digest) {
        String destinationFilePath = storagePath + File.separator +  currentDateFilePath(digest) + File.separator + digest;
        File destinationFile = new File(destinationFilePath);
        if(!destinationFile.exists()){
            try {
                var success = destinationFile.getParentFile().mkdirs();
                Preconditions.checkArgument(success);

                Files.copy(inputStream,Path.of(destinationFilePath));
            }catch (IOException e){
                logger.error(e.getLocalizedMessage(),e);
                throw new MediaUploadFailedException();
            }
        }
        return new LocalMediaExtra(digest,destinationFilePath);
    }

    @Override
    public InputStream downloadFromStorage(MediaExtra extra) {
        LocalMediaExtra localMediaExtra = (LocalMediaExtra)extra;
        try {
            return new FileInputStream(localMediaExtra.destPath());
        } catch (FileNotFoundException e) {
            throw new MediaNotFoundException();
        }
    }

    @Override
    public MediaExtra loadMediaExtra(String data) {
        return new Gson().fromJson(data,LocalMediaExtra.class);
    }

    @Override
    public String mediaExtraToString(MediaExtra extra) {
        return new Gson().toJson(extra);
    }
}
