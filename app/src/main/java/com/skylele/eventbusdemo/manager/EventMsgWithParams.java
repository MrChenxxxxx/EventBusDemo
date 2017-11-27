package com.skylele.eventbusdemo.manager;

/**
 * Created by cxg on 2017/11/27.
 */

public class EventMsgWithParams {

    public StudentBean studentBean;

    public EventMsgWithParams(StudentBean studentBean) {
        this.studentBean = studentBean;
    }
}
