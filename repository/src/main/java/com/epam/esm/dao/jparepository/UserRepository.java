package com.epam.esm.dao.jparepository;

import com.epam.esm.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User save(User user);

}
