package com.epam.esm.util;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.service.jpaservice.JpaUserService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = JpaUserService.class)
public interface JpaRepoUserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}
