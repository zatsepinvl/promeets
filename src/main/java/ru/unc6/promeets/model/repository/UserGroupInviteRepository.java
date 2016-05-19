package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.entity.UserGroupInvite;
import ru.unc6.promeets.model.entity.UserGroupPK;

/**
 * Created by Vladimir on 18.05.2016.
 */
public interface UserGroupInviteRepository extends CrudRepository<UserGroupInvite, UserGroupPK> {

    @Query("select userGroupInvite from UserGroupInvite  userGroupInvite where userGroupInvite.id.user.userId=(:userId)")
    Iterable<UserGroupInvite> getUserGroupInvitesByUserId(@Param("userId") long userId);

    @Query("select userGroupInvite from UserGroupInvite  userGroupInvite where userGroupInvite.id.group.groupId=(:groupId)")
    Iterable<UserGroupInvite> getUserInvitesByGroupId(@Param("groupId") long groupId);

    @Query("select userGroupInvite.id.user from UserGroupInvite  userGroupInvite where userGroupInvite.id.group.groupId=(:groupId)")
    Iterable<User> getInvitedUsersByGroupId(@Param("groupId") long groupId);



    @Modifying
    @Transactional
    @Query("delete from UserGroupInvite userGroupInvite where userGroupInvite.id.user.userId=(:userId) and userGroupInvite.id.group.groupId=(:groupId)")
    void deleteUserGroupInviteByUserIdAndGroupId(@Param("userId") long userId, @Param("groupId") long groupId);
}
