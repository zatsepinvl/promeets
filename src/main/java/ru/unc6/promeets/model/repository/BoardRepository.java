/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.Board;

/**
 * @author MDay
 */

public interface BoardRepository extends PagingAndSortingRepository<Board, Long> {
    @Query(value = "select board from Board board where board.meet.id=(:meetId) order by board.id")
    Page<Board> getBoardByMeetId(@Param("meetId") long meetId, Pageable pageable);
}
