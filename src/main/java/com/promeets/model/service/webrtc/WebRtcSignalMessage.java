/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.webrtc;

public class WebRtcSignalMessage 
{
    private String data;
    private String type;
    private Long meetId;
    private Long duserId;
    private Long suserId;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDuserId() {
        return duserId;
    }

    public void setDuserId(Long duserId) {
        this.duserId = duserId;
    }

    public Long getSuserId() {
        return suserId;
    }

    public void setSuserId(Long suserId) {
        this.suserId = suserId;
    }

    public Long getMeetId() {
        return meetId;
    }

    public void setMeetId(Long meetId) {
        this.meetId = meetId;
    }
    
}
    

