package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.Board;
import ru.unc6.promeets.model.repository.BoardRepository;
import ru.unc6.promeets.model.service.entity.BoardService;
import ru.unc6.promeets.model.service.notification.BoardNotificationService;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Vladimir on 23.04.2016.
 */
@Service
public class BoardServiceImpl extends BaseNotifiedServiceImpl<Board, Long>
        implements BoardService {

    @Value("${board-update-timer-delay}")
    private long TIMER_DELAY;

    private BoardRepository boardPageRepository;
    private static HashMap<Long, Timer> timers = new HashMap<>();
    private static final int PAGE_SIZE = 1;

    private BoardNotificationService boardNotificationService;

    @Autowired
    public BoardServiceImpl(BoardRepository repository, BoardNotificationService notificationService) {
        super(repository, notificationService);
        this.boardPageRepository = repository;
        this.boardNotificationService = notificationService;
    }

    @Override
    public Board update(Board entity) {
        entity = super.update(entity);
        if (entity.getEditor() != null) {
            startTimer(entity);
        } else {
            stopTimer(entity);
        }
        return entity;
    }

    private void startTimer(final Board board) {
        final Long key = board.getBoardId();
        stopTimer(board);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timers.put(key, null);
                board.setEditor(null);
                update(board);
            }
        }, TIMER_DELAY);
        timers.put(key, timer);
    }

    private void stopTimer(Board entity) {
        Long key = entity.getBoardId();
        if (timers.containsKey(key)) {
            Timer timer = timers.get(key);
            timer.cancel();
            timer.purge();
        }
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
