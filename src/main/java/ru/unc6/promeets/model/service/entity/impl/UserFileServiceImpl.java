package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.UserFile;
import ru.unc6.promeets.model.entity.UserFilePK;
import ru.unc6.promeets.model.repository.UserFileRepository;
import ru.unc6.promeets.model.service.entity.UserFileService;

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
}
