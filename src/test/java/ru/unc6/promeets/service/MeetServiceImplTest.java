/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.service;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.unc6.promeets.models.entities.Meet;

/**
 *
 * @author MDay
 */
public class MeetServiceImplTest {
    
    public MeetServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getById method, of class MeetServiceImpl.
     */
    @org.junit.Test
    public void testGetById() 
    {
        System.out.println("getById");
        long id = 1L;
        MeetServiceImpl instance = new MeetServiceImpl();
        Meet expResult = null;
        Meet result = instance.getById(id);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of addMeet method, of class MeetServiceImpl.
     */
    @org.junit.Test
    public void testAddMeet() {
        System.out.println("addMeet");
        Meet meet = null;
        MeetServiceImpl instance = new MeetServiceImpl();
        instance.addMeet(meet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateMeet method, of class MeetServiceImpl.
     */
    @org.junit.Test
    public void testUpdateMeet() {
        System.out.println("updateMeet");
        Meet meet = null;
        MeetServiceImpl instance = new MeetServiceImpl();
        instance.updateMeet(meet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteMeet method, of class MeetServiceImpl.
     */
    @org.junit.Test
    public void testDeleteMeet() {
        System.out.println("deleteMeet");
        Meet meet = null;
        MeetServiceImpl instance = new MeetServiceImpl();
        instance.deleteMeet(meet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class MeetServiceImpl.
     */
    @org.junit.Test
    public void testGetAll() {
        System.out.println("getAll");
        MeetServiceImpl instance = new MeetServiceImpl();
        List<Meet> expResult = null;
        List<Meet> result = instance.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
