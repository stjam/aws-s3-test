package com.test.profile.service.ProfileService.service;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

public interface ImageService {

    S3ObjectInputStream getImage(String predefinedTypeName,
                                 String dummySeoName,
                                 String reference);

    void flushImageInS3(String predefinedTypeName, String reference);

}
