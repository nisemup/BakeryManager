package com.nisemup.bakerymanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions {
    READ("read"),
    WRITE("write");

    private final String permission;
}
