package org.myddd.extesions.media.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public abstract class MediaStorage {

    protected static Logger logger = LoggerFactory.getLogger(MediaStorage.class);

    protected String storagePath = System.getProperty("java.io.tmpdir")  + File.separator + "STORAGE";

    public abstract MediaExtra uploadToStorage(String fileName,InputStream inputStream,String digest);

    public abstract InputStream downloadFromStorage(MediaExtra extra);

    public abstract MediaExtra loadMediaExtra(String data);

    public abstract String mediaExtraToString(MediaExtra extra);

    protected MediaStorage(){
        createDirs(storagePath);
    }

    protected String currentDateFilePath(String digest){
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() + File.separator + now.getMonthValue() + File.separator + now.getDayOfMonth()  + File.separator + digest;
    }

    protected void createDirs(String path){
        try{
            Path of = Path.of(path);
            if(!Files.exists(of)) Files.createDirectories(of);
        }catch (IOException e){
            logger.error(e.getLocalizedMessage(),e);
        }
    }
}
