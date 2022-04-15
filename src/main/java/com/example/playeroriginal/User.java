package com.example.playeroriginal;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
    @JSONField(name = "username")
    public String username;

    @JSONField(name = "passwords")
    public String passwords;

    @JSONField(name = "permission")
    public int permission;

    public User(){}
    public User(String username, int permission) {

        this.username = username;
        this.permission = permission;
    }

    public User(String username, String passwords,int permission) {
        super();
        this.username = username;
        this.permission = permission;
        this.passwords = passwords;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }
}
