package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

    private final Long CREATE_USER_ID = 1l;
    private final String CREATE_USER_NAME = "CreateUser";
    private final Long UPDATE_USER_ID = 10l;
    private final String UPDATE_USER_NAME = "UpdateUser";

    private User createdUser;
    private User updatableUser;
    private UserDto createUserDto;
    private UserDto updateUserDto;



    @Autowired
    private UserService userService;

    @MockBean
    private UserDao userDao;

    @MockBean
    private PageParameterDto pageParameterDto;

    @BeforeEach
    void prepare() {
        createdUser = User.builder()
                .id(CREATE_USER_ID)
                .username(CREATE_USER_NAME)
                .build();
        createUserDto = UserDto.builder()
                .username(CREATE_USER_NAME)
                .build();

        updatableUser = User.builder()
                .id(UPDATE_USER_ID)
                .username(UPDATE_USER_NAME)
                .build();
        updateUserDto = UserDto.builder()
                .id(UPDATE_USER_ID)
                .username(UPDATE_USER_NAME)
                .build();
    }

    @Test
    void create() {
        when(userDao.create(DtoMapper.dtoToUser(createUserDto))).thenReturn(createdUser);
        UserDto userDto = userService.create(createUserDto);
        Assertions.assertEquals(DtoMapper.dtoToUser(userDto), createdUser);
    }

    @Test
    void updateExpectNoSuchEntityException() {
        when(userDao.isContains(updatableUser.getId())).thenReturn(false);
        Assertions.assertThrows(NoSuchEntityException.class, () -> userService.update(updateUserDto));
    }

    @Test
    void getAll() {
        List<User> users = new ArrayList<>();
        users.add(createdUser);
        users.add(updatableUser);
        when(userDao.getAll(any())).thenReturn(users);
        List<UserDto> dtoList = userService.getAll(pageParameterDto);
        Assertions.assertEquals(dtoList.size(), 2);
    }

    @Test
    void getById() {
        when(userDao.findById(CREATE_USER_ID)).thenReturn(Optional.of(createdUser));
        UserDto userDto =userService.getById(CREATE_USER_ID);
        Assertions.assertEquals(userDto.getId(), createdUser.getId());
        Assertions.assertEquals(userDto.getUsername(), createdUser.getUsername());
    }

    @Test
    void delete() {
        when(userDao.isContains(updatableUser.getId())).thenReturn(false);
        Assertions.assertThrows(NoSuchEntityException.class, () -> userService.delete(updateUserDto.getId()));
    }
}