//package com.epam.esm.util;
//
//import com.epam.esm.dto.UserDto;
//import com.epam.esm.entity.User;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JpaRepoUserMapperIml implements JpaRepoUserMapper {
//
//    @Override
//    public User updateUserFromDto(UserDto userDto, User user) {
//        if ( userDto == null ) {
//            return null;
//        }
//
//        if ( userDto.getId() != null ) {
//            user.setId( userDto.getId() );
//        }
//        if ( userDto.getPassword() != null ) {
//            user.setPassword( userDto.getPassword() );
//        }
//        if ( userDto.getUsername() != null ) {
//            user.setUsername( userDto.getUsername() );
//        }
//        if ( userDto.getRole() != null ) {
//            user.setRole( userDto.getRole() );
//        }
//
//        return user;
//    }
//}
