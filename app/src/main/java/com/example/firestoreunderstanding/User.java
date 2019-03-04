package com.example.firestoreunderstanding;

public class User {

    private String name;
    private String phone;
    private String profile_image;
    private String security_level;
    private String user_id;

    public User(String name, String phone, String profile_image, String security_level, String user_id) {
        this.name = name;
        this.phone = phone;
        this.profile_image = profile_image;
        this.security_level = security_level;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public String getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", security_level='" + security_level + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
