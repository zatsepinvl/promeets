/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.entity.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Meet;
import ru.unc6.promeets.model.repository.MeetRepository;
import ru.unc6.promeets.model.service.entity.MeetService;


@Service
public class MeetServiceImpl extends BaseServiceImpl<Meet, Long>
        implements MeetService {

    private static final Logger log = Logger.getLogger(MeetServiceImpl.class);

    private MeetRepository meetRepository;



    @Autowired
    public MeetServiceImpl(MeetRepository repository) {
        super(repository);
        this.meetRepository = repository;
    }


}
