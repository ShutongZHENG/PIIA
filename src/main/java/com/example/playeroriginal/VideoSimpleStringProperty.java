package com.example.playeroriginal;

import javafx.beans.property.SimpleStringProperty;

public class VideoSimpleStringProperty {
    public final SimpleStringProperty name;
    public final SimpleStringProperty src;
    public final SimpleStringProperty permission;
    public final SimpleStringProperty type;
    public final SimpleStringProperty duration;


    public VideoSimpleStringProperty(String name, String src, int permission, String  type, String duration) {
        this.name = new SimpleStringProperty(name);
        this.src = new SimpleStringProperty(src);
        this.permission = new SimpleStringProperty(String.valueOf(permission));
        this.type = new SimpleStringProperty(type) ;
        this.duration = new SimpleStringProperty(duration);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSrc() {
        return src.get();
    }

    public SimpleStringProperty srcProperty() {
        return src;
    }

    public void setSrc(String src) {
        this.src.set(src);
    }

    public String getPermission() {
        return permission.get();
    }

    public SimpleStringProperty permissionProperty() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission.set(permission);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDuration() {
        return duration.get();
    }

    public SimpleStringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }
}
