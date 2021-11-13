package com.epam.esm.service.jpaservice;

import com.epam.esm.dao.jparepository.UserRepository;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jpaUserService")
public class JpaUserService implements UserService {

    private final UserRepository userRepository;

    public JpaUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto create(UserDto userDto) {
        return DtoMapper.userToDto(userRepository.save(DtoMapper.dtoToUser(userDto)));
    }

    @Override
    public List<UserDto> getAll(PageParameterDto pageParameterDto) {
        return null;
    }

    @Override
    public UserDto getById(long id) {
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public void delete(UserDto userDto) {

    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchEntityException("user.username_is_not_present"));
    }
}
