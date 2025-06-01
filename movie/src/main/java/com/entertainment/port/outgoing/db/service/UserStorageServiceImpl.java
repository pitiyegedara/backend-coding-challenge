package com.entertainment.port.outgoing.db.service;

import com.entertainment.domain.user.core.User;
import com.entertainment.domain.user.exception.UserCreationException;
import com.entertainment.domain.user.exception.UserDetailAccessException;
import com.entertainment.domain.user.exception.UserNotFoundException;
import com.entertainment.domain.user.outgoing.UserStorageService;
import com.entertainment.port.outgoing.db.mapper.DomainDatabaseMapper;
import com.entertainment.port.outgoing.db.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserStorageServiceImpl implements UserStorageService {

    private final UserRepository userRepository;
    private final DomainDatabaseMapper domainDatabaseMapper;

    public UserStorageServiceImpl(UserRepository userRepository, DomainDatabaseMapper domainDatabaseMapper) {
        this.userRepository = userRepository;
        this.domainDatabaseMapper = domainDatabaseMapper;
    }

    @Override
    public void createUser(User user) {
        try {
            var userEntity = domainDatabaseMapper.mapFromDomainUser(user);
            userRepository.save(userEntity);
            user.setId(userEntity.getId());
        } catch (DataAccessException e) {
            throw new UserCreationException("save to database failed");
        }
    }

    @Override
    public boolean isUserNameAlreadyExist(String userName) {
        try {
            return userRepository.findByUserName(userName).isPresent();
        } catch (DataAccessException e) {
            throw new UserDetailAccessException("an error occurred while accessing database");
        }
    }

    @Override
    public boolean isEmailAddressAlreadyExist(String emailAddress) {
        try {
            return userRepository.findByEmail(emailAddress).isPresent();
        } catch (DataAccessException e) {
            throw new UserDetailAccessException("an error occurred while accessing database");
        }
    }

    @Override
    public User findById(UUID id) {
        try {
            var userEntity = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id.toString()));
            return domainDatabaseMapper.mapFromUserEntity(userEntity);
        } catch (DataAccessException e) {
            throw new UserDetailAccessException("an error occurred while accessing database");
        }
    }
}
