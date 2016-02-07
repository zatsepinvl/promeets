/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;


/**
 *
 * @author MDay
 */
public class HibernateUtil 
{
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    
    private static final String PERSISTENT_UNIT_NAME = "item-manager";
    
    
    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityManager entityManager = null;
    
        

    public static EntityManagerFactory getEntityManagerFactory() 
    {
        try
        {
            return Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        }
        catch(Exception ex)
        {
            log.error(ex);
        }
        
        return entityManagerFactory;
    }
    public static EntityManager getEntityManager()
    {
        getEntityManagerFactory();
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
        }
        catch(Exception ex)
        {
            log.error(ex);
        }
        return entityManager;
    }
}

