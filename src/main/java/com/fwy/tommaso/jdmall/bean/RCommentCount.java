package com.fwy.tommaso.jdmall.bean;

/**
 * Created by Tommaso on 2018/1/31.
 */

public class RCommentCount {
    private int allComment;
    private int positiveCom;
    private int negativeCom;
    private int moderateCom;
    private int hasImgCom;

    public int getAllComment() {
        return allComment;
    }

    public void setAllComment(int allComment) {
        this.allComment = allComment;
    }

    public int getPositiveCom() {
        return positiveCom;
    }

    public void setPositiveCom(int positiveCom) {
        this.positiveCom = positiveCom;
    }

    public int getNegativeCom() {
        return negativeCom;
    }

    public void setNegativeCom(int negativeCom) {
        this.negativeCom = negativeCom;
    }

    public int getModerateCom() {
        return moderateCom;
    }

    public void setModerateCom(int moderateCom) {
        this.moderateCom = moderateCom;
    }

    public int getHasImgCom() {
        return hasImgCom;
    }

    public void setHasImgCom(int hasImgCom) {
        this.hasImgCom = hasImgCom;
    }
}
