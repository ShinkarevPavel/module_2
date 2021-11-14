package com.epam.esm.service.jpaservice;

import com.epam.esm.dao.jparepository.UserRepository;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DtoMapper;
import com.epam.esm.util.JpaRepoUserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jpaUserService")
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    private final JpaRepoUserMapper jpaRepoUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JpaUserService(UserRepository userRepository, JpaRepoUserMapper jpaRepoUserMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jpaRepoUserMapper = jpaRepoUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto userDto) {
        String encodePassword = encodePassword(userDto.getPassword());
        userDto.setPassword(encodePassword);
        return DtoMapper.userToDto(userRepository.save(DtoMapper.dtoToUser(userDto)));
    }

    @Override
    public List<UserDto> getAll(PageParameterDto pageParameterDto) {
        return null;
    }

    @Override
    public UserDto getById(long id) {
        return DtoMapper.userToDto(userRepository.findById(id).orElseThrow(NoSuchEntityException::new));
    }

    @Override
    public UserDto update(UserDto userDto) {
        if (Strings.isNotEmpty(userDto.getPassword())) {
            String encodePassword = encodePassword(userDto.getPassword());
            userDto.setPassword(encodePassword);
        }
        User user = userRepository.findById(userDto.getId()).orElseThrow(NoSuchEntityException::new);
        jpaRepoUserMapper.updateUserFromDto(userDto, user);
        return DtoMapper.userToDto(userRepository.save(user));
    }

    @Override
    public void delete(UserDto userDto) {

    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchEntityException("user.username_is_not_present"));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
