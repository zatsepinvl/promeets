package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.unc6.promeets.model.entity.MeetNote;
import ru.unc6.promeets.model.repository.NoteRepository;
import ru.unc6.promeets.model.service.entity.NoteService;
import ru.unc6.promeets.model.service.notification.NoteNotificationService;

import java.util.List;

/**
 * Created by Vladimir on 30.03.2016.
 */
@Component
public class NoteServiceImpl extends BaseNotifiedServiceImpl<MeetNote, Long>
        implements NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository repository, NoteNotificationService notificationService) {
        super(repository, notificationService);
        this.noteRepository = repository;
    }

    @Override
    public List<MeetNote> getNotesByMeetId(long meetId) {
        return (List<MeetNote>) noteRepository.getMeetNotesByMeetId(meetId);
    }

    @Override
    public void deleteNotesByMeetId(long meetId) {
        noteRepository.deleteNotesByMeetId(meetId);
    }
}
