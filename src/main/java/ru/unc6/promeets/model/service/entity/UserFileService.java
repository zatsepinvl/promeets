package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.UserFile;
import ru.unc6.promeets.model.entity.UserFilePK;

import java.util.List;

/**
 * Created by Vladimir on 04.05.2016.
 */
public interface UserFileService extends BaseService<UserFile, UserFilePK> {
    List<File> getFilesByUserId(long userId);

    void deleteUserFileByUserIdAndFileId(long userId, long fileId);
}
