package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Card;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.repository.CardRepository;
import ru.unc6.promeets.model.service.entity.CardService;
import ru.unc6.promeets.model.service.entity.FileService;
import ru.unc6.promeets.model.service.notification.CardNotificationService;

import java.util.List;

@Service
public class CardServiceImpl extends BaseNotificatedServiceImpl<Card, Long>
        implements CardService {

    private static final int PAGE_SIZE = 20;
    private CardRepository cardRepository;
    private CardNotificationService cardNotificationService;

    @Autowired
    private FileService fileService;

    @Autowired
    public CardServiceImpl(CardRepository repository, CardNotificationService notificationService) {
        super(repository, notificationService);
        this.cardRepository = repository;
        this.cardNotificationService = notificationService;
    }

    @Override
    public List<Card> getCardsByMeetIdAndPage(long meetId, int page) {
        return cardRepository.getCardByMeetId(meetId, new PageRequest(page, PAGE_SIZE)).getContent();
    }

    @Override
    public Card create(Card entity) {
        entity.setImage(fileService.create(new File()));
        return super.create(entity);
    }
}
