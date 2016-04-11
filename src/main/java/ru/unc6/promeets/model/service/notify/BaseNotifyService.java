/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notify;

/**
 *
 * @author MDay
 */
public interface BaseNotifyService<T>
{
    public void onCreate(T entity);
    public void onUpdate(T entity);
    public void onDelete(T entity);
}
