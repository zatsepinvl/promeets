package com.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import com.promeets.model.entity.CardFile;
import com.promeets.model.entity.CardFilePK;
import com.promeets.model.service.entity.CardFileService;

/**
 * Created by Vladimir on 06.05.2016.
 */

public class CardFileController extends BaseRestController<CardFile, CardFilePK> {

    private CardFileService cardFileService;

    @Autowired
    public CardFileController(CardFileService service) {
        super(service);
        this.cardFileService = service;
    }
}
