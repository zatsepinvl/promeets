package ru.unc6.promeets.controller.rest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.unc6.promeets.controller.exception.*;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserFile;
import ru.unc6.promeets.model.service.entity.FileService;
import ru.unc6.promeets.model.service.entity.UserFileService;
import ru.unc6.promeets.security.CurrentUser;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vladimir on 20.04.2016.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Logger log = Logger.getLogger(FileController.class);

    @Value("${file-hash-algorithm}")
    private String algorithm;

    @Value("${file-upload-real-folder}")
    private String uploadRealFolder;

    @Value("${file-upload-host-folder}")
    private String uploadHostFolder;

    @Value("${file-upload-max-size}")
    private long maxSize;

    @Value("${file-max-size-error-message}")
    private String maxSizeExceptionMessage;

    @Value("${file-upload-invalid-formats}")
    private String invalidFileFormats;

    @Value("${file-upload-invalid-format-error-message}")
    private String invalidFileFormatsErrorMessage;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserFileService userFileService;

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public File deleteFile(@PathVariable("id") long fileId) {
        File file = fileService.getById(fileId);
        if (file != null) {
            file.setUrl(null);
            file.setName(null);
            return fileService.update(file);
        } else {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public File updateFile(@PathVariable("id") long fileId, @RequestBody File file) {
        File temp = fileService.getById(fileId);
        if (temp != null) {
            return fileService.update(file);
        } else {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public File uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") long fileId, @CurrentUser User user) {
        checkIsFileAvailable(file);
        BufferedOutputStream stream = null;
        ru.unc6.promeets.model.entity.File entityFile = fileService.getById(fileId);
        if (entityFile == null) {
            throw new NotFoundException();
        }
        try {
            byte[] bytes = file.getBytes();
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            String fileName = convertByteArrayToHexString(digest.digest(bytes));
            java.io.File uploadedFile = new java.io.File(servletContext.getRealPath(uploadRealFolder) + "/" + fileName);
            uploadedFile.createNewFile();
            stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
            stream.write(bytes);
            fileName = uploadHostFolder + "/" + fileName;
            entityFile.setUrl(fileName);
            entityFile.setName(file.getOriginalFilename());
            File newFile = fileService.update(entityFile);
            saveUserFile(newFile, user);
            return newFile;
        } catch (NoSuchAlgorithmException | IOException ex) {
            throw new BaseControllerException()
                    .setResponseErrorMessage(new ResponseErrorMessage(ex.getMessage()))
                    .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }


    private void saveUserFile(File file, User user) {
        File newFile = new File();
        newFile.setUrl(file.getUrl());
        newFile.setName(file.getName());
        newFile = fileService.create(newFile);
        UserFile userFile = new UserFile();
        userFile.setFile(newFile);
        userFile.setUser(user);
        userFileService.create(userFile);
    }

    private String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (byte arrayByte : arrayBytes) {
            stringBuffer.append(Integer.toString((arrayByte & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }


    private void checkIsFileAvailable(MultipartFile file) {
        if (file.getSize() > maxSize) {
            throw new BadRequestException()
                    .setResponseErrorMessage(new ResponseErrorMessage(maxSizeExceptionMessage));
        }

        if (invalidFileFormats.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new BadRequestException()
                    .setResponseErrorMessage(new ResponseErrorMessage(invalidFileFormatsErrorMessage));
        }
    }
}
