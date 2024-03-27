package com.example.security.RolesAndPermission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {
    AUTHOR_CREATE("author:create"),
    AUTHOR_UPDATE("author:update"),
    AUTHOR_DELETE("author:delete"),
    AUTHOR_READ("author:read");




    @Getter
    private final String permission;
}
