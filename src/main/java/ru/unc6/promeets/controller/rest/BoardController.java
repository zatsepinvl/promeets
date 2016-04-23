/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.service.entity.BoardService;

/**
 * @author MDay
 */

@RestController
@RequestMapping(value = "/api/boards")
public class BoardController extends BaseRestController<Board, Long> {

    private BoardService boardService;


    @Autowired
    public BoardController(BoardService service) {
        super(service);
        this.boardService = service;
    }

    @RequestMapping(value = "/{boardId}/pages")
    public Board getPage(@PathVariable("boardId") long pageId, @RequestParam("number") int number) {
        checkIsNotFoundById(pageId);
        return boardService.getBoardByMeetId(pageId, number);
    }
}
