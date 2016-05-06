package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.Card;
import ru.unc6.promeets.model.entity.CardFile;
import ru.unc6.promeets.model.entity.CardFilePK;
import ru.unc6.promeets.model.entity.File;

import java.util.List;

/**
 * Created by Vladimir on 02.05.2016.
 */
public interface CardFileService extends BaseService<CardFile, CardFilePK> {

    List<Card> getCardFilesByCardId(long cardId);

    void deleteCardFilesByCardId(long cardId);

    void deleteCardFileByCardIdAndFileId(long cardId, long fileId);

    File createFileByCardId(long cardId);
}
