package org.myddd.media.aliyun;

import org.apache.commons.io.IOUtils;
import org.myddd.media.MediaAccess;
import org.myddd.media.MediaFile;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

public class AliyunMediaAccess implements MediaAccess {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    public static AliyunMediaAccess createInstance(String endpoint,String accessKeyId,String accessKeySecret,String bucketName){
        AliyunMediaAccess aliyunMediaAccess = new AliyunMediaAccess();
        aliyunMediaAccess.accessKeyId = accessKeyId;
        aliyunMediaAccess.endpoint = endpoint;
        aliyunMediaAccess.accessKeySecret = accessKeySecret;
        aliyunMediaAccess.bucketName = bucketName;
        return aliyunMediaAccess;
    }



    @Override
    public String saveMediaFile(byte[] content, String fileName) {

        String extensionName = getExtensionName(fileName);
        String fileNameNoEx = getFileNameNoEx(fileName);
        String withTimestampFileName = fileNameNoEx + "_" +System.currentTimeMillis()  + "."  + extensionName;
        String encodedString = null;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try{
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                ossClient.createBucket(bucketName);
            }

            InputStream is = new ByteArrayInputStream(content);
            ossClient.putObject(bucketName, withTimestampFileName, is);
            encodedString = Base64.getUrlEncoder().encodeToString(withTimestampFileName.getBytes());
        }catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return encodedString;
    }

    private String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    private String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    @Override
    public MediaFile getMediaFile(String mediaId) {

        byte[] decodedBytes = Base64.getUrlDecoder().decode(mediaId);
        String fullFileName = new String(decodedBytes);
        String fileName = fullFileName.substring(fullFileName.lastIndexOf("/")+1);


        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            OSSObject ossObject = ossClient.getObject(bucketName, fullFileName);
            InputStream inputStream = ossObject.getObjectContent();
            byte[] targetArray = IOUtils.toByteArray(inputStream);



            return new MediaFile(targetArray,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
        return null;
    }
}
