package com.aifubook.book.download;

import java.util.Map;

public class DownloadModel {
    private String httpUrl;
    private Map<String, String> headersMap;
    private Object tag;
    private String downloadPath;
    private AbsFileProgressCallback fileProgressCallback;

    public DownloadModel() {
    }

    public String getHttpUrl() {
        return this.httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public Map<String, String> getHeadersMap() {
        return this.headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public String getDownloadPath() {
        return this.downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public AbsFileProgressCallback getFileProgressCallback() {
        return this.fileProgressCallback;
    }

    public void setFileProgressCallback(AbsFileProgressCallback fileProgressCallback) {
        this.fileProgressCallback = fileProgressCallback;
    }


}
