package com.promeets.model.repository;

import com.promeets.model.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Vladimir on 08.05.2016.
 */
public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {
}
