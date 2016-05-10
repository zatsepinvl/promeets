package ru.unc6.promeets.model.service.entity;

import org.springframework.web.multipart.MultipartFile;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vladimir on 21.04.2016.
 */
public interface FileService extends BaseService<File, Long> {
    File updateByUploading(MultipartFile multipartFile, long fileId, User user) throws IOException, NoSuchAlgorithmException;
    File getDefaultUserImage();
}
