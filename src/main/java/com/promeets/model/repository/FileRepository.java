package com.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import com.promeets.model.entity.File;

public interface FileRepository extends CrudRepository<File, Long> {
}
