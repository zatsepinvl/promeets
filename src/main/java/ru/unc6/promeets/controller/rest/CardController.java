package ru.unc6.promeets.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.unc6.promeets.controller.exception.NotFoundException;
import ru.unc6.promeets.controller.exception.NotFoundResponseErrorMessage;
import ru.unc6.promeets.model.entity.Card;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.service.entity.CardFileService;
import ru.unc6.promeets.model.service.entity.CardService;
import ru.unc6.promeets.model.service.entity.FileService;

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
