package ru.unc6.promeets.model.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.UserFile;
import ru.unc6.promeets.model.entity.UserFilePK;

/**
 * Created by Vladimir on 04.05.2016.
 */
public interface UserFileRepository extends PagingAndSortingRepository<UserFile, UserFilePK> {

    @Query("select userFile.userFilePK.file from UserFile userFile where userFile.id.user.userId=(:userId)")
    Iterable<File> getFilesByUserId(@Param("userId") long userId);

    @Modifying
    @Transactional
    @Query("delete from UserFile userFile where userFile.id.user.userId=(:userId) and userFile.id.file.fileId=(:fileId)")
    void deleteUserFileByUserIdAndFileId(@Param("userId") long userId, @Param("fileId") long fileId);
}
