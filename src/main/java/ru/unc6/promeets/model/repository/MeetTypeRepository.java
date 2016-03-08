package ru.unc6.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.unc6.promeets.model.entity.MeetType;

/**
 * Created by Vladimir on 01.03.2016.
 */
@Repository
public interface MeetTypeRepository extends CrudRepository<MeetType, Long> {
}
