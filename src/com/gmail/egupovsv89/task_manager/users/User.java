package com.gmail.egupovsv89.task_manager.users;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private final String encPassword;

    public User(String name, String encPassword) {
        this.name = name;
        this.encPassword = encPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncPassword() {
        return encPassword;
    }

}
