package com.litvinov.secure.auth;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.litvinov.secure.security.UserRole.*;

@RequiredArgsConstructor
@Repository("fake")
public class FakeAppUserDAOService implements ApplicationUserDAO {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AppUser> selectAppUserByUsername(String username) {
        return getAppUser().stream().filter(appUser -> username.equalsIgnoreCase(appUser.getUsername()))
                .findFirst();
    }

    private List<AppUser> getAppUser() {
        List<AppUser> appUsers = Lists.newArrayList(
                new AppUser("annasmith"
                        , passwordEncoder.encode("password")
                        , STUDENT.getGrantedAuthority()
                        , true, true, true, true)
                , new AppUser("linda"
                        , passwordEncoder.encode("password")
                        , ADMIN.getGrantedAuthority()
                        , true, true, true, true)
                , new AppUser("tom"
                        , passwordEncoder.encode("password")
                        , ADMINTRAIN.getGrantedAuthority()
                        , true, true, true, true)

        );

        return appUsers;
    }

}
