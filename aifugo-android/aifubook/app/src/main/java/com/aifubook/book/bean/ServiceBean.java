package com.aifubook.book.bean;

import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/5/13 1:59
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class ServiceBean {


    private Integer pageNo;
    private Integer pageSize;
    private Integer offset;
    private Integer totalCount;
    private List<list> list;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<list> getList() {
        return list;
    }

    public void setList(List<list> list) {
        this.list = list;
    }

    public static class list {
        private Integer id;
        private String title;
        private String content;
        private Integer status;
        private Long publishTime;
        private Object updater;
        private Long createTime;
        private Long updateTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(Long publishTime) {
            this.publishTime = publishTime;
        }

        public Object getUpdater() {
            return updater;
        }

        public void setUpdater(Object updater) {
            this.updater = updater;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
