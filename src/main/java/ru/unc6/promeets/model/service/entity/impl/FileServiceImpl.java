package ru.unc6.promeets.model.service.entity.impl;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserFile;
import ru.unc6.promeets.model.repository.FileRepository;
import ru.unc6.promeets.model.service.entity.FileService;
import ru.unc6.promeets.model.service.entity.UserFileService;
import ru.unc6.promeets.model.service.entity.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Vladimir on 21.04.2016.
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<File, Long>
        implements FileService {

    private static final Logger log = Logger.getLogger(FileService.class);

    @Value("${file-hash-algorithm}")
    private String algorithm;

    @Value("${file-upload-real-folder}")
    private String uploadRealFolder;

    @Value("${file-upload-host-folder}")
    private String uploadHostFolder;

    @Value("${file-upload-image-extensions}")
    private String uploadImageExtensions;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private UserService userService;

    @Autowired
    public FileServiceImpl(FileRepository repository) {
        super(repository);
    }

    @Override
    public File update(File entity) {
        entity = super.update(entity);
        User user = userService.getCurrentAuthenticatedUser();
        updateIfUserImage(entity, user);
        return entity;
    }

    @Override
    public File updateByUploading(MultipartFile multipartFile, long fileId, int size, User user) throws IOException, NullPointerException, NoSuchAlgorithmException {
        ru.unc6.promeets.model.entity.File entityFile = getById(fileId);
        if (entityFile == null) {
            throw new NullPointerException();
        }
        //save original file
        String originalFileName = saveFile(multipartFile.getBytes());
        entityFile.setOriginalUrl(uploadHostFolder + "/" + originalFileName);

        //check is file Image and resize if it's true
        if (isImage(multipartFile.getOriginalFilename())) {
            String resizedFileName = saveFile(
                    resizeImage(
                            multipartFile.getInputStream(),
                            size,
                            FilenameUtils.getExtension(
                                    multipartFile.getOriginalFilename()
                            )
                    )
            );
            entityFile.setUrl(uploadHostFolder + "/" + resizedFileName);
        } else {
            entityFile.setUrl(entityFile.getOriginalUrl());
        }
        entityFile.setName(multipartFile.getOriginalFilename());
        File newFile = update(entityFile);
        saveUserFile(newFile, user);
        updateIfUserImage(newFile, user);
        return newFile;
    }

    private void updateIfUserImage(File file, User user) {
        if (file.getFileId() == user.getImage().getFileId()) {
            user.setImage(file);
            userService.updateCurrentAuthenticatedUser(user);
        }
    }

    private void saveUserFile(File file, User user) {
        File newFile = file.clone();
        newFile = create(newFile);
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

    private byte[] resizeImage(InputStream inputStream, int size, String fileExtension) throws IOException {
        ByteArrayOutputStream baos = null;
        byte[] imageInByte = null;
        try {
            BufferedImage originalImage = ImageIO.read(inputStream);
            int width = size;
            int height = (int) ((double) originalImage.getHeight() * ((double) width / (double) originalImage.getWidth()));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = new BufferedImage(width, height, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
            baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, fileExtension, baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return imageInByte;
    }

    private String saveFile(byte[] bytes) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        String fileName = convertByteArrayToHexString(digest.digest(bytes));
        java.io.File uploadedFile = new java.io.File(servletContext.getRealPath(uploadRealFolder) + "/" + fileName);
        BufferedOutputStream stream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(uploadedFile);
            stream = new BufferedOutputStream(fileOutputStream);
            stream.write(bytes);
            return fileName;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    private boolean isImage(String fileName) {
        return uploadImageExtensions.contains(FilenameUtils.getExtension(fileName));
    }

}
