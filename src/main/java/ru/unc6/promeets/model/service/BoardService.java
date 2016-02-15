/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;
import ru.unc6.promeets.model.entity.Board;

/**
 *
 * @author MDay
 */
public interface BoardService 
{
    Board getById(long id);
    
    List<Board> getAll();
    
    void save(Board board);

    void delete(long id);
}
