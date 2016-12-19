package com.promeets.model.service.entity;

import com.promeets.model.entity.Card;
import com.promeets.model.entity.CardFile;
import com.promeets.model.entity.CardFilePK;
import com.promeets.model.entity.File;

import java.util.List;

/**
 * Created by Vladimir on 02.05.2016.
 */
public interface CardFileService extends BaseService<CardFile, CardFilePK> {

    List<Card> getCardFilesByCardId(long cardId);

    void deleteCardFilesByCardId(long cardId);

    void deleteCardFileByCardIdAndFileId(long cardId, long fileId);

    File createFileByCardId(long cardId);

    void deleteCardFilesByMeetId(long meetId);
}
