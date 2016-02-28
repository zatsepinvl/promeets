package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.unc6.promeets.model.entity.User;

/**
 * Created by Vladimir on 28.02.2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select user from User user where user.email=(:email)")
    User getUserByEmail(@Param("email") String email);
}
