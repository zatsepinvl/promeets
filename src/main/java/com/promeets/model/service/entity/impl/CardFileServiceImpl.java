package com.promeets.model.service.entity.impl;

import com.promeets.model.entity.Card;
import com.promeets.model.entity.CardFile;
import com.promeets.model.entity.CardFilePK;
import com.promeets.model.repository.CardFileRepository;
import com.promeets.model.service.entity.CardService;
import com.promeets.model.service.entity.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.model.entity.File;
import com.promeets.model.service.entity.CardFileService;

import java.util.List;

@Service
public class CardFileServiceImpl extends BaseServiceImpl<CardFile, CardFilePK>
        implements CardFileService {

    @Autowired
    private CardService cardService;

    @Autowired
    private FileService fileService;

    private CardFileRepository cardFileRepository;


    @Autowired
    public CardFileServiceImpl(CardFileRepository repository) {
        super(repository);
        this.cardFileRepository = repository;
    }

    @Override
    public List<Card> getCardFilesByCardId(long cardId) {
        return (List<Card>) cardFileRepository.getCardFilesByCardId(cardId);
    }

    @Override
    public void deleteCardFilesByCardId(long cardId) {
        cardFileRepository.deleteCardFilesByCardId(cardId);
    }

    @Override
    public void deleteCardFileByCardIdAndFileId(long cardId, long fileId) {
        cardFileRepository.deleteFileCardByCardIdAndFileId(cardId, fileId);
    }

    @Override
    public File createFileByCardId(long cardId) {
        Card card = cardService.getById(cardId);
        File file = fileService.create(new File());
        CardFile cardFile = new CardFile();
        cardFile.setFile(file);
        cardFile.setCard(card);
        create(cardFile);
        return file;
    }

    @Override
    public void deleteCardFilesByMeetId(long meetId) {
        cardFileRepository.deleteCardFilesByMeetId(meetId);
    }
}
