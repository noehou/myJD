package com.fwy.tommaso.jdmall.listener;

import com.fwy.tommaso.jdmall.bean.RArea;

/**
 * Created by Tommaso on 2018/3/13.
 */

public interface IAreaChangeListener {
    public void onAreaChanged(RArea province,RArea city,RArea area);
}
