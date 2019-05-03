package com.sk.blog.bean;

public class MessagesBoard {
    private Integer mid;
    private Long created;
    private String author;
    private String mail;
    private String ip;
    private String content;
    private String stringCreated;

    public String getStringCreated() {
        return stringCreated;
    }

    public void setStringCreated(String stringCreated) {
        this.stringCreated = stringCreated;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
