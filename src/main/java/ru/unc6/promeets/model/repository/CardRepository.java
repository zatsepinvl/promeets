package ru.unc6.promeets.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.Card;

public interface CardRepository extends PagingAndSortingRepository<Card, Long> {

    @Query(value = "select card from Card card where card.meet.meetId=(:meetId) order by card.time")
    Page<Card> getCardByMeetId(@Param("meetId") long meetId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Card card where card.meet.meetId=(:meetId)")
    void deleteCardsByMeetId(@Param("meetId") long meetId);
}
