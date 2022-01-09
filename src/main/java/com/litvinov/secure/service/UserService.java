package com.litvinov.secure.service;

import com.litvinov.secure.domain.AppUser;
import com.litvinov.secure.domain.Role;

import java.util.List;

public interface UserService {

    AppUser saveAppUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getAppUser(String username);
    List<AppUser> getUsers();
}
