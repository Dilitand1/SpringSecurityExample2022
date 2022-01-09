package com.litvinov.secure.service;

import com.litvinov.secure.domain.AppUser;
import com.litvinov.secure.domain.Role;
import com.litvinov.secure.repository.RoleRepo;
import com.litvinov.secure.repository.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public AppUser saveAppUser(AppUser user) {
        log.info("Saving new user {}", user.getUsername());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
        //userRepo.save(user);
    }

    @Override
    public AppUser getAppUser(String username) {
        log.info("Getting user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Getting all users");
        return userRepo.findAll();
    }
}
