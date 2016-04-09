package ru.unc6.promeets.model.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.repository.NoteRepository;

import java.util.List;

/**
 * Created by Vladimir on 30.03.2016.
 */
@Component
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public MeetNote getById(long id) {
        return noteRepository.findOne(id);
    }

    @Override
    public MeetNote save(MeetNote entity) {
        return noteRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        noteRepository.delete(id);
    }

    @Override
    public List<MeetNote> getAll() {
        return null;
    }
}
