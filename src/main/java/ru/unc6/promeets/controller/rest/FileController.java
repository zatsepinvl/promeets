package ru.unc6.promeets.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.unc6.promeets.controller.ResponseMessage;
import ru.unc6.promeets.controller.exception.BaseControllerException;
import ru.unc6.promeets.controller.exception.NotFoundException;
import ru.unc6.promeets.controller.exception.ResponseErrorMessage;
import ru.unc6.promeets.model.service.entity.FileService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by Vladimir on 20.04.2016.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Logger log = Logger.getLogger(FileController.class);

    private String algorithm;

    private String uploadRealFolder;

    private String uploadHostFolder;

    private long maxSize;

    @Resource(name = "appProperties")
    private Properties properties;

    @PostConstruct
    public void init() {
        algorithm = properties.getProperty("file-hash-algorithm");
        uploadRealFolder = properties.getProperty("file-upload-real-folder");
        uploadHostFolder = properties.getProperty("file-upload-host-folder");
        maxSize = Long.parseLong(properties.getProperty("file-upload-max-size"));
    }

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") long fileId) {
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
            File uploadedFile = new File(servletContext.getRealPath(uploadRealFolder) + "/" + fileName);
            uploadedFile.createNewFile();
            stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
            stream.write(bytes);
            fileName = uploadHostFolder + "/" + fileName;
            entityFile.setUrl(fileName);
            fileService.update(entityFile);
            return new ResponseMessage().setMessage(fileName);
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
            throw new NotFoundException();
        }
    }
}
