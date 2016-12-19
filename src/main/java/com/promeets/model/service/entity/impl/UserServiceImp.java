package com.promeets.model.service.entity.impl;

import com.promeets.model.repository.UserRepository;
import com.promeets.model.service.entity.FileService;
import com.promeets.model.service.entity.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.promeets.model.entity.File;
import com.promeets.model.entity.User;
import com.promeets.model.entity.UserInfo;
import com.promeets.model.service.entity.UserService;
import com.promeets.security.CustomUserDetails;

import java.util.Collections;
import java.util.List;

/**
 * Created by Vladimir on 20.03.2016.
 */
@Service
public class UserServiceImp extends BaseServiceImpl<User, Long>
        implements UserService {


    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByChatId(long chatId) {
        return (List<User>) userRepository.getUsersByChatId(chatId);
    }

    @Override
    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUser();
        }
        return null;
    }

    @Override
    public void updateCurrentAuthenticatedUser(User currentUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            ((CustomUserDetails) authentication.getPrincipal()).setUser(currentUser);
        }
    }

    @Override
    public List<User> searchByFistNameAndLastName(String firstName, String lastName) {
        return (List<User>) userRepository.findByFirstNameAndLastName(firstName.toLowerCase(), lastName.toLowerCase());
    }

    @Override
    public List<User> searchByEmail(String email) {
        return Collections.singletonList(userRepository.findByEmailOrderByEmail(email));
    }

    @Override
    public User create(User entity) {
        //Encoding password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));

        //Set default image if it's needed
        if (entity.getImage() == null || entity.getImage().getOriginal() == null) {
            // entity.setImage(fileService.getDefaultUserImage());
            entity.setImage(new File());
        }
        fileService.create(entity.getImage());
        entity = userRepository.save(entity);

        //Create UserInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(entity);
        userInfo.setUserId(entity.getUserId());
        userInfoService.create(userInfo);

        return entity;
    }

    @Override
    public User update(User entity) {
        User currentUser = getCurrentAuthenticatedUser();
        entity.setPassword(currentUser.getPassword());
        entity.setEmail(currentUser.getEmail());
        updateCurrentAuthenticatedUser(entity);
        return super.update(entity);
    }
}
