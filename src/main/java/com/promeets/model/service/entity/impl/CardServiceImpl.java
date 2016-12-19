package com.promeets.model.service.entity.impl;

import com.promeets.model.service.entity.CardService;
import com.promeets.model.service.entity.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.promeets.model.entity.Card;
import com.promeets.model.entity.CardFile;
import com.promeets.model.entity.File;
import com.promeets.model.repository.CardRepository;
import com.promeets.model.service.entity.CardFileService;
import com.promeets.model.service.notification.CardNotificationService;
import com.promeets.model.service.notification.MeetNotificationService;

import java.util.Collections;
import java.util.List;

@Service
public class CardServiceImpl extends BaseNotifiedServiceImpl<Card, Long>
        implements CardService {

    private static final int PAGE_SIZE = 20;
    private CardRepository cardRepository;

    @Autowired
    private MeetNotificationService meetNotificationService;

    @Autowired
    private CardFileService cardFileService;

    @Autowired
    private FileService fileService;

    @Autowired
    public CardServiceImpl(CardRepository repository, CardNotificationService notificationService) {
        super(repository, notificationService);
        this.cardRepository = repository;
    }

    @Override
    public List<Card> getCardsByMeetIdAndPage(long meetId, int page) {
        return cardRepository.getCardByMeetId(meetId, new PageRequest(page, PAGE_SIZE)).getContent();
    }

    @Override
    public void deleteCardByMeetId(long meetId) {
        cardFileService.deleteCardFilesByMeetId(meetId);
        cardRepository.deleteCardsByMeetId(meetId);
    }

    @Override
    public Card create(Card entity) {
        entity.setImage(fileService.create(new File()));
        entity = super.create(entity);

        //Create one card file as a default option
        File file = fileService.create(new File());
        CardFile cardFile = new CardFile();
        cardFile.setCard(entity);
        cardFile.setFile(file);
        cardFileService.create(cardFile);
        entity.setFiles(Collections.singletonList(file));

        return entity;
    }

    @Override
    public Card update(Card entity) {
        entity = super.update(entity);
        meetNotificationService.onUpdate(entity.getMeet());
        return entity;
    }
}
