package com.example.book_pitch.Models;

public class User {
    private String uid; //namlee
    private String displayName;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String avatar;
    private MLocation mLocation;
    public User() {

    }
    public User(String uid,String displayName, String email, String phoneNumber, String address, String gender, String avatar) {
        this.uid = uid; //namlee
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.avatar = avatar;
    }

    public User(String uid, String displayName, String email, String phoneNumber, String address, String gender, String avatar, MLocation mLocation) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.avatar = avatar;
        this.mLocation = mLocation;
    }

    // namleee
    public String getId() {
        return uid;
    }
    public void setId(String uid) {
        this.uid = uid;
    }
    // namleee
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MLocation getmLocation() {
        return mLocation;
    }

    public void setmLocation(MLocation mLocation) {
        this.mLocation = mLocation;
    }
}
