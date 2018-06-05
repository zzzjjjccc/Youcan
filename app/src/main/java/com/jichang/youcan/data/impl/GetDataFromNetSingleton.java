package com.jichang.youcan.data.impl;

import com.jichang.jichangprojectlibrary.util.HttpUtils;
import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.constant.ApiConstants;
import com.jichang.youcan.data.IGetDataFromNet;
import com.jichang.youcan.data.bean.req.Register;
import com.jichang.youcan.data.bean.req.Share;
import com.jichang.youcan.data.callback.IYoucanDataResponse;
import com.jichang.youcan.db.model.Content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_CONTENT;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_DATE;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_IDOL;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_MAJOR;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_NAME;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_OPEN;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_PASSWORD;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_PHONE;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_SCHOOL;
import static com.jichang.youcan.data.impl.GetDataFromNetSingleton.RequestConstans.COLUMN_SEX;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ:
 */

public class GetDataFromNetSingleton implements IGetDataFromNet {


    // Todo OkHttp call need migrate to JichangProjectLibrary

    private static final String TAG = GetDataFromNetSingleton.class.getSimpleName();

    private static GetDataFromNetSingleton mInstance;

    static class RequestConstans {

        static final String COLUMN_DATE = "date";
        static final String COLUMN_TIME = "time";
        static final String COLUMN_CONTENT = "content";
        static final String COLUMN_PRIORITY = "priority";
        static final String COLUMN_DONE = "done";
        static final String COLUMN_NOTICE_TIME = "notice";
        static final String COLUMN_FAN = "fan";
        static final String COLUMN_IDOL = "idol";
        static final String COLUMN_OPEN = "open";
        static final String COLUMN_PHONE = "phone";
        static final String COLUMN_PASSWORD = "password";
        static final String COLUMN_SCHOOL = "school";
        static final String COLUMN_MAJOR = "major";
        static final String COLUMN_NAME = "name";
        static final String COLUMN_SEX = "sex";

    }

    private GetDataFromNetSingleton() {
    }

    public static GetDataFromNetSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new GetDataFromNetSingleton();
        }
        return mInstance;
    }

    @Override
    public void register(Register register, final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(register.getPhone());
        postData.add(register.getPassword());

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        key.add(COLUMN_PASSWORD);


        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "register/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void login(Register register, final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(register.getPhone());
        postData.add(register.getPassword());

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        key.add(COLUMN_PASSWORD);


        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "login/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void share(Share share, IYoucanDataResponse<String> response) {

    }

    @Override
    public void getUsers(final IYoucanDataResponse<String> response) {
        HttpUtils.get(ApiConstants.SERVER_ADDRESS + "getUsers/", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void getUser(String phone, final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "getUser/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void addUserDetail(String phone,
                              String school,
                              String major,
                              String name,
                              String sex,
                              final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);
        postData.add(school);
        postData.add(major);
        postData.add(name);
        postData.add(sex);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        key.add(COLUMN_SCHOOL);
        key.add(COLUMN_MAJOR);
        key.add(COLUMN_NAME);
        key.add(COLUMN_SEX);
        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "addUserDetail/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void addSubscribe(String phone, String idol, final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);
        postData.add(idol);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        key.add(COLUMN_IDOL);

        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "addSubscribe/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void isIdol(String phone, String idol, final IYoucanDataResponse<String> response) {

        LogUtils.i(TAG, phone + ":  " + idol);

        List<String> postData = new ArrayList<>();
        postData.add(phone);
        postData.add(idol);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        key.add(COLUMN_IDOL);

        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "isIdol/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void getIdols(String phone, final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);

        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "getIdols/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void getFans(String phone, final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);

        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "getFans/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void addContent(String phone, Content content, final IYoucanDataResponse<String> response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);
        postData.add(content.getDate());
        postData.add(content.getContent());

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        key.add(COLUMN_DATE);
        key.add(COLUMN_CONTENT);

        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "addContent/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void share(String phone, String date, String fan, final IYoucanDataResponse response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);
        postData.add(date);
        postData.add(fan);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);
        key.add(COLUMN_DATE);
        key.add(COLUMN_OPEN);

        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "share/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

    @Override
    public void discover(String phone, final IYoucanDataResponse response) {
        List<String> postData = new ArrayList<>();
        postData.add(phone);

        List<String> key = new ArrayList<>();
        key.add(COLUMN_PHONE);

        HttpUtils.post(ApiConstants.SERVER_ADDRESS + "discover/", key, postData, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                response.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response res) throws IOException {
                response.onSuccess(res.body().string());
            }
        });
    }

}
