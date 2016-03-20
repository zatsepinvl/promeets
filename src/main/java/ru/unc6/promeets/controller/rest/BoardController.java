/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.entity.BoardPage;
import ru.unc6.promeets.model.service.BoardService;

/**
 *
 * @author MDay
 */

@RestController
public class BoardController 
{
    @Autowired
    BoardService boardService;
    
     @RequestMapping(value = "api/boards/{id}", method = RequestMethod.GET)
    public ResponseEntity<Board> getBoardById(@PathVariable long id) 
    {
        Board board = boardService.getById(id);
        
        if (board == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(board, HttpStatus.OK);
    }
    
     @RequestMapping(value = "api/boards", method = RequestMethod.GET)
    public ResponseEntity<List<Board>> getBoards() 
    {
        List<Board> boards = boardService.getAll();
        
        if (boards.isEmpty()) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "api/boards/", method = RequestMethod.POST)
    public ResponseEntity<Void> createBoard(@RequestBody Board board) 
    { 
        if (board == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        boardService.save(board);
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "api/boards/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBoard(@RequestBody Board board, @PathVariable long id) 
    { 
        if (boardService.getById(id) == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        board.setBoardId(id);
        boardService.save(board);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "api/boards/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> updateBoard(@PathVariable long id) 
    { 
        if (boardService.getById(id) == null) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        boardService.delete(id);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "api/boards/{id}/pages", method = RequestMethod.GET)
    public ResponseEntity<List<BoardPage>> getBoardPages(@PathVariable long id) 
    { 
        List<BoardPage> pages = boardService.getAllBoardPagesByMeetId(id);
        
        if (pages.isEmpty()) 
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    /**
     * Created by Vladimir on 15.03.2016.
     */
}
