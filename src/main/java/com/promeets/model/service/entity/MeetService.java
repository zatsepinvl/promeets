/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.promeets.model.service.entity;

import java.util.List;

import com.promeets.model.entity.Meet;

public interface MeetService extends BaseService<Meet, Long> {
    List<Meet> getMeetsByGroupId(long groupId);

    void deleteMeetsByGroupId(long groupId);
}
