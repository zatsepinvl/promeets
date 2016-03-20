package ru.unc6.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.entity.File;


/**
 * Created by Vladimir on 20.03.2016.
 */
public interface FileRepository extends CrudRepository<File, Long> {
}
