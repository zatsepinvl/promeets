package com.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promeets.model.entity.UserInfo;
import com.promeets.model.repository.UserInfoRepository;
import com.promeets.model.service.entity.UserInfoService;

/**
 * Created by Vladimir on 08.05.2016.
 */
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Long>
        implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository repository) {
        super(repository);
        this.userInfoRepository = repository;
    }
}
