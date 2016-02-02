/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.models.entities.Meet;
import ru.unc6.promeets.util.HibernateUtil;

/**
 *
 * @author MDay
 */

@Service
@Repository
@Transactional
public class MeetServiceImpl implements MeetService
{
    private static final Logger log = Logger.getLogger(MeetServiceImpl.class);

    //@PersistenceContext
    private EntityManager em;
    
    @Override
    public Meet getById(long id) 
    {
        em = HibernateUtil.getEntityManager();
        Meet meet = em.find(Meet.class, 1L);
        return meet;
    }


    @Override
    public void addMeet(Meet meet) 
    {
        
    }

    @Override
    public void updateMeet(Meet meet) 
    {
        
    }

    @Override
    public void deleteMeet(Meet meet) 
    {
        
    }

    @Override
    public List<Meet> getAll() 
    {
        List<Meet> meets = null;
        
        return meets;
    }

}
    
