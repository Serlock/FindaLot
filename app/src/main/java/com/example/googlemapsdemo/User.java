package com.example.googlemapsdemo;

public class User {
    private int id;
    private String Name;
    private String Surname;
    private String Email;
    private String Password;
    private String Country;
    private String City;
    private String District;

    public User(int id, String name, String surname, String email, String password, String country, String city, String district) {
        this.id = id;
        Name = name;
        Surname = surname;
        Email = email;
        Password = password;
        Country = country;
        City = city;
        District = district;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Country='" + Country + '\'' +
                ", City='" + City + '\'' +
                ", District='" + District + '\'' +
                '}';
    }
}
