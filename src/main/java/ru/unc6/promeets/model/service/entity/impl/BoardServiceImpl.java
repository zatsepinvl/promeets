package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.repository.BoardRepository;
import ru.unc6.promeets.model.service.entity.BoardService;
import ru.unc6.promeets.model.service.notification.BoardNotificationService;

import java.util.List;

/**
 * Created by Vladimir on 23.04.2016.
 */
@Service
public class BoardServiceImpl extends BaseNotificatedServiceImpl<Board, Long>
        implements BoardService {

    private BoardRepository boardPageRepository;

    private static final int PAGE_SIZE = 1;

    @Autowired
    public BoardServiceImpl(BoardRepository repository, BoardNotificationService notificationService) {
        super(repository, notificationService);
        this.boardPageRepository = repository;
    }

    @Override
    public Board getBoardByMeetId(long meetId, int page) {
        List<Board> list = boardPageRepository.getBoardByMeetId(meetId, new PageRequest(page, PAGE_SIZE)).getContent();
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<Board> getBoardsByMeetId(long meetId) {
        return (List<Board>) boardPageRepository.getBoardsByMeetId(meetId);
    }

    @Override
    public void deleteBoardsByMeetId(long meetId) {
        boardPageRepository.deleteBoardsByMeetId(meetId);
    }
}
