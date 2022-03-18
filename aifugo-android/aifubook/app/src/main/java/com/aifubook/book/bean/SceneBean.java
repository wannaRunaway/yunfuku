package com.aifubook.book.bean;

import java.util.List;

public class SceneBean {
    private Long createTime;
    private Long updateTime;
    private Integer id;
    private Integer scene;
    private List<ItemsDTO> items;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public List<ItemsDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemsDTO> items) {
        this.items = items;
    }

    public static class ItemsDTO {
        private Long createTime;
        private Long updateTime;
        private Integer id;
        private Integer scene;
        private String image;
        private Integer linkType;
        private String value;

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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getScene() {
            return scene;
        }

        public void setScene(Integer scene) {
            this.scene = scene;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getLinkType() {
            return linkType;
        }

        public void setLinkType(Integer linkType) {
            this.linkType = linkType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


//    {
//        "createTime": 1614130188929,
//            "updateTime": 1616394105450,
//            "id": 1,
//            "scene": 1,
//            "items": [
//        {
//            "createTime": 0,
//                "updateTime": 1616815777704,
//                "id": 4,
//                "scene": 1,
//                "image": "30a18ba9a5b1778455531348c2213982bcb4aa48.jpg",
//                "linkType": 0,
//                "value": "5544"
//        },
//        {
//            "createTime": 0,
//                "updateTime": 1616815295310,
//                "id": 6,
//                "scene": 1,
//                "image": "00ae6decbfc387e82f1a8f39526461ba83d78a4c.jpg",
//                "linkType": 2,
//                "value": "29"
//        },
//        {
//            "createTime": 0,
//                "updateTime": 1616815799951,
//                "id": 7,
//                "scene": 1,
//                "image": "e6be84eb7899fa732c2de6dfa2c95b897931da01.jpg",
//                "linkType": 0,
//                "value": "https://api.aifubook.com/bookstatic/html/userinfoAgreement.html"
//        }
//        ]
//    }

}
