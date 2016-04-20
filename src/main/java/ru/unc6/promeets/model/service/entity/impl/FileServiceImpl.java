package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.repository.FileRepository;
import ru.unc6.promeets.model.service.entity.FileService;

/**
 * Created by Vladimir on 21.04.2016.
 */
@Component
public class FileServiceImpl extends BaseServiceImpl<File, Long>
        implements FileService {

    @Autowired
    public FileServiceImpl(FileRepository repository) {
        super(repository);
    }
}
