package ru.unc6.promeets.model.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.*;

import javax.transaction.Transactional;

public interface MeetRepository extends CrudRepository<Meet, Long> {
}
