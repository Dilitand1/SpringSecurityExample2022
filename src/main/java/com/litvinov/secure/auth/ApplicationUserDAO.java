package com.litvinov.secure.auth;

import java.util.Optional;

public interface ApplicationUserDAO {

    public Optional<AppUser> selectAppUserByUsername(String username);

}
