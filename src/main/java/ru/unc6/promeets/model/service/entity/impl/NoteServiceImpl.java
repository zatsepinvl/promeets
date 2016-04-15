package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.repository.NoteRepository;
import ru.unc6.promeets.model.service.entity.NoteService;

/**
 * Created by Vladimir on 30.03.2016.
 */
@Component
public class NoteServiceImpl extends BaseServiceImpl<MeetNote, Long>
        implements NoteService {
    @Autowired
    public NoteServiceImpl(NoteRepository repository) {
        super(repository);
    }
}
