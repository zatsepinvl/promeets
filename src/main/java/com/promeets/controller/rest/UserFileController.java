package com.promeets.controller.rest;

import com.promeets.model.entity.User;
import com.promeets.model.entity.UserFile;
import com.promeets.model.entity.UserFilePK;
import com.promeets.model.service.entity.FileService;
import com.promeets.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.promeets.model.entity.File;
import com.promeets.model.service.entity.UserFileService;

import java.util.List;

/**
 * Created by Vladimir on 04.05.2016.
 */
@RestController
@RequestMapping("/api/users/files")
public class UserFileController extends BaseUserRestController<UserFile, UserFilePK, File> {

    private UserFileService userFileService;
    private FileService fileService;

    @Autowired
    public UserFileController(FileService entityService, UserFileService userEntityService) {
        super(entityService, userEntityService);
        this.userFileService = userEntityService;
        this.fileService = entityService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<File> getAllFilesByUser(@CurrentUser User user) {
        return userFileService.getFilesByUserId(user.getUserId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserFileByFileId(@PathVariable("id") long fileId, @CurrentUser User user) {
        userFileService.deleteUserFileByUserIdAndFileId(user.getUserId(), fileId);
    }

    @Override
    protected void checkHasAccess(UserFile entity, User user) {

    }

    @Override
    protected void checkHasOwnerAccess(UserFile entity, User user) {

    }
}
