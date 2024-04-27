package com.bohdan.abramovych.clear_solutions.infra.service;

import com.bohdan.abramovych.clear_solutions.core.exception.AgeCheckerException;
import com.bohdan.abramovych.clear_solutions.core.exception.NotFoundException;
import com.bohdan.abramovych.clear_solutions.core.repository.AddressRepository;
import com.bohdan.abramovych.clear_solutions.core.repository.UserRepository;
import com.bohdan.abramovych.clear_solutions.core.service.UserService;
import com.bohdan.abramovych.clear_solutions.core.service.mapper.AddressMapper;
import com.bohdan.abramovych.clear_solutions.core.service.mapper.UserMapper;
import com.bohdan.abramovych.clear_solutions.core.service.model.Address;
import com.bohdan.abramovych.clear_solutions.core.service.model.User;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.AddressRecord;
import com.bohdan.abramovych.clear_solutions.persistence.tables.records.UsersRecord;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

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
    @Value("${user.ageOfMajority:18}")
    int ageOfMajority;

    @Override
    public User store(User user) {
        log.debug("Store new user: {}", user);

        Period age = Period.between(user.getBirthday(), LocalDate.now());
        if (age.getYears() < ageOfMajority) {
            log.error("Age of user:{} less 18, cant store", user);
            throw new AgeCheckerException();
        }

        UsersRecord userRecord = userRepository.upsert(user);

        Address address = addressMapper.toAddress(user.getAddress(), userRecord.getId());
        AddressRecord addressRecord = addressRepository.upsert(address);

        user = userMapper.toUser(userRecord, addressRecord);
        log.debug("New user was stored: {}", user);

        return user;
    }

    @Override
    public User update(User entity) {
        return store(entity);
    }

    @Override
    public User get(String id) {
        log.debug("Getting user by id: {}", id);

        UsersRecord userRecord = userRepository.getByUserId(id)
            .orElseThrow(() -> new NotFoundException("User not found by id {}", id));
        AddressRecord addressRecord = addressRepository.getByUserId(id)
            .orElse(null);
        User user = userMapper.toUser(userRecord, addressRecord);

        log.debug("Got user {} by id: {}", user, id);
        return user;
    }

    @Override
    public User remove(String id) {
        Optional<UsersRecord> remove = userRepository.remove(id);

        if (remove.isPresent()) {
            log.debug("User {} was removed", id);
            return userMapper.toUser(remove.get(), new AddressRecord());
        } else {
            log.debug("User {} not found", id);
            return null;
        }
    }

    @Override
    public List<User> findByBirthdayRange(LocalDate start, LocalDate end) {
        log.debug("Getting List<User> by birthday range");
        return userRepository.findByBirthdayRange(start, end)
            .map(usersRecord ->
                userMapper.toUser(usersRecord, addressRepository.getByUserId(usersRecord.getId()).orElse(null)))
            .toList();
    }
}
