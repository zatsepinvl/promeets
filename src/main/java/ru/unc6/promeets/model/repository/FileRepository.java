package ru.unc6.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.entity.File;

public interface FileRepository extends CrudRepository<File, Long> {
}
