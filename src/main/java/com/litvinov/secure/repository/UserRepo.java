package com.litvinov.secure.repository;

import com.litvinov.secure.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

}
