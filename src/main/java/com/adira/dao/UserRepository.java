package com.adira.dao;

import com.adira.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by didi-realtime on 14/11/16.
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByUsername(String username);
}
