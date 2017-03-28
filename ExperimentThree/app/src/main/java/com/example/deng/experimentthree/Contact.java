package com.example.deng.experimentthree;

import java.io.Serializable;

/**
 * Created by deng on 2016/10/13.
 */
public class Contact implements Serializable {
    private String name;
    private String phone;
    private String type;
    private String belong;
    private String backgroundColor;
    private String firstName;
    public Contact(String name, String phone, String type, String belong, String backgroundColor) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.belong = belong;
        this.backgroundColor = backgroundColor;
        this.firstName = name.substring(0, 1);
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getBelong() {
        return belong;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getFirstName() {
        return firstName;
    }
}
