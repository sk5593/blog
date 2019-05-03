package com.sk.blog.bean;

import java.util.List;

public class Archives {
    public Integer count;
    public String data;
    public List<Contents> articleList;
    public String temp="archives";

    public String getTemp() {
        return temp;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Contents> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Contents> articleList) {
        this.articleList = articleList;
    }
}
