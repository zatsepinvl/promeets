package com.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.promeets.model.entity.MeetType;

/**
 * Created by Vladimir on 01.03.2016.
 */
@Repository
public interface MeetTypeRepository extends CrudRepository<MeetType, Long> {
}
