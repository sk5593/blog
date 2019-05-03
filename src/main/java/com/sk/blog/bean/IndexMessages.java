package com.sk.blog.bean;

public class IndexMessages {
    private Integer articlesNum;
    private Integer commentsNum;
    private String lastUpdate;
    private Visitors visitors;
    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }



    public Integer getArticlesNum() {
        return articlesNum;
    }

    public void setArticlesNum(Integer articlesNum) {
        this.articlesNum = articlesNum;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }



    public Visitors getVisitors() {
        return visitors;
    }

    public void setVisitors(Visitors visitors) {
        this.visitors = visitors;
    }
}
