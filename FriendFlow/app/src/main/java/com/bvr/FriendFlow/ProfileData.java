package com.bvr.FriendFlow;

public class ProfileData {
    String FName;
    String LName;
    String Bio;
    String Age;
    String Email;
    String PhNumber;
    String Gender;
    String Address;


    public ProfileData(String FName, String LName, String bio, String age, String email, String phNumber, String gender, String address) {
        this.FName = FName;
        this.LName = LName;
        Bio = bio;
        Age = age;
        Email = email;
        PhNumber = phNumber;
        Gender = gender;
        Address = address;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public void setAge(String age) {
        Age = age;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhNumber(String phNumber) {
        PhNumber = phNumber;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }

    public String getBio() {
        return Bio;
    }

    public String getAge() {
        return Age;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhNumber() {
        return PhNumber;
    }

    public String getGender() {
        return Gender;
    }

    public String getAddress() {
        return Address;
    }
}
