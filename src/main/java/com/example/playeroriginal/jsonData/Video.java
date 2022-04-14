package com.example.playeroriginal.jsonData;

import com.alibaba.fastjson.annotation.JSONField;

public class Video {
    @JSONField(name = "name")
    public String name;

    @JSONField(name = "src")
    public String src;

    @JSONField(name = "permission")
    public int permission;

    @JSONField(name = "type")
    public String type;

    @JSONField(name = "duration")
    public String duration;


    public Video() {
    }

    public Video(String name, String src, int permission, String type, String duration) {
        super();
        this.name = name;
        this.src = src;
        this.permission = permission;
        this.type = type;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
