/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification;

/**
 *
 * @author MDay
 */
public interface BaseNotificationService<T>
{
    void onCreate(T entity);
    void onUpdate(T entity);
    void onDelete(T entity);
}
