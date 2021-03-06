package com.ly.a316.ly_meetingroommanagement.login.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ly.a316.ly_meetingroommanagement.FacePack.DetecterActivity;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.MainActivity;
import com.ly.a316.ly_meetingroommanagement.customView.CountDownButton;
import com.ly.a316.ly_meetingroommanagement.login.models.UserInfoModel;
import com.ly.a316.ly_meetingroommanagement.login.services.UserServiceImp;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.act_hint_title)
    TextView actHintTitle;
    @BindView(R.id.login_s_userID_et)
    EditText loginSUserIDEt;
    @BindView(R.id.login_s_pwd_et)
    EditText loginSPwdEt;
    private static final String TAG = "Login:";
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_identifying_code)
    CountDownButton getIdentifyingCode;
    @BindView(R.id.verification_ll)
    LinearLayout verificationLl;
    //切换登录模式的标记
    boolean flag = false;
    @BindView(R.id.password_tv)
    TextView passwordTv;
    @BindView(R.id.act_message_verification)
    TextView actMessageVerification;
    private final String NO_EMPTY_ACCOUNT = "账号不能为空！";
    private final String NO_EMPTY_PWD = "密码不能为空！";
    private final String NO_EMPTY_VWD = "验证码不能为空！";
    String phone = "";
    //mob短信验证的监听事件
    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            Log.d(TAG, "handleMessage: 成功发送了短信验证码");
                        } else {
                            ((Throwable) data).printStackTrace();
                            Log.d(TAG, "handleMessage: 发送短信验证码失败");
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.d(TAG, "handleMessage: 短信验证成功");
                            new UserServiceImp(LoginActivity.this).loginValidate(phone, "", "2");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "handleMessage: 短信验证失败");
                            ((Throwable) data).printStackTrace();
                        }
                    }

                    return false;
                }
            }).sendMessage(msg);
        }
    };
    @BindView(R.id.act_login_iv)
    RoundedImageView actLoginIv;
    @BindView(R.id.login_ll)
    LinearLayout loginLl;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.act_forget_password)
    TextView actForgetPassword;
    //表示国家的手机前缀
    private String COUNTRY_PRE = "86";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initMobMessage();
        SMSSDK.setAskPermisionOnReadContact(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initMobMessage() {

        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initView() {
        //离线的头像和名字
     String userName=MyApplication.getUserName();
     String imageURL=MyApplication.getImageURL();
        this.loginSPwdEt.setHintTextColor(getResources().getColor(R.color.tran_white));
        this.loginSUserIDEt.setHintTextColor(getResources().getColor(R.color.tran_white));

     String phoneNumber=MyApplication.getId();
     if("".equals(userName)){

     }else{
         this.actHintTitle.setText("Hello! "+userName+"\n欢迎回来");
         this.loginSUserIDEt.setText(phoneNumber);
     }
     if("".equals(imageURL)){

     }
     else{
         //设置glide加载的选项
         RequestOptions requestOptions=new RequestOptions()
                 .placeholder(R.drawable.touxiang)
                 .error(R.drawable.touxiang);
         Glide
                 .with(this)
                 .load(imageURL)
                 .apply(requestOptions)
                 .into(this.actLoginIv);
     }

    }

    //监听事件
    @OnClick({R.id.act_login_sign_up, R.id.login_button, R.id.act_message_verification, R.id.act_forget_password,R.id.face_login})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //跳转到注册界面
            case R.id.act_login_sign_up:
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            //登录
            case R.id.login_button:
                login();
                break;
            //短信验证登录
            case R.id.act_message_verification:
                changeLoginMod();
                break;
            //找回密码
            case R.id.act_forget_password:
                intent = new Intent(LoginActivity.this, ForgetPWDOneActivity.class);
                startActivity(intent);
                break;
            case R.id.face_login:
                DetecterActivity.start_in(this,1);
        }

    }

    private void changeLoginMod() {
        //正常账号登录转短信验证
        if (flag == false) {
            flag = true;
            this.loginSPwdEt.setVisibility(View.GONE);
            this.passwordTv.setText("验证码");
            this.actMessageVerification.setText("密码登录");
            this.verificationLl.setVisibility(View.VISIBLE);
        } else {
            flag = false;
            this.verificationLl.setVisibility(View.GONE);
            this.passwordTv.setText("密码");
            this.actMessageVerification.setText("验证码登录");
            this.loginSPwdEt.setVisibility(View.VISIBLE);
        }

    }

    void login() {
        /*
        */

        phone="";
        phone += this.loginSUserIDEt.getText().toString();
        String pwd = "";
        if (!("".equals(phone))) {
            //如果登录模式是短信验证
            if (this.verificationLl.getVisibility() == View.VISIBLE) {

                SMSSDK.submitVerificationCode(COUNTRY_PRE, phone, verificationEt.getText().toString());
            }
            //账号密码登录
            else {
                pwd="";
                pwd += this.loginSPwdEt.getText().toString();
                if (!("".equals(pwd))) {
                    new UserServiceImp(this).loginValidate(phone, pwd, "1");
                } else {
                    subThreadToast(NO_EMPTY_PWD);
                }

            }
        } else
            subThreadToast(NO_EMPTY_ACCOUNT);

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    //发送验证码
    @OnClick(R.id.get_identifying_code)
    public void onViewClicked() {

        String phone = loginSUserIDEt.getText().toString();
        if (!("".equals(phone))){
            if (getIdentifyingCode.isFinish()) {
                //发送验证码请求成功后调用
                getIdentifyingCode.start();
            }
            SMSSDK.getVerificationCode(COUNTRY_PRE, phone);

        }

        else
            subThreadToast(NO_EMPTY_ACCOUNT);
    }

    public void loginCallBack(String result,UserInfoModel model) {
        String temp="";
        if("0".equals(result)){
            temp+="账号不存在！";
        }else if("2".equals(result)){
            temp+="密码错误";
        }else{
            temp+="登录成功！";
        }
        final String status=temp;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, status, Toast.LENGTH_LONG).show();
            }
        });
        if("1".equals(result)){
            //保存账号、头像、昵称
            MyApplication.setId(phone);
            MyApplication.setImageURL(model.profile);
            MyApplication.setUserName(model.UserName);
            MyApplication.setToken("1");
            MyApplication.setDepartment(model.department);
            MyApplication.setPosition(model.position);
            //暂时不传model给mainActivity
            MainActivity.start(LoginActivity.this);
            finish();
        }
    }
}
