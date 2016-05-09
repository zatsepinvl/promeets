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
import ru.unc6.promeets.model.service.entity.FileService;
import ru.unc6.promeets.security.CurrentUser;

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
    private long maxSize;

    @Value("${file-max-size-error-message}")
    private String maxSizeExceptionMessage;

    @Value("${file-upload-invalid-formats}")
    private String invalidFileFormats;

    @Value("${file-upload-invalid-format-error-message}")
    private String invalidFileFormatsErrorMessage;

    @Value("${file-upload-default-image-size}")
    private int defaultImageSize;

    @Autowired
    private FileService fileService;


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
    public File uploadFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("id") long fileId,
                           @RequestParam(value = "size", required = false) Integer size,
                           @CurrentUser User user) {
        checkIsFileAvailable(file);
        try {
            return fileService.updateByUploading(file, fileId, size == null ? defaultImageSize : size, user);
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new BaseControllerException()
                    .setResponseErrorMessage(new ResponseErrorMessage(ex.getMessage()))
                    .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException ex) {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
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
