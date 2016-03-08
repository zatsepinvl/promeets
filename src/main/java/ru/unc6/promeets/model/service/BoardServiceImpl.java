/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.entity.BoardPage;
import ru.unc6.promeets.model.repository.BoardRepository;

/**
 *
 * @author MDay
 */

@Transactional
@Service
public class BoardServiceImpl implements BoardService
{
     private static final Logger log = Logger.getLogger(BoardServiceImpl.class);
    
    @Autowired
    BoardRepository boardRepository;

    @Override
    public Board getById(long id) 
    {
        return boardRepository.findOne(id);
    }

    @Override
    public List<Board> getAll() 
    {
        return (List<Board>) boardRepository.findAll();
    }

    @Override
    public void save(Board board) 
    {
        boardRepository.save(board);
        
        log.debug("Save board with id=" + board.getBoardId());
    }

    @Override
    public void delete(long id) 
    {
        List<BoardPage> pages = (List) boardRepository.getAllBoardPagesById(id);
        
        for (BoardPage page : pages) 
        {
            boardRepository.deleteAllBoardItemsByPageId(page.getPageId());
        }
        
        boardRepository.deleteAllBoardPagesById(id);
        boardRepository.delete(id);
        
        log.debug("Delete board with id=" + id);
    }
    
    @Override
    public List<BoardPage> getAllBoardPagesByMeetId (long id)
    {
        return (List) boardRepository.getAllBoardPagesById(id);
    }
    
}
