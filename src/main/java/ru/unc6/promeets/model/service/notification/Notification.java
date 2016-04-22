/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.notification;

/**
 * @author MDay
 */
public class Notification {
    private Action action;
    private String entity;
    private Long id;
    private Object data;

    public Notification(){}
    public Notification(Class entityClass, Action action, Long id) {
        this.entity = entityClass.getSimpleName().toLowerCase();
        this.action = action;
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public Notification setAction(Action action) {
        this.action = action;
        return this;
    }

    public String getEntity() {
        return entity;
    }

    public Notification setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Notification setId(Long id) {
        this.id = id;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Notification setData(Object data) {
        this.data = data;
        return this;
    }

    public enum Action {CREATE, UPDATE, DELETE}
}
