package com.springboot.wzh.model;

public class JDBCBean {
    private String url;
    private String userName;
    private String passWord;
    private String database;
    public void init(String url,String userName,String passWord,String database){
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        this.database = database;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
