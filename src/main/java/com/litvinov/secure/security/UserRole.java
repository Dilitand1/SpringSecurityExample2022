package com.litvinov.secure.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.litvinov.secure.security.UserPermission.*;

public enum UserRole {

    STUDENT(Sets.newHashSet())
    ,ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE))
    ,ADMINTRAIN(Sets.newHashSet(COURSE_READ,STUDENT_READ));

    private final Set<UserPermission> permissionSet;

    UserRole(Set<UserPermission> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Set<UserPermission> getPermissionSet() {
        return permissionSet;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        var per = getPermissionSet().stream().map(p-> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
        per.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return per;
    }
}
