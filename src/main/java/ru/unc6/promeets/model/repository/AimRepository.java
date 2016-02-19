package ru.unc6.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.entity.MeetAim;

/**
 * Created by Vladimir on 11.02.2016.
 */
public interface AimRepository extends CrudRepository<MeetAim, Long> {
}
