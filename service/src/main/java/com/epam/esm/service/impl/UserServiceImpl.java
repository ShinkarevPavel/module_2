package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userDao.create(DtoMapper.dtoToUser(userDto));
        return DtoMapper.userToDto(user);
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
    public void update(UserDto userDto) {
        if (!userDao.isContains(userDto.getId())) {
            throw new NoSuchEntityException();
        }
        userDao.update(DtoMapper.dtoToUser(userDto));
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
