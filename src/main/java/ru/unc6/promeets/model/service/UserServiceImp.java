package ru.unc6.promeets.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
        if (entity.getImage() != null) {
            if (entity.getImage().getUrl() == null) {
                entity.setImage(null);
            } else {
                fileRepository.save(entity.getImage());
            }
        }
        userRepository.save(entity);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
