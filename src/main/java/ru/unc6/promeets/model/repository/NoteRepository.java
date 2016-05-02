/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.MeetNote;

import javax.transaction.Transactional;

public interface NoteRepository extends CrudRepository<MeetNote, Long>
{
    @Modifying
    @Transactional
    @Query("delete from MeetNote meetNote where meetNote.meet.meetId=(:meetId)")
    void deleteNotesByMeetId(@Param("meetId") long id);

    @Query("select meetNote from MeetNote meetNote where  meetNote.meet.meetId=(:meetId) order by meetNote.noteId")
    Iterable<MeetNote> getMeetNotesByMeetId(@Param("meetId") long id);
}
