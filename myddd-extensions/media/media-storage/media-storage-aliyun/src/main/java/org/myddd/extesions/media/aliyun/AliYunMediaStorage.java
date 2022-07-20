package org.myddd.extesions.media.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.google.gson.Gson;
import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Named
public class AliYunMediaStorage extends MediaStorage {

    private final String storagePath = System.getProperty("java.io.tmpdir") + File.separator + "STORAGE";

    public AliYunMediaStorage(){
        createDirs(storagePath);
    }

    @Value("${media.aliyun.endpoint}")
    private String endpoint;

    @Value("${media.aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${media.aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Value("${media.aliyun.bucketName}")
    private String bucketName;

    @Override
    public MediaExtra uploadToStorage(String fileName, InputStream inputStream, String digest) {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String filePath = currentDateFilePath(digest);
        try{
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
            }
            ossClient.putObject(bucketName, currentDateFilePath(digest), inputStream);
        } catch (Exception oe) {
            logger.error(oe.getLocalizedMessage(),oe);
        } finally {
            ossClient.shutdown();
        }

        return new AliYunMediaExtra((filePath));
    }

    @Override
    public InputStream downloadFromStorage(MediaExtra extra) {
        AliYunMediaExtra aliYunMediaExtra = (AliYunMediaExtra)extra;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            OSSObject ossObject = ossClient.getObject(bucketName, aliYunMediaExtra.getFilePath());
            Path destPath = Path.of(this.storagePath + File.separator + aliYunMediaExtra.getFilePath());
            if(!Files.exists(destPath)){
                if(!Files.exists(destPath.getParent())){
                    Files.createDirectories(destPath.getParent());
                }
                Files.copy(ossObject.getObjectContent(),destPath);
            }

            return Files.newInputStream(destPath);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(),e);
        }finally {
            ossClient.shutdown();
        }
        return null;
    }

    public MediaExtra loadMediaExtra(String data) {
        return new Gson().fromJson(data,AliYunMediaExtra.class);
    }

    public String mediaExtraToString(MediaExtra extra) {
        return new Gson().toJson(extra);
    }
}
