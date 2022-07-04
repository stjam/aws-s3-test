package com.test.profile.service.ProfileService.configuration;

public class EndpointConstants {

    public final static String BASE_PATH = "/api/v1";

    public final static String IMAGE_ENDPOINT = BASE_PATH + "/image/";
    public final static String SHOW_IMAGE_ENDPOINT = IMAGE_ENDPOINT+"/show/{predefinedTypeName}/{dummySeoName}";
    public final static String FLUSH_IMAGE_ENDPOINT = IMAGE_ENDPOINT+"/flush/{predefinedTypeName}/{dummySeoName}";

}
