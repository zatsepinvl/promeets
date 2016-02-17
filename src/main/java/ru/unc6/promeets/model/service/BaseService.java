/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service;

import java.util.List;

/**
 *
 * @author MDay
 */
public interface  BaseService<T> 
{
    T getById(long id);

    void save(T entity);

    void delete(long id);

    List<T> getAll();
}
