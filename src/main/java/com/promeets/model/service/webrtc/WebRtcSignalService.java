/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.webrtc;

import java.security.Principal;

public interface WebRtcSignalService 
{
    void signalRTCByMeetId(WebRtcSignalMessage message, Principal principal);
}
