package com.ly.a316.ly_meetingroommanagement.login.services;

import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.login.activities.SignUpLastActivity;
import com.ly.a316.ly_meetingroommanagement.main.MainActivity;
import com.ly.a316.ly_meetingroommanagement.main.fragment.MineFragment;
import com.ly.a316.ly_meetingroommanagement.utils.MyHttpUtil;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
Date:2018/12/31
Time:15:50
auther:xwd
*/
public class UploadServiceImp implements UploadService {
    SignUpLastActivity activity;
    MineFragment mainActivity;
    public UploadServiceImp(SignUpLastActivity activity) {
        this.activity = activity;
    }

    public UploadServiceImp(MineFragment mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void uploadFile(File file, final String type) {
      String URL= Net.HEAD+Net.UPLOAD;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //第一个参数要与Servlet中的一致,create里用null试试
        builder.addFormDataPart("profile",file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),file));

        MultipartBody multipartBody = builder.build();
        MyHttpUtil.sendOkhttpPostRequest(URL, multipartBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if("1".equals(type))
                activity.subThreadToast(Net.FAIL);
                else{
                    mainActivity.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyApplication.getContext(),Net.FAIL,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result1=response.body().string();
                response.close();
                try {
                    JSONObject jsonObject = new JSONObject(result1);
                    String comeOut=jsonObject.getString("result");
                    if("1".equals(type)){
                        activity.phoneURL=comeOut;
                        activity.uploadBack();
                    }
                    else{
                      mainActivity.phoneURL=comeOut;
                      mainActivity.uploadBack();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }
}
