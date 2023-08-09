package com.nisemup.bakerymanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write"),
    DELETE("delete");

    private final String permissions;
}
