package org.myddd.extesions.media.qcloud;

import com.google.gson.Gson;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.myddd.extesions.media.MediaUploadFailedException;
import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaStorage;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


@Named
public class QCloudMediaStorage extends MediaStorage {

    @Value("${media.qcloud.region}")
    private final String region = "";

    @Value("${media.qcloud.bucketName}")
    private String bucketName;

    @Value("${media.qcloud.secretId}")
    private String secretId;

    @Value("${media.qcloud.secretKey}")
    private String secretKey;

    private COSClient cosClient;

    private COSClient getCosClient(){
        if(Objects.isNull(cosClient)){
            BasicCOSCredentials basicCOSCredentials = new BasicCOSCredentials(secretId,secretKey);
            Region region = new Region(this.region);
            ClientConfig clientConfig =new ClientConfig(region);
            clientConfig.setHttpProtocol( HttpProtocol.https);
            cosClient = new COSClient(basicCOSCredentials,clientConfig);
        }
        return cosClient;
    }


    @Override
    public MediaExtra uploadToStorage(String fileName, InputStream inputStream, String digest) {
        try(inputStream){
            String filePath = currentDateFilePath(digest);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream,metadata);
            PutObjectResult putObjectResult = getCosClient().putObject(putObjectRequest);
            if(Objects.isNull(putObjectResult)){
                throw new MediaUploadFailedException(fileName);
            }
            return new QCloudMediaExtra(filePath);
        }catch (IOException e){
            logger.error(e.getLocalizedMessage(),e);
            throw new MediaUploadFailedException(fileName);
        }
    }

    @Override
    public InputStream downloadFromStorage(MediaExtra extra) {
        try{
            QCloudMediaExtra qCloudMediaExtra = (QCloudMediaExtra)extra;
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, qCloudMediaExtra.getFilePath());
            COSObject cosObject = getCosClient().getObject(getObjectRequest);

            Path destPath = Path.of(this.storagePath + File.separator + qCloudMediaExtra.getFilePath());
            if(!Files.exists(destPath)){
                if(!Files.exists(destPath.getParent())){
                    Files.createDirectories(destPath.getParent());
                }
                Files.copy(cosObject.getObjectContent(),destPath);
            }

            return Files.newInputStream(destPath);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(),e);
            return null;
        }
    }

    @Override
    public MediaExtra loadMediaExtra(String data) {
        return new Gson().fromJson(data,QCloudMediaExtra.class);
    }

    @Override
    public String mediaExtraToString(MediaExtra extra) {
        return new Gson().toJson(extra);
    }
}
