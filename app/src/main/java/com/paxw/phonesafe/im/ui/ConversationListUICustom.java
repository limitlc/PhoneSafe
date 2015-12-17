package com.paxw.phonesafe.im.ui;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;

/**
 * Created by lichuang on 2015/12/17.
 */
public class ConversationListUICustom extends IMConversationListUI{
    public ConversationListUICustom(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public boolean needHideTitleView(Fragment fragment) {
        return true;
    }
}
