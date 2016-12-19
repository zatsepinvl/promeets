package com.promeets.controller.rest;

import com.promeets.controller.exception.NotFoundException;
import com.promeets.controller.exception.NotFoundResponseErrorMessage;
import com.promeets.model.entity.Card;
import com.promeets.model.entity.File;
import com.promeets.model.service.entity.CardFileService;
import com.promeets.model.service.entity.CardService;
import com.promeets.model.service.entity.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController extends BaseRestController<Card, Long> {

    private CardService cardService;

    @Autowired
    private CardFileService cardFileService;

    @Autowired
    private FileService fileService;

    @Autowired
    public CardController(CardService service) {
        super(service);
        this.cardService = cardService;
    }

    @RequestMapping(value = "/{id}/files", method = RequestMethod.POST)
    public File createFileByCardId(@PathVariable("id") long cardId) {
        getAndCheckIsNotFoundById(cardId);
        return cardFileService.createFileByCardId(cardId);
    }

    @RequestMapping(value = "/{id}/files/{fileId}", method = RequestMethod.DELETE)
    public void deleteFileByCardIdAndFileId(@PathVariable("id") long cardId, @PathVariable("fileId") long fileId) {
        cardFileService.deleteCardFileByCardIdAndFileId(cardId, fileId);
    }

    @RequestMapping(value = "/{id}/files/{fileId}", method = RequestMethod.PUT)
    public void updateFileByCardIdAndFileId(@PathVariable("id") long cardId, @PathVariable("fileId") long fileId, @RequestParam File file) {
        checkIsNotFoundById(cardId);
        if (fileService.getById(fileId) != null) {
            fileService.update(file);
        } else {
            throw new NotFoundException().setResponseErrorMessage(new NotFoundResponseErrorMessage());
        }
    }
}
