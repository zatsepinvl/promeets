package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.Board;

/**
 * Created by Vladimir on 23.04.2016.
 */
public interface BoardService extends BaseService<Board, Long> {
    Board getBoardByMeetId(long meetId, int page);
}
