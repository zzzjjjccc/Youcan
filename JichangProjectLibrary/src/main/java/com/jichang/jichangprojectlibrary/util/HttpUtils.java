package com.jichang.jichangprojectlibrary.util;

import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author jichang     on 2018/1/18.
 *         email:   2218982471@qq.com
 *         describ：Just simple request for http
 */

public class HttpUtils {

    public static void get(String url, Callback callback) {
        /*if (NetworkStatusUtils.status(context) && context instanceof Activity) {
            request(url, callback);
        } else {
            // Todo Setting network dialog
            ToastUtils.showToast(context, "status : " + NetworkStatusUtils.status(context));
        }*/

        request(url, callback);
    }

    public static void post(String url, List<String> key,List<String> formData, Callback callback) {
        /*if (NetworkStatusUtils.status(context) && context instanceof Activity) {

        } else {
            // Todo Setting network dialog
            ToastUtils.showToast(context, "status : " + NetworkStatusUtils.status(context));
        }*/

        request(url, key, formData, callback);
    }

    private static void request(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    private static void request(String url, List<String> key, List<String> formData, Callback callback) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (int i = 0; i < key.size(); i++) {
                builder.add(key.get(i), formData.get(i));
            }
            RequestBody formBody = builder.build();
            Request req = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            new OkHttpClient().newCall(req).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
