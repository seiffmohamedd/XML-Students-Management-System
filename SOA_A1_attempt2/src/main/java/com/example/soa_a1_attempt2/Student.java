package com.example.soa_a1_attempt2;

public class Student {
    private String firstName;
    private String lastName;

    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private String gender;
    private double gpa;

    private int level;

    private String address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Student(String firstName, String lastName, int ID,String gender, double gpa, int level, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID=ID;
        this.gender = gender;
        this.gpa = gpa;
        this.level = level;
        this.address = address;
    }

}
