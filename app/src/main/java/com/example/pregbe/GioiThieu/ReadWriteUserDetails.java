package com.example.pregbe.GioiThieu;

public class ReadWriteUserDetails {
    public String fullName, email, gender, phoneNumber;

    //Constructor
    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String fullName, String email, String gender, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
