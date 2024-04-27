package com.clear.solutions.task.infra.service;

import com.clear.solutions.task.core.exception.NotFoundException;
import com.clear.solutions.task.core.repository.AddressRepository;
import com.clear.solutions.task.core.repository.UserRepository;
import com.clear.solutions.task.core.repository.model.AddressRecord;
import com.clear.solutions.task.core.repository.model.UserRecord;
import com.clear.solutions.task.core.service.UserService;
import com.clear.solutions.task.core.service.mapper.AddressMapper;
import com.clear.solutions.task.core.service.mapper.UserMapper;
import com.clear.solutions.task.core.service.model.Address;
import com.clear.solutions.task.core.service.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserMapper userMapper;
    UserRepository userRepository;
    AddressMapper addressMapper;
    AddressRepository addressRepository;

    @NonFinal
    @Value("${user.ageOfMajority}")
    int ageOfMajority;

    @Override
    public User store(User user) {
        log.debug("Store new user: {}", user);

        //TODO check if user enough age and throw exception

        UserRecord userRecord = userRepository.upsert(user);

        Address address = addressMapper.toAddress(user.getAddress(), userRecord.getId());
        AddressRecord addressRecord = addressRepository.upsert(address);

        user = userMapper.toUser(userRecord, addressRecord);
        log.debug("New user was stored: {}", user);

        return user;
    }

    @Override
    public User update(User entity) {
        //TODO implement update.
        throw new NotImplementedException("update is not yet implemented");
    }

    @Override
    public User get(String id) {
        log.debug("Getting user by id: {}", id);

        UserRecord userRecord = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id {}", id));
        AddressRecord addressRecord = addressRepository.getById(id)
                .orElse(null);
        User user = userMapper.toUser(userRecord, addressRecord);

        log.debug("Got user {} by id: {}", user, id);
        return user;
    }

    @Override
    public User remove(String id) {
        //TODO implement remove.
        throw new NotImplementedException("remove search is not yet implemented");
    }

    @Override
    public List<User> findByBirthdayRange(LocalDate start, LocalDate end) {
        //TODO implement findByBirthdayRange.
        throw new NotImplementedException("birthday search is not yet implemented");
    }
}
