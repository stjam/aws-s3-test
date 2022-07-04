package com.test.profile.service.ProfileService.controller;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.test.profile.service.ProfileService.model.ImageNotFoundException;
import com.test.profile.service.ProfileService.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Optional;

import static com.test.profile.service.ProfileService.configuration.EndpointConstants.*;

@RestController(BASE_PATH)
@RequiredArgsConstructor
public class ImageController {

    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    private final ImageService imageService;


    @RequestMapping(value = {SHOW_IMAGE_ENDPOINT}, method = RequestMethod.GET, produces = APPLICATION_OCTET_STREAM)
    public ResponseEntity<StreamingResponseBody> showImage(
            @PathVariable(value = "predefinedTypeName") Optional<String> predefinedTypeName,
            @PathVariable(value = "dummySeoName") Optional<String> dummySeoName,
            @RequestParam(value = "reference") Optional<String> reference) {

        String predefinedTypeNameValue = predefinedTypeName.orElseThrow(() -> new ImageNotFoundException(1L, "Predefined image type is not presented"));
        String dummySeoNameValue = dummySeoName.orElseThrow(() -> new ImageNotFoundException(2L, "Dummy seo image type is not presented"));
        String referenceValue = reference.orElseThrow(() -> new ImageNotFoundException(3L, "Reference image type is not presented"));
        return ResponseEntity.ok().header(CONTENT_DISPOSITION, referenceValue)
                .body(out -> {
                    S3ObjectInputStream inputStream = imageService.getImage(predefinedTypeNameValue, dummySeoNameValue, referenceValue);
                    IOUtils.copy(inputStream, out);
                });
    }

    @RequestMapping(value = {FLUSH_IMAGE_ENDPOINT}, method = RequestMethod.GET)
    public ResponseEntity<StreamingHttpOutputMessage> flushImage(
            @PathVariable(value = "predefinedTypeName") Optional<String> predefinedTypeName,
            @PathVariable(value = "dummySeoName") Optional<String> dummySeoName,
            @RequestParam(value = "reference") Optional<String> reference) {
        checkInputParameters(predefinedTypeName, dummySeoName, reference);
        imageService.flushImageInS3(predefinedTypeName.get(), reference.get());
        return ResponseEntity.noContent().build();
    }

    private void checkInputParameters(Optional<String> predefinedTypeName, Optional<String> dummySeoName, Optional<String> reference) {
        predefinedTypeName.orElseThrow(() -> new ImageNotFoundException(1L, "Predefined image type is not presented"));
        dummySeoName.orElseThrow(() -> new ImageNotFoundException(2L, "Dummy seo image type is not presented"));
        reference.orElseThrow(() -> new ImageNotFoundException(3L, "Reference image type is not presented"));
    }
}
