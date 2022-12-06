package ru.skypro.homework.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Возвращает пользователя по указанному ID.
     *
     * @param id ID пользователя.
     * @return пользователь.
     * @throws NotFoundException пользователь с указанным ID отсутствует в базе.
     */
    public UserEntity findUser(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found"));
    }

    /**
     * Возвращает пользователя по указанному username.
     *
     * @param username username пользователя.
     * @return пользователь.
     * @throws NotFoundException пользователь с указанным username отсутствует в базе.
     */
    public UserEntity findUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User not found"));
    }

    /**
     * Возвращает всех пользователей.
     *
     * @return список всех пользователей.
     */
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Обновляет данные пользователя (поля firstName, lastName, phone).
     *
     * @param user обновленные данные пользователя.
     * @return обновленный пользователь.
     * @throws NotFoundException пользователь с указанными параметрами отсутствует в базе.
     */
    public UserEntity updateUser(UserEntity user) {
        UserEntity userBd = findUserByName(user.getUsername());
        userBd.setFirstName(user.getFirstName());
        userBd.setLastName(user.getLastName());
        userBd.setPhone(user.getPhone());
        logger.info("Method to update User " + user + " in the DB in table 'users' was invoked");
        userRepository.save(userBd);
        return userBd;
    }
}