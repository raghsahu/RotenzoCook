package com.cook.rotenzonew.Model;

/**
 * Created by Raghvendra Sahu on 04/12/2019.
 */
public class TabTipslist {
    String id;
    String tips;
    String mn_tips;

    public TabTipslist(String id, String tips) {
        this.id = id;
        this.tips = tips;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getMn_tips() {
        return mn_tips;
    }

    public void setMn_tips(String mn_tips) {
        this.mn_tips = mn_tips;
    }
}
