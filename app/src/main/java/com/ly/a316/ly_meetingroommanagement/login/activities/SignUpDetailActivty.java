package com.ly.a316.ly_meetingroommanagement.login.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ly.a316.ly_meetingroommanagement.utils.PointConst.PWD_EMPTY_POINT;
import static com.ly.a316.ly_meetingroommanagement.utils.PointConst.PWD_POINT;

public class SignUpDetailActivty extends AppCompatActivity {

    @BindView(R.id.act_sign_up_detail_pwd)
    EditText actSignUpDetailPwd;
    @BindView(R.id.act_sign_up_detail_back_ll)
    ImageView actSignUpDetailBackLl;
  //密码输入的提示文字
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#00A7FF").init();
        setContentView(R.layout.activity_sign_up_detail_activty);
        ButterKnife.bind(this);

    }
    public  static  final  void start(Context context, String phoneNumber){
        Intent intent=new Intent();
        intent.putExtra("phoneNumber",phoneNumber);
        intent.setClass(context,SignUpDetailActivty.class);
        context.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @OnClick({R.id.act_sign_up_detail_back_ll, R.id.act_sign_up_detail_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_sign_up_detail_back_ll:
                finish();
                break;
            case R.id.act_sign_up_detail_bt:
                turnNext();
                break;
        }
    }

    private void turnNext() {
        /*
        1.判断密码是否为空，长度是否符合条件
        2.将密码数据带到下一个页面
        */
        String pwd=actSignUpDetailPwd.getText().toString();
        if(pwd.length()>=6){
            String phone=getIntent().getExtras().getString("phoneNumber","");
            SignUpLastActivity.start(SignUpDetailActivty.this,phone,pwd);
            finish();

        }else{
            //分情况给以用户提示
            if(pwd.isEmpty()||pwd.equals("")){
                Toast.makeText(this, PWD_EMPTY_POINT, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, PWD_POINT, Toast.LENGTH_SHORT).show();
            }
        }


    }

}
