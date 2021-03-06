package com.ly.a316.ly_meetingroommanagement.Schedule.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：余智强
 * ${Date}
 * 日程类
 */
public class Schedule {
    public  static  List<Schedule> list  ;
    private String dateTime;//日期
    private String content;//内容

    private int year,month,day;
    private String alert_startTime;//开始的时间
    private String alert_endtime;//结束的时间
    private String attribute;//事物属性

    private String alert_difang;//地点
    private String alert_people;//参会人员
    private String alert_head;//会议主题
    private String alert_beizhu;//会议备注

    private String alert_time;//提醒时间

    public Schedule( ) {

    }


    private String event_idd;



    public Schedule(String dateTime, String content) {
        this.dateTime = dateTime;
        this.content = content;

}

    public Schedule(String dateTime, String content, String alert_startTime, String alert_endtime, String attribute) {
        this.dateTime = dateTime;
        this.content = content;
        this.alert_startTime = alert_startTime;
        this.alert_endtime = alert_endtime;
        this.attribute = attribute;
    }



    public Schedule( String alert_startTime, String alert_endtime, String attribute, String alert_difang, String alert_people, String alert_head, String alert_beizhu,String event_idd) {

        this.alert_startTime = alert_startTime;
        this.alert_endtime = alert_endtime;
        this.attribute = attribute;
        this.alert_difang = alert_difang;
        this.alert_people = alert_people;
        this.alert_head = alert_head;
        this.alert_beizhu = alert_beizhu;
        this.event_idd = event_idd;
    }
    public Schedule( String alert_startTime, String alert_endtime, String attribute, String alert_difang, String alert_people, String alert_head, String alert_beizhu,String event_idd,int year,int month,int day) {
        this.alert_startTime = alert_startTime;
        this.alert_endtime = alert_endtime;
        this.attribute = attribute;
        this.alert_difang = alert_difang;
        this.alert_people = alert_people;
        this.alert_head = alert_head;
        this.alert_beizhu = alert_beizhu;
        this.event_idd = event_idd;
        this.month = month;
        this.year = year;
        this.day = day;
    }
    public Schedule( String alert_startTime, String alert_endtime, String attribute, String alert_difang, String alert_people, String alert_head, String alert_beizhu,String event_idd,int year,int month,int day,String  alert_time) {
        this.alert_startTime = alert_startTime;
        this.alert_endtime = alert_endtime;
        this.attribute = attribute;
        this.alert_difang = alert_difang;
        this.alert_people = alert_people;
        this.alert_head = alert_head;
        this.alert_beizhu = alert_beizhu;
        this.event_idd = event_idd;
        this.month = month;
        this.year = year;
        this.day = day;
        this.alert_time = alert_time;
    }
    public String getEvent_idd() {
        return event_idd;
    }

    public void setEvent_idd(String event_idd) {
        this.event_idd = event_idd;
    }

    public int getYear() {
        return year;
    }

    public String getAlert_time() {
        return alert_time;
    }

    public void setAlert_time(String alert_time) {
        this.alert_time = alert_time;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public static List<Schedule> getList() {
        return list;
    }

    public static void setList(List<Schedule> list) {
        Schedule.list = list;
    }

    public String getAlert_difang() {
        return alert_difang;
    }

    public void setAlert_difang(String alert_difang) {
        this.alert_difang = alert_difang;
    }

    public String getAlert_people() {
        return alert_people;
    }

    public void setAlert_people(String alert_people) {
        this.alert_people = alert_people;
    }

    public String getAlert_head() {
        return alert_head;
    }

    public void setAlert_head(String alert_head) {
        this.alert_head = alert_head;
    }

    public String getAlert_beizhu() {
        return alert_beizhu;
    }

    public void setAlert_beizhu(String alert_beizhu) {
        this.alert_beizhu = alert_beizhu;
    }

    public String getAlert_startTime() {
        return alert_startTime;
    }

    public void setAlert_startTime(String alert_startTime) {
        this.alert_startTime = alert_startTime;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAlert_endtime() {
        return alert_endtime;
    }

    public void setAlert_endtime(String alert_endtime) {
        this.alert_endtime = alert_endtime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
