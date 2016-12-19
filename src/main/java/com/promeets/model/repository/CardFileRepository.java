package com.promeets.model.repository;

import com.promeets.model.entity.CardFile;
import com.promeets.model.entity.CardFilePK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.promeets.model.entity.Card;

/**
 * Created by Vladimir on 02.05.2016.
 */
public interface CardFileRepository extends PagingAndSortingRepository<CardFile, CardFilePK> {

    @Query("select cardFile from CardFile cardFile where cardFile.id.card.cardId=(:cardId)")
    Iterable<Card> getCardFilesByCardId(@Param("cardId") long cardId);

    @Modifying
    @Transactional
    @Query("delete from CardFile cardFile where cardFile.id.card.cardId=(:cardId)")
    void deleteCardFilesByCardId(@Param("cardId") long cardId);

    @Modifying
    @Transactional
    @Query("delete from CardFile cardFile where cardFile.id.card.cardId=(:cardId) and cardFile.id.file.fileId=(:fileId)")
    void deleteFileCardByCardIdAndFileId(@Param("cardId") long cardId, @Param("fileId") long fileId);

    @Modifying
    @Transactional
    @Query("delete from CardFile cardFile where cardFile.id.card.meet.meetId=(:meetId)")
    void deleteCardFilesByMeetId(@Param("meetId") long meetId);
}
