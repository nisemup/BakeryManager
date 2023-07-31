package com.nisemup.bakerymanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions {
    READ("read"),
    WRITE("write"),
    DELETE("delete");

    private final String permission;
}
