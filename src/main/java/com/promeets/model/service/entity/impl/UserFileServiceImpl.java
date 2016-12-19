package com.promeets.model.service.entity.impl;

import com.promeets.model.entity.File;
import com.promeets.model.entity.UserFile;
import com.promeets.model.entity.UserFilePK;
import com.promeets.model.repository.UserFileRepository;
import com.promeets.model.service.entity.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserFileServiceImpl extends BaseServiceImpl<UserFile, UserFilePK>
        implements UserFileService {

    private UserFileRepository userFileRepository;

    @Autowired
    public UserFileServiceImpl(UserFileRepository repository) {
        super(repository);
        this.userFileRepository = repository;
    }

    @Override
    public List<File> getFilesByUserId(long userId) {
        return (List<File>) userFileRepository.getFilesByUserId(userId);
    }

    @Override
    public void deleteUserFileByUserIdAndFileId(long userId, long fileId) {
        userFileRepository.deleteUserFileByUserIdAndFileId(userId, fileId);
    }
}
