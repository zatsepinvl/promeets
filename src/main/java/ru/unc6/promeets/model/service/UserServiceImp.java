package ru.unc6.promeets.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.unc6.promeets.model.entity.File;
import ru.unc6.promeets.model.entity.User;
import ru.unc6.promeets.model.repository.FileRepository;
import ru.unc6.promeets.model.repository.UserRepository;

import java.util.List;

/**
 * Created by Vladimir on 20.03.2016.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User getById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void save(User entity) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        if (entity.getImage() == null) {
            entity.setImage(new File());
        }
        fileRepository.save(entity.getImage());
        userRepository.save(entity);
    }

    @Secured("ROLE_GOD")
    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Secured("ROLE_ADMIN")
    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }
}
