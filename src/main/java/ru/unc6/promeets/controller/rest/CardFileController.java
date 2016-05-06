package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.unc6.promeets.model.entity.CardFile;
import ru.unc6.promeets.model.entity.CardFilePK;
import ru.unc6.promeets.model.service.entity.CardFileService;

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
