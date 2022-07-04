package com.test.profile.service.ProfileService.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@Setter
public class Image {

    @Id
    public Long id;

    public String fileName;

    public String path;

    public UploadStatus status;

    public Long retry;

}
