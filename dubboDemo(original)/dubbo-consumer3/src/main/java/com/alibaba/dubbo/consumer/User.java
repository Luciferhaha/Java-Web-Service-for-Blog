package com.alibaba.dubbo.consumer;

public class User {
    private int id;
    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
    User(int id , String nickname, String password,String firstName,String lastName){
        this.id=id;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.nickname=nickname;

    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }
}
