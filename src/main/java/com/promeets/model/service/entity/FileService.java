package com.promeets.model.service.entity;

import org.springframework.web.multipart.MultipartFile;
import com.promeets.model.entity.File;
import com.promeets.model.entity.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vladimir on 21.04.2016.
 */
public interface FileService extends BaseService<File, Long> {
    File updateByUploading(MultipartFile multipartFile, long fileId, User user) throws IOException, NoSuchAlgorithmException;
    File getDefaultUserImage();
}
