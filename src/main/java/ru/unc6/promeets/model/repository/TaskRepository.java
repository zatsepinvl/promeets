package ru.unc6.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.entity.MeetTask;

/**
 * Created by Vladimir on 11.02.2016.
 */
public interface TaskRepository extends CrudRepository<MeetTask, Long> {
}
