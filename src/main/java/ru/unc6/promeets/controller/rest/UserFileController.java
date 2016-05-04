package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserFile;
import ru.unc6.promeets.model.entity.UserFilePK;
import ru.unc6.promeets.model.service.entity.BaseService;
import ru.unc6.promeets.model.service.entity.FileService;
import ru.unc6.promeets.model.service.entity.UserFileService;
import ru.unc6.promeets.security.CurrentUser;

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

    @Override
    protected void checkHasAccess(UserFile entity, User user) {

    }

    @Override
    protected void checkHasOwnerAccess(UserFile entity, User user) {

    }
}
