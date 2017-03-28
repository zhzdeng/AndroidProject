package com.example.deng.experimenteight.entity;

/**
 * Created by deng on 2016/11/16.
 */
public class BirthInfo {
    private long id;
    private String name;
    private String birth;
    private String gift;

    public BirthInfo(String name, String birth, String gift) {
        this.name = name;
        this.birth = birth;
        this.gift = gift;
    }

    public BirthInfo(long id, String name, String birth, String gift) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.gift = gift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BirthInfo [id=" + id + ", name=" + name + ", birth=" + birth + ", gift="
                + gift + "]";
    }
}
