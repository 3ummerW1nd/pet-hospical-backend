package com.example.pethospitalbackend.util;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobInputStream;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.MessageFormat;
import java.util.UUID;

public class FileUtil {
    private static final String ACCOUNT_NAME = "pethospitalresources";
    private static final String ACCOUNT_KEY = "lipM7B7z2eRwa4x8I1WVjAA6vsthMm/Mz1oDyC+BSOYugJ+2gy0FRIyy86C/8TmEG6SYIev1uT/m+AStXwcPnA==";
    private static final String END_POINT = "core.windows.net";
    private static final String CONTAINER_NAME = "pethospicalfiles";

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

    public static BlobInputStream download(String blobPath) {
        try {
            CloudBlockBlob blob = container.getBlockBlobReference(blobPath);
            return blob.openInputStream();
        } catch (URISyntaxException | StorageException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String upload(File file) {
        try {
            String fileName = UUID.randomUUID().toString();
            CloudBlockBlob blob = container.getBlockBlobReference(fileName);
            // 上传到Azure Container
            blob.uploadFromFile(file.getPath());
            // 上传后的文件大小
            // todo:校验上传完成
            return fileName;
        } catch (URISyntaxException | StorageException | IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
