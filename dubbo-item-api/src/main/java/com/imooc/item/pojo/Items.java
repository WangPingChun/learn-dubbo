package com.imooc.item.pojo;

/**
 * @author : chris
 * 2018-06-30
 */
public class Items {
    private String id;

    private String name;

    private Integer counts;

    private Integer buyCounts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(Integer buyCounts) {

        this.buyCounts = buyCounts;
    }

}
