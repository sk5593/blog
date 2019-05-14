package com.sk.blog.bean;

public class Logs {
    private Integer id;

    private String data;

    private Long created;

    private String stringCreated;

    public String getStringCreated() {
        return stringCreated;
    }

    public void setStringCreated(String stringCreated) {
        this.stringCreated = stringCreated;
    }

    public void setCreated(Long created) {

        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public Long getCreated() {
        return created;
    }
}