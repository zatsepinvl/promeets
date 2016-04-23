/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.unc6.promeets.model.service.webrtc;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.controller.AppSTOMPController;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.UserMeetRepository;

/**
 *
 * @author MDay
 */
@Service
public class WebRtcSignalServiceImpl implements WebRtcSignalService
{
    @Autowired
    UserMeetRepository userMeetRepository;
    
    @Autowired
    AppSTOMPController appSTOMPController;
    
    @Override
    public void signalRTCByMeetId(WebRtcSignalMessage message, User currentUser)
    {
        List<User> users = (List<User>) userMeetRepository.getUsersByMeetId(message.getMeetId());
        for (User user : users)
        {
            if (!currentUser.equals(user))
                appSTOMPController.sendRtcSignalMessage(message, user);
        }
    }
}
