package ru.unc6.promeets.model.service.entity;

import ru.unc6.promeets.model.entity.Board;

import java.util.List;

/**
 * Created by Vladimir on 23.04.2016.
 */
public interface BoardService extends BaseService<Board, Long> {
    Board getBoardByMeetId(long meetId, int page);
    List<Board> getBoardsByMeetId(long meetId);
    void deleteBoardsByMeetId(long meetId);
}
