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
public class Notify
{
    private Action action;
    private String entity;
    private Long id;
    
    public Notify(Class entityClass, Action action, Long id)
    {
        this.entity = entityClass.getSimpleName().toLowerCase();
        this.action = action;
        this.id = id;
    }

    public Action getAction() 
    {
        return action;
    }

    public void setAction(Action action) 
    {
        this.action = action;
    }

    public String getEntity() 
    {
        return entity;
    }

    public void setEntity(String entity) 
    {
        this.entity = entity;
    }

    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }
    
    public enum Action { CREATE, UPDATE, DELETE}
}
