package com.landmark.app.model.repository;

import com.landmark.app.model.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String account);

}
