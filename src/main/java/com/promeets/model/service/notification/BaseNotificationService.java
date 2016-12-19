/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.notification;

public interface BaseNotificationService<T>
{
    void onCreate(T entity);
    void onUpdate(T entity);
    void onDelete(T entity);
}
