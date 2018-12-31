package com.ly.a316.ly_meetingroommanagement.fragments;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ddz.floatingactionbutton.FloatingActionButton;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.ly.a316.ly_meetingroommanagement.Adapter.Calendar_Adapter;
import com.ly.a316.ly_meetingroommanagement.activites.AddSchedule;
import com.ly.a316.ly_meetingroommanagement.activites.AlarmActivity;
import com.ly.a316.ly_meetingroommanagement.calendarActivity.OneDayCountActivity;
import com.ly.a316.ly_meetingroommanagement.ceshi;
import com.ly.a316.ly_meetingroommanagement.classes.EventModel;
import com.ly.a316.ly_meetingroommanagement.classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.classes.jilei;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.SwipeItemLayout;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;
import com.ly.a316.ly_meetingroommanagement.utils.CalanderUtils;
import com.ly.a316.ly_meetingroommanagement.utils.RealmHelper;

import java.text.ParseException;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述：日历
 * 作者： 余智强
 * 创建时间：12/4 21：06 第一次提交
 */
public class CalendarFragment extends jilei implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {
    View view;
    TextView tvMonthDay, tvYear, tvLunar, tvCurrentDay, tv_today;
    ImageView ibCalendar;
    FrameLayout flCurrent;
    RelativeLayout rlTool;
    CalendarView ibCalendarview;
    CalendarLayout calendarLayout;
    RecyclerView calRecycleview;
    Calendar_Adapter calendar_adapter;
    @BindView(R.id.fl_addday)
    com.ddz.floatingactionbutton.FloatingActionButton flAddday;
    @BindView(R.id.fl_schedule)
    FloatingActionButton fl_schedule;
    @BindView(R.id.more)
    TextView more;
    Unbinder unbinder;
    private int mYear;
    String t_year, t_month, t_day;
    List<Schedule> list = new ArrayList<>();
    Intent intent1;//跳转统计的页面

    private TextView tv_ok, tv_cancel;//确定、取消button
    java.util.Calendar calendar_all;//获取今天的时间
    private DatePicker dp_test;//时间选择的控件
    private TimePicker tp_test;
    PopupWindow pw;
    String selectDatae, selectTime;
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour, currentMinute, currentDay, selectHour, selectMinute, selectDay;
    @BindView(R.id.fra)
    FrameLayout fra;
    DatePicker.OnChangeListener dp_onchanghelistener;
    TimePicker.OnChangeListener tp_onchanghelistener;

    //0代表无 1代表日程发生时 5-120代表分钟 2代表一天前 3代表2天前
    int time_result[] = {0, 1, 5, 15, 30, 60, 120, 2, 3};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fr_calendar;

    }

    @Override
    protected void initView() {
        initview();
    }

    @Override
    protected void initData() {
    }

    void init() {
        calendar_all = java.util.Calendar.getInstance();
        view = jilei.view;
        tv_today = view.findViewById(R.id.tv_today);
        tvMonthDay = view.findViewById(R.id.tv_month_dayyy);
        tvYear = view.findViewById(R.id.tv_year);
        tvLunar = view.findViewById(R.id.tv_lunar);
        ibCalendar = view.findViewById(R.id.ib_calendar);
        tvCurrentDay = view.findViewById(R.id.tv_current_day);
        flCurrent = view.findViewById(R.id.fl_current);
        rlTool = view.findViewById(R.id.rl_tool);
        ibCalendarview = view.findViewById(R.id.ib_calendarview);
        calendarLayout = view.findViewById(R.id.calendarLayout);
        calRecycleview = view.findViewById(R.id.cal_recycleview);

        ibCalendarview.setOnCalendarSelectListener(this);
        ibCalendarview.setOnYearChangeListener(this);


    }


    void moren() {
        tvYear.setText(String.valueOf(ibCalendarview.getCurYear()));
        mYear = ibCalendarview.getCurYear();
        tv_today.setText((String.valueOf(ibCalendarview.getCurMonth()) + "月" + String.valueOf(ibCalendarview.getCurDay()) + "日"));
        tvMonthDay.setText((String.valueOf(ibCalendarview.getCurMonth()) + "月" + String.valueOf(ibCalendarview.getCurDay()) + "日"));
        tvLunar.setText("今天");
        tvCurrentDay.setText(String.valueOf(ibCalendarview.getCurDay()));
    }


    void initview() {
        setStatusBarDarkMode();
        init();
        /**
         * 伪造数据
         */
        Schedule schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);

        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calRecycleview.setLayoutManager(linearLayoutManager);
        calendar_adapter = new Calendar_Adapter(getContext(), Schedule.list);
        calRecycleview.setAdapter(calendar_adapter);
        calendar_adapter.setOnItemClick(new Calendar_Adapter.OnItemClick() {
            @Override
            public void onitemClick(int position) {
                Toast.makeText(getContext(), "all被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onitemDelete(int position) {
                Toast.makeText(getContext(), "删除按钮被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onitemAlarm(int po) {
                RecyclerView.LayoutManager manager = calRecycleview.getLayoutManager();
                View vieww = manager.findViewByPosition(po);
                Calendar_Adapter.MyViewHolder holder = (Calendar_Adapter.MyViewHolder) calRecycleview.getChildViewHolder(vieww);
                holder.item_calendar_alerm.setBackgroundColor(R.drawable.btn_delete);
                Intent i = new Intent(getActivity(), AlarmActivity.class);
                i.putExtra("title", "新建提醒");
                i.putExtra("choose", "1");

                startActivityForResult(i, 12);
            }
        });
        calRecycleview.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        calRecycleview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        calRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    calendarLayout.shrink();
                } else {
                    calendarLayout.expand();
                }
            }
        });

        moren();
        int year = ibCalendarview.getCurYear();
        int month = ibCalendarview.getCurMonth();
        //伪造数据
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFFdf1356, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFFdf1356, "假"));
        ibCalendarview.setSchemeDate(map);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 12:
                Toast.makeText(getActivity(), resultCode + "", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    //日历被点击
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tv_today.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        tvLunar.setVisibility(View.VISIBLE);
        tvYear.setVisibility(View.VISIBLE);
        tvMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        tvYear.setText(String.valueOf(calendar.getYear()));
        tvLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        t_year = calendar.getYear() + "";
        t_day = calendar.getDay() + "";
        t_month = calendar.getMonth() + "";
    }

    @Override
    public void onYearChange(int year) {
        tvMonthDay.setText(String.valueOf(year));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        dp_onchanghelistener = new DatePicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
                selectDay = day;
                selectDatae = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
            }
        };
        tp_onchanghelistener = new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                selectTime = hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                selectHour = hour;
                selectMinute = minute;
            }
        };

        return rootView;
    }


    @SuppressLint("MissingPermission")
    @OnClick({R.id.fl_addday, R.id.fl_schedule, R.id.tv_month_dayyy, R.id.tv_current_day, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_schedule:
                try {
                    List<EventModel> calendarEvent = CalanderUtils.getCalendarEvent(getActivity(), 2018, 12);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.fl_addday:
                //添加日程
                Intent intent = new Intent(getContext(), AddSchedule.class);
                startActivity(intent);
                break;
            case R.id.tv_month_dayyy:
                if (!calendarLayout.isExpand()) {
                    ibCalendarview.showYearSelectLayout(mYear);////快速弹出年份选择月份
                    return;
                }
                ibCalendarview.showYearSelectLayout(mYear);
                tvLunar.setVisibility(View.GONE);
                tvYear.setVisibility(View.GONE);
                tvMonthDay.setText(String.valueOf(mYear));
                break;

            case R.id.tv_current_day:
                ibCalendarview.scrollToCurrent();////滚动到当前日期
                break;
            case R.id.more:
                intent1 = new Intent(getActivity(), OneDayCountActivity.class);
                startActivity(intent1);
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void initImmersionBar() {

    }


}
