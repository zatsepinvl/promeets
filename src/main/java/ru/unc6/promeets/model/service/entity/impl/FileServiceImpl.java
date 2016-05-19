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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vladimir on 21.04.2016.
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<File, Long>
        implements FileService {

    private static final Logger log = Logger.getLogger(FileService.class);

    @Value("${file-hash-algorithm}")
    private String ALGORITHM;

    @Value("${file-upload-real-folder}")
    private String UPLOAD_REAL_FOLDER;

    @Value("${file-upload-host-folder}")
    private String UPLOAD_HOST_FOLDER;

    @Value("${file-upload-image-extensions}")
    private String UPLOAD_IMAGE_EXTENSIONS;

    @Value("${file-upload-image-width-small}")
    private int UPLOAD_IMAGE_SMALL_WIDTH;

    @Value("${file-upload-image-width-medium}")
    private int UPLOAD_IMAGE_MEDIUM_WIDTH;

    @Value("${file-upload-image-width-large}")
    private int UPLOAD_IMAGE_LARGE_WIDTH;

    @Value("${default-user-image-url}")
    private String DEFAULT_USER_IMAGE_URL;

    @Value("${default-user-image-min-url}")
    private String DEFAULT_USER_IMAGE_MIN_URL;

    @Value("${default-user-image-name}")
    private String DEFAULT_USER_IMAGE_NAME;

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
        User user = userService.getCurrentAuthenticatedUser();
        updateIfUserImage(entity, user);
        return super.update(entity);
    }

    @Override
    public File updateByUploading(MultipartFile multipartFile, long fileId, User user) throws IOException, NullPointerException, NoSuchAlgorithmException {
        ru.unc6.promeets.model.entity.File entityFile = getById(fileId);
        if (entityFile == null) {
            throw new NullPointerException();
        }
        //save original file
        String originalFileName = saveFile(multipartFile.getBytes());
        entityFile.setOriginal(UPLOAD_HOST_FOLDER + "/" + originalFileName);

        //check is file Image and resize if it's true
        if (isImage(multipartFile.getOriginalFilename())) {
            String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            java.util.List<Thread> threads = new ArrayList<>();
            threads.add(resizeImageAsync(entityFile, multipartFile, File.ImageSize.SMALL, UPLOAD_IMAGE_SMALL_WIDTH, fileExtension));
            threads.add(resizeImageAsync(entityFile, multipartFile, File.ImageSize.MEDIUM, UPLOAD_IMAGE_MEDIUM_WIDTH, fileExtension));
            threads.add(resizeImageAsync(entityFile, multipartFile, File.ImageSize.LARGE, UPLOAD_IMAGE_LARGE_WIDTH, fileExtension));
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        } else {
            entityFile.setSmall(entityFile.getOriginal());
            entityFile.setMedium(entityFile.getOriginal());
            entityFile.setLarge(entityFile.getOriginal());
        }
        entityFile.setName(multipartFile.getOriginalFilename());
        entityFile.setTime(System.currentTimeMillis());
        File newFile = update(entityFile);
        saveUserFile(newFile, user);
        updateIfUserImage(newFile, user);
        return newFile;
    }

    @Override
    public File getDefaultUserImage() {
        File image = new File();
        setDefaultUserImage(image);
        return image;
    }

    private void setDefaultUserImage(File image) {
        image.setName(DEFAULT_USER_IMAGE_NAME);
        image.setSmall(DEFAULT_USER_IMAGE_MIN_URL);
        image.setMedium(DEFAULT_USER_IMAGE_MIN_URL);
        image.setLarge(DEFAULT_USER_IMAGE_URL);
        image.setOriginal(DEFAULT_USER_IMAGE_URL);
    }


    private void updateIfUserImage(File file, User user) {
        if (file.getFileId() == user.getImage().getFileId()) {
           /* if (file.getOriginal() == null) {
                setDefaultUserImage(file);
            }*/
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

    private Thread resizeImageAsync(File entityFile, MultipartFile multipartFile, File.ImageSize size, int sizeValue, String fileExtension) {
        return new Thread(() -> {
            String imageName = null;
            try {
                imageName = saveFile(
                        resizeImage(
                                multipartFile.getInputStream(),
                                sizeValue,
                                fileExtension
                        )
                );
            } catch (NoSuchAlgorithmException | IOException e) {
                log.error(e.getMessage(), e);
            }
            entityFile.setUrlByImageSize(size, UPLOAD_HOST_FOLDER + "/" + imageName);
        });
    }

    private byte[] resizeImage(InputStream inputStream, int width, String fileExtension) throws IOException {
        ByteArrayOutputStream baos = null;
        byte[] imageInByte = null;
        try {
            BufferedImage originalImage = ImageIO.read(inputStream);
            int height = (int) ((double) originalImage.getHeight() * ((double) width / (double) originalImage.getWidth()));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = new BufferedImage(width, height, type);
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            //below three lines are for RenderingHints for better image quality at cost of higher processing time
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        String fileName = convertByteArrayToHexString(digest.digest(bytes));

        //Check does UPLOAD_REAL_FOLDER exist and create if it doesn't
        Path path = Paths.get(servletContext.getRealPath(UPLOAD_REAL_FOLDER));
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        java.io.File uploadedFile = new java.io.File(servletContext.getRealPath(UPLOAD_REAL_FOLDER) + "/" + fileName);
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
        return UPLOAD_IMAGE_EXTENSIONS.contains(FilenameUtils.getExtension(fileName));
    }


}
