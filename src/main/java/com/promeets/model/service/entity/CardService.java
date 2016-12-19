package com.promeets.model.service.entity;

import com.promeets.model.entity.Card;

import java.util.List;

/**
 * Created by Vladimir on 01.05.2016.
 */
public interface CardService extends BaseService<Card, Long> {
    List<Card> getCardsByMeetIdAndPage(long meetId, int page);

    void deleteCardByMeetId(long meetId);
}
