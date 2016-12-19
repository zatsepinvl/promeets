package com.promeets.model.service.entity;

import com.promeets.model.entity.UserFile;
import com.promeets.model.entity.UserFilePK;
import com.promeets.model.entity.File;

import java.util.List;

/**
 * Created by Vladimir on 04.05.2016.
 */
public interface UserFileService extends BaseService<UserFile, UserFilePK> {
    List<File> getFilesByUserId(long userId);

    void deleteUserFileByUserIdAndFileId(long userId, long fileId);
}
