/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.webrtc;

import java.security.Principal;

import com.promeets.model.entity.User;
import com.promeets.model.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.controller.StompNotificationController;

@Service
public class WebRtcSignalServiceImpl implements WebRtcSignalService {
    @Autowired
    UserService userService;

    @Autowired
    StompNotificationController notificationController;

    @Override
    public void signalRTCByMeetId(WebRtcSignalMessage message, Principal principal) {
        User currentUser = userService.getUserByEmail(principal.getName());
        message.setSuserId(currentUser.getUserId());
        notificationController.sendRtcSignalMessage(message);
    }
}
