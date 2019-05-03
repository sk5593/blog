package com.sk.blog.bean;

import java.util.List;

/**
 * kid：种类id
 * cname：种类名
 * list：每个种类对应的文章集合
 */
public class Categories {
    public Integer kid;
    private Integer count;
    public String cname;
    public List<Contents> articleList;
    public String temp="category";

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTemp() {
        return temp;
    }
    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<Contents> getList() {
        return articleList;
    }

    public void setList(List<Contents> list) {
        this.articleList = list;
    }
}
