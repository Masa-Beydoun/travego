package com.example.security.RolesAndPermission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Roles {
    AUTHOR(
            Set.of(
                    Permissions.AUTHOR_CREATE,
                    Permissions.AUTHOR_UPDATE,
                    Permissions.AUTHOR_DELETE,
                    Permissions.AUTHOR_READ
            )
    ),
    ULTIMATE_STUDENT(Collections.emptySet()),
    SIMPLE_STUDENT(Collections.emptySet());

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities()
    {
        var authorities=getPermissions()
                .stream()
                .map(permissions->new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
