package com.jichang.youcan.util;

import com.jichang.jichangprojectlibrary.util.LogUtils;
import com.jichang.youcan.constant.Constants;
import com.jichang.youcan.data.bean.res.DiscoverInfo;
import com.jichang.youcan.db.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jichang
 * @date 2018/1/27
 * email: 2218982471@qq.com
 * describ:
 */

public class ConvertUtils {

    private static final String TAG = ConvertUtils.class.getSimpleName();

    public static List<User> convert(String data) {
        LogUtils.e(TAG, "convert data : " + data);
        List<User> users = new ArrayList<>();
        String[] da1 = data.split(Constants.DIVIDER);
        for (String d : da1) {
            if (d.contains(Constants.DIVIDER_PART)) {
                User user = new User();
                String[] p = d.split(Constants.DIVIDER_PART);
                if (p.length >= 5) {
                    user.setPhone(p[0]);
                    user.setSchool(p[1]);
                    user.setMajor(p[2]);
                    user.setName(p[3]);
                    user.setSex(p[4]);
                }
                users.add(user);
            }
        }
        return users;
    }

    public static List<DiscoverInfo> convertDiscover(String data) {
        List<DiscoverInfo> discoverInfos = new ArrayList<>();
        String[] da1 = data.split(Constants.DIVIDER);
        for (String d : da1) {
            if (d.contains(Constants.DIVIDER_1)) {
                DiscoverInfo discoverInfo = new DiscoverInfo();
                String[] p = d.split(Constants.DIVIDER_1);
                if (p.length >= 3) {
                    discoverInfo.setName(p[0] + "\n");
                    discoverInfo.setDate(p[1]);
                    String[] c = p[2].split(Constants.DIVIDER_PART);
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < c.length; i++) {
                        String b = c[i];
                        str.append(String.valueOf(i + 1));
                        str.append(": ");
                        str.append(b);
                        str.append("\n\n");
                    }
                    discoverInfo.setContent(str.toString());
                }
                discoverInfos.add(discoverInfo);
            }
        }
        return discoverInfos;
    }
}
