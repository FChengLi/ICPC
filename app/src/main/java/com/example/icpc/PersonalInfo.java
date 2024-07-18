package com.example.icpc;

public class PersonalInfo {
    private String id;
    private String username;
    private String email;
    private String password;
    private String area;
    private String occupation;
    private String certification;
    private String avatar;

    public PersonalInfo(String id, String username,String email, String password,String area, String occupation,String certification,String avatar) {
        this.id = id;
        this.username= username;
        this.email = email;
        this.password = password;
        this.area = area;
        this.occupation = occupation;
        this.certification = certification;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getArea() {
        return area;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getCertification() {
        return certification;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}