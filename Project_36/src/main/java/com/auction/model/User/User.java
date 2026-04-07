package com.auction.model.User;

public abstract class User {
    protected String id;
    protected String name;

    public User(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("User id must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name must not be blank");
        }
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
