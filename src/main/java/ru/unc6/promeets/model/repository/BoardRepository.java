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
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.entity.BoardPage;

/**
 *
 * @author MDay
 */
public interface BoardRepository extends CrudRepository<Board, Long>
{
    @Modifying
    @Query("select boardPage from BoardPage boardPage where boardPage.board.boardId=(:boardId)")
    Iterable<BoardPage> getAllBoardPagesById(@Param("boardId") Long id);
    
    @Modifying
    @Query("delete from BoardPage boardPage where boardPage.board.boardId=(:boardId)")
    void deleteAllBoardPagesById(@Param("boardId") Long id);
    
    @Modifying
    @Query("delete from BoardItem boardItem where boardItem.boardPage.pageId=(:pageId)")
    void deleteAllBoardItemsByPageId(@Param("pageId") Long id);
}
