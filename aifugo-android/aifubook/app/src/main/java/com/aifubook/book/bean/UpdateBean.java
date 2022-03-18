package com.aifubook.book.bean;

public class UpdateBean {


    private String content;
    private boolean isJudging;
    private int status;// 0不更新  1提示更新  2强制更新
    private String title;
    private String url;
    private String version;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isJudging() {
        return isJudging;
    }

    public void setJudging(boolean judging) {
        isJudging = judging;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "content='" + content + '\'' +
                ", isJudging=" + isJudging +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
