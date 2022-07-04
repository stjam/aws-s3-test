package com.test.image.catalog.model;

import lombok.Getter;

@Getter
public class ImageNotFoundException extends RuntimeException {

    private final Long code;

    private final String message;

    public ImageNotFoundException(final Long code, final String message) {
        this.code = code;
        this.message = message;
    }
}
