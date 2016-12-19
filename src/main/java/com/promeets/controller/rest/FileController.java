package com.promeets.controller.rest;

import com.promeets.controller.exception.*;
import com.promeets.model.service.entity.FileService;
import com.promeets.security.CurrentUser;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.promeets.model.entity.File;
import com.promeets.model.entity.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vladimir on 20.04.2016.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Logger log = Logger.getLogger(FileController.class);

    @Value("${file-upload-max-size}")
    private long MAX_SIZE;

    @Value("${file-max-size-error-message}")
    private String MAX_SIZE_EXCEPTION_MESSAGE;

    @Value("${file-upload-invalid-formats}")
    private String INVALID_FILE_FORMATS;

    @Value("${file-upload-invalid-format-error-message}")
    private String INVALID_FILE_FORMATS_ERROR_MESSAGE;


    @Autowired
    private FileService fileService;


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteFile(@PathVariable("id") long fileId) {
        File file = fileService.getById(fileId);
        if (file != null) {
            file.setOriginal(null);
            file.setSmall(null);
            file.setMedium(null);
            file.setLarge(null);
            file.setName(null);
            fileService.update(file);
        } else {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public File updateFile(@PathVariable("id") long fileId, @RequestBody File file) {
        File temp = fileService.getById(fileId);
        if (temp != null) {
            if (file.getOriginal() == null) {
                file.setSmall(null);
                file.setMedium(null);
                file.setLarge(null);
            }
            return fileService.update(file);
        } else {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public File uploadFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("id") long fileId,
                           @CurrentUser User user) {
        checkIsFileAvailable(file);
        try {
            return fileService.updateByUploading(file, fileId, user);
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new BaseControllerException()
                    .setResponseErrorMessage(new ResponseErrorMessage(ex.getMessage()))
                    .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException ex) {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }

    private void checkIsFileAvailable(MultipartFile file) {
        if (file.getSize() > MAX_SIZE) {
            throw new BadRequestException()
                    .setResponseErrorMessage(new ResponseErrorMessage(MAX_SIZE_EXCEPTION_MESSAGE));
        }

        if (INVALID_FILE_FORMATS.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new BadRequestException()
                    .setResponseErrorMessage(new ResponseErrorMessage(INVALID_FILE_FORMATS_ERROR_MESSAGE));
        }
    }


}
