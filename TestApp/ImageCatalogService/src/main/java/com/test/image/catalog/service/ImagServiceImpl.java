package com.test.image.catalog.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.test.image.catalog.configuration.ServiceConfiguration;
import com.test.image.catalog.model.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImagServiceImpl implements ImageService {

    private final AmazonS3 amazonS3Client;

    private final ServiceConfiguration awsClientConfig;

    @Override
    public S3ObjectInputStream getImage(String predefinedTypeName, String dummySeoName, String reference) {
        log.info("Downloading file '{}' from bucket: '{}' ", reference, awsClientConfig.getBucketName());
        try {
            final S3Object s3Object = amazonS3Client.getObject(awsClientConfig.getBucketName(), reference);
            return s3Object.getObjectContent();
        } catch (AmazonS3Exception ex) {
            //Todo make call to server with images
            try {
                log.error("Object is not presented", ex);
                saveImageToS3bucket(predefinedTypeName, reference);
                final S3Object s3Object = amazonS3Client.getObject(awsClientConfig.getBucketName(), buildPathToS3Object(predefinedTypeName, reference));
                return s3Object.getObjectContent();
            } catch (IOException e) {
                log.error("Object is not presented", e);
            }

        }
        throw new ImageNotFoundException(4L, "Image not found in S3 ");
    }

    @Override
    public void flushImageInS3(String predefinedTypeName, String reference) {
        amazonS3Client.deleteObject(awsClientConfig.getBucketName(), buildPathToS3Object(predefinedTypeName, reference));
    }


    private void saveImageToS3bucket(String predefinedTypeName, String reference) throws IOException {
        InputStream resourceInputStream = new ClassPathResource(String.format("%s:data/%s", "classpath", reference)).getInputStream();
        byte[] contentToUpload = resourceInputStream.readAllBytes();
        InputStream inputStream = new ByteArrayInputStream(contentToUpload);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentToUpload.length);
        amazonS3Client.putObject(awsClientConfig.getBucketName(), buildPathToS3Object(predefinedTypeName, reference), inputStream, objectMetadata);
    }

    private String buildPathToS3Object(String predefinedTypeName, String reference) {
        String firstSubdirectory = (reference.length() > 4) ? reference.substring(0, 4) : reference;
        String secondSubdirectory = reference.length() >= 8 ? reference.substring(4, 8) : Strings.EMPTY;
        return String.format("/%s/%s/%s", predefinedTypeName, firstSubdirectory, secondSubdirectory);
    }
}
