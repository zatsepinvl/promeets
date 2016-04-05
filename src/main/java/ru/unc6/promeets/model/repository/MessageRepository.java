/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.entity.Message;

/**
 *
 * @author MDay
 */
public interface MessageRepository extends CrudRepository<Message, Long>
{
    
}
