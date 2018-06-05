package com.jichang.youcan.util;

import com.jichang.jichangprojectlibrary.BaseApplication;
import com.jichang.jichangprojectlibrary.util.HttpUtils;
import com.jichang.jichangprojectlibrary.util.SpUtils;
import com.jichang.youcan.constant.ApiConstants;
import com.jichang.youcan.constant.SpConstants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author jichang
 * @date 2018/1/22
 * email: 2218982471@qq.com
 * describ:
 */

public class ImageUtils {

    public static void loadImageBingPicture() {
        final String requestBingPic = ApiConstants.BING_PICTURE_URL;
        HttpUtils.get(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SpUtils.put(BaseApplication.getInstance(), SpConstants.BING_PICTURE_KEY, bingPic);
            }
        });
    }
}
