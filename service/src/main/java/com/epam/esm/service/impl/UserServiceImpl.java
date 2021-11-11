package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.jparepository.UserRepository;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserRepository userRepository;


    public UserServiceImpl(UserDao userDao, UserRepository userRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userDao.create(DtoMapper.dtoToUser(userDto));
        return DtoMapper.userToDto(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchEntityException("user.username_is_not_present"));
    }

    @Override
    public List<UserDto> getAll(PageParameterDto pageParameterDto) {
        List<User> users = userDao.getAll(DtoMapper.dtoToPageParameter(pageParameterDto));
        return users.stream()
                .map(DtoMapper::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(long id) {
        return userDao.findById(id).map(DtoMapper::userToDto)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        if (!userDao.isContains(userDto.getId())) {
            throw new NoSuchEntityException();
        }
        User updatedUser = userDao.update(DtoMapper.dtoToUser(userDto));
        return DtoMapper.userToDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(UserDto userDto) {
        if (!userDao.isContains(userDto.getId())) {
            throw new NoSuchEntityException();
        }
        userDao.delete(userDto.getId());
    }
}
