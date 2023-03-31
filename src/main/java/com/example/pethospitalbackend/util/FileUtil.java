package com.example.pethospitalbackend.util;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FileUtil {
    @Value("${azure.storage.account-name}")
    private static String ACCOUNT_NAME;
    @Value("${azure.storage.account-key}")
    private static String ACCOUNT_KEY;
    @Value("${azure.storage.endpoint}")
    private static String END_POINT;
    @Value("${azure.storage.container-name}")
    private static String CONTAINER_NAME;

    private static final Set<String> allowImageType = new HashSet<String>(){{
        add("jpg");
        add("png");
        add("jpeg");
        add("bmp");
        add("JPG");
        add("PNG");
        add("BMP");
        add("JPEG");
    }};

    private static final Set<String> allowVideoType = new HashSet<String>(){{
        add("avi");
        add("mp4");
        add("wmv");
        add("mpeg");
        add("m4v");
        add("AVI");
        add("MP4");
        add("WMV");
        add("MPEG");
        add("M4V");
    }};

    private static CloudBlobContainer container = null;

    static {
        String PROTOCOL = "https";
        String format = "DefaultEndpointsProtocol={0};AccountName={1};AccountKey={2};EndpointSuffix={3}";
        CloudStorageAccount storageAccount = null;
        CloudBlobClient blobClient = null;
        try {
            // 获得StorageAccount对象
            storageAccount = CloudStorageAccount.parse(
                    MessageFormat.format(format, PROTOCOL, ACCOUNT_NAME, ACCOUNT_KEY, END_POINT));
            // 由StorageAccount对象创建BlobClient
            blobClient = storageAccount.createCloudBlobClient();
            // 根据传入的containerName, 获得container实例
            container = blobClient.getContainerReference(CONTAINER_NAME);
        } catch (URISyntaxException | InvalidKeyException | StorageException e) {
            e.printStackTrace();
        }
    }


    public static String upload(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString();
            CloudBlockBlob blob = container.getBlockBlobReference(fileName);
            // 上传到Azure Container
            InputStream inputStream = file.getInputStream();
            System.out.println(inputStream.available());
            blob.upload(inputStream, inputStream.available());
            // 上传后的文件大小
            // todo:校验上传完成
            return fileName;
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static boolean isImage(MultipartFile multipartFile) {
        String fileSuffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        return allowImageType.contains(fileSuffix);
    }

    public static boolean isVideo(MultipartFile multipartFile) {
        String fileSuffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        return allowVideoType.contains(fileSuffix);
    }

}
