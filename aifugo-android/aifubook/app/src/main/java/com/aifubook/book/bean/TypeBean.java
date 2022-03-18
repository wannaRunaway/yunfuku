package com.aifubook.book.bean;

import com.aifubook.book.mine.self.SerializableMap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ListKer_Hlg on 2021/4/28 22:09
 * 此类是作用于:
 * 邮箱: 1425034784@qq.com
 */
public class TypeBean implements Serializable {


    private Integer id;
    private Integer parentId;
    private String name;
    private String logo;
    private Integer leaf;
    private Integer rootId;
    private Integer layer;
    private String fullIdPath;
    private Object fullNames;
    private List<ChildrenBean> children;

    public static class ChildrenBean implements Serializable{
        private int clickposition = 0;
        private Integer id;
        private Integer parentId;
        private String name;
        private String logo;
        private Integer leaf;
        private Integer rootId;
        private Integer layer;
        private String fullIdPath;
        private Object fullNames;
        private List<ChildrenBean.ChildrenBeans> children;

        public static class ChildrenBeans implements Serializable{
            private Integer id;
            private Integer parentId;
            private String name;
            private String logo;
            private Integer leaf;
            private Integer rootId;
            private Integer layer;
            private String fullIdPath;
            private Object fullNames;
            private Object children;
            private int clickposition = 0;

            public int getClickposition() {
                return clickposition;
            }

            public void setClickposition(int clickposition) {
                this.clickposition = clickposition;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getParentId() {
                return parentId;
            }

            public void setParentId(Integer parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public Integer getLeaf() {
                return leaf;
            }

            public void setLeaf(Integer leaf) {
                this.leaf = leaf;
            }

            public Integer getRootId() {
                return rootId;
            }

            public void setRootId(Integer rootId) {
                this.rootId = rootId;
            }

            public Integer getLayer() {
                return layer;
            }

            public void setLayer(Integer layer) {
                this.layer = layer;
            }

            public String getFullIdPath() {
                return fullIdPath;
            }

            public void setFullIdPath(String fullIdPath) {
                this.fullIdPath = fullIdPath;
            }

            public Object getFullNames() {
                return fullNames;
            }

            public void setFullNames(Object fullNames) {
                this.fullNames = fullNames;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
            }
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Integer getLeaf() {
            return leaf;
        }

        public void setLeaf(Integer leaf) {
            this.leaf = leaf;
        }

        public Integer getRootId() {
            return rootId;
        }

        public void setRootId(Integer rootId) {
            this.rootId = rootId;
        }

        public Integer getLayer() {
            return layer;
        }

        public void setLayer(Integer layer) {
            this.layer = layer;
        }

        public String getFullIdPath() {
            return fullIdPath;
        }

        public void setFullIdPath(String fullIdPath) {
            this.fullIdPath = fullIdPath;
        }

        public Object getFullNames() {
            return fullNames;
        }

        public void setFullNames(Object fullNames) {
            this.fullNames = fullNames;
        }

        public List<ChildrenBeans> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeans> children) {
            this.children = children;
        }

        public int getClickposition() {
            return clickposition;
        }

        public void setClickposition(int clickposition) {
            this.clickposition = clickposition;
        }

        @Override
        public String toString() {
            return "ChildrenBean{" +
                    "id=" + id +
                    ", parentId=" + parentId +
                    ", name='" + name + '\'' +
                    ", logo='" + logo + '\'' +
                    ", leaf=" + leaf +
                    ", rootId=" + rootId +
                    ", layer=" + layer +
                    ", fullIdPath='" + fullIdPath + '\'' +
                    ", fullNames=" + fullNames +
                    ", children=" + children +
                    '}';
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public String getFullIdPath() {
        return fullIdPath;
    }

    public void setFullIdPath(String fullIdPath) {
        this.fullIdPath = fullIdPath;
    }

    public Object getFullNames() {
        return fullNames;
    }

    public void setFullNames(Object fullNames) {
        this.fullNames = fullNames;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", leaf=" + leaf +
                ", rootId=" + rootId +
                ", layer=" + layer +
                ", fullIdPath='" + fullIdPath + '\'' +
                ", fullNames=" + fullNames +
                ", children=" + children +
                '}';
    }
}
