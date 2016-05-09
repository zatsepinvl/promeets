package ru.unc6.promeets.model.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserInfo;
import ru.unc6.promeets.model.repository.UserInfoRepository;
import ru.unc6.promeets.model.service.entity.UserInfoService;

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
