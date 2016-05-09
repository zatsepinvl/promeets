package ru.unc6.promeets.model.repository;

import org.springframework.data.repository.CrudRepository;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserInfo;

/**
 * Created by Vladimir on 08.05.2016.
 */
public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {
}
