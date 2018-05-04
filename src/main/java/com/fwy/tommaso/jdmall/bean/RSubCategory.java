package com.fwy.tommaso.jdmall.bean;

/**
 * Created by Tommaso on 2018/1/5.
 */

public class RSubCategory {
    private long id;
    private String name;
    private String thirdCategory;

    public String getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(String thirdCategory) {
        this.thirdCategory = thirdCategory;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
